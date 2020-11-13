package com.ponking.pblog.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.model.entity.User;
import io.jsonwebtoken.*;
import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
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
     * # AES密码加密私钥(Base64加密)
     * encryptAESKey=V2FuZzkyNjQ1NGRTQkFQSUpXVA==
     * # JWT认证加密私钥(Base64加密)
     * encryptJWTKey=U0JBUElKV1RkV2FuZzkyNjQ1NA==
     * # AccessToken过期时间-5分钟-5*60(秒为单位)
     * accessTokenExpireTime=
     * # RefreshToken过期时间-30分钟-30*60(秒为单位)
     * refreshTokenExpireTime=1800
     */

    /**
     * 私钥
     */
    private static String SECRET_KEY = "V2FuZzkyNjQ1NGRTQkFQSUpXVA==";

    /**
     * 过期时间
     */
    private static long ACCESS_TOKEN_EXPIRE_TIME = 300L;


    /**
     * 获取用户账号
     * @param token
     * @return
     */
    public static String getAccount(String token){
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(AuthConstants.ACCOUNT).asString();
        } catch (JWTDecodeException e) {
            throw new GlobalException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }


    /**
     * 校验token是否正确
     * @param token Token
     * @return boolean 是否正确
     * @author Wang926454
     * @date 2018/8/31 9:05
     */
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, AuthConstants.ACCOUNT) + Base64ConvertUtil.decode(SECRET_KEY);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     * @param token
     * @param claim
     * @return java.lang.String
     * @author Wang926454
     * @date 2018/9/7 16:54
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            String value = jwt.getClaim(claim).asString();
            return value;
        } catch (JWTDecodeException e) {
            throw new GlobalException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 生成签名
     * @param account 帐号
     * @return java.lang.String 返回加密的Token
     * @author Wang926454
     * @date 2018/8/31 9:07
     */
    public static String sign(String account, String currentTimeMillis) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtil.decode(SECRET_KEY);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim(AuthConstants.ACCOUNT, account)
                    .withClaim(AuthConstants.CURRENT_TIME_MILLIS, currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            throw new GlobalException("JWTToken加密出现异常:" + e.getMessage());
        }
    }

//
//    /**
//     * 生成一个jwtToken
//     * @param subject 用户名
//     * @return json web token
//     */
//    public static String generateToken(String subject,long nowMillis) {
//        JwtBuilder jwt = Jwts.builder();
//        // 设置token的唯一标识
//        jwt.setId(UUID.randomUUID().toString());
//        // 设置token的主体
//        jwt.setSubject(subject);
//        // 签发者
//        jwt.setIssuer("ponking");
//        // 签发时间
//        jwt.setIssuedAt(new Date(nowMillis));
//        // 设置有效时间
//        Date expiration = new Date(nowMillis+ EXPIRE_TIME);
//        jwt.setExpiration(expiration);
//        // 私钥
//        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
//        // 签名
//        jwt.signWith(SignatureAlgorithm.HS256, secretKeyBytes);
//         /*	iss: jwt签发者
//                sub: jwt所面向的用户
//                aud: 接收jwt的一方
//                exp: jwt的过期时间，这个过期时间必须要大于签发时间
//                nbf: 定义在什么时间之前，该jwt都是不可用的.
//                iat: jwt的签发时间
//                jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
//            */
//
//        return jwt.compact();
//    }
//
//    /**
//     *
//     * 解析JWT字符串
//     * @param jwToken
//     * @return
//     * @throws Exception
//     */
//    public static Claims parseJWT(String jwToken) {
//        return Jwts.parser()
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .parseClaimsJws(jwToken)
//                .getBody();
//    }



    @Value("${jwt.encryptAESKey}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

    @Value("${jwt.accessTokenExpireTime}")
    public void setExpireTime(long expireTime) {
        ACCESS_TOKEN_EXPIRE_TIME = expireTime;
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        String token = sign("admin", currentTimeMillis + "");
        System.out.println(token);
        System.out.println(verify(token));
    }
}
