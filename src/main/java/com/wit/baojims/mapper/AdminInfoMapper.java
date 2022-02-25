package com.wit.baojims.mapper;

import com.wit.baojims.entity.AdminInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-02-24
 */
@Mapper
public interface AdminInfoMapper extends BaseMapper<AdminInfo> {
    public Integer login(HashMap param);
}
