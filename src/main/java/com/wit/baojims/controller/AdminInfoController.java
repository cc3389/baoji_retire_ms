package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.entity.AdminInfo;
import com.wit.baojims.entity.Member;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.LoginForm;
import com.wit.baojims.mapper.AdminInfoMapper;
import com.wit.baojims.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-02-24
 */
@Slf4j
@RestController
@RequestMapping("/adminInfo")
public class AdminInfoController {


    @Autowired
    private AdminInfoMapper adminInfoMapper;

    @Autowired
    private MemberService memberService;

    /*
     * @Author Shawn Yue
     * @Description //TODO Shawn Yue
     * @Date 8:51 2022/2/25
     * @Param [admin, bindingResult]
     * @return cn.dev33.satoken.util.SaResult
     **/
    @RequestMapping("/login")
    public SaResult doLogin(@Valid @RequestBody LoginForm admin, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【用户登录】用户信息不能为空");
            throw new BaojiException(ResponseEnum.ADMIN_INFO_NULL);
        }
//        log.info(jsonString);
        log.info(admin.toString());
        HashMap param= JSONUtil.parse(admin).toBean(HashMap.class);
        Integer adminId =adminInfoMapper.login(param);

        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<AdminInfo>();
        queryWrapper.eq("name", admin.getName());
        AdminInfo one = adminInfoMapper.selectOne(queryWrapper);
        SaResult saResult = null;
        if(adminId != null){
            StpUtil.isLogin();
            StpUtil.login(adminId);
            saResult = SaResult.data(one);

            saResult.setMsg("success");
            return saResult;
        } else {
            saResult = SaResult.error("false");
            return saResult;
        }
    }

    @RequestMapping("/token")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok("success");
    }

    @SaCheckRole("low")
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8")
    public String add() {

//        List<Member> memberList = memberService.list();

//        return "SaResult.ok().setData(memberList)";
        return "add!";
    }


}

