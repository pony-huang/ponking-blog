package com.ponking.pblog.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author peng
 * @date 2020/10/25--23:19
 * @Des
 **/
@Controller
public class SearchTestController {

    @GetMapping("/websocket/index")
    public String index() {
        return "blog/websocket";
    }
}
