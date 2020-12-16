package com.runyuanj.authorization.config;

import com.runyuanj.authorization.config.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

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
    private AuthenticationSuccessHandler securityAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler securityAuthenticationFailureHandler;
    @Autowired
    private LogoutSuccessHandler securityLogoutSuccessHandler;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService securityUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不用security来管理
        web.ignoring().antMatchers("/login", "/logout", "/jwt");
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

        // Ant Pattern: spring里的url匹配算法 *: 0或多个字符  **: 匹配0级或多级路径  ?: 单个字符  {spring:[a-z]+}: 按照正则匹配[a-z]+，并且将其作为路径变量，变量名为"spring"
        http.antMatcher("/**")
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(securityProperties.getMatchers()).permitAll()
                        //.anyRequest().authenticated()
                        //.antMatchers("/login").hasRole("user")
                )
                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                //.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                // 关闭csrf
                .csrf().disable()
                .oauth2Login(o -> o.failureHandler((request, response, exception) -> {
                    request.getSession().setAttribute("error.message", exception.getMessage());
                    handler.onAuthenticationFailure(request, response, exception);
                }))
                // .addFilterBefore()
                // FormLoginConfigurer
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
                        //.failureHandler(securityAuthenticationFailureHandler)
                        )
                //.logout(logout -> logout.logoutSuccessUrl("/logout").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll())
                .userDetailsService(securityUserDetailsService)
        ;


        /*http.formLogin() //指定采用form方式登录
                // 登录页面
                .loginPage(securityProperties.getLoginPage())
                // 登录错误页面
                .failureUrl(securityProperties.getFailureUrl())
                // 拦截的登录处理URL
                .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                // 登录成功处理器
                .successHandler(securityAuthenticationSuccessHandler)
                // 登录失败处理器
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
                .logout(l -> l.logoutSuccessUrl("/").permitAll())//登出相关配置
                //.logoutUrl(securityProperties.getLogoutUrl())
                // 登出的url
                //.logoutSuccessUrl(securityProperties.getLoginPage())
                // 登出成功后跳转的url
                //.permitAll()
                .invalidateHttpSession(true)
                // 是session失效
                .deleteCookies(SESSION_NAME)
                // 登出成功的处理器
                .logoutSuccessHandler(securityLogoutSuccessHandler)
                .and()
                .authorizeRequests()//权限
                .antMatchers(securityProperties.getMatchers())
                // 不拦截这些请求
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .userDetailsService(securityUserDetailsService)
                // 指定userdetailsService
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .disable();//禁用跨域*/

        /*// @formatter:off
        http.antMatcher("/**")
                .authorizeRequests(a -> a
                        .antMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login(o -> o
                        .failureHandler((request, response, exception) -> {
                            request.getSession().setAttribute("error.message", exception.getMessage());
                            handler.onAuthenticationFailure(request, response, exception);
                        })
                );*/
    }
}