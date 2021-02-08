package com.ponking.pblog.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author peng
 * @date 2020/9/3--9:17
 * @Des
 **/
@Data
public class DashboardArticleCommentsVO {

    private List<Integer> expectedData;

    private List<Integer> actualData;

    private Integer nums;
}
