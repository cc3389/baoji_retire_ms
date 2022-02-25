//package com.wit.baojims.pj;
//
//import cn.dev33.satoken.stp.StpInterface;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义权限验证接口扩展
// */
//@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
//public class StpInterfaceImpl implements StpInterface {
//    @Autowired
//    private  adminInfoMapper;
//
//    /**
//     * 返回一个账号所拥有的权限码集合
//     */
//    @Override
//    public List<String> getPermissionList(Object loginId, String loginType) {
//        return null;
//    }
//
//    /**
//     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
//     * @return
//     */
//    @Override
//    public List<String> getRoleList(Object loginId, String loginType) {
//        QueryWrapper<AdminInfo> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("admin_id", loginId);
//        AdminInfo adminInfo = adminInfoMapper.selectOne(queryWrapper);
//        List<String> perList = new ArrayList<>();
//        perList.add(adminInfo.getPerName());
//        return perList;
//    }
//
//}