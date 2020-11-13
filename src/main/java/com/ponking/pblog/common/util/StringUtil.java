package com.ponking.pblog.common.util;

import org.bouncycastle.util.Strings;

/**
 * 字符串工具类
 *
 * @author peng
 * @date 2020/10/28--16:10
 * @Des
 **/
public class StringUtil {

    private StringUtil() {

    }

    /**
     * example: MAX_VALUE -> maxValue
     * 驼峰命名
     *
     * @param source
     * @return
     */
    public static String toLowerCamel(String source) {
        StringBuffer buffer = new StringBuffer(source);
        // 首字母
        char first = getLowAlpha(buffer.charAt(0));
        buffer.setCharAt(0, first);
        for (int i = buffer.length() - 1; i >= 1; i--) {
            if (buffer.charAt(i) == '_' && i + 1 < buffer.length()) {
                char upperAlpha = getUpperAlpha(buffer.charAt(i + 1));
                buffer.setCharAt(i + 1, upperAlpha);
                buffer.deleteCharAt(i);
            } else {
                char lowAlpha = getLowAlpha(buffer.charAt(i));
                buffer.setCharAt(i, lowAlpha);
            }
        }
        return buffer.toString();
    }

    private static char getLowAlpha(char first) {
        if (first >= 'A' && first <= 'Z') {
            first = (char) (first - 'A' + 'a');
        }
        return first;
    }

    private static char getUpperAlpha(char first) {
        if (first >= 'a' && first <= 'z') {
            first = (char) (first - 'a' + 'A');
        }
        return first;
    }

}