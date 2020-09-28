package com.ponking.pblog.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 验证码配置
 * @author peng
 * @date 2020/9/3--10:50
 * @Des
 **/
@Component
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha dk = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
        // 边框颜色
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
        // 字体颜色
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        // 图片宽
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "110");
        // 图片高
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "40");
        // 字体大小
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
        // session key
        properties.setProperty(Constants.KAPTCHA_SESSION_KEY, "code");
        // 验证码长度
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // 字体
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        dk.setConfig(config);

        return dk;
    }

}
