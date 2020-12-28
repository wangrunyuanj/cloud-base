package com.runyuanj.authorization.filter;

import com.runyuanj.authorization.handler.DoNothingAuthenticationSuccessHandler;
import com.runyuanj.authorization.handler.SimpleAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截请求判断是否对路径拥有权限
 *
 * @author runyu
 */
@Slf4j
public class ResourcePermissionFilter extends OncePerRequestFilter {

    /**
     * 不会进入到successHandler.
     */
    private AuthenticationSuccessHandler successHandler = new DoNothingAuthenticationSuccessHandler();

    private AuthenticationFailureHandler failureHandler = new SimpleAuthenticationFailureHandler();

    private AuthenticationManager authenticationManager;

    private RequestMatcher requiresAuthenticationRequestMatcher;

    public ResourcePermissionFilter(AuthenticationManager authenticationManager) {
        // 拦截所有不在白名单的请求
        this.authenticationManager = authenticationManager;
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }


    /**
     * 所有经过该过滤器的请求都应该被处理
     * 验证不通过则转到失败处理流程
     * <p>
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!requiresAuthentication(request, response)) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication result;
        try {
            // 缺少权限的抛出异常
            result = getAuthenticationManager().authenticate(SecurityContextHolder.getContext().getAuthentication());
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
            return;
        }
        successHandler.onAuthenticationSuccess(request, response, filterChain, result);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

    public void setSuccessHandler(AuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

}
