package com.wit.baojims.serviceImpl;

import com.wit.baojims.entity.Admin;
import com.wit.baojims.mapper.AdminMapper;
import com.wit.baojims.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
