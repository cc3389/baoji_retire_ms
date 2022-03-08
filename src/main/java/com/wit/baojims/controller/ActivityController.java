package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.ActivityForm;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.ActivityService;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.vo.ActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
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
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private ManageService manageService;

    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;

        //只查询管理员当前社区的所有活动
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        List<Manage> list = manageService.list(queryWrapperManage);
        List<Integer> comIdList = new ArrayList<>();
        for (Manage manage : list) comIdList.add(manage.getComId());
        IPage<Activity> iPage = activityService.selectActivityPage(pageCurrent, sizeCurrent, comIdList);

        // 得到当前页、总页数、页面大小
        List<Activity> activityList = iPage.getRecords();

        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("totalPage", iPage.getTotal());
        data.put("size", iPage.getSize());

        // 封装成VO
        List<ActivityVo> activityVoList = new ArrayList<>();
        for(Activity activity : activityList){
            ActivityVo activityVo = new ActivityVo();

            QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("com_id", activity.getComId());
            Community one = communityService.getOne(queryWrapper);
            BeanUtils.copyProperties(activity, activityVo);

            activityVo.setId(activity.getActId());
            activityVo.setComName(one.getName());
            activityVoList.add(activityVo);
        }

        data.put("list", activityVoList);
        return SaResult.ok().setData(data);
    }

    @PostMapping("/add")
    public SaResult add(@Valid @RequestBody ActivityForm activityForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            SaResult result = null;
            for (ObjectError allError : bindingResult.getAllErrors()) {
                String defaultMessage = allError.getDefaultMessage();
                log.info("[增加活动]",defaultMessage);
                result = new SaResult(300,defaultMessage,new Object());
                result.setMsg(defaultMessage);
                result.setCode(300);
            }
            return result;
        }

        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", activityForm.getComName());
        Community one = communityService.getOne(queryWrapperCommunity);

        if(one == null){
            return SaResult.code(300).setMsg("查无社区");
        }
        Activity activity = new Activity();
        activity.setDate(activityForm.getDate());
        activity.setName(activityForm.getName());
        activity.setDescription(activityForm.getDescription());
        activity.setComId(one.getComId());

        activityService.save(activity);
        return SaResult.ok();
    }
}

