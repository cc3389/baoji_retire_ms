package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.Config.MD5Utils;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.County;
import com.wit.baojims.form.AdminForm;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.mapper.CountyMapper;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.CountyService;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.adminListVo;
import com.wit.baojims.vo.adminVo;
import com.wit.baojims.vo.areaVo;
import com.wit.baojims.vo.comVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardServletPartUtils;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private AdminMapper adminMapper;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private CountyService countyService;

    @Autowired
    private AdminService adminService;



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
        List<adminListVo> adminListVos = BeanCopyUtil.copyListProperties(records, adminListVo::new);
        map.put("list",adminListVos);
        return SaResult.ok().setData(map);
    }

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
        Admin admin = new Admin();
        admin.setName(adminForm.getName());
        admin.setPassword(MD5Utils.getPwd("12345678"));
        String type = adminForm.getType();
        if (type.equals("com")){
            List<Community> communities = communityService.selectAllCommunity();
            for (Community community : communities) {
                if (community.getName().equals(adminForm.getAreaName())){
                    admin.setPerName("low");
                    admin.setManageArea(adminForm.getAreaName());
                    break;
                }else{
                    return SaResult.code(300).setMsg("区域不存在");
                }
            }
        }else if(type.equals("county")){
            List<County> counties = countyService.selectAllCounty();
            for (County county : counties) {
                if (county.getCountyName().equals(adminForm.getAreaName())){
                    admin.setPerName("mid");
                    admin.setManageArea(adminForm.getAreaName());
                    break;
                }else {
                    return SaResult.code(300).setMsg("区域不存在");
                }
            }
        }else{
            return SaResult.code(300).setMsg("区域类型有误");
        }
        adminMapper.insert(admin);
        return SaResult.ok();
    }

    @GetMapping("/revoke")
    public SaResult revokePermission(@RequestParam("id") Integer id){
        if (id == null){
           return SaResult.code(300).setData("id不能为空");
        }
        adminService.deleteAdmin(id);
        return SaResult.ok();
    }

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
       if (adminForm.getType().equals("com")){
           one.setPerName("low");
       }else if(adminForm.getType().equals("county")){
           one.setPerName("mid");
       }else {
           return SaResult.code(300).setMsg("区域不存在");
       }

       one.setManageArea(adminForm.getAreaName());
       adminService.updatePermission(one);
       return  SaResult.ok();
    }

    @GetMapping("/info")
    public SaResult getInfo(){
        Integer loginId = (Integer) StpUtil.getLoginId();
        if (loginId == null){
            SaResult.code(300).setMsg("您还未登录");
        }
        Admin one = adminService.selectOneAdmin(loginId);
        adminVo adminVo = new adminVo();
        BeanUtils.copyProperties(one,adminVo);
        return SaResult.ok().setData(adminVo);

    }

    //搜索社区和县
    @GetMapping("/suggestion")
    public SaResult getAllCommunityAndCounty(){
        List<Community> communityList = communityService.selectAllCommunity();
        List<County> countyList = countyService.selectAllCounty();

        if (communityList == null && countyList == null){
            return SaResult.code(300).setMsg("数据为空");
        }
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
    }
}

