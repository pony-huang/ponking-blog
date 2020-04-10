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
    * 链接表
    * </p>
*
* @author peng
* @since 2020-04-08
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @TableName("blog_link")
    public class Link implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            /**
            * 链接名称
            */
    private String name;

            /**
            * 链接地址
            */
    private String url;

            /**
            * 链接类型 1：友情链接 2：个人链接
            */
    private Integer type;


}
