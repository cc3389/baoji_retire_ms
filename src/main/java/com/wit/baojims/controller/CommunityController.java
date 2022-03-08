package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.*;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.CommunityPageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 13:45 2022/2/27
 * @Param
 **/
@Slf4j
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public SaResult add(@RequestBody JSONObject data){
        if(data == null) {
            return SaResult.code(300).setMsg("社区名为空");
        }

        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Admin> queryWrapperAdmin = new QueryWrapper<>();
        queryWrapperAdmin.eq("admin_id", loginId);
        Admin admin = adminService.getOne(queryWrapperAdmin);
        log.info(data.getStr("name"));
        //判断名字是否重复
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", data.getStr("name"));
        Community one = communityService.getOne(queryWrapperCommunity);

        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        List<Manage> list = manageService.list(queryWrapperManage);
        if(one == null){
            // 新建社区实体 并传入数据库
            Community community = new Community();
            community.setName(data.getStr("name"));
            community.setVisCount(0);
            community.setCountyId(list.get(1).getCountyId());
            communityService.save(community);
            // 新建manage表 并传入数据库
            queryWrapperCommunity.clear();
            queryWrapperCommunity.eq("name", community.getName());
            Community communityServiceOne = communityService.getOne(queryWrapperCommunity);
            Manage manage = new Manage();
            manage.setComId(communityServiceOne.getComId());
            manage.setCountyId(communityServiceOne.getCountyId());
            manage.setAdminId(admin.getAdminId());
            manageService.save(manage);
        } else {
            return SaResult.code(300).setMsg("社区名称重复");
        }
        return SaResult.ok();
    }

    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) {
            pageCurrent = 1;
        }
        if(sizeCurrent == null) {
            sizeCurrent = 10;
        }
        IPage<Community> iPage = communityService.selectCommunityPage(pageCurrent, sizeCurrent);

        // 得到当前页、总页数、页面大小
        List<Community> communityList = iPage.getRecords();

        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());

        List<CommunityPageVo> communityPageVoList = new ArrayList<>();
        for(Community community : communityList){
            CommunityPageVo communityPageVo = new CommunityPageVo();
            communityPageVo.setName(community.getName());
            communityPageVo.setId(community.getComId());
            communityPageVoList.add(communityPageVo);
        }

        data.put("list", communityPageVoList);

        return SaResult.ok().setData(data);
    }

    @PostMapping("/del")
    public SaResult del(@RequestBody JSONObject id){
        if(id.getInt("id") == null) {
            return SaResult.code(300).setMsg("社区id为空");
        }

        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", id.getInt("id"));
        Community one = communityService.getOne(queryWrapper);

        // 判断数据库中是否有要删除的社区信息
        if(one == null){
            return SaResult.code(300).setMsg("无此社区信息");
        } else {
            communityService.remove(queryWrapper);
        }
        return SaResult.ok();
    }

    @PostMapping("/update")
    public SaResult update(@RequestBody  JSONObject data){
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", data.getInt("comId"));
        Community one = communityService.getOne(queryWrapper);
        one.setName(data.getStr("name"));
        communityService.updateById(one);
        return SaResult.ok();
    }

}

