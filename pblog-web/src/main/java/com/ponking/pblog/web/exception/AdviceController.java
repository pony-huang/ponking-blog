package com.ponking.pblog.web.exception;

import com.ponking.pblog.common.result.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统一全局异常处理器
 *
 * @author peng
 * @date 2020/9/7--15:51
 * @Des
 **/
@ControllerAdvice
@RestController
public class AdviceController {

    @ExceptionHandler(Exception.class)
    public R userLoginError(Exception e) {
        e.printStackTrace();
        return R.failed().message(e.getMessage());
    }
}
