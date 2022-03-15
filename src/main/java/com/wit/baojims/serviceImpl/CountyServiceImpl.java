package com.wit.baojims.serviceImpl;/**
 * @author: zz
 * createTime: 2022/2/26 20:15
 * description:
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.wit.baojims.entity.County;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.mapper.ActivityMapper;
import com.wit.baojims.mapper.CountyMapper;
import com.wit.baojims.service.ActivityService;
import com.wit.baojims.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author Zeman
 * @Description //TODO
 * @Date 20:15 2022/2/26
 * @Param
 * @return
 **/
@Service
public class CountyServiceImpl extends ServiceImpl<CountyMapper, County> implements CountyService {

    @Autowired
    private CountyMapper countyMapper;

    @Override
    public int insertCounty(String name) {
        County county = new County();
        county.setCountyName(name);
        return countyMapper.insert(county);

    }

    @Override
    public List<County> selectAllCounty(List<Manage> manages) {
        QueryWrapper<County> wrapper = new QueryWrapper<>();

        for (Manage manage : manages) {
            Integer countyId = manage.getCountyId();
            wrapper.or().ne("county_id",countyId);
        }
        return countyMapper.selectList(wrapper);
    }
}
