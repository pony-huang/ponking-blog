package com.ponking.pblog.model.vo;

import lombok.Data;

/**
 * @author Ponking
 * @ClassName PersonInfoVO
 * @date 2020/4/8--16:41
 * @Des 博客个人信息
 **/
@Data
public class PersonInfoVO {
    private String name;
    private String city;
    private String avatar;
    private String title;
    private Integer articleCount;
    private Integer tagCount;
    private Integer categoryCount;

    public PersonInfoVO(String name, String city, String avatar, String title, Integer articleCount, Integer tagCount, Integer categoryCount) {
        this.name = name;
        this.city = city;
        this.avatar = avatar;
        this.title = title;
        this.articleCount = articleCount;
        this.tagCount = tagCount;
        this.categoryCount = categoryCount;
    }
}
