package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
public interface AdminService extends IService<Admin> {

    IPage<Admin> selectAllAdmin(Integer page, Integer size);

    Admin selectOneById(Integer id);

    Admin selectOneAdmin(Object id);

    int updatePermission(Admin admin);

    int deleteAdmin(Integer id);

    Admin getInfo(Object id);
}
