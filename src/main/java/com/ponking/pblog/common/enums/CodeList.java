package com.ponking.pblog.common.enums;

import java.util.Map;

/**
 * @author peng
 * @date 2020/9/18--20:37
 * @Des
 **/
public interface CodeList {

    Map<String, String> getMap(String bizType);

    Map<String, String> toMap();
}