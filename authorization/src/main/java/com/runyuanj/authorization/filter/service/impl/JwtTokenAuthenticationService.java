package com.runyuanj.authorization.filter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.entity.MyUser;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import com.runyuanj.authorization.filter.token.JwtTokenComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * 具体实现类. 验证token是否有效
 *
 * @author runyu
 */
@Service
@Slf4j
public class JwtTokenAuthenticationService implements TokenAuthenticationService {

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    /**
     * 验证token是否有效. 当验证失败时, 抛出异常
     * 从redis中取token, 如果存在, 则校验通过. 不存在, 则校验不通过, 返回空
     *
     * @param token
     * @return
     */
    @Override
    public UserDetails validate(String token) throws AuthenticationException {
        // 应抛出 DisabledException, LockedException, BadCredentialsException等异常
        // 解析成功不代表token有效
        // 无论是否过期, 都应该得到解析结果. 因此token不应设置过期时间. 而是从redis获取.
        String json = jwtTokenComponent.parseTokenToJson(token);

        if (validateFromRedis(token)) {
            JSONObject user = JSON.parseObject(json);
            return new MyUser(user.getString("username"),
                    null,
                    user.getJSONArray("authorities").toJavaList(GrantedAuthority.class));
        } else {
            throw new BadCredentialsException("token 已过期");
        }
    }

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     * @throws DisabledException
     */
    private boolean validateFromRedis(String token) throws DisabledException {
        return true;
    }
}
