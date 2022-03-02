package com.wit.baojims.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wit.baojims.entity.Activity;
import com.wit.baojims.entity.Community;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wit.baojims.entity.Institute;
import org.apache.ibatis.annotations.Param;
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
/* * @Author Shawn Yue
 * @Description //TODO Shawn Yue
 * @Date 11:13 2022/3/1
 * @Param
 * @return
 **/
public interface CommunityService extends IService<Community> {

    int insertCommunity(String name);

    List<Community> selectAllCommunity();

    Community selectComByName(String comName);

    Community selectById(Integer id);

    IPage<Community> selectCommunityPage(Integer page, Integer size);
}
