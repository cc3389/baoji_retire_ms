package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Institute;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
public interface InstituteService extends IService<Institute> {
    IPage<Institute> selectByPage(Integer id,Integer page,Integer size);

    Institute selectInfo(Integer id);

    int addInstitute(Institute institute);

    int deleteIns(Integer id);

    int updateIns(Institute institute);
}
