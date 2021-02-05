package com.ponking.pblog.web.api;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;

/**
 * @author peng
 * @date 2020/9/3--10:51
 * @Des
 **/

@Api(tags = {"验证码操作接口"})
@Controller
@Slf4j
public class ApiCaptchaController {

    @GetMapping("/captcha")
    @ApiModelProperty("获取验证码")
    public void captcha(HttpServletRequest request, HttpServletResponse response, String randomCode) throws Exception {
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 三个参数分别为宽、高、位数
            // gif类型
            GifCaptcha gifCaptcha = new GifCaptcha(130, 45, 5);
            // 设置字体,有默认字体，可以不用设置
            gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 30));
            // 设置类型，纯数字、纯字母、字母数字混合
            gifCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
            String createText = gifCaptcha.text().toLowerCase();
            log.info("captcha: {}", createText);
            // todo 存入redis并设置过期时间为30分钟
            // 输出到文件返回给前端
            gifCaptcha.out(out);
            // 将生成的验证码保存在session中
            request.getSession().setAttribute("rightCode", createText);
            captcha = out.toByteArray();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-com.ponking.pblog.cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");

        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(captcha);
        servletOutputStream.flush();
        servletOutputStream.close();
    }
}
