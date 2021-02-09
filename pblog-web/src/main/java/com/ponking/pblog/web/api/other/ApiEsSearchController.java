package com.ponking.pblog.web.api.other;

import com.ponking.pblog.common.result.R;
import com.ponking.pblog.search.IEsArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peng
 * @date 2020/10/23--17:52
 * @Des
 **/
@RestController
@Api(tags = {"搜索功能模块"})
public class ApiEsSearchController {

    @Autowired
    private IEsArticleService esArticleService;


    @GetMapping("/es/import")
    @ApiOperation("导入数据（测试）")
    public R importAll(){
        // todo
        return R.success();
    }
}
