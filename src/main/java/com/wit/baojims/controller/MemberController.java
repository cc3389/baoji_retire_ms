package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.*;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.MemberForm;
import com.wit.baojims.form.MemberUpdateForm;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.*;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.MemberOneVo;
import com.wit.baojims.vo.MemberPageVo;
import com.wit.baojims.vo.memberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.*;
import java.util.*;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 16:59 2022/2/25
 * @Param
 **/
@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private ManageService manageService;
    @Autowired
    private InstituteService instituteService;
    @Autowired
    private TransService transService;
    @Autowired
    private CommunityService communityService;

    @SaCheckRole("low")
    @PostMapping("/insert")
    public SaResult insert(@Valid MemberForm memberForm, BindingResult bindingResult, @RequestParam(value = "avatar") MultipartFile avatar) throws IOException {

        UUID uuid = UUID.randomUUID();
        avatar.transferTo(new File("D:\\baoji_retire_ms\\src\\main\\resources\\static\\"+uuid+".jpg"));

        if (bindingResult.hasErrors()){
            log.info("??????????????????????????????????????????");
            throw new BaojiException(ResponseEnum.MEMBER_INFO_NULL);
        }

        //??????????????????????????????
        QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
        queryWrapperInstitute.eq("name", memberForm.getInsName());
        if(instituteService.getOne(queryWrapperInstitute) == null){
            return SaResult.code(300).setMsg("??????????????????");
        }

        // LocalDate??????LocalDateTime
        LocalDateTime localDateTime = memberForm.getBirthday().atStartOfDay();
        Member member = new Member();
        BeanUtils.copyProperties(memberForm, member);       //???????????? ???????????????Member??????
        member.setBirth(localDateTime);

        QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
        queryWrapperMember.eq("name", member.getName());
        Member one = memberService.getOne(queryWrapperMember);
        // ??????????????????????????????
        if(one == null){
            Object loginId = StpUtil.getLoginId();
            log.info(loginId.toString());
            QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
            queryWrapperManage.eq("admin_id", loginId);
            Manage manage = manageService.getOne(queryWrapperManage);     //??????token???????????????????????????
            QueryWrapper<Institute> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", memberForm.getInsName());
            Institute institute = instituteService.getOne(queryWrapper1);   //?????????????????????????????????????????????

            // ????????????  ????????????Member??????
            member.setAge(LocalDateTime.now().getYear()-memberForm.getBirthday().getYear());
            member.setIsDeath("???");
            member.setComId(manage.getComId());
            member.setInstId(institute.getInsId());
            member.setAvatar(uuid.toString()+".jpg");

            memberService.save(member);
            return SaResult.ok();
        } else {
            return SaResult.code(300).setMsg("????????????????????????");
        }
    }

    @SaCheckRole("low")
    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent, @RequestParam(required = false, value = "name") String name){
        if(pageCurrent == null) {
            pageCurrent = 1;
        }
        if(sizeCurrent == null) {
            sizeCurrent = 10;
        }

        name = name.trim();
        if (name.equals("")){
            Object loginId = StpUtil.getLoginId();
            QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_id", loginId);
            Manage one = manageService.getOne(queryWrapper);
            IPage<Member> iPage = memberService.selectMemberPage(pageCurrent, sizeCurrent, one.getComId());

            // ??????????????????????????????????????????
            List<Member> memberList = iPage.getRecords();
            List<MemberPageVo> memberPageVoList =BeanCopyUtil.copyListProperties(memberList, MemberPageVo::new);
            HashMap<String, Object> data = new HashMap<>();
            data.put("page", iPage.getCurrent());
            data.put("size", iPage.getSize());
            data.put("totalPage", iPage.getTotal());

            if(memberList == null){
                return SaResult.code(300).setMsg("????????????");
            }

            data.put("list", memberPageVoList);

            return SaResult.ok().setData(data);
        }
        else {
            Object loginId = StpUtil.getLoginId();
            QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_id", loginId);
            Manage one = manageService.getOne(queryWrapper);
            name = name.trim();
            IPage<Member> iPage = memberService.selectMemberPageByName(pageCurrent, sizeCurrent, one.getComId(), name);

            // ??????????????????????????????????????????
            List<Member> memberList = iPage.getRecords();
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("page", iPage.getCurrent());
            data.put("size", iPage.getSize());
            data.put("totalPage", iPage.getTotal());

            if(memberList == null){
                return SaResult.code(300).setMsg("????????????");
            }

            data.put("list", memberList);

            return SaResult.ok().setData(data);
        }
    }

    @SaCheckRole("low")
    @GetMapping("/one")
    public SaResult one(@RequestParam Integer peoId) throws UnknownHostException {
        if(peoId == null) return SaResult.code(300).setMsg("??????id????????????");

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", peoId);
        Member one = memberService.getOne(queryWrapper);

        if(one == null){
            return SaResult.code(300).setMsg("????????????");
        }
        MemberOneVo memberVo = new MemberOneVo();
        BeanUtils.copyProperties(one, memberVo);//????????????

        QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
        queryWrapperInstitute.eq("ins_id", one.getInstId());
        Institute institute = instituteService.getOne(queryWrapperInstitute);
        memberVo.setInsName(institute.getName());

        HashMap<String, MemberOneVo> data = new HashMap<>();
        InetAddress ia = InetAddress.getLocalHost();
        String ip = ia.getHostAddress();//??????IP
        memberVo.setAvatar("http://"+ip+":8080/static/"+one.getAvatar());
        data.put("list", memberVo);
        return SaResult.ok().setData(data);
    }

    @SaCheckRole("low")
    @PostMapping("/edit")
    public SaResult update (@Valid MemberUpdateForm memberUpdateForm, BindingResult bindingResult, @RequestParam(value = "avatar", required = false) MultipartFile avatar) throws IOException {
        if(bindingResult.hasErrors()){
            log.info("????????????????????????????????????????????????");
            throw new BaojiException(ResponseEnum.MEMBER_INFO_NULL);
        }

        //???????????????peo_id?????????????????????????????? ???????????????????????????
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", memberUpdateForm.getId());
        Member one = memberService.getOne(queryWrapper);

        if(one == null){
            return SaResult.code(300).setMsg("????????????????????????");
        }
        //??????????????????????????????
        if(avatar != null) {
            String originalFilename = avatar.getOriginalFilename();
            UUID uuid = UUID.randomUUID();
            avatar.transferTo(new File("D:\\baoji_retire_ms\\src\\main\\resources\\static\\"+uuid));
            one.setAvatar(originalFilename);
        }
        if(memberUpdateForm.getName() != null) one.setName(memberUpdateForm.getName());
        if(memberUpdateForm.getPhone() != null) one.setPhone(memberUpdateForm.getPhone());
        if(memberUpdateForm.getGender() != null) one.setGender(memberUpdateForm.getGender());
        if(memberUpdateForm.getBirth() != null) one.setBirth(memberUpdateForm.getBirth().atStartOfDay());

        memberService.updateById(one);
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @PostMapping("/out")
    public SaResult out(@RequestBody JSONObject data){
        if(data.getInt("id") == null){
            SaResult.code(300).setMsg("??????id??????");
        }

        QueryWrapper<Trans> queryWrapperTest = new QueryWrapper<>();
        queryWrapperTest.eq("peo_id", data.getInt("id")).ne("status", "??????").ne("status", "??????");
        Trans test = transService.getOne(queryWrapperTest);
        if(test != null && test.getStatus().equals("?????????")){
            return SaResult.code(300).setMsg("?????????????????????????????????");
        }

        //???????????????????????????
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", data.getStr("community"));
        Community inCom = communityService.getOne(queryWrapperCommunity);
        if(inCom == null){
            return SaResult.code(300).setMsg("???????????????");
        }

        //??????token?????????????????? ??????outCom
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Manage one2 = manageService.getOne(queryWrapper);

        //???????????????outComId ??????outComName
        QueryWrapper<Community> queryWrapperCommunity1 = new QueryWrapper<>();
        queryWrapperCommunity1.eq("com_id", one2.getComId());
        Community outCom = communityService.getOne(queryWrapperCommunity1);

        //???????????????????????????
        Trans trans = new Trans();
        trans.setOutComId(outCom.getComId());
        trans.setInComId(inCom.getComId());
        trans.setPeoId(data.getInt("id"));
        trans.setOutDate(LocalDateTime.now());
        trans.setStatus("?????????");
        transService.save(trans);
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @PostMapping("/in")
    public SaResult in(@RequestBody JSONObject data){
        if(data.getStr("id") == null) return SaResult.code(300).setMsg("??????????????????");

        //??????token??????????????????manage??????
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", StpUtil.getLoginId());
        Manage one = manageService.getOne(queryWrapperManage);

        //??????proId??????????????????????????????
        QueryWrapper<Trans> queryWrapperTrans = new QueryWrapper<>();
        queryWrapperTrans.eq("peo_id", data.getInt("id")).ne("status", "??????").ne("status", "??????");
        Trans one1 = transService.getOne(queryWrapperTrans);

        if(!data.getBool("status")) one1.setStatus("??????");// ????????????

        else if(data.getBool("status")){   // ????????????
            //???????????????????????????????????? ??????????????????
            one1.setInDate(LocalDateTime.now());
            one1.setStatus("??????");

            QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
            queryWrapperMember.eq("peo_id", one1.getPeoId());
            Member member = memberService.getOne(queryWrapperMember);
            member.setComId(one1.getInComId());
            memberService.updateById(member);
        } else {
            return SaResult.code(300).setMsg("????????????");
        }

        transService.updateById(one1);
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @PostMapping("/death")
    public SaResult death(@RequestBody JSONObject id){
        if(id.getInt("id") == null) return SaResult.code(300).setMsg("??????????????????");

        //???????????????peoId?????????????????????
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", id.getInt("id"));
        Member one = memberService.getOne(queryWrapper);

        if(one == null){
            return SaResult.code(300).setMsg("????????????");
        }
        if(one.getIsDeath().equals("???")){
            return SaResult.code(300).setMsg("???????????????");
        }
        //??????????????????????????????????????? ??????????????????
        one.setIsDeath("???");
        memberService.updateById(one);
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @GetMapping("/deathPage")
    public SaResult deathPage(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;

        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Manage one = manageService.getOne(queryWrapper);
        IPage<Member> iPage = memberService.selectMemberDeathPage(pageCurrent, sizeCurrent, one.getComId());

        // ??????????????????????????????????????????
        List<Member> memberList = iPage.getRecords();
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());

        if(memberList == null){
            return SaResult.code(300).setMsg("????????????");
        }

        // ?????????????????????????????????
        for(Member member : memberList){
            if(member.getIsDeath().equals("???")){
                member.setIsDeath("??????");
            }
            if(member.getIsDeath().equals("???")){
                member.setIsDeath("??????");
            }
        }

        data.put("list", memberList);

        return SaResult.ok().setData(data);
    }
    /*
     * @Author Zeman
     * @Description //TODO
     * @Date 19:48 2022/2/25
     * @Param [page, size]
     * @return cn.dev33.satoken.util.SaResult
     **/
    @SaCheckRole("low")
    @GetMapping("/feePage")
    public SaResult getMemberFeePage(@RequestParam("page") Integer Page, @RequestParam("size") Integer Size){
        //??????iPage
        IPage<Member> iPage = memberService.selectMemberFeePage(Page, Size);
        //????????????????????? list???current???total
        List<Member> memberList = iPage.getRecords();
        long page = iPage.getCurrent();
        long total = iPage.getTotal();
        long size = iPage.getSize();
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("size",size);
        map.put("totalPage",total);
        List<memberVo> memberVos = BeanCopyUtil.copyListProperties(memberList, memberVo::new);
        map.put("list",memberVos);
        //???????????????
        return SaResult.ok().setData(map);
    }

    @SaCheckRole("low")
    @PostMapping("/export")
    public SaResult export (@RequestBody JSONObject data){
        List<Integer> peoIdList = (List<Integer>) data.getObj("list");
        log.info(peoIdList.toString());

        QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
        for (Integer peoId : peoIdList){
            queryWrapperMember.eq("peo_id", peoId).or();
        }
        List<Member> memberList = memberService.list(queryWrapperMember);

        List<MemberOneVo> memberOneVoList = new ArrayList<>();
        for (Member member : memberList){
            MemberOneVo memberOneVo = new MemberOneVo();
            BeanUtils.copyProperties(member, memberOneVo);

            QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
            queryWrapperInstitute.eq("ins_id", member.getInstId());
            memberOneVo.setInsName(instituteService.getOne(queryWrapperInstitute).getName());
            memberOneVoList.add(memberOneVo);
        }

        HashMap<String, Object> dataVo = new HashMap<String, Object>();
        dataVo.put("list", memberOneVoList);
        return SaResult.ok().setData(dataVo);
    }

//    @RequestMapping("/asd")
//    public void asd(){
//        for (int i=1;i<=50;i++){
//            Member member = new Member();
//            member.setFee(50.0);
//            member.setIsDeath("???");
//            member.setAvatar("asd.jpg");
//            member.setBirth(LocalDateTime.now());
//            member.setAge(i);
//            member.setInstId(7);
//            member.setComId(8);
//            member.setName("??????"+i+"???");
//            member.setPhone("12345890"+i);
//            member.setGender("???");
//            memberService.save(member);
//        }
//    }
}

