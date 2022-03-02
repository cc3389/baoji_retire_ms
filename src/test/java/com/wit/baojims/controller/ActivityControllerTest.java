package com.wit.baojims.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.mapper.ActivityMapper;
import com.wit.baojims.service.ActivityService;
import com.wit.baojims.serviceImpl.ActivityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ActivityControllerTest {

    @Autowired
    private ActivityService activityService;

    @Test
    void one() {

        QueryWrapper<Activity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("act_id", 1);

        Activity one = activityService.getOne(queryWrapper);

        System.out.println(one);
    }
}