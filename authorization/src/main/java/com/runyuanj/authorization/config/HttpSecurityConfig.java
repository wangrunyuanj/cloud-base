package com.runyuanj.authorization.config;

import com.runyuanj.authorization.filter.MyResourcePermissionFilter;
import com.runyuanj.authorization.access.MySecurityMetadataSource;
import com.runyuanj.authorization.access.RoleBasedVoter;
import com.runyuanj.authorization.filter.JwtAuthenticationFilter;
import com.runyuanj.authorization.filter.MyUsernamePasswordAuthenticationFilter;
import com.runyuanj.authorization.filter.builder.JwtAuthenticateFilterBuilder;
import com.runyuanj.authorization.filter.builder.ResourcePermissionFilterBuilder;
import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import com.runyuanj.authorization.filter.service.WhiteListFilterService;
import com.runyuanj.authorization.filter.service.impl.JwtTokenAuthenticationService;
import com.runyuanj.authorization.filter.token.JwtTokenComponent;
import com.runyuanj.authorization.handler.*;
import com.runyuanj.authorization.properties.SecurityProperties;
import com.runyuanj.authorization.service.ResourcePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2B;

/**
 * @author Administrator
 * @EnableGlobalMethodSecurity 为controller层方法添加权限控制:
 * @PreAuthorize("hasAuthority('course_teachplan_add')")
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackages = {"com.runyuanj.authorization.**"})
public class HttpSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private WhiteListFilterService whiteListFilterService;

    @Qualifier("userDetailsService")
    @Autowired
    private UserDetailsService securityUserDetailsService;

    @Autowired
    private ResourcePermissionAuthenticationService resourcePermissionAuthenticationService;
    @Autowired
    private JwtTokenAuthenticationService jwtTokenAuthenticationService;
    @Autowired
    private JwtTokenComponent jwtTokenComponent;
    @Autowired
    private ResourcePermissionService rService;


    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder($2B, 4, new SecureRandom());
        String encode = passwordEncoder.encode("12345678");
        System.out.println(encode);
    }

    /**
     * 添加了一个过滤器链.
     * this.delegate = [{options}]
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 不用security来管理
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        // web.addSecurityFilterChainBuilder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder($2B, 4, new SecureRandom());
    }

/*    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //直接使用它默认的manager
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }*/
   /* @Bean
    public MyResourcePermissionFilter myResourcePermissionFilter() {
        return new MyResourcePermissionFilter(resourcePermissionAuthenticationService);
    }*/

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

    /**
     * 登录判断: AuthenticationManager
     * 权限判断: UnanimousBased. 当所有vote执行完未抛出异常, 且验证通过的数量大于0时, 则拥有权限.
     *
     *
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // super.setObjectPostProcessor(null);

        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        RequestHeaderRequestMatcher authorizationHeaderMatcher = new RequestHeaderRequestMatcher("Authorization");

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticateFilterBuilder()
                .authenticationProvider(jwtTokenAuthenticationService)
                .requestMatcher(authorizationHeaderMatcher)
                .build();

        // 默认的authenticationManager
        AuthenticationManager defaultManager = authenticationManager();

        MyUsernamePasswordAuthenticationFilter loginFilter = new MyUsernamePasswordAuthenticationFilter(defaultManager);
        loginFilter.setAuthenticationSuccessHandler(new JsonLoginSuccessHandler(jwtTokenComponent));
        loginFilter.setAuthenticationFailureHandler(new JsonLoginFailureHandler());

        // providerManager.getProviders().add();

        // 配置不同的url使用不同的过滤器链
        // 配置AuthenticateManager管理的路径
        // Ant Pattern: spring里的url匹配算法 *: 0或多个字符  **: 匹配0级或多级路径  ?: 单个字符  {spring:[a-z]+}: 按照正则匹配[a-z]+，并且将其作为路径变量，变量名为"spring"
        http
                // 只有login 和 logout需要过滤, 其他直接通过.
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        // .withObjectPostProcessor(getfilterSecurityInterceptor())
                        // UrlMapping
                        // 静态资源访问无需认证
                        .antMatchers("/image/**").permitAll()
                        .antMatchers("/*/admin/**").hasRole("ADMIN")
                        .antMatchers("/*/fin/**").hasRole("FIN")
                        // 匹配every的不需要认证
                        .antMatchers(securityProperties.getMatchers()).permitAll()
                        .accessDecisionManager(accessDecisionManager())
                        // 此处通过. 见AuthenticatedVoter.
                        .anyRequest().permitAll()
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
                        .passwordParameter("password")
                        //.loginPage(securityProperties.getLoginPage())
                        // 登录错误页面
                        //.failureUrl(securityProperties.getFailureUrl())
                        // 拦截的登录处理URL
                        //.loginProcessingUrl(securityProperties.getLoginProcessingUrl())
                        // 登录成功处理器
                        .successHandler(new JsonLoginSuccessHandler(jwtTokenComponent))
                        // 登录失败处理器
                        .failureHandler(new JsonLoginFailureHandler()))

                //.logout(logout -> logout.logoutSuccessUrl("/logout").permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/").permitAll().logoutSuccessHandler(new DefaultLogoutSuccessHandler()))
                .userDetailsService(securityUserDetailsService)
                // 在LogoutFilter类之前, 添加过滤器
                .addFilterBefore(loginFilter, LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                // 使用权限验证. 权限认证不该在这里配置. 应该在最后的filter中配置
                // .addFilterAfter(resourcePermissionFilter, BasicAuthenticationFilter.class)
        // 启动跨域支持
        // .cors(cors -> cors.addObjectPostProcessor(null));
        ;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new WebExpressionVoter(),
                // new RoleVoter(),
                new RoleBasedVoter(),
                new AuthenticatedVoter());
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public MySecurityMetadataSource mySecurityMetadataSource() {
        return new MySecurityMetadataSource(rService);
    }
}