package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.entity.Member;
import com.wit.baojims.entity.Trans;
import com.wit.baojims.mapper.MemberMapper;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.service.MemberService;
import com.wit.baojims.service.TransService;
import com.wit.baojims.vo.TransGroupVo;
import com.wit.baojims.vo.TransOneVo;
import com.wit.baojims.vo.TransSuggestionVo;
import com.wit.baojims.vo.TransVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
@RequestMapping("/trans")
@SuppressWarnings("all")
public class TransController {

    @Autowired
    private TransService transService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private CommunityService communityService;

    @SaCheckRole("low")
    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent,@RequestParam("name") String name){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;

        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        Manage oneManage = manageService.getOne(queryWrapperManage);
        Integer currentComId = oneManage.getComId();

        if(name.equals("")){
            IPage<Trans> iPage = transService.selectTransPage(pageCurrent, sizeCurrent, currentComId);

            // 得到当前页、总页数、页面大小
            List<Trans> transList = iPage.getRecords();
            HashMap data = new HashMap();
            data.put("page", iPage.getCurrent());
            data.put("size", iPage.getSize());
            data.put("totalPage", iPage.getTotal());

            // 查询返回参数的所有信息 封装成list
            List<TransVo> transVoList = new ArrayList<>();
            for(Trans trans : transList){
                TransVo transVo = new TransVo();
                QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
                QueryWrapper<Community> queryWrapperCommunity1 = new QueryWrapper<>();
                QueryWrapper<Community> queryWrapperCommunity2 = new QueryWrapper<>();
                QueryWrapper<Community> queryWrapperCommunity3 = new QueryWrapper<>();
                BeanUtils.copyProperties(trans, transVo);
                transVo.setId(trans.getPeoId());
                transVo.setName(memberService.getOne(queryWrapperMember.eq("peo_id", trans.getPeoId())).getName());
                transVo.setOutCom(communityService.getOne(queryWrapperCommunity3.eq("com_id", trans.getOutComId())).getName());
                transVo.setInCom(communityService.getOne(queryWrapperCommunity1.eq("com_id", trans.getInComId())).getName());
                if (trans.getStatus().equals("通过")){
                    transVo.setInCom(communityService.getOne(queryWrapperCommunity2.eq("com_id", trans.getInComId())).getName());
                }
                transVoList.add(transVo);
            }
            data.put("list", transVoList);

            return SaResult.ok().setData(data);
        }
        else {
            QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name", name).select("peo_id");
            List<Object> list = memberService.listObjs(queryWrapper);

            List<Trans> transList = new ArrayList<>();
            for(Object peoId : list){
                QueryWrapper<Trans> queryWrapperTrans = new QueryWrapper<>();
                queryWrapperTrans.eq("peo_id", peoId);
                Trans one = transService.getOne(queryWrapperTrans);
                if(one.getOutComId() == currentComId || one.getInComId() == currentComId){
                    transList.add(one);
                }
            }

            HashMap data = new HashMap();
            data.put("list", transList);
            return SaResult.ok().setData(data);
        }
    }

    @GetMapping("/suggestion")
    public SaResult suggestion(){
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        List<Manage> manageList = manageService.list(queryWrapperManage);

        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        for (Manage manage : manageList) {
            queryWrapperCommunity.or().ne("com_id", manage.getComId());
        }
        List<Community> communityList = communityService.list(queryWrapperCommunity);

        // 封装成vo传给前端
        List<TransSuggestionVo> transSuggestionVoList = new ArrayList<>();
        for(Community community : communityList){
            TransSuggestionVo transSuggestionVo = new TransSuggestionVo();
            transSuggestionVo.setValue(community.getName());
            transSuggestionVoList.add(transSuggestionVo);
        }
        HashMap data = new HashMap();
        data.put("list",transSuggestionVoList);
        return SaResult.ok().setData(data);
    }

    @GetMapping("/groupByDate")
    public SaResult groupByDate (){
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        Manage manage = manageService.getOne(queryWrapperManage);

        List<TransGroupVo> inGroupList = transService.groupByInMonth(manage.getComId());
        List<TransGroupVo> outGroupList = transService.groupByOutMonth(manage.getComId());

        int[] out = new int[12];
        int[] in = new int[12];
        for (TransGroupVo transGroupVo : outGroupList) {
            out[transGroupVo.getMonth()] = transGroupVo.getTotal();
        }
        for (TransGroupVo transGroupVo : inGroupList) {
            in[transGroupVo.getMonth()] = transGroupVo.getTotal();
        }
        HashMap<String, Object> data = new HashMap<>();
        data.put("in", in);
        data.put("out", out);
        return SaResult.ok().setData(data);
    }

    @RequestMapping("/test")
    public SaResult test(){
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .lt("age","15")
                .or()
                .gt("age","20");
        List<Member> list = memberService.list(queryWrapper);
        return SaResult.ok().setData(list);
    }
}

