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
 * @since 2022-02-25
 */
@Getter
@Setter
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
      @TableId(value = "act_id", type = IdType.AUTO)
    private Integer actId;

    /**
     * 活动名称
     */
    @TableField("name")
    private String name;

    /**
     * 活动举行时间
     */
    @TableField("date")
    private LocalDateTime date;

    /**
     * 活动描述
     */
    @TableField("description")
    private String description;

    /**
     * 所属社区id
     */
    @TableField("com_id")
    private Integer comId;


}
