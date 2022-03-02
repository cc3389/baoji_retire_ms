package com.wit.baojims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

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
