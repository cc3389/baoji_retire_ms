package com.wit.baojims.serviceImpl;

import cn.hutool.db.DbUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Member;
import com.wit.baojims.mapper.ManageMapper;
import com.wit.baojims.mapper.MemberMapper;
import com.wit.baojims.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.namespace.QName;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransServiceImplTest {

    @Autowired
    private MemberService memberService;
    @Test
    void selectTransPage() {
        MemberService memberService = new MemberServiceImpl();
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
//        queryWrapper
//                .lt("age","20")
//                .or()
//                .gt("age","50");
        Map<String, Object> map = memberService.getMap(queryWrapper);
        System.out.println(map.toString());
    }
}