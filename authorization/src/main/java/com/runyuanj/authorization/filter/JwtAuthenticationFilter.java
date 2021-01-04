package com.runyuanj.authorization.filter;

import com.runyuanj.authorization.filter.token.JwtAuthorization;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.exception.type.SystemErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 从用户请求头中获取认证token, 验证token是否有效, 如果有效, 添加到ThreadLocal中. 如果没有或无效, 不做处理
 * 该filter用来验证用户token, 在权限控制过滤器之前, 不需要担心权限问题.
 * 下一个权限过滤器根据资源权限和用户权限, 判断是否验证通过.
 *
 * @author Administrator
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;

    private RequestMatcher requiresAuthenticationRequestMatcher;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RequestMatcher requestMatcher) {
        // 拦截所有不在白名单的请求
        // 拦截header中带Authorization的请求
        // 拦截header中带Authorization的请求
        this.authenticationManager = authenticationManager;
        this.requiresAuthenticationRequestMatcher = requestMatcher;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     * 基于jwt的认证方式.
     * 验证token是否合法
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

        Result result;
        try {
            //从头中获取token并封装后提交给AuthenticationManager
            String token = getJwtToken(request);
            // token为空, 继续进行权限认证
            if (StringUtils.isEmpty(token)) {
                log.error("Jwt Token is empty");
                result = Result.fail(AuthErrorType.EMPTY_TOKEN);
            } else {
                // 如果抛出异常, 代表校验失败
                try {
                    // 此处不对token解析.
                    Authentication authToken = new JwtAuthorization(token, request, null, null);
                    // 验证token  JwtAuthenticationManager.authenticate() -> JwtAuthenticationProvider.authenticate()
                    Authentication authentication = this.getAuthenticationManager().authenticate(authToken);
                    // 将用户的认证信息存到ThreadLocal, 用来进行下一步的权限认证. 因此, authentication必须能够取出用户的唯一ID.
                    if (authentication != null) {
                        authentication.setAuthenticated(true);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        if (authentication.getDetails() == null) {
                            // TODO 判断是否需要更新token
                            result = Result.fail(AuthErrorType.EXPIRED_TOKEN);
                        } else {
                            result = Result.success();
                        }
                    } else {
                        result = Result.fail(AuthErrorType.INVALID_TOKEN, "authentication result is null");
                    }
                } catch (Exception e) {
                    result = Result.fail(AuthErrorType.SERVER_ERROR);
                }
            }
            // 当检验失败时不做处理, catch异常, 继续下一步权限校验
        } catch (Exception e) {
            log.error("请求头校验异常", e);
            result = new Result(SystemErrorType.SYSTEM_ERROR, "error filter in header, " + e.getMessage());
        }
        if (result.isFail()) {
            log.info("token验证失败, code: {}, message: {}, request path: {}", result.getCode(), result.getMessage(), request.getPathInfo());
        }
        // 下一步权限认证将从redis取出资源权限信息和用户权限信息, 进行比对校验.
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

}
