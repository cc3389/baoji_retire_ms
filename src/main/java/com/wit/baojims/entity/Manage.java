package com.wit.baojims.entity;

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
 * @since 2022-02-25
 */
@Getter
@Setter
@TableName("manage")
public class Manage implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId("admin_id")
    private Integer adminId;

    @TableField("com_id")
    private Integer comId;

    @TableField("ins_id")
    private Integer insId;


}
