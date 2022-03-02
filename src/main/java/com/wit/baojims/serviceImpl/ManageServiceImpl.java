package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.mapper.ManageMapper;
import com.wit.baojims.service.ManageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@Service
public class ManageServiceImpl extends ServiceImpl<ManageMapper, Manage> implements ManageService {

    @Autowired
    private ManageMapper manageMapper;

    @Override
    public Manage getInsId(Object id) {
        QueryWrapper<Manage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id",id);
        return manageMapper.selectOne(queryWrapper);
    }
}
