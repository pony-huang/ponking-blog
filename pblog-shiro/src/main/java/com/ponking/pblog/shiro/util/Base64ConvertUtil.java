package com.ponking.pblog.shiro.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具
 * @author peng
 * @date 2020/11/12--19:13
 * @Des
 **/
public class Base64ConvertUtil {

    private Base64ConvertUtil(){

    }

    /**
     * 加密JDK1.8
     * @param str
     * @return java.lang.String
     */
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes(StandardCharsets.UTF_8));
        return new String(encodeBytes);
    }

    /**
     * 解密JDK1.8
     * @param str
     * @return java.lang.String
     */
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
        return new String(decodeBytes);
    }
}
