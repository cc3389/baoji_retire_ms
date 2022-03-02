package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.mapper.ActivityMapper;
import com.wit.baojims.service.ActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 13:58 2022/2/27
 * @Param
 **/
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public IPage<Activity> selectActivityPage(Integer page, Integer size, Integer comId) {
        //获取当前页和页面大小
        IPage<Activity> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", comId);
        return activityMapper.selectPage(iPage, queryWrapper);
    }
}
