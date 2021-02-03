package com.ponking.pblog.model.vo;

import lombok.Data;

import java.util.List;

/**
 * @author peng
 * @date 2020/9/3--9:16
 * @Des
 **/
@Data
public class DashboardArticleVisitsVo {

    private List<Integer> expectedData;

    private List<Integer> actualData;

    private Integer nums;
}
