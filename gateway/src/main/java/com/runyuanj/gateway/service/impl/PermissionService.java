package com.runyuanj.gateway.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.common.response.Result;
import com.runyuanj.gateway.service.IPermissionService;
import com.runyuanj.gateway.service.ServiceFeign;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@Slf4j
public class PermissionService implements IPermissionService {

    /**
     * Authorization认证开头是"bearer "
     */
    private static final String BEARER = "Bearer ";
    @Resource
    private ServiceFeign serviceFeign;
    /**
     * jwt filter 密钥，主要用于token解析，签名验证
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    /**
     * 不需要网关签权的url配置(/oauth,/open)
     * 默认/oauth开头是不需要的
     */
    @Value("${gate.ignore.authentication.startWith:/oauth}")
    private String ignoreUrls;

    /**
     * 校验权限, 在本地缓存权限数据.
     * TODO 当用户权限或资源角色权限发生变更时, 需要确保及时清除缓存
     *
     * @param authentication
     * @param url
     * @param method
     * @return
     */
    @Override
    public boolean permission(String authentication, String url, String method) {

        // 如果请求未携带token信息, 直接拒绝
        if (StringUtils.isBlank(authentication) || !authentication.startsWith(BEARER)) {
            log.error("user filter is null");
            return Boolean.FALSE;
        }

        //token是否有效，在网关进行校验，无效/过期等
        if (invalidJwtAccessToken(authentication)) {
            return Boolean.FALSE;
        }

        return sendRequestToAuth(authentication, url, method);
    }

    public boolean invalidJwtAccessToken(String authentication) {
        // 是否无效true表示无效
        boolean invalid = Boolean.TRUE;
        try {
            getJwt(authentication);
            invalid = Boolean.FALSE;
        } catch (SignatureException | ExpiredJwtException | MalformedJwtException ex) {
            log.error("user filter error :{}", ex.getMessage());
        }
        return invalid;
    }

    public Jws<Claims> getJwt(String jwtToken) {
        if (jwtToken.startsWith(BEARER)) {
            jwtToken = StringUtils.substring(jwtToken, BEARER.length());
        }
        return Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(signingKey.getBytes()) //设置签名的秘钥
                .parseClaimsJws(jwtToken);
    }

    /**
     * 是否忽略该url
     *
     * @param url
     * @return
     */
    @Override
    public boolean ignoreAuthentication(String url) {
        return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    /**
     * 向authentication服务发送验证请求
     *
     * @param authentication
     * @param url
     * @param method
     * @return
     */
    private boolean sendRequestToAuth(String authentication, String url, String method) {
        JSONObject jsonObject = serviceFeign.hasPermission(authentication, url, method);
        if (Result.isJsonSuccess(jsonObject)) {
            Boolean data = jsonObject.getBoolean("data");
            if (data != null) {
                return data;
            }
        }
        return false;
    }
}
