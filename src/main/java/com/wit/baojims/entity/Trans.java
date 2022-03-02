package com.wit.baojims.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-02-25
 */
@Getter
@Setter
@TableName("trans")
public class Trans implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 转出通知表id
     */
      @TableId("trans_id")
    private Integer transId;

    /**
     * 人员id
     */
    @TableField("peo_id")
    private Integer peoId;

    /**
     * 转入社区id
     */
    @TableField("in_com_id")
    private Integer inComId;

    /**
     * 转出社区id
     */
    @TableField("out_com_id")
    private Integer outComId;

    /**
     * 待转入/待转出/通过/拒绝
     */
    @TableField("status")
    private String status;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("in_date")
    private LocalDateTime inDate;

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @TableField("out_date")
    private LocalDateTime outDate;


}
