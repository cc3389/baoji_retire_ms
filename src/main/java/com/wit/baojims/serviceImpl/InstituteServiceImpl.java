package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.baojims.entity.Institute;
import com.wit.baojims.mapper.InstituteMapper;
import com.wit.baojims.service.InstituteService;
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
public class InstituteServiceImpl extends ServiceImpl<InstituteMapper, Institute> implements InstituteService {

    @Autowired
    private InstituteMapper instituteMapper;

    @Override
    public IPage<Institute> selectByPage(List<Object> comIdList,Integer page,Integer size) {
        IPage<Institute> iPage = new Page<>(page,size);
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        for (Object id : comIdList) {
            wrapper.or().eq("com_id",id);
        }
        wrapper.select("ins_id","name");
        return instituteMapper.selectPage(iPage, wrapper);
    }

    @Override
    public Institute selectInfo(Integer id) {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        wrapper.eq("ins_id",id);
        return instituteMapper.selectOne(wrapper);
    }

    @Override
    public Institute select(Integer id) {
        QueryWrapper<Institute> wrapper = new QueryWrapper<>();
        return instituteMapper.selectOne(wrapper);
    }

    @Override
    public int addInstitute(Institute institute) {
        return instituteMapper.insert(institute);
    }

    @Override
    public int deleteIns(Integer id) {
        return instituteMapper.deleteById(id);
    }

    @Override
    public int updateIns(Institute institute) {
        return instituteMapper.updateById(institute);
    }





}
