package com.wit.baojims.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.Config.MD5Utils;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.exception.BaojiException;
import com.wit.baojims.form.LoginForm;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.result.ResponseEnum;
import com.wit.baojims.service.AdminService;
import com.wit.baojims.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;
import java.util.HashMap;

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
    private MemberService memberService;

    @PostMapping("/login")
    public SaResult doLogin(@Valid @RequestBody LoginForm admin, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("【用户登录】用户信息不能为空");
            throw new BaojiException(ResponseEnum.ADMIN_INFO_NULL);
        }

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
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("name", admin.getUsername());
        Admin one = adminService.getOne(queryWrapper);
        String pwd = MD5Utils.getPwd(admin.getPassword());

        if(one == null) return SaResult.code(300).setMsg("用户不存在");//判断用户名是否存在
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

