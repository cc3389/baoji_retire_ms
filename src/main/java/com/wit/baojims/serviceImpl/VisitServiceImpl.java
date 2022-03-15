package com.wit.baojims.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wit.baojims.entity.*;
import com.wit.baojims.mapper.ManageMapper;
import com.wit.baojims.mapper.VisitMapper;
import com.wit.baojims.service.VisitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wit.baojims.vo.TransGroupVo;
import com.wit.baojims.vo.VisitGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 22:38 2022/2/28
 * @Param
 **/
@Service
public class VisitServiceImpl extends ServiceImpl<VisitMapper, Visit> implements VisitService {

    @Autowired
    private VisitMapper visitMapper;
    @Autowired
    private ManageMapper manageMapper;

    @Override
    public IPage<Visit> selectVisitPage(Integer page, Integer size, Admin admin) {
        //获取当前页和页面大小
        IPage<Visit> iPage = new Page<>(page, size);

        QueryWrapper<Manage> queryWrapperManage = new QueryWrapper<>();
        queryWrapperManage.eq("admin_id", admin.getAdminId());
        List<Manage> manageList = manageMapper.selectList(queryWrapperManage);

        QueryWrapper<Visit> queryWrapperVisit = new QueryWrapper<>();
        for (Manage manage : manageList){
            queryWrapperVisit.or().eq("com_id", manage.getComId());
        }
        return visitMapper.selectPage(iPage, queryWrapperVisit);
    }

//    @Override
//    public List<VisitGroupVo> groupByYear(Integer comId) {
//        return visitMapper.groupByYear(comId);
//    }
}
