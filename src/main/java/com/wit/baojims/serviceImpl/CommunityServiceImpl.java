package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Community;
import com.wit.baojims.mapper.CommunityMapper;
import com.wit.baojims.service.CommunityService;
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
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;
    @Override
    public int insertCommunity(String name) {
        Community community = new Community();
        community.setName(name);
        community.setVisCount(0);
        return communityMapper.insert(community);
    }

    @Override
    public List<Community> selectAllCommunity() {
        return communityMapper.selectAll();
    }

    @Override
    public Community selectComByName(String comName) {
        QueryWrapper<Community> communityQueryWrapper = new QueryWrapper<>();
        communityQueryWrapper.eq("name",comName);
        return communityMapper.selectOne(communityQueryWrapper);
    }

    @Override
    public Community selectById(Integer id) {
        return communityMapper.selectById(id);
    }
}
