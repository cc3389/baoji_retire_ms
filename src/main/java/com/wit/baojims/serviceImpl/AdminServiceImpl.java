package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.Admin;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.service.AdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public IPage<Admin> selectAllAdmin(Integer page,Integer size) {
        IPage<Admin> iPage = new Page<>(page,size);
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.select("admin_id","per_name","name","manage_area");
        wrapper.ne("per_name","high");
        return adminMapper.selectPage(iPage,wrapper);
    }

    @Override
    public Admin selectOneById(Integer id) {
        return adminMapper.selectById(id);
    }

    @Override
    public Admin selectOneAdmin(Object id) {
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.select("admin_id","name");
        adminMapper.selectById(id.toString());
        return null;
    }


    @Override
    public int updatePermission(Admin admin) {
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        return adminMapper.updateById(admin);
    }

    @Override
    public int deleteAdmin(Integer id) {
        return adminMapper.deleteById(id);
    }

    @Override
    public Admin getInfo(Object id) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("admin_id",id);
        return adminMapper.selectOne(queryWrapper);
    }

}
