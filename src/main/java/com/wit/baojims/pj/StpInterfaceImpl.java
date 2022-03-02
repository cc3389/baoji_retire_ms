package com.wit.baojims.pj;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.wit.baojims.entity.Admin;
import com.wit.baojims.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }


    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", loginId);
        Admin adminInfo = adminMapper.selectOne(queryWrapper);
        List<String> perList = new ArrayList<>();
        perList.add(adminInfo.getPerName());
        return perList;
    }

}