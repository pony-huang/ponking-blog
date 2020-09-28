package com.ponking.pblog.model.vo;

import lombok.Data;

/**
 * 主页信息
 * @author Peng
 * @date 2020/9/3--9:05
 **/
@Data
public class DashboardVo {


    private DashboardArticleVisitsVo visits;

    private DashboardArticleCommentsVo comments;

    private DashboardArticleNumVo articleNum;
}
