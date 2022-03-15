package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Member;
import com.wit.baojims.entity.Trans;
import com.wit.baojims.mapper.TransMapper;
import com.wit.baojims.service.TransService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.baojims.vo.TransGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 22:38 2022/2/28
 * @Param
 **/
@Service
public class TransServiceImpl extends ServiceImpl<TransMapper, Trans> implements TransService {

    @Autowired
    private TransMapper transMapper;

    @Override
    public IPage<Trans> selectTransPage(Integer page, Integer size, Integer comId) {
        //获取当前页和页面大小
        IPage<Trans> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Trans> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("in_com_id", comId).or().eq("out_com_id", comId);
        return transMapper.selectPage(iPage, queryWrapper);
    }

    @Override
    public List<TransGroupVo> groupByInMonth(Integer comId) {
        return transMapper.groupByInMonth(comId);
    }

    @Override
    public List<TransGroupVo> groupByOutMonth(Integer comId) {
        return transMapper.groupByOutMonth(comId);
    }

//    @Override
//    public IPage<Trans> selectTransPageByName(Integer page, Integer size, Integer comId, String name) {
//        //获取当前页和页面大小
//        IPage<Trans> iPage = new Page<>(page, size);
//        //无条件的话 默认查询所有
//        QueryWrapper<Trans> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like("")
//        return transMapper.selectPage(iPage, queryWrapper);
//    }
}
