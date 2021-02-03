package com.ponking.pblog.shiro.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Ponking
 * @ClassName Md5Utils
 * @date 2020/3/15--11:05
 **/
public class Md5Utils {

    private static final String publicSalt = "7af4a47cb431d8f4";

    public static String encrypt(String credential, String salt) {
        ByteSource credentialsSalt01 = ByteSource.Util.bytes(salt);
        //加密方式
        String hashAlgorithmName = "MD5";
        //1024指的是加密的次数
        Object simpleHash = new SimpleHash(hashAlgorithmName, credential,
                credentialsSalt01, 1024);
        return simpleHash.toString();
    }

    public static String encrypt(String credential){
        ByteSource credentialsSalt01 = ByteSource.Util.bytes(publicSalt);
        //加密方式
        String hashAlgorithmName = "MD5";
        //1024指的是加密的次数
        Object simpleHash = new SimpleHash(hashAlgorithmName, credential,
                credentialsSalt01, 1024);
        return simpleHash.toString();
    }

    public static void main(String[] args) {
        System.out.println("加密后的值----->" + encrypt("123456","7af4a47cb431d8f4"));
    }
}
