## 基于JWT的登录与权限验证
1. JwtAuthenticationManager覆盖了ProviderManager, 在添加Filter时初始化providers
2. 实现JwtAuthenticationProvider 和 ResourcePermissionAuthenticationProvider
2. 向FilterChain中添加了过滤器MyUsernamePasswordAuthenticationFilter, JwtAuthenticationFilter, MyResourcePermissionFilter
3. JwtAuthenticationFilter从Authentication中获取JWT token并使用JwtAuthenticationProvider解析得到用户信息
4. MyResourcePermissionFilter通过ResourcePermissionAuthenticationProvider判断当前用户是否拥有权限
5. 实现了JsonLoginSuccessHandler, SimpleLoginAuthenticationFailureHandler, DefaultLogoutSuccessHandler

## pom.xml
1. nacos-discovery
2. nacos-config
3. spring-boot-starter-web
4. actuator // 监控
5. spring-boot-admin // 监控
6. security
7. oauth2-client
8. openfeign
9. webflux
10. jdbc
11. mysql
12. core
13. cache

