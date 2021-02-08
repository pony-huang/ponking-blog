package com.ponking.pblog.model.vo;

import lombok.Data;

/**
 * 主页信息
 *
 * @author Peng
 * @date 2020/9/3--9:05
 **/
@Data
public class DashboardVO {


    private DashboardArticleVisitsVO visits;

    private DashboardArticleCommentsVO comments;

    private DashboardArticleNumVO articleNum;
}
