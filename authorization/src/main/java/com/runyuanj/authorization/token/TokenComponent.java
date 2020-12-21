package com.runyuanj.authorization.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenComponent {

    @Value("${jwt_secret:runyuanj}")
    public String jwtSecret;

    public String generalToken(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = this.generalKey();
        JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0L) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        return builder.compact();
    }

    public Claims parseToken(String jwt) {
        SecretKey key = this.generalKey();
        Claims claims = (Claims)Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
        return claims;
    }

    public String parseTokenToJson(String jwt) {
        SecretKey key = this.generalKey();
        Jws<Claims> jwtParser = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        if (jwtParser != null) {
            Claims claims = (Claims)jwtParser.getBody();
            return claims.getSubject();
        } else {
            return "";
        }
    }

    private SecretKey generalKey() {
        if (this.jwtSecret == null || this.jwtSecret.trim().length() <= 0) {
            this.jwtSecret = "runyuanj";
        }

        byte[] encodedKey = Base64.decodeBase64(this.jwtSecret);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    public static void main(String[] args) throws Exception {
        String token = (new TokenComponent()).generalToken("1", "zhangsan", System.currentTimeMillis());
        Claims claims = (new TokenComponent()).parseToken(token);
        System.out.println(token);
        System.out.println(claims.getSubject());
    }
}
