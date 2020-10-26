package com.ponking.pblog.controller.api;

import com.ponking.pblog.model.result.R;
import com.ponking.pblog.search.IEsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peng
 * @date 2020/10/23--17:52
 * @Des
 **/
@RestController
public class ApiEsSearchController {

    @Autowired
    private IEsArticleService esArticleService;


    @RequestMapping("/es/import")
    public R importAll(){
        int i = esArticleService.importAll();
        return R.success(i);
    }
}
