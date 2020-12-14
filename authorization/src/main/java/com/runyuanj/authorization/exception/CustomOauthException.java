package com.runyuanj.authorization.exception;

import com.runyuanj.common.response.Result;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Getter
public class CustomOauthException extends OAuth2AuthorizationException {

    private final Result result;

    public CustomOauthException(OAuth2AuthorizationException oAuth2Exception) {
        super(oAuth2Exception.getError(), oAuth2Exception);
        this.result = new Result(oAuth2Exception.getError().getErrorCode(), oAuth2Exception.getMessage(), null);
    }
}