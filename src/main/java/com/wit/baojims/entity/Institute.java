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
 * @since 2022-02-24
 */
@Getter
@Setter
@TableName("institute")
public class Institute implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退管机构id
     */
      @TableId(value = "ins_id", type = IdType.AUTO)
    private Integer insId;

    /**
     * 机构电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 机构名称
     */
    @TableField("name")
    private String name;

    /**
     * 机构地址
     */
    @TableField("address")
    private String address;

    /**
     * 机构邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 机构总退休人数
     */
    @TableField("sum_people")
    private Integer sumPeople;


}
