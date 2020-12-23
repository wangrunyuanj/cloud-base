package com.runyuanj.authorization.exception;

import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Slf4j
public class CustomOauthException extends OAuth2AuthorizationException {

    private final Result result;

    public CustomOauthException(OAuth2AuthorizationException oAuth2Exception) {
        super(oAuth2Exception.getError(), oAuth2Exception);
        log.error("oauth2 登录失败", oAuth2Exception);
        this.result = new Result(AuthErrorType.AUTHORIZATION_FAILED, oAuth2Exception.getMessage(), oAuth2Exception.getError());
    }
}