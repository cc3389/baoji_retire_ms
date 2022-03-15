package com.wit.baojims.mapper;

import com.wit.baojims.entity.Trans;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wit.baojims.vo.TransGroupVo;
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
public interface TransMapper extends BaseMapper<Trans> {
    public List<TransGroupVo> groupByInMonth(Integer comId);
    public List<TransGroupVo> groupByOutMonth(Integer comId);
}
