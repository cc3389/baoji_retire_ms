package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wit.baojims.entity.Manage;
import com.wit.baojims.mapper.ManageMapper;
import com.wit.baojims.service.ManageService;
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
public class ManageServiceImpl extends ServiceImpl<ManageMapper, Manage> implements ManageService {

    @Autowired
    private ManageMapper manageMapper;


    @Override
    public List<Manage> getComId(Object id) {
        QueryWrapper<Manage> manageQueryWrapper = new QueryWrapper<>();
        manageQueryWrapper.eq("com_id",id);
        return manageMapper.selectList(manageQueryWrapper);
    }

    @Override
    public int insert(Manage manage) {
        return manageMapper.insert(manage);
    }

    @Override
    public List<Manage> getIdList() {
        QueryWrapper<Manage> manageQueryWrapper = new QueryWrapper<>();
        return manageMapper.selectList(manageQueryWrapper);
    }


}
