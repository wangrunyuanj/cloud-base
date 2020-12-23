package com.runyuanj.authorization.config;

import com.runyuanj.authorization.filter.JwtAuthenticationFilter;
import com.runyuanj.authorization.filter.ResourcePermissionFilter;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import com.runyuanj.authorization.filter.service.WhiteListFilterService;
import com.runyuanj.authorization.handler.SimpleLoginAuthenticationFailureHandler;
import com.runyuanj.authorization.handler.TokenAuthenticationSuccessHandler;
import com.runyuanj.authorization.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @author Administrator
 * @EnableGlobalMethodSecurity 为controller层方法添加权限控制:
 * @PreAuthorize("hasAuthority('course_teachplan_add')")
 */
@Configuration
@EnableWebSecurity
@Order(-1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private LogoutSuccessHandler securityLogoutSuccessHandler;
    @Autowired
    private WhiteListFilterService whiteListFilterService;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService securityUserDetailsService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private TokenAuthenticationSuccessHandler tokenAuthenticationSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不用security来管理
        web.ignoring().antMatchers(whiteListFilterService.getWhiteListPath());
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTION"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // configuration.addExposedHeader("cors");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //直接使用它默认的manager
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }*/

    /**
     * TODO:
     * 1. 修改为表单登陆
     * 2. 用户名和密码数据库配置
     * 3. 自定义登录页和错误页面
     * 4. 完成登出操作
     * 5. 记录登陆成功和失败
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        // 配置不同的url使用不同的过滤器链
        // Ant Pattern: spring里的url匹配算法 *: 0或多个字符  **: 匹配0级或多级路径  ?: 单个字符  {spring:[a-z]+}: 按照正则匹配[a-z]+，并且将其作为路径变量，变量名为"spring"
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        // 静态资源访问无需认证
                        .antMatchers("/image/**").permitAll()
                        .antMatchers("/*/admin/**").hasRole("ADMIN")
                        .antMatchers("/*/fin/**").hasRole("FIN")
                        // 匹配every的不需要认证
                        .antMatchers(securityProperties.getMatchers()).permitAll()
                        // 其他的请求都需要认证
                        .anyRequest().authenticated()
                )
                // 关闭csrf，因为不使用session
                .csrf().disable()
                .sessionManagement().disable()  //禁用session
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))

                // 配置oauth2登录失败处理方法
                .oauth2Login(o -> o.failureHandler((request, response, exception) -> {
                    /*response.setStatus(401); // 返回错误结果
                    response.getWriter().write(JSON.toJSONString(Result.fail(AuthErrorType.UNAUTHORIZED)).toCharArray());*/

                    // 重定向到失败页面
                    request.getSession().setAttribute("error.message", exception.getMessage());
                    handler.onAuthenticationFailure(request, response, exception);
                }))

                // .addFilterBefore()
                // 表单登录, 非json登录. FormLoginConfigurer
                .formLogin(formLogin -> formLogin
                        // 默认username
                        .usernameParameter("username")
                        // 默认password
                        .passwordParameter("password")// 登录页面
                        //.loginPage(securityProperties.getLoginPage())
                        // 登录错误页面
                        //.failureUrl(securityProperties.getFailureUrl())
                        // 拦截的登录处理URL
                        //.loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                        // 登录成功处理器
                        //.successHandler(securityAuthenticationSuccessHandler)
                        // 登录失败处理器
                        .failureHandler(new SimpleLoginAuthenticationFailureHandler()))

                //.logout(logout -> logout.logoutSuccessUrl("/logout").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .userDetailsService(securityUserDetailsService)
                // 在LogoutFilter类之前, 添加过滤器
                .addFilterBefore(new JwtAuthenticationFilter(), LogoutFilter.class)
                .addFilterAfter(new ResourcePermissionFilter(), BasicAuthenticationFilter.class)
        // 启动跨域支持
        // .cors(cors -> cors.addObjectPostProcessor(null));
        ;
    }
}