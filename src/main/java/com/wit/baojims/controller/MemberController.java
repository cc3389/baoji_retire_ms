package com.wit.baojims.controller;


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
import java.util.ArrayList;
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

    @PostMapping("/insert")
    public SaResult insert(@Valid MemberForm memberForm, BindingResult bindingResult, @RequestParam(value = "avatar") MultipartFile avatar) throws IOException {

        String originalFilename = avatar.getOriginalFilename();
        String fileName = originalFilename.replace(" ", "");
        avatar.transferTo(new File("D:\\baoji_retire_ms\\src\\main\\resources\\static\\"+fileName));

        if (bindingResult.hasErrors()){
            log.info("【人员录入】人员信息不能为空");
            throw new BaojiException(ResponseEnum.MEMBER_INFO_NULL);
        }

        //判断传入机构是否存在
        QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
        queryWrapperInstitute.eq("name", memberForm.getInsName());
        if(instituteService.getOne(queryWrapperInstitute) == null){
            return SaResult.code(300).setMsg("查无机构信息");
        }

        // LocalDate转换LocalDateTime
        LocalDateTime localDateTime = memberForm.getBirthday().atStartOfDay();
        Member member = new Member();
        BeanUtils.copyProperties(memberForm, member);       //属性复制 封装成一个Member实体
        member.setBirth(localDateTime);

        QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
        queryWrapperMember.eq("name", member.getName());
        Member one = memberService.getOne(queryWrapperMember);
        // 判断人员是否已经录入
        if(one == null){
            Object loginId = StpUtil.getLoginId();
            log.info(loginId.toString());
            QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
            queryWrapperManage.eq("admin_id", loginId);
            Manage manage = manageService.getOne(queryWrapperManage);     //根据token查询到登录的管理员
            QueryWrapper<Institute> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("name", memberForm.getInsName());
            Institute institute = instituteService.getOne(queryWrapper1);   //查询登录的管理员管理的机构信息

            // 计算年龄  继续封装Member实体
            member.setAge(LocalDateTime.now().getYear()-memberForm.getBirthday().getYear());
            member.setIsDeath("否");
            member.setComId(manage.getComId());
            member.setInstId(institute.getInsId());
            member.setAvatar(fileName);

            memberService.save(member);
            return SaResult.ok();
        } else {
            return SaResult.code(300).setMsg("人员信息已经存在");
        }
    }

    @GetMapping("/page")
    public SaResult page(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent, @RequestParam(required = false, value = "name") String name){
        if(pageCurrent == null) {
            pageCurrent = 1;
        }
        if(sizeCurrent == null) {
            sizeCurrent = 10;
        }

        if (name.equals("")){
            Object loginId = StpUtil.getLoginId();
            QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("admin_id", loginId);
            Manage one = manageService.getOne(queryWrapper);
            IPage<Member> iPage = memberService.selectMemberPage(pageCurrent, sizeCurrent, one.getComId());

            // 得到当前页、总页数、页面大小
            List<Member> memberList = iPage.getRecords();
            List<MemberPageVo> memberPageVoList =BeanCopyUtil.copyListProperties(memberList, MemberPageVo::new);
            HashMap data = new HashMap();
            data.put("page", iPage.getCurrent());
            data.put("size", iPage.getSize());
            data.put("totalPage", iPage.getTotal());

            if(memberList == null){
                return SaResult.code(300).setMsg("查无信息");
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

            // 得到当前页、总页数、页面大小
            List<Member> memberList = iPage.getRecords();
            HashMap data = new HashMap();
            data.put("page", iPage.getCurrent());
            data.put("size", iPage.getSize());
            data.put("totalPage", iPage.getTotal());

            if(memberList == null){
                return SaResult.code(300).setMsg("查无信息");
            }

            data.put("list", memberList);

            return SaResult.ok().setData(data);
        }
    }

    @GetMapping("/one")
    public SaResult one(@RequestParam Integer peoId) throws UnknownHostException {
        if(peoId == null) return SaResult.code(300).setMsg("人员id不能为空");

        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", peoId);
        Member one = memberService.getOne(queryWrapper);

        if(one == null){
            return SaResult.code(300).setMsg("查无信息");
        }
        MemberOneVo memberVo = new MemberOneVo();
        BeanUtils.copyProperties(one, memberVo);//属性复制

        QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
        queryWrapperInstitute.eq("ins_id", one.getInstId());
        Institute institute = instituteService.getOne(queryWrapperInstitute);
        memberVo.setInsName(institute.getName());

        HashMap data = new HashMap();
        InetAddress ia = InetAddress.getLocalHost();
        String ip = ia.getHostAddress();//获取IP
        memberVo.setAvatar("http://"+ip+":8080/static/"+one.getAvatar());
        data.put("list", memberVo);
        return SaResult.ok().setData(data);
    }

    @PostMapping("/update")
    public SaResult update (@Valid @RequestBody MemberUpdateForm memberUpdateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            log.info("【修改人员信息】人员信息不能为空");
            throw new BaojiException(ResponseEnum.MEMBER_INFO_NULL);
        }
        //根据传入的peo_id查询到人员的详细信息 做下一步信息的修改
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", memberUpdateForm.getId());
        log.info(memberUpdateForm.getId().toString());
        Member one = memberService.getOne(queryWrapper);
        log.info(one.toString());

        if(one == null){
            return SaResult.code(300).setMsg("查询不到人员信息");
        }
        //传入部分信息可能为空
        if(memberUpdateForm.getName() != null) one.setName(memberUpdateForm.getName());
        if(memberUpdateForm.getPhone() != null) one.setPhone(memberUpdateForm.getPhone());
        if(memberUpdateForm.getAvatar() != null) one.setAvatar(memberUpdateForm.getAvatar());
        if(memberUpdateForm.getGender() != null) one.setGender(memberUpdateForm.getGender());
        if(memberUpdateForm.getBirth() != null) one.setBirth(memberUpdateForm.getBirth().atStartOfDay());

        memberService.updateById(one);
        return SaResult.ok();
    }

    @PostMapping("/out")
    public SaResult out(@RequestBody JSONObject data){
        if(data.getInt("id") == null){
            SaResult.code(300).setMsg("人员id为空");
        }

        QueryWrapper<Trans> queryWrapperTest = new QueryWrapper<>();
        queryWrapperTest.eq("peo_id", data.getInt("id")).ne("status", "通过");
        Trans test = transService.getOne(queryWrapperTest);
        if(test != null && test.getStatus().equals("待转移")){
            return SaResult.code(300).setMsg("上次转入转出未审核完成");
        }

        //传入参数的异常判断
        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.eq("name", data.getStr("community"));
        Community inCom = communityService.getOne(queryWrapperCommunity);
        if(inCom == null){
            return SaResult.code(300).setMsg("社区不存在");
        }

        //根据token得到当前社区 作为outCom
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Manage one2 = manageService.getOne(queryWrapper);

        //根据得到的outComId 查询outComName
        QueryWrapper<Community> queryWrapperCommunity1 = new QueryWrapper<>();
        queryWrapperCommunity1.eq("com_id", one2.getComId());
        Community outCom = communityService.getOne(queryWrapperCommunity1);

        //新建一张转入转出表
        Trans trans = new Trans();
        trans.setOutComId(outCom.getComId());
        trans.setInComId(inCom.getComId());
        trans.setPeoId(data.getInt("id"));
        trans.setOutDate(LocalDateTime.now());
        trans.setStatus("待转移");
        transService.save(trans);
        return SaResult.ok();
    }

    @PostMapping("/in")
    public SaResult in(@RequestBody JSONObject data){
        if(data.getStr("id") == null) return SaResult.code(300).setMsg("人员信息为空");

        //根据token查询到管理的manage信息
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", StpUtil.getLoginId());
        Manage one = manageService.getOne(queryWrapperManage);

        //根据proId查询到转入转出表信息
        QueryWrapper<Trans> queryWrapperTrans = new QueryWrapper<>();
        queryWrapperTrans.eq("peo_id", data.getInt("id")).ne("status", "通过").ne("status", "拒绝");
        Trans one1 = transService.getOne(queryWrapperTrans);

        if(!data.getBool("status")) one1.setStatus("拒绝");// 拒绝转入

        else if(data.getBool("status")){   // 同意转入
            //设置人员的转入的各类信息 并更新数据库
            one1.setInDate(LocalDateTime.now());
            one1.setStatus("通过");

            QueryWrapper<Member> queryWrapperMember = new QueryWrapper<>();
            queryWrapperMember.eq("peo_id", one1.getPeoId());
            Member member = memberService.getOne(queryWrapperMember);
            member.setComId(one1.getInComId());
            memberService.updateById(member);
        } else {
            return SaResult.code(300).setMsg("操作失败");
        }

        transService.updateById(one1);
        return SaResult.ok();
    }

    @PostMapping("/death")
    public SaResult death(@RequestBody JSONObject id){
        if(id.getInt("id") == null) return SaResult.code(300).setMsg("人员信息为空");

        //根据传入的peoId查询到人员信息
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("peo_id", id.getInt("id"));
        Member one = memberService.getOne(queryWrapper);

        if(one == null){
            return SaResult.code(300).setMsg("查无信息");
        }
        if(one.getIsDeath().equals("是")){
            return SaResult.code(300).setMsg("已经死过了");
        }
        //设置人员是否死亡信息为死亡 并跟新数据库
        one.setIsDeath("是");
        memberService.updateById(one);
        return SaResult.ok();
    }

    @GetMapping("/deathPage")
    public SaResult deathPage(@RequestParam("page") Integer pageCurrent,@RequestParam("size") Integer sizeCurrent){
        if(pageCurrent == null) pageCurrent = 1;
        if(sizeCurrent == null) sizeCurrent = 10;

        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Manage one = manageService.getOne(queryWrapper);
        IPage<Member> iPage = memberService.selectMemberDeathPage(pageCurrent, sizeCurrent, one.getComId());

        // 得到当前页、总页数、页面大小
        List<Member> memberList = iPage.getRecords();
        HashMap data = new HashMap();
        data.put("page", iPage.getCurrent());
        data.put("size", iPage.getSize());
        data.put("totalPage", iPage.getTotal());

        if(memberList == null){
            return SaResult.code(300).setMsg("查无信息");
        }

        // 转换成前端所需要的数据
        for(Member member : memberList){
            if(member.getIsDeath().equals("是")){
                member.setIsDeath("死亡");
            }
            if(member.getIsDeath().equals("否")){
                member.setIsDeath("健康");
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
    @GetMapping("/feePage")
    public SaResult getMemberFeePage(@RequestParam("page") Integer Page, @RequestParam("size") Integer Size){
        //得到iPage
        IPage<Member> iPage = memberService.selectMemberFeePage(Page, Size);
        //通过其方法得到 list、current、total
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
        //返回给前端
        return SaResult.ok().setData(map);
    }

//    @RequestMapping("/asd")
//    public void asd(){
//        for (int i=1;i<=50;i++){
//            Member member = new Member();
//            member.setFee(50.0);
//            member.setIsDeath("否");
//            member.setAvatar("asd.jpg");
//            member.setBirth(LocalDateTime.now());
//            member.setAge(i);
//            member.setInstId(7);
//            member.setComId(8);
//            member.setName("张三"+i+"号");
//            member.setPhone("12345890"+i);
//            member.setGender("男");
//            memberService.save(member);
//        }
//    }
}

