package com.runyuanj.authorization.filter;


import com.runyuanj.authorization.exception.LessPermissionException;
import com.runyuanj.authorization.handler.token.EmptyAuthenticationSuccessHandler;
import com.runyuanj.authorization.handler.token.SimpleAuthenticationFailureHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
public class MyResourcePermissionFilter extends OncePerRequestFilter {

    /**
     * 不会进入到successHandler.
     */
    private AuthenticationSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    private AuthenticationManager authenticationManager;

    private RequestMatcher requestMatcher;

    public MyResourcePermissionFilter(AuthenticationManager authenticationManager, RequestMatcher requestMatcher) {
        // 拦截所有不在白名单的请求
        this.requestMatcher = requestMatcher;
        this.authenticationManager = authenticationManager;
        this.successHandler = new EmptyAuthenticationSuccessHandler();
        this.failureHandler = new SimpleAuthenticationFailureHandler();
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
        Authentication authResult;
        try {
            // 缺少权限的抛出异常
            authResult = getAuthenticationManager().authenticate(SecurityContextHolder.getContext().getAuthentication());
            if (authResult == null) {
                throw new LessPermissionException("缺少权限");
            }
        } catch (AuthenticationException e) {
            log.info("MyResourcePermissionFilter.doFilterInternal cause {}, message: {}", e.getClass().getName(), e.getMessage());
            failureHandler.onAuthenticationFailure(request, response, e);
            return;
        } catch (Exception e) {
            log.error("MyResourcePermissionFilter.doFilterInternal cause {}, message: {}", e.getClass().getName(), e.getMessage());
            failureHandler.onAuthenticationFailure(request, response, new AuthenticationServiceException(e.getMessage()));
            return;
        }
        successHandler.onAuthenticationSuccess(request, response, filterChain, authResult);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }


    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requestMatcher.matches(request);
    }

}

