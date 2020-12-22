package com.runyuanj.authorization.filter;

import com.runyuanj.authorization.exception.AuthErrorType;
import com.runyuanj.authorization.token.JwtAuthenticationToken;
import com.runyuanj.common.exception.type.SystemErrorType;
import com.runyuanj.common.response.Result;
import com.runyuanj.core.token.JwtTokenComponent;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从用户请求头中获取认证token, 验证token是否有效, 如果有效, 添加到ThreadLocal中. 如果没有或无效, 不做处理
 * 该filter在权限控制模块之前, 不需要担心权限问题
 *
 * @author Administrator
 */
@Slf4j
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    private AuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
    private AuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    private AuthenticationManager authenticationManager;


    private RequestMatcher requiresAuthenticationRequestMatcher;

    public JwtAuthenticationFilter() {
        // 拦截所有不在白名单的请求
        // 拦截header中带Authorization的请求
        //拦截header中带Authorization的请求
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *  基于jwt的认证方式.
     *   验证token是否合法
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 如果请求头中不包括Authorization, 直接跳过
        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            //从头中获取token并封装后提交给AuthenticationManager
            String token = getJwtToken(request);
            Result result;
            if (StringUtils.isEmpty(token)) {
                log.error("Jwt Token is empty");
                result = Result.fail(AuthErrorType.EMPTY_TOKEN);
            } else {
                try {
                    // 解析成功, 代表验证通过
                    String json = jwtTokenComponent.parseTokenToJson(token);
                    JwtAuthenticationToken authToken = new JwtAuthenticationToken(json);
                    this.getAuthenticationManager().authenticate(authToken);
                } catch (ExpiredJwtException e1) {
                    result = Result.fail(AuthErrorType.EXPIRED_TOKEN);
                } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                    result = Result.fail(AuthErrorType.INVALID_TOKEN);
                } catch (Exception e) {
                    result = Result.fail(SystemErrorType.SYSTEM_ERROR);
                }
            }

        } catch (Exception e) {
            log.info("error token in header, Path: {}", request.getPathInfo());
        }

        filterChain.doFilter(request, response);
    }


    private String getJwtToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    /**
     * Sets the strategy used to handle a successful authentication. By default a
     * {@link SavedRequestAwareAuthenticationSuccessHandler} is used.
     */
    public void setAuthenticationSuccessHandler(
            AuthenticationSuccessHandler successHandler) {
        Assert.notNull(successHandler, "successHandler cannot be null");
        this.successHandler = successHandler;
    }

    public void setAuthenticationFailureHandler(
            AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "failureHandler cannot be null");
        this.failureHandler = failureHandler;
    }
}
