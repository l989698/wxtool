package com.tool.wxtoolproject.api.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tool.wxtoolproject.api.user.entity.User;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.UUID;

public abstract class BaseController {
    /**
     * 生成token
     *
     * @param user
     * @return String
     */
    @SneakyThrows
    protected static String getToken(User user) {
        String token = "";
        token = JWT.create()
                .withKeyId(user.getId())
                .withIssuer("liuhy") //发布者
                .withIssuedAt(new Date())
                .withJWTId(UUID.randomUUID().toString()) //编号
                .withClaim("session_key", user.getSessionKey())
                .withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
