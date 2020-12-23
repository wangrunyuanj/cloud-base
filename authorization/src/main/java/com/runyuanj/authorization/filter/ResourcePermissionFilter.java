package com.runyuanj.authorization.filter;

import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.manager.ResourcePermissionAuthenticationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
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
import java.util.ArrayList;
import java.util.List;

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
    private AuthenticationSuccessHandler successHandler;

    private AuthenticationFailureHandler failureHandler;

    private List<AuthenticationProvider> providers = new ArrayList<>();

    private AuthenticationManager authenticationManager;

    private RequestMatcher requiresAuthenticationRequestMatcher;

    public ResourcePermissionFilter() {
        // 拦截所有不在白名单的请求
        this.authenticationManager = new ResourcePermissionAuthenticationManager(providers);
        this.requiresAuthenticationRequestMatcher = new RequestHeaderRequestMatcher("Authorization");
    }


    /**
     * 所有经过该过滤器的请求都应该被处理
     * 验证不通过则转到失败处理流程
     *
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
        try {
            // 缺少权限的抛出异常
            getAuthenticationManager().authenticate(SecurityContextHolder.getContext().getAuthentication());
        } catch (AuthenticationException e) {
            failureHandler.onAuthenticationFailure(request, response, e);
        }
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setFailureHandler(AuthenticationFailureHandler failureHandler) {
        this.failureHandler = failureHandler;
    }

}
