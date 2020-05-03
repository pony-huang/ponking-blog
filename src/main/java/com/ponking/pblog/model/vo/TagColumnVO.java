package com.ponking.pblog.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Ponking
 * @ClassName TagColumnVO
 * @date 2020/4/8--11:49
 **/
@Data
public class TagColumnVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Integer count;
}
