package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.Member;
import com.wit.baojims.mapper.MemberMapper;
import com.wit.baojims.service.MemberService;
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
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public IPage<Member> selectMemberPage(Integer page, Integer size, Integer currentComId) {
        //获取当前页和页面大小
        IPage<Member> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", currentComId);
        queryWrapper.select("peo_id", "name");
        return memberMapper.selectPage(iPage, queryWrapper);
    }

    @Override
    public IPage<Member> selectMemberDeathPage(Integer page, Integer size, Integer currentComId) {
        //获取当前页和页面大小
        IPage<Member> iPage = new Page<>(page, size);
        //无条件的话 默认查询所有
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("com_id", currentComId);
        queryWrapper.select("peo_id", "name", "birth", "is_death");
        return memberMapper.selectPage(iPage, queryWrapper);
    }
}
