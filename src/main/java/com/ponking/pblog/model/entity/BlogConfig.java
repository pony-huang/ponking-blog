package com.ponking.pblog.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 博客配置表
 * </p>
 *
 * @author peng
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_config")
public class BlogConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数值
     */
    private String value;

    /**
     * 参数类型 1：全局变量 2：系统配置
     */
    private Integer type;

    /**
     * 描述
     */
    private String description;


}
