package com.wit.baojims.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.entity.County;
import com.wit.baojims.entity.Institute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * @Author Zeman
 * @Description //TODO
 * @Date 20:17 2022/2/26
 * @Param
 * @return
 **/
@Transactional
public interface CountyService extends IService<County> {

    int insertCounty(String name);

    List<County> selectAllCounty();
}
