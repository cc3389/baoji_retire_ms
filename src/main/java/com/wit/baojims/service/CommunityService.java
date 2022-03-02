package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 11:13 2022/3/1
 * @Param 
 * @return
 **/
public interface CommunityService extends IService<Community> {
    IPage<Community> selectCommunityPage(Integer page, Integer size);
}
