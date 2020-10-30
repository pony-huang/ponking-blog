package com.ponking.pblog.controller.api;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author peng
 * @date 2020/9/3--10:51
 * @Des
 **/
@Controller
@Api(value="验证码controller",tags={"验证码操作接口"})
public class ApiCaptchaController {


//    @Autowired
//    private DefaultKaptcha defaultKaptcha;


//    @RequestMapping("/captcha")
    @RequestMapping("/defaultKaptcha")
    public void createCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        byte[] captcha = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // 三个参数分别为宽、高、位数
            // gif类型
            GifCaptcha gifCaptcha = new GifCaptcha(130, 45,5);
            // 设置字体,有默认字体，可以不用设置
            gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 30));
            // 设置类型，纯数字、纯字母、字母数字混合
            gifCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);
            String createText = gifCaptcha.text().toLowerCase();
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
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/gif");

        ServletOutputStream sout = response.getOutputStream();
        sout.write(captcha);
        sout.flush();
        sout.close();
    }
}
