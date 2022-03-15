package com.wit.baojims.mapper;

import com.wit.baojims.entity.Visit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wit.baojims.vo.TransGroupVo;
import com.wit.baojims.vo.VisitGroupVo;
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
public interface VisitMapper extends BaseMapper<Visit> {
//    public List<VisitGroupVo> groupByYear(Integer comId);
}
