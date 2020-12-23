package com.runyuanj.authorization.filter;

import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.token.JwtAuthenticationToken;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.exception.type.SystemErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 从用户请求头中获取认证token, 验证token是否有效, 如果有效, 添加到ThreadLocal中. 如果没有或无效, 不做处理
 * 该filter用来验证用户token, 在权限控制过滤器之前, 不需要担心权限问题.
 * 下一个权限过滤器根据资源权限和用户权限, 判断是否验证通过.
 *
 * @author Administrator
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationSuccessHandler successHandler;

    /**
     * 无论验证是否成功, 都不会进入到failureHandler.
     */
    private AuthenticationFailureHandler failureHandler;

    private List<AuthenticationProvider> providers = new ArrayList<>();

    private AuthenticationManager authenticationManager;

    private RequestMatcher requiresAuthenticationRequestMatcher;

    public JwtAuthenticationFilter() {
        // 拦截所有不在白名单的请求
        // 拦截header中带Authorization的请求
        //拦截header中带Authorization的请求
        // providers.add(new JwtAuthenticationProvider());
        this.authenticationManager = new JwtAuthenticationManager(providers);
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
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

        try {
            //从头中获取token并封装后提交给AuthenticationManager
            String token = getJwtToken(request);
            Result result;
            // token为空, 继续进行权限认证
            if (StringUtils.isEmpty(token)) {
                log.error("Jwt Token is empty");
                result = Result.fail(AuthErrorType.EMPTY_TOKEN);
            } else {
                // 如果抛出异常, 代表校验失败
                try {
                    // 此处不对token解析.
                    Authentication authToken = new JwtAuthenticationToken(token, request);
                    // 验证token  JwtAuthenticationManager.authenticate() -> JwtAuthenticationProvider.authenticate()
                    Authentication authentication = this.getAuthenticationManager().authenticate(authToken);
                    // 将用户的认证信息存到ThreadLocal, 用来进行下一步的权限认证. 因此, authentication必须能够取出用户的唯一ID.
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    result = Result.success();
                } catch (Exception e) {
                    result = Result.fail(SystemErrorType.SYSTEM_ERROR);
                }
            }
            if (result.isFail()) {
                log.info("token验证失败, code: {}, message: {}, request path: {}", result.getCode(), result.getData(), request.getPathInfo());
            }
            // 当检验失败时不做处理, catch异常, 继续下一步权限校验
        } catch (Exception e) {
            log.info("error filter in header, request path: {}", request.getPathInfo(), e);
        }
        // 下一步权限认证将从redis取出资源权限信息和用户权限信息, 进行比对校验.
        filterChain.doFilter(request, response);
    }

    /**
     * Calls the {@code initFilterBean()} method that might
     * contain custom initialization of a subclass.
     * <p>Only relevant in case of initialization as bean, where the
     * standard {@code init(FilterConfig)} method won't be called.
     *
     * @see #initFilterBean()
     * @see #init(FilterConfig)
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        if (providers.isEmpty()) {
            throw new RuntimeException("providers 不能为空");
        }
    }

    public void addProviders(AuthenticationProvider provider) {
        this.providers.add(provider);
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


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }
}
