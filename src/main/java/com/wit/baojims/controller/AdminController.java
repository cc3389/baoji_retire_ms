package com.wit.baojims.controller;



import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.Config.MD5Utils;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.County;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.AdminForm;
import com.wit.baojims.form.ChangePwdForm;
import com.wit.baojims.form.LoginForm;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.mapper.ManageMapper;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.*;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.adminListVo;
import com.wit.baojims.vo.adminVo;
import com.wit.baojims.vo.areaVo;
import com.wit.baojims.vo.comVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
/*
 * @Author Zeman
 * @Description //TODO
 * @Date 22:38 2022/2/26
 * @Param
 * @return
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private  ManageMapper manageMapper;
    @Autowired
    private ManageService manageService;
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CountyService countyService;




    @SaCheckRole("high")
    @GetMapping("/list")
    public SaResult adminInfo(@RequestParam("page") Integer page, @RequestParam("size") Integer size){
        IPage<Admin> adminIPage = adminService.selectAllAdmin(page, size);
        long pages = adminIPage.getPages();
        long total = adminIPage.getTotal();
        long size1 = adminIPage.getSize();
        List<Admin> records = adminIPage.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("size",size1);
        map.put("page",pages);
        map.put("totalPage",total);
        ArrayList<adminListVo> adminListVos = new ArrayList<>();
        for (Admin record : records) {
            adminListVo adminListVo = new adminListVo();
            adminListVo.setId(record.getAdminId());
            adminListVo.setName(record.getName());
            adminListVo.setPermission(record.getPerName());
            adminListVo.setAreaName(record.getManageArea());
            adminListVos.add(adminListVo);
        }
        map.put("list",adminListVos);
        return SaResult.ok().setData(map);
    }

    @SaCheckRole("high")
    @PostMapping("/create")
    public SaResult createAdmin(@Valid @RequestBody AdminForm adminForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            HashMap<String, String> map = new HashMap<String, String>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                map.put(field,message);
            }
            return SaResult.code(300).setData(map);
        }

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        List<Admin> admins = adminMapper.selectList(queryWrapper);
        for (Admin admin : admins) {
            if (admin.getName().equals(adminForm.getName()))
            {
                return SaResult.code(300).setMsg("用户名重复");
            }
        }

        Admin admin = new Admin();
        admin.setName(adminForm.getName());
        admin.setPassword(MD5Utils.getPwd("12345678"));
        String type = adminForm.getType();

        Manage manage = new Manage();
        if (type.equals("社区")){
            Community community = communityService.selectComByName(adminForm.getAreaName());
            if (community == null) return SaResult.code(300).setMsg("社区不存在");
            manage.setComId(community.getComId());
            admin.setPerName("low");
            admin.setManageArea(adminForm.getAreaName());
        }else {
            QueryWrapper<County> w = new QueryWrapper<>();
            w.eq("county_name",adminForm.getAreaName());
            County county = countyService.getOne(w);
            if (county == null) return SaResult.code(300).setMsg("区县不存在");
            manage.setCountyId(county.getCountyId());
            admin.setPerName("mid");
            admin.setManageArea(adminForm.getAreaName());
        }
        adminMapper.insert(admin);
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("name",adminForm.getName());
        Admin one = adminService.getOne(adminQueryWrapper);
        manage.setAdminId(one.getAdminId());
        manageMapper.insert(manage);
        return SaResult.ok();
    }

    @SaCheckRole("high")
    @PostMapping("/revoke")
    public SaResult revokePermission(@RequestBody AdminForm adminForm){
        if (adminForm.getId() == null){
            return SaResult.code(300).setData("id不能为空");
        }
        adminService.deleteAdmin(adminForm.getId());
        return SaResult.ok();
    }

    @SaCheckRole("high")
    @PostMapping("/grant")
    public SaResult updatePer(@Valid @RequestBody AdminForm adminForm,BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            HashMap<String, String> map = new HashMap<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                map.put(field,defaultMessage);
                return SaResult.code(300).setData(map);
            }
        }

        Admin one = adminService.selectOneById(adminForm.getId());
        if (adminForm.getType().equals("社区")){
            one.setPerName("low");
        }else if(adminForm.getType().equals("区县")){
            one.setPerName("mid");
        }else {
            return SaResult.code(300).setMsg("区域不存在");
        }

        one.setManageArea(adminForm.getAreaName());
        adminService.updatePermission(one);
        return  SaResult.ok();
    }

    @SaCheckRole("high")
    @GetMapping("/info")
    public SaResult getInfo(){
        Object loginId = StpUtil.getLoginId();
        if (loginId == null){
            return SaResult.code(300).setMsg("您还未登录");
        }
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Admin one = adminService.getOne(queryWrapper);
//        Admin one = adminService.selectOneAdmin(loginId);
        if (one == null){
            return SaResult.code(300).setMsg("用户名不存在");
        }
        adminVo adminVo = new adminVo();
        adminVo.setName(one.getName());
        adminVo.setRoles(one.getPerName());
//        BeanUtils.copyProperties(one,adminVo);
        return SaResult.ok().setData(adminVo);

    }

    //搜索社区和县
    /**
     * @Author Shawn Yue
     * @Description //TODO Shawn Yue
     * @Date 18:41 2022/3/6
     * @Param [admin, bindingResult]
     * @return cn.dev33.satoken.util.SaResult
     **/
    @PostMapping("/login")
    public SaResult doLogin(@Valid @RequestBody LoginForm admin, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【用户登录】用户信息不能为空");
            throw new BaojiException(ResponseEnum.ADMIN_INFO_NULL);
        }

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("name", admin.getUsername());
        Admin one = adminService.getOne(queryWrapper);
        String pwd = MD5Utils.getPwd(admin.getPassword());

        if(one == null) {
            return SaResult.code(300).setMsg("用户不存在");//判断用户名是否存在
        }
        SaResult saResult = null;
        if(pwd.equals(one.getPassword())){  //根据用户名查询到的用户的密码是否与传入的密码是否一致
            StpUtil.login(one.getAdminId());
            HashMap vo = new HashMap();
            vo.put("token", StpUtil.getTokenValue());
            return SaResult.ok().setData(vo);
        } else {
            return SaResult.code(300).setMsg("用户名或密码错误");
        }
    }

    @GetMapping("/suggestion")
    public SaResult getAllCommunityAndCounty() {
        List<Manage> manageList = manageService.list();

        List<Integer> comIdList = new ArrayList<>();
        for (Manage manage : manageList) {
            if (manage.getComId() != null){
                comIdList.add(manage.getComId());
            }
        }
        List<Integer> countyIdList = new ArrayList<>();
        for (Manage manage : manageList) {
            if (manage.getCountyId() != null){
                countyIdList.add(manage.getCountyId());
            }
        }

        QueryWrapper<Community> queryWrapperCommunity = new QueryWrapper<>();
        queryWrapperCommunity.notIn("com_id", comIdList);
        QueryWrapper<County> queryWrapperCounty = new QueryWrapper<>();
        queryWrapperCounty.notIn("county_id", countyIdList);

        List<Community> communityList = communityService.list(queryWrapperCommunity);
        List<County> countyList = countyService.list(queryWrapperCounty);

        HashMap<String, Object> map = new HashMap<>();
        ArrayList<comVo> comVos = new ArrayList<>();
        for (Community community : communityList) {
            comVo comVo = new comVo();
            comVo.setValue(community.getName());
            comVos.add(comVo);
        }
        ArrayList<areaVo> areaVos = new ArrayList<>();
        for (County county : countyList) {
            areaVo areaVo = new areaVo();
            areaVo.setValue(county.getCountyName());
            areaVos.add(areaVo);
        }
        map.put("community",comVos);
        map.put("area",areaVos);

        return SaResult.data(map);
//
//        if (idList == null){
//            return SaResult.code(300);
//        }
//
//        List<Community> communityList = communityService.selectAllCommunity(idList);
//        List<County> countyList = countyService.selectAllCounty(idList);
//
//        if (communityList == null || countyList == null){
//            return SaResult.code(300).setMsg("暂无地区可分配");
//        }else {
//            HashMap<String, Object> map = new HashMap<>();
//            ArrayList<comVo> comVos = new ArrayList<>();
//
//            for (Community community : communityList) {
//                comVo comVo = new comVo();
//                comVo.setValue(community.getName());
//                comVos.add(comVo);
//            }
//            ArrayList<areaVo> areaVos = new ArrayList<>();
//            for (County county : countyList) {
//                areaVo areaVo = new areaVo();
//                areaVo.setValue(county.getCountyName());
//                areaVos.add(areaVo);
//            }
//            map.put("community",comVos);
//            map.put("area",areaVos);
//            return SaResult.data(map);
//        }

    }

    @PostMapping("/changePwd")
    public SaResult changePwd (@Valid @RequestBody ChangePwdForm changePwdForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【修改密码】修改密码信息不能为空");
            throw new BaojiException(ResponseEnum.CHANGE_PWD_NULL);
        }

        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Admin> queryWrapperAdmin = new QueryWrapper<>();
        queryWrapperAdmin.eq("admin_id", loginId);
        Admin admin = adminService.getOne(queryWrapperAdmin);

        if (!(admin.getPassword().equals(MD5Utils.getPwd(changePwdForm.getOldPwd())))){
           return SaResult.code(300).setMsg("旧密码输入不正确");
        }
        else {
            admin.setPassword(MD5Utils.getPwd(changePwdForm.getNewPwd()));
            adminService.updateById(admin);
            return SaResult.ok();
        }
    }

    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @SaCheckRole("low")
    @RequestMapping(value = "/add", produces = "text/html;charset=utf-8")
    public String add() {
        return "add!";
    }
}

