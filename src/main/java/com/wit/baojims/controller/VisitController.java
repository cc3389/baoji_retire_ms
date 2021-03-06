package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.*;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.ActivityForm;
import com.wit.baojims.form.VisitForm;
import com.wit.baojims.mapper.VisitMapper;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.service.VisitService;
import com.wit.baojims.vo.VisitGroupVo;
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
    private VisitMapper visitMapper;
    @Autowired
    private CommunityService communityService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private AdminService adminService;

    @SaCheckRole("mid")
    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;

        QueryWrapper<Admin> queryWrapperAdmin = new QueryWrapper<>();
        queryWrapperAdmin.eq("admin_id", StpUtil.getLoginId());
        Admin admin = adminService.getOne(queryWrapperAdmin);

        IPage<Visit> iPage = visitService.selectVisitPage(pageCurrent, sizeCurrent, admin);

        // ??????????????????????????????????????????
        List<Visit> visitList = iPage.getRecords();
        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());

        // ??????comId?????????comName????????????VO??????????????????
        List<VisitVo> visitVoList = new ArrayList<>();
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        for(Visit visit : visitList){
            VisitVo visitVos = new VisitVo();
            queryWrapperCommunity.eq("com_id", visit.getComId());
            Community one = communityService.getOne(queryWrapperCommunity);
            visitVos.setComName(one.getName());
            visitVos.setDate(visit.getVisDate());
            visitVos.setComId(one.getComId());
            visitVos.setDesc(visit.getVisDesc());
            visitVos.setComName(one.getName());
            visitVos.setId(visit.getVisId());
            visitVoList.add(visitVos);
            queryWrapperCommunity.clear();
        }
        data.put("list", visitVoList);

        return SaResult.ok().setData(data);
    }

    @SaCheckRole("mid")
    @PostMapping("/add")
    public SaResult add(@Valid @RequestBody VisitForm visitForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.info("??????????????????????????????????????????");
            throw new BaojiException(ResponseEnum.ACTIVITY_INFO_NULL);
        }
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", visitForm.getComName());
        Community one = communityService.getOne(queryWrapperCommunity);
        if (one == null){
            return SaResult.code(300).setMsg("????????????");
        }
        else {
            Visit visit = new Visit();
            visit.setVisDate(visitForm.getDate().atStartOfDay());
            visit.setVisDesc(visitForm.getDesc());
            visit.setComId(one.getComId());
            visitMapper.insert(visit);

            one.setVisCount(one.getVisCount()+1);
            communityService.updateById(one);
            return SaResult.ok();
        }
    }

    @SaCheckRole("mid")
    @GetMapping("/one")
    public SaResult one(@RequestParam Integer visId){
        if (visId == null) return SaResult.code(300).setMsg("????????????id??????");

        //??????????????????id?????????????????????????????????
        QueryWrapper<Visit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("vis_id", visId);
        Visit one = visitService.getOne(queryWrapper);
        if(one == null){
            return SaResult.code(300).setMsg("????????????");
        }
        HashMap data = new HashMap();
        data.put("desc", one.getVisDesc());
        return SaResult.ok().setData(data);
    }

    @GetMapping("/groupByCom")
    public SaResult groupByCom (){
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        List<Manage> manageList = manageService.list(queryWrapperManage);

        List<VisitGroupVo> visitGroupVoList = new ArrayList<>();
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        for (Manage manage : manageList){
            VisitGroupVo visitGroupVo = new VisitGroupVo();
            queryWrapperCommunity.eq("com_id", manage.getComId());
            Community community = communityService.getOne(queryWrapperCommunity);
            visitGroupVo.setComName(community.getName());
            visitGroupVo.setTotal(community.getVisCount());
            visitGroupVoList.add(visitGroupVo);
            queryWrapperCommunity.clear();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("list", visitGroupVoList);
        return SaResult.ok().setData(data);
    }
}

