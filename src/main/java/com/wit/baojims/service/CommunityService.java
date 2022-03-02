package com.wit.baojims.service;

import com.wit.baojims.entity.Community;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.entity.Institute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@Transactional
public interface CommunityService extends IService<Community> {

    int insertCommunity(String name);

    List<Community> selectAllCommunity();

    Community selectComByName(String comName);

    Community selectById(Integer id);
}
