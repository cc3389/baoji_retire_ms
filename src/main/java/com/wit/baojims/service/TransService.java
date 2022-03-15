package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Member;
import com.wit.baojims.entity.Trans;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.vo.TransGroupVo;

import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 19:16 2022/2/28
 * @Param
 **/
public interface TransService extends IService<Trans> {
    IPage<Trans> selectTransPage(Integer page, Integer size, Integer comId);
    public List<TransGroupVo> groupByInMonth(Integer comId);
    public List<TransGroupVo> groupByOutMonth(Integer comId);
//    IPage<Trans> selectTransPageByName(Integer page, Integer size, Integer comId, String name);
}
