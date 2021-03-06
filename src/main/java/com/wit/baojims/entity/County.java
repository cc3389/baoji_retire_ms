package com.wit.baojims.entity;
/**
 * @author: zz
 * createTime: 2022/2/26 20:10
 * description:
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/*
 * @Author Zeman
 * @Description //TODO
 * @Date 20:11 2022/2/26
 * @Param
 * @return
 **/


/**
 * <p>
 *
 * </p>
 *
 * @author 作者
 * @since 2022-02-27
 */
@Getter
@Setter
@TableName("county")
@Alias("county")
public class County implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 县id
     */

    @TableId(value = "county_id", type = IdType.AUTO)
    private Integer countyId;

    /**
     * 县名
     */
    @TableField("county_name")
    private String countyName;


}
