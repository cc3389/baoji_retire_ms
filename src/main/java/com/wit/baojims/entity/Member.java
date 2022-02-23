package com.wit.baojims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-02-23
 */
@Getter
@Setter
@TableName("member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退休人员id
     */
      @TableId(value = "peo_id", type = IdType.AUTO)
    private Integer peoId;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 人员图片
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 生日
     */
    @TableField("birth")
    private LocalDateTime birth;

    /**
     * 是否死亡
     */
    @TableField("is_death")
    private String isDeath;

    /**
     * 转入机构地址
     */
    @TableField("in_address")
    private String inAddress;

    /**
     * 转出机构地址
     */
    @TableField("out_address")
    private String outAddress;

    /**
     * 转入时间
     */
    @TableField("in_date")
    private LocalDateTime inDate;

    /**
     * 转出时间
     */
    @TableField("out_date")
    private LocalDateTime outDate;

    /**
     * 隶属社区id（外键）
     */
    @TableField("com_id")
    private Integer comId;

    /**
     * 隶属机构id（外键）
     */
    @TableField("inst_id")
    private Integer instId;

    /**
     * 需缴费用
     */
    @TableField("fee")
    private Double fee;


}