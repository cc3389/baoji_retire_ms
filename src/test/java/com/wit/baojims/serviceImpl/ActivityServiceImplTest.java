package com.wit.baojims.serviceImpl;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.mapper.ActivityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ActivityServiceImplTest {

    @Autowired
    private ActivityMapper activityMapper;
    @Test
    void selectActivityPage() {
        Integer page = 1;
        Integer size = 10;
        List<Integer> comIdList = new ArrayList<>();
        comIdList.add(8);
        comIdList.add(11);
        //获取当前页和页面大小
        IPage<Activity> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", comIdList);
        System.out.println(activityMapper.selectPage(iPage, queryWrapper));
    }
}