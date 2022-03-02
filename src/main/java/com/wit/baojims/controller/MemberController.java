package com.wit.baojims.controller;


import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Member;
import com.wit.baojims.mapper.MemberMapper;
import com.wit.baojims.service.MemberService;
import com.wit.baojims.utils.BeanCopyUtil;
import com.wit.baojims.vo.memberVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    
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
}

