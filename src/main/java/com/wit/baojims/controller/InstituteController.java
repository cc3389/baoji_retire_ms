package com.wit.baojims.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Institute;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.form.DeleteInsIdForm;
import com.wit.baojims.form.InstituteForm;
import com.wit.baojims.service.CommunityService;
import com.wit.baojims.service.InstituteService;
import com.wit.baojims.service.ManageService;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.insListVo;

import com.wit.baojims.vo.instituteVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@RestController
@Slf4j
@RequestMapping("/institute")
public class InstituteController {
    @Autowired
    private InstituteService instituteService;

    @Autowired
    private CommunityService communityService;

    @Autowired
    private ManageService manageService;

    /*
     * @Author Zeman
     * @Description //TODO
     * @Date 19:48 2022/2/25
     * @Param [id, page, size]
     * @return cn.dev33.satoken.util.SaResult
     **/
    @GetMapping("/list")
    public SaResult institutePage(@RequestParam("page") Integer Page, @RequestParam("size") Integer Size){

        Object loginId = StpUtil.getLoginId();
        Manage insId = manageService.getInsId(loginId);
        Integer comId = insId.getComId();

        IPage<Institute> iPage = instituteService.selectByPage(comId, Page, Size);
        long page = iPage.getPages();
        long totalPage = iPage.getTotal();
        long size = iPage.getSize();
        List<Institute> list = iPage.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("page",page);
        map.put("size",size);
        map.put("totalPage",totalPage);
        ArrayList<insListVo> insListVos = new ArrayList<>();
        for (Institute institute : list) {
            insListVo insListVo = new insListVo();
            insListVo.setId(institute.getInsId());
            insListVo.setName(institute.getName());
            insListVos.add(insListVo);
        }
        map.put("list",insListVos);
        return SaResult.data(map);
    }

    @GetMapping("/info")
    public SaResult detailInfo(@RequestParam("id") Integer id)  {
        if (id == null){
            return SaResult.code(300).setData("id不能为空");
        }
        Institute institute = instituteService.selectInfo(id);
        Community community = communityService.selectById(institute.getComId());
        instituteVo instituteVo = new instituteVo();
        instituteVo.setInsId(institute.getInsId());
        instituteVo.setAddress(institute.getAddress());
        instituteVo.setEmail(institute.getEmail());
        instituteVo.setSumPeople(institute.getSumPeople());
        instituteVo.setPhone(institute.getPhone());
        instituteVo.setName(institute.getName());
        instituteVo.setComName(community.getName());

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id",instituteVo.getInsId());
        map.put("name",instituteVo.getName());
        map.put("phone",instituteVo.getPhone());
        map.put("address",instituteVo.getAddress());
        map.put("sum",instituteVo.getSumPeople());
        map.put("email",instituteVo.getEmail());
        map.put("comName",instituteVo.getComName());

        return SaResult.data(map);
    }

    @PostMapping("/add")
    public SaResult add(@RequestBody InstituteForm instituteForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            HashMap<String, String> map = new HashMap<>();
            for (FieldError fieldError : fieldErrors) {
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return  SaResult.code(200).setData(map);
        }
        Community community = communityService.selectComByName(instituteForm.getComName());
        if ( community == null){
            return SaResult.code(300).setMsg("社区不存在");
        }
        Institute institute = new Institute();
        institute.setName(instituteForm.getName());
        institute.setAddress(instituteForm.getAddress());
        institute.setEmail(instituteForm.getEmail());
        institute.setPhone(instituteForm.getPhone());
        institute.setSumPeople(instituteForm.getSumPeople());
        institute.setComId(community.getComId());
        instituteService.addInstitute(institute);
        return SaResult.ok();
    }

    @PostMapping("/delete")
    public SaResult delete(@RequestBody DeleteInsIdForm deleteInsIdForm){
        log.info("delete_id",deleteInsIdForm.getId());
        int i = instituteService.deleteIns(deleteInsIdForm.getId());
        if (i>0){
            return SaResult.ok();
        }else {
            return SaResult.code(300).setMsg("删除出错啦");
        }

    }

    @PostMapping("/update")
    public SaResult update(@RequestBody InstituteForm instituteForm){
        Community community = communityService.selectComByName(instituteForm.getComName());
        if ( community == null){
            return SaResult.code(300).setMsg("社区不存在");
        }
        Institute institute = instituteService.selectInfo(instituteForm.getId());
        institute.setName(instituteForm.getName());
        institute.setAddress(instituteForm.getAddress());
        institute.setEmail(instituteForm.getEmail());
        institute.setPhone(instituteForm.getPhone());
        institute.setSumPeople(instituteForm.getSumPeople());
        institute.setComId(community.getComId());
        instituteService.updateIns(institute);
        return SaResult.ok();
    }

    @GetMapping("/suggestion")
    public SaResult suggestion (){
        Object loginId = StpUtil.getLoginId();
        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", loginId);
        Manage manage = manageService.getOne(queryWrapperManage);

        QueryWrapper<Institute> queryWrapperInstitute = new QueryWrapper<>();
        queryWrapperInstitute.eq("com_id", manage.getComId());
        List<Institute> instituteList = instituteService.list(queryWrapperInstitute);

        HashMap data = new HashMap();
        data.put("list", instituteList);
        return SaResult.ok().setData(data);
    }
}

