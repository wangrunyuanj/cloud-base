package com.runyuanj.authorization.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class DefaultJwtTokenService implements JwtTokenService {

    @Override
    public String getUserJwt(UserDetails userDetails) {
        return "default jwt token service";
    }
}
