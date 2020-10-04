package com.ponking.pblog.controller.api;

import com.ponking.pblog.model.result.R;
import com.ponking.pblog.model.vo.DashboardArticleCommentsVo;
import com.ponking.pblog.model.vo.DashboardArticleNumVo;
import com.ponking.pblog.model.vo.DashboardArticleVisitsVo;
import com.ponking.pblog.model.vo.DashboardVo;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Peng
 * @date 2020/9/3--8:56
 **/
@Api(value="主页信息controller",tags={"主页信息操作接口"})
@RestController
@RequestMapping("/sys")
public class ApiDashboardController {


    /**
     * visitis: {
     * expectedData: [100, 120, 161, 134, 105, 160, 165],
     * actualData: [120, 82, 91, 154, 162, 140, 145]
     * },
     * comments: {
     * expectedData: [200, 192, 120, 144, 160, 130, 140],
     * actualData: [180, 160, 151, 106, 145, 150, 130]
     * },
     * articleNum: {
     * expectedData: [80, 100, 121, 104, 105, 90, 100],
     * actualData: [120, 90, 100, 138, 142, 130, 130]
     * }
     */
    @GetMapping("/dashboard/info")
    public R dashboardInfo() {
        //todo 模拟数据
        DashboardVo dashboardVO = new DashboardVo();
        DashboardArticleVisitsVo visitsVO = new DashboardArticleVisitsVo();
        DashboardArticleCommentsVo commentsVO = new DashboardArticleCommentsVo();
        DashboardArticleNumVo numVO = new DashboardArticleNumVo();
        visitsVO.setActualData(getData());
        visitsVO.setNums(count(visitsVO.getActualData()));
        commentsVO.setActualData(getData());
        commentsVO.setNums(count(commentsVO.getActualData()));
        numVO.setActualData(getData());
        numVO.setNums(count(numVO.getActualData()));

        dashboardVO.setVisits(visitsVO);
        dashboardVO.setArticleNum(numVO);
        dashboardVO.setComments(commentsVO);
        return R.success(dashboardVO);
    }

    private List<Integer> getData() {
        Random random = new Random();
        return IntStream.rangeClosed(1, 7).map(i -> {
            return random.nextInt(200);
        }).distinct().boxed().collect(Collectors.toList());
    }

    private Integer count(List<Integer> nums){
        return nums.stream().reduce(0,Integer::sum);
    }
}
