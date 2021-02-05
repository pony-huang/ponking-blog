package com.ponking.pblog.web.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ponking.pblog.common.constants.AuthConstants;
import com.ponking.pblog.common.exception.GlobalException;
import com.ponking.pblog.common.params.PBlogProperties;
import com.ponking.pblog.common.result.R;
import com.ponking.pblog.common.result.ResultCode;
import com.ponking.pblog.common.util.RedisUtils;
import com.ponking.pblog.model.dto.UserInfoDTO;
import com.ponking.pblog.model.entity.User;
import com.ponking.pblog.model.vo.LoginVo;
import com.ponking.pblog.service.IUserService;
import com.ponking.pblog.shiro.base.TokenProvider;
import com.ponking.pblog.shiro.util.JwtUtils;
import com.ponking.pblog.shiro.util.Md5Utils;
import com.ponking.pblog.shiro.util.UserHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
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
@Slf4j
@RestController
@Api(tags = {"登陆功能模块"})
public class ApiLoginController {

    @Autowired
    private IUserService userService;

    @Autowired
    private PBlogProperties config;

    @Autowired
    private TokenProvider tokenProvider;

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
    @ApiOperation("登陆")
    public R login(@RequestBody LoginVo loginForm, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(loginForm.getUsername())) {
            throw new GlobalException("用户名不能为空");
        }
        if (StringUtils.isEmpty(loginForm.getPassword())) {
            throw new GlobalException("密码不能为空");
        }
        // 验证码 todo 可以将验证码放置redis缓存 测试调用
        String rightCode = (String) request.getSession().getAttribute("rightCode");
        String tryCode = loginForm.getCode();
        if (!rightCode.equals(tryCode)) {
            throw new CredentialsException("验证码错误");
        }
        // 是否存在该用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", loginForm.getUsername());
        User dbUser = userService.getById(1L);
        if (StringUtils.isEmpty(dbUser)) {
            throw new UnknownAccountException("不存在该用户");
        }
        // 判断密码是否正确
        String pwd = Md5Utils.encrypt(loginForm.getPassword(), dbUser.getSalt());
        if (!pwd.equals(dbUser.getPassword())) {
            throw new CredentialsException("密码错误");
        }
        // 用于注销登录(若已在其他地方登录，刷新token)

        // 生成Token
        response.setHeader("Authorization", tokenProvider.createToken(loginForm.getUsername(), true));
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        return R.success();
    }

    /**
     * 获取权限
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public R userInfo(@RequestParam String token) {
        User user = null;
        UserInfoDTO info = new UserInfoDTO();
        // todo 模拟权限
        String[] roles = {"templates/admin"};
        info.setRoles(roles);
        info.setIntroduction(config.getAuthorDescription());
        info.setAvatar(config.getBlogAvatar());

        assert user != null;

        String userName = user.getUsername();

        info.setName(userName);
        return R.success(info);
    }

    @PostMapping("/logout")
    @ApiOperation("注销")
    public R logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Assert.notNull(token, "注销失败，Account不存在(Deletion Failed. Account does not exist.)");
        UserHelper.logout(token);
        return R.success().message("注销成功");
    }

}
