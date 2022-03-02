package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.mapper.CommunityMapper;
import com.wit.baojims.service.CommunityService;
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
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public IPage<Community> selectCommunityPage(Integer page, Integer size) {
        //获取当前页和页面大小
        IPage<Community> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("com_id", "name");
        return communityMapper.selectPage(iPage, queryWrapper);
    }
}
