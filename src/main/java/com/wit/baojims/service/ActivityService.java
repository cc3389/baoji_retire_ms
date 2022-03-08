package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.entity.Manage;

import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 13:51 2022/2/27
 * @Param 
 * @return 
 **/
public interface ActivityService extends IService<Activity> {
    IPage<Activity> selectActivityPage(Integer page, Integer size, List<Integer> comIdList);
}
