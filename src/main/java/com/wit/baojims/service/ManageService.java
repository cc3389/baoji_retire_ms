package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.County;
import com.wit.baojims.entity.Manage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
public interface ManageService extends IService<Manage> {


    List<Manage> getComId(Object id);

    int insert(Manage manage);

    List<Manage> getIdList();

}
