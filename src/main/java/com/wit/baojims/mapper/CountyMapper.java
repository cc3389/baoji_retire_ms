package com.wit.baojims.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wit.baojims.entity.County;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 * @Author Zeman
 * @Description //TODO
 * @Date 20:15 2022/2/26
 * @Param
 * @return
 **/
@Mapper
public interface CountyMapper extends BaseMapper<County> {

    List<County> selectAll();
}
