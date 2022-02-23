package com.wit.baojims.entity;

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
@TableName("visit")
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 区县管理员走访id
     */
      @TableId("vis_id")
    private Integer visId;

    /**
     * 走访时间
     */
    @TableField("vis_date")
    private LocalDateTime visDate;

    /**
     * 走访记录
     */
    @TableField("vis_desc")
    private String visDesc;

    /**
     * 走访社区id
     */
    @TableField("com_id")
    private Integer comId;


}
