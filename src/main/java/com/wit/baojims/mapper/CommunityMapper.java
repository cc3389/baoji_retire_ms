package com.wit.baojims.mapper;

import com.wit.baojims.entity.Community;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@Mapper
public interface CommunityMapper extends BaseMapper<Community> {
    List<Community> selectAll();
}
