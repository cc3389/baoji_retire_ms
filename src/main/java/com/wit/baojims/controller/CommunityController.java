package com.wit.baojims.controller;


import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.Config.BeanCopyUtil;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Member;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.vo.CommunityPageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public SaResult add(@RequestBody JSONObject data){
        if(data == null) return SaResult.code(300).setMsg("社区名为空");

        log.info(data.getStr("name"));
        //判断名字是否重复
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", data.getStr("name"));
        Community one = communityService.getOne(queryWrapper);
        if(one == null){
            //新建社区实体    并传入数据库
            Community community = new Community();
            community.setName(data.getStr("name"));
            community.setVisCount(0);
            communityService.save(community);
        } else {
            return SaResult.code(300).setMsg("社区名称重复");
        }
        return SaResult.ok();
    }

    @GetMapping("/page")
    public SaResult page( Integer page, Integer size){
        if(page == null) page = 1;
        if(size == null) size = 10;
        IPage<Community> iPage = communityService.selectCommunityPage(page, size);

        // 得到当前页、总页数、页面大小
        List<Community> communityList = iPage.getRecords();

        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());
        List<CommunityPageVo> communityPageVoList = BeanCopyUtil.copyListProperties(communityList, CommunityPageVo::new);
        data.put("list", communityPageVoList);

        return SaResult.ok().setData(data);
    }

    @GetMapping("/del")
    public SaResult del(@RequestParam Integer comId){
        if(comId == null) return SaResult.code(300).setMsg("社区id为空");

        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", comId);
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
    public SaResult update(@RequestBody JSONObject data){
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", data.getStr("comId"));
        Community one = communityService.getOne(queryWrapper);
        one.setName(data.getStr("name"));
        communityService.updateById(one);
        return SaResult.ok();
    }

}

