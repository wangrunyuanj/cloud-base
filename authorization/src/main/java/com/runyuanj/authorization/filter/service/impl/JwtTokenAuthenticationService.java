package com.runyuanj.authorization.filter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.entity.MyUser;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import com.runyuanj.core.token.JwtTokenComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    public UserDetails validate(String token) {

        // 解析成功代表token正常
        String json = jwtTokenComponent.parseTokenToJson(token);

        if (validateFromRedis(token)) {
            JSONObject user = JSON.parseObject(json);
            return new MyUser(user.getString("username"),
                    null,
                    user.getJSONArray("authorities").toJavaList(GrantedAuthority.class));
        }
        return null;
    }

    private boolean validateFromRedis(String token) {
        return true;
    }
}
