package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Member;
import com.wit.baojims.mapper.MemberMapper;
import com.wit.baojims.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public IPage<Member> selectMemberFeePage(Integer page,Integer size) {
        //获取当前页和页面大小
        IPage<Member> iPage = new Page<>(page,size);
        //无条件查询所有
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.select("peo_id","name","fee");
        return memberMapper.selectPage(iPage, wrapper);
    }
}
