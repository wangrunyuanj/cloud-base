package com.runyuanj.authorization.handler;

import com.alibaba.fastjson.JSON;
import com.runyuanj.authorization.filter.token.JwtTokenComponent;
import com.runyuanj.authorization.utils.ResponseUtils;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 * @author runyu
 */
@Slf4j
public class JsonLoginSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenComponent jwtTokenComponent;

    public JsonLoginSuccessHandler(JwtTokenComponent jwtTokenComponent) {
        this.jwtTokenComponent = jwtTokenComponent;
    }

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // String token = (String) authentication.getCredentials();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("userDetails: {}", JSON.toJSONString(userDetails));
        String token = jwtTokenComponent.generalToken(userDetails, 60 * 60 * 24 * 30);
        ResponseUtils.writeResponseJson(response, 200, Result.success(token));
    }
}
