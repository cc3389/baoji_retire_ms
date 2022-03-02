package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.entity.Visit;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.ActivityForm;
import com.wit.baojims.form.VisitForm;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.service.VisitService;
import com.wit.baojims.vo.VisitVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 13:44 2022/2/27
 * @Param
 **/
@Slf4j
@RestController
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private ManageService manageService;

    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;
        IPage<Visit> iPage = visitService.selectVisitPage(pageCurrent, sizeCurrent);

        // 得到当前页、总页数、页面大小
        List<Visit> visitList = iPage.getRecords();
        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());

        // 根据comId查询到comName并封装成VO对象传给前端
        List<VisitVo> visitVoList = new ArrayList<>();
        VisitVo visitVos = new VisitVo();
        for(Visit visit : visitList){
            QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("com_id", visit.getComId());
            Community one = communityService.getOne(queryWrapper);
            visitVos.setComName(one.getName());
            visitVos.setDate(visit.getVisDate());
            visitVos.setComId(one.getComId());
            visitVos.setDesc(visit.getVisDesc());
            visitVos.setComName(one.getName());
            visitVos.setId(visit.getVisId());
            visitVoList.add(visitVos);
        }
        data.put("list", visitVoList);

        return SaResult.ok().setData(data);
    }

    @PostMapping("/add")
    public SaResult add(@Valid @RequestBody VisitForm visitForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("【走访录入】用户信息不能为空");
            throw new BaojiException(ResponseEnum.ACTIVITY_INFO_NULL);
        }
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Manage manage = manageService.getOne(queryWrapper);

        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", visitForm.getComName());
        Community community = communityService.getOne(queryWrapperCommunity);

        if (community == null){
            return SaResult.code(300).setMsg("社区不存在");
        }

        Visit newVisit = new Visit();
        newVisit.setVisDate(visitForm.getDate().atStartOfDay());
        newVisit.setVisDesc(visitForm.getDesc());
        newVisit.setComId(manage.getComId());

        visitService.save(newVisit);
        return SaResult.ok();
    }
    @GetMapping("/one")
    public SaResult one(@RequestParam Integer visId){
        if (visId == null) return SaResult.code(300).setMsg("走访记录id为空");

        //根据走访记录id查询走访记录的详细信息
        QueryWrapper<Visit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vis_id", visId);
        Visit one = visitService.getOne(queryWrapper);
        if(one == null){
            return SaResult.code(300).setMsg("查无信息");
        }
        HashMap data = new HashMap();
        data.put("desc", one.getVisDesc());
        return SaResult.ok().setData(data);
    }
}

