package com.ponking.pblog.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.util.AdminUserUtil;
import com.ponking.pblog.common.util.RedisUtil;
import com.ponking.pblog.model.params.PBlogProperties;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.common.util.JwtUtil;
import com.ponking.pblog.common.util.MD5Util;
import com.ponking.pblog.model.result.R;
import com.ponking.pblog.model.result.ResultCode;
import com.ponking.pblog.model.entity.User;
import com.ponking.pblog.model.params.UserInfo;
import com.ponking.pblog.model.vo.LoginVo;
import com.ponking.pblog.service.IUserService;
import com.ponking.pblog.shiro.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author Ponking
 * @ClassName LoginController
 * @date 2020/3/14--23:13
 **/
@RestController
@Slf4j
public class ApiAdminLoginController {


    @Autowired
    private IUserService userService;

    @Autowired
    private PBlogProperties config;

    @Autowired
    private AdminUserUtil userUtil;


    /**
     * 认证未通过
     *
     * @return
     */
    @GetMapping("/unauth")
    public R unauthorized() {
        return R.failed(ResultCode.Unauthorized);
    }

    /**
     * 登录操作
     *
     * @return token值
     * @loginForm
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginForm, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(loginForm.getUsername())) {
            throw new GlobalException("用户名不能为空");
        }
        if (StringUtils.isEmpty(loginForm.getPassword())) {
            throw new GlobalException("密码不能为空");
        }
        // 验证码 todo 可以将验证码放置redis缓存 测试调用
//        String rightCode = (String) request.getSession().getAttribute("rightCode");
//        String tryCode = loginForm.getCode();
//        if (!rightCode.equals(tryCode)) {
//            throw new CredentialsException("验证码错误");
//        }

        // 是否存在该用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginForm.getUsername());
        User dbUser = userService.getById(1L);
        if (StringUtils.isEmpty(dbUser)) {
            throw new UnknownAccountException("不存在该用户");
        }

        // TODO 账号是否已被禁用

        // 判断密码是否正确
        String pwd = MD5Util.encrypt(loginForm.getPassword(), dbUser.getSalt());
        if (!pwd.equals(dbUser.getPassword())) {
            throw new CredentialsException("密码错误");
        }
        // 用于注销登录(若已在其他地方登录，刷新token)
        if (RedisUtil.exists(AuthConstants.PREFIX_SHIRO_CACHE + dbUser.getUsername())) {
            RedisUtil.del(AuthConstants.PREFIX_SHIRO_CACHE + dbUser.getUsername());
        }
        long currentTimeMillis = System.currentTimeMillis();
        RedisUtil.setObject(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + dbUser.getUsername(), currentTimeMillis, AuthConstants.REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        // 生成jToken
        String token = JwtUtil.sign(dbUser.getUsername(), currentTimeMillis + "");
        response.setHeader("Authorization", token);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        //todo 便于测试
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return R.success(data);
    }

    /**
     * 获取权限
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
//    @RequiresAuthentication
    public R userInfo(@RequestParam String token) {
        User user = userUtil.getUser();
        UserInfo info = new UserInfo();
        // todo 模拟权限
        String[] roles = {"templates/admin"};
        info.setRoles(roles);
        info.setIntroduction(config.getAuthorDescription());
        info.setAvatar(config.getBlogAvatar());

        String userName = user.getUsername();
        info.setName(userName);
        return R.success(info);

//        return R.failed(HttpStatus.OK.value(), "还没登录(Not login)", null);

    }

    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        SecurityUtils.getSubject().logout();
        String token = request.getHeader("Authorization");
        if (RedisUtil.exists(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + JwtUtil.getAccount(token))) {
            if (RedisUtil.del(AuthConstants.PREFIX_SHIRO_REFRESH_TOKEN + JwtUtil.getAccount(token)) > 0) {
                return R.success().code(HttpStatus.OK.value()).message("注销成功");
            }
        }
        throw new GlobalException("注销失败，Account不存在(Deletion Failed. Account does not exist.)");
    }


    /**
     * 测试登录
     *
     * @param
     * @return com.wang.model.common.ResponseBean
     * @author dolyw.com
     * @date 2018/8/30 16:18
     */
    @GetMapping("/article")
    public R article() {
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return R.success(HttpStatus.OK.value(), "您已经登录了(You are already logged in)", null);
        } else {
            return R.failed(HttpStatus.OK.value(), "你是游客(You are guest)", null);
        }
    }

}
