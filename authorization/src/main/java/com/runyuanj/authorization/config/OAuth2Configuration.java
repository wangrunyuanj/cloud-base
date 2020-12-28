package com.runyuanj.authorization.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

/**
 *
 * @author Administrator
 */
@Configuration
@Slf4j
@ComponentScan(basePackages = { "com.runyuanj.authorization.**"})
public class OAuth2Configuration {

    @Bean
    public WebClient webClient(ClientRegistrationRepository clients, OAuth2AuthorizedClientRepository authz) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServletOAuth2AuthorizedClientExchangeFilterFunction(clients, authz);
        return WebClient.builder().filter(oauth2).build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient webClient) {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return request -> {
            OAuth2User user = delegate.loadUser(request);
            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
                return user;
            }

            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient
                    (request.getClientRegistration(), user.getName(), request.getAccessToken());
            String url = user.getAttribute("organizations_url");
            List<Map<String, Object>> orgs = webClient.get().uri(url)
                    .attributes(oauth2AuthorizedClient(client))
                    // 执行Http请求并解析body
                    .retrieve()
                    // 提取body转成Mono
                    .bodyToMono(List.class)
                    // 在收到下一个信号前一直阻塞
                    .block();

            if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
                return user;
            }

            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "无效token", ""));
        };
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(githubClientRegistration());
    }

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    private ClientRegistration githubClientRegistration() {
        return ClientRegistration.withRegistrationId("github")  // (1)
                .clientId(clientId)  // (2)
                .clientSecret(clientSecret)  // (3)
                .clientAuthenticationMethod(ClientAuthenticationMethod.POST)  // (4)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")  // (5)
                .clientName("AAA")       // (6)
                .tokenUri("http://your.provider.com/oauth/token")  // (7)
                .authorizationUri("http://your.provider.com/oauth/authorize")  // (8)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)  // (9)
                .scope("api")  // (10)
                .userNameAttributeName("username")  // (11)
                .userInfoUri("http://your.provider.com/api/v3/user")  // (12)
                .jwkSetUri("")  // (13)
                .build();
    }
}
