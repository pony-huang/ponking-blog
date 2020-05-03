package com.ponking.pblog.controller;




import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.common.util.MD5Util;
import com.ponking.pblog.model.R;
import com.ponking.pblog.model.ResultCode;
import com.ponking.pblog.model.entity.User;
import com.ponking.pblog.model.vo.UserInfo;
import com.ponking.pblog.model.vo.LoginVO;
import com.ponking.pblog.service.IUserService;
import com.ponking.pblog.shiro.JwtToken;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Ponking
 * @ClassName LoginController
 * @date 2020/3/14--23:13
 **/
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;


    /**
     * 认证未通过
     * @return
     */
    @GetMapping("/unauth")
    public R unauthorized() {
        return R.failed(ResultCode.Unauthorized);
    }

    /**
     * 登录操作
     * @loginForm
     * @return token值
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginForm) {
        if(StringUtils.isEmpty(loginForm.getUsername())){
            throw new GlobalException("用户名不能为空");
        }
        if(StringUtils.isEmpty(loginForm.getPassword())){
            throw new GlobalException("密码不能为空");
        }

        // 是否存在该用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",loginForm.getUsername());
        User user = userService.getById(1L);
        if(StringUtils.isEmpty(user)){
            throw new UnknownAccountException("不存在该用户");
        }

        // TODO 账号是否已被禁用

        // 判断密码是否正确
        String pwd = MD5Util.encrypt(loginForm.getPassword(),user.getSalt());
        if(!pwd.equals(user.getPassword())){
            throw new CredentialsException("密码错误");
        }
        //2.生成jwtToken
        String jwtToken = JwtUtil.generateToken(user.getUsername());
        log.info("登录成功...",jwtToken);
        Map<String,String> token = new HashMap<>();
        token.put("token",jwtToken);
        return R.success(token);
    }

    /**
     * 获取权限
     * @param token
     * @return
     */
    @GetMapping("/info")
    public R userInfo(@RequestParam String token){
        JwtToken jwtToken = new JwtToken(token);
        Subject subject = SecurityUtils.getSubject();
        subject.login(jwtToken);

        // 没有抛异常执行下一步
        UserInfo info = new UserInfo();
        String [] roles = {"admin"};
        info.setRoles(roles);
        info.setIntroduction("I am a super administrator");
        info.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        Claims claims = JwtUtil.parseJWT(token);
        info.setName(claims.getSubject());
        return R.success(info);
    }

    @PostMapping("/logout")
    public R logout(){
        // TODO 日后更新为redis存储
        return R.success();
    }
}
