package com.ponking.pblog.model.vo;

import lombok.Data;

/**
 * @author Ponking
 * @ClassName CategoryColumnVO
 * @date 2020/4/8--10:38
 **/
@Data
public class CategoryColumnVO {
    private Integer id;
    private String name;
    private Integer count;
    private String path;
}
