package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Visit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.vo.TransGroupVo;
import com.wit.baojims.vo.VisitGroupVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
public interface VisitService extends IService<Visit> {
    IPage<Visit> selectVisitPage(Integer page, Integer size, Admin admin);
//    public List<VisitGroupVo> groupByYear(Integer comId);
}
