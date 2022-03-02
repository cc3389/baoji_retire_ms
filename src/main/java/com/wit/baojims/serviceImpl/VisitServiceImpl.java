package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Visit;
import com.wit.baojims.mapper.VisitMapper;
import com.wit.baojims.service.VisitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 22:38 2022/2/28
 * @Param
 **/
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {

    @Autowired
    private VisitMapper visitMapper;

    @Override
    public IPage<Visit> selectVisitPage(Integer page, Integer size) {
        //获取当前页和页面大小
        IPage<Visit> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Visit> queryWrapper = new QueryWrapper<>();
        return visitMapper.selectPage(iPage, queryWrapper);
    }
}
