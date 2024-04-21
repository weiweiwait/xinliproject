package com.qing.admin.system.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.qing.admin.system.entity.ConditionException;

import java.util.Calendar;
import java.util.Date;

public class TokenUtil {

    public static final String ISSUER = "Qing"; //签发者

    public static String generateToken(Integer userId) {
        Algorithm algorithm;
        try {
             algorithm = Algorithm.none();
        } catch (Exception e) {
            throw new ConditionException("token生成错误！");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 180);
        return JWT.create()
                .withKeyId(String.valueOf(userId))
                .withIssuer(ISSUER)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.none());
    }

    public static Integer verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.none();
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String userId = decodedJWT.getKeyId();
            return Integer.valueOf(userId);
        } catch (TokenExpiredException e) {
            throw new ConditionException("555", "token过期");
        } catch (Exception e) {
            throw new ConditionException("非法用户token！");
        }
    }
}
