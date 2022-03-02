package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Community;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.PrimitiveIterator;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 11:13 2022/3/1
 * @Param
 * @return
 **/
public interface MemberService extends IService<Member> {
    IPage<Member> selectMemberFeePage(Integer page, Integer size);
    IPage<Member> selectMemberPage(Integer page, Integer size, Integer currentComId);
    IPage<Member> selectMemberDeathPage(Integer page, Integer size, Integer currentComId);
}
