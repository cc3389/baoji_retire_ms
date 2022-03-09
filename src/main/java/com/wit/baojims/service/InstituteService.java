package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.entity.Institute;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
public interface InstituteService extends IService<Institute> {
    IPage<Institute> selectByPage(List<Object> comList, Integer page, Integer size);

    Institute selectInfo(Integer id);

    Institute select(Integer id);

    int addInstitute(Institute institute);

    int deleteIns(Integer id);

    int updateIns(Institute institute);


}
