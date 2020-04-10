package com.ponking.pblog.common.util;

import com.ponking.pblog.model.entity.User;
import io.jsonwebtoken.*;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.UUID;

/**
 * @author Ponking
 * @ClassName JwtUtil
 * @date 2020/3/14--23:14
 **/
@Configuration
public class JwtUtil {
    /**
     * 私钥
     */
    private static String SECRET_KEY;

    /**
     * 过期时间
     */
    private static long EXPIRE_TIME;

    /**
     * 生成一个jwtToken
     * @param subject 用户名
     * @return json web token
     */
    public static String generateToken(String subject) {
        long nowMillis = System.currentTimeMillis();
        JwtBuilder jwt = Jwts.builder();
        // 设置token的唯一标识
        jwt.setId(UUID.randomUUID().toString());
        // 设置token的主体
        jwt.setSubject(subject);
        // 签发者
        jwt.setIssuer("ponking");
        // 签发时间
        jwt.setIssuedAt(new Date(nowMillis));
        // 设置有效时间
        Date expiration = new Date(nowMillis+ EXPIRE_TIME);
        jwt.setExpiration(expiration);
        // 私钥
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        // 签名
        jwt.signWith(SignatureAlgorithm.HS256, secretKeyBytes);
        return jwt.compact();
    }

    /**
     *
     * 解析JWT字符串
     * @param jwToken
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwToken) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwToken)
                .getBody();
    }



    @Value("${jwt.secret-key}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${jwt.expire-time}")
    public void setExpireTime(long expireTime) {
        EXPIRE_TIME = expireTime;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
//        String temp = generateToken("Test");
//        System.out.println(temp);
    }
}
