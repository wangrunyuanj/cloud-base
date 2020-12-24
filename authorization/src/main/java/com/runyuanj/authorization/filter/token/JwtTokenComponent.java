package com.runyuanj.authorization.filter.token;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class JwtTokenComponent {

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt_secret:runyuanj}")
    public String jwtSecret;

    public String generalToken(String id, String subject, long ttlMillis) {

        JwtBuilder builder = generate(ttlMillis)
                .setId(id)
                .setSubject(subject);
        return builder.compact();
    }

    public String generalToken(UserDetails userDetails, long ttlMillis) {

        Map<String, Object> map = new HashMap<>();
        map.put("username", userDetails.getUsername());

        JwtBuilder builder = generate(ttlMillis)
                .setId(userDetails.getUsername())
                .setSubject(JSON.toJSONString(map));

        return builder.compact();
    }

    public JwtBuilder generate(long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = this.generalKey();
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, key);

        if (ttlMillis >= 0L) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder;
    }

    public Claims parseToken(String jwt) {
        SecretKey key = this.generalKey();
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    public String parseTokenToJson(String jwt) {
        SecretKey key = this.generalKey();
        Jws<Claims> jwtParser = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
        if (jwtParser != null) {
            Claims claims = jwtParser.getBody();
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
        Map<String, Object> map = new HashMap<>();
        map.put("username", "admin");
        map.put("password", "12345678");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
        authorities.add(admin);
        map.put("authorities", authorities);
        String token = (new JwtTokenComponent()).generalToken("1", JSON.toJSONString(map), System.currentTimeMillis());
        Claims claims = (new JwtTokenComponent()).parseToken(token);
        System.out.println(token);
        System.out.println(claims.getSubject());
    }
}
