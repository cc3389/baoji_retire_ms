package com.wit.baojims.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.BaojimsApplication;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.mapper.ActivityMapper;
import com.wit.baojims.service.ActivityService;
import com.wit.baojims.serviceImpl.ActivityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.HasItemInArray;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaojimsApplication.class)
class ActivityControllerTest {

    @Autowired
    private ActivityService activityService;

    public void setActivityService(ActivityService activityService){
        this.activityService = activityService;
    }

    @Test
    void page() {
//        System.out.println("进入Test");
        Integer page = 1;
        Integer size = 10;
        List<Integer> comIdList = new ArrayList<>();
        comIdList.add(8);
        IPage<Activity> iPage = activityService.selectActivityPage(page, size, comIdList);

        List<Activity> testList = new ArrayList<>();
        Activity activity1 = new Activity();
        activity1.setName("金马区");
        activity1.setComId(8);
        activity1.setDescription("123");
        activity1.setDate(LocalDateTime.of(2021,02,27,16,00,00));
        activity1.setActId(4);

        Activity activity2 = new Activity();
        activity2.setName("探望老人");
        activity2.setComId(8);
        activity2.setDescription("欣慰");
        activity2.setDate(LocalDateTime.of(2021,02,27,16,00,00));
        activity2.setActId(8);
        testList.add(activity1);
        testList.add(activity2);

//        MatcherAssert.assertThat(String.valueOf(iPage.getRecords()), equals(testList));
//        Assert.assert(iPage.getTotal() == null);
//        MatcherAssert.assertThat(iPage.getTotal(), new GreaterThan(0));
//        Assert.assertArrayEquals(iPage.getRecords());
//        assert iPage.getTotal()!=0 : "成功";
    }


    @Test
    void add() {
    }
}