package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.Config.MD5Utils;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.LoginForm;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 16:39 2022/2/25
 * @Param
 **/
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MemberService memberService;

    @PostMapping("/login")
    public SaResult doLogin(@Valid @RequestBody LoginForm admin, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【用户登录】用户信息不能为空");
            throw new BaojiException(ResponseEnum.ADMIN_INFO_NULL);
        }

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("name", admin.getUsername());
        Admin one = adminService.getOne(queryWrapper);
        String pwd = MD5Utils.getPwd(admin.getPassword());

        if(one == null) return SaResult.code(300).setMsg("用户不存在");//判断用户名是否存在
        SaResult saResult = null;
        if(pwd.equals(one.getPassword())){  //根据用户名查询到的用户的密码是否与传入的密码是否一致
            StpUtil.login(one.getAdminId());
            HashMap vo = new HashMap();
            vo.put("token", StpUtil.getTokenValue());
            return SaResult.ok().setData(vo);
        } else {
            return SaResult.code(300).setMsg("用户名或密码错误");
        }
    }

    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8")
    public String add() {
        return "add!";
    }
}

