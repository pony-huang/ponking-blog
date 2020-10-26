package com.ponking.pblog.other;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;

/**
 * @author peng
 * @date 2020/10/23--22:12
 * @Des
 **/
public class Main {
    public static void main(String[] args) {
        User user = new User("rose", 123, "beijing");
        String[] excludeProperties = {"country", "address"};
        String[] includeProperties = {"id", "username", "mobile"};
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        PropertyPreFilters.MySimplePropertyPreFilter includefilter = filters.addFilter();
        includefilter.addIncludes(includeProperties);
        String json = JSON.toJSONString(user, excludefilter, SerializerFeature.PrettyFormat);
        System.out.println(json);
    }
}
