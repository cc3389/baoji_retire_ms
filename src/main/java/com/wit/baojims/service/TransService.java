package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Member;
import com.wit.baojims.entity.Trans;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 19:16 2022/2/28
 * @Param
 **/
public interface TransService extends IService<Trans> {
    IPage<Trans> selectTransPage(Integer page, Integer size, Integer comId);
//    IPage<Trans> selectTransPageByName(Integer page, Integer size, Integer comId, String name);
}
