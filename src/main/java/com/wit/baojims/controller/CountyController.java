package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-02-27
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
 * @Author Zeman
 * @Description //TODO
 * @Date 20:20 2022/2/26
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/county")
public class CountyController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ManageService manageService;

    @GetMapping("/community")
    public SaResult community (){
        Object loginId = StpUtil.getLoginId();

        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        List<Manage> manageList = manageService.list(queryWrapperManage);

        List<String> comNameList = new ArrayList<>();
        for (Manage manage : manageList){
            QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("com_id", manage.getComId());
            Community community = communityService.getOne(queryWrapper);
            comNameList.add(community.getName());
        }

        HashMap data = new HashMap();
        data.put("list", comNameList);
        return SaResult.ok().setData(data);
    }
}

