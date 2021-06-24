#授权服务

## 基于JWT的登录与权限验证
1. JwtAuthenticationManager覆盖了ProviderManager, 在添加Filter时初始化providers
2. 实现JwtAuthenticationProvider 和 ResourcePermissionAuthenticationProvider
2. 向FilterChain中添加了过滤器MyUsernamePasswordAuthenticationFilter, JwtAuthenticationFilter, MyResourcePermissionFilter
3. JwtAuthenticationFilter从Authentication中获取JWT token并使用JwtAuthenticationProvider解析得到用户信息
4. MyResourcePermissionFilter通过ResourcePermissionAuthenticationProvider判断当前用户是否拥有权限
5. 实现了JsonLoginSuccessHandler, SimpleLoginAuthenticationFailureHandler, DefaultLogoutSuccessHandler

## pom.xml
1. security
2. oauth2-client
3. core
4. cache

## Stream
1. @Input(Filter) 更改过滤路径配置
2. @Input(OrgResources) Org更新resources
3. @Input(UserPermissionCache) Org更新用户权限, 清除用户权限缓存
