package com.ponking.pblog.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peng
 * @date 2020/10/23--22:11
 * @Des
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;

    private int age;

    @JsonIgnore
    private String address;
}
