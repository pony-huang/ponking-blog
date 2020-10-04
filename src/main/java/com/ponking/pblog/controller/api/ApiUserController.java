package com.ponking.pblog.controller.api;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author peng
 * @since 2020-03-14
 */
@Api(value="管理者controller",tags={"管理者(作者)操作接口"})
@RestController
@RequestMapping("/sys/user")
public class ApiUserController {

}
