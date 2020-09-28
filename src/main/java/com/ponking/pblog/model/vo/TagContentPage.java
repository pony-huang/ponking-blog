package com.ponking.pblog.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ponking.pblog.model.entity.Article;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ponking
 * @ClassName TagContentPage
 * @date 2020/4/24--21:15
 **/
@Data
public class TagContentPage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 标签名
     */
    private String name;


    private List<Article> articles;
}
