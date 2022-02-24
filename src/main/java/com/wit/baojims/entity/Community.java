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
@TableName("community")
public class Community implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 社区id
     */
      @TableId(value = "com_id", type = IdType.AUTO)
    private Integer comId;

    /**
     * 社区名
     */
    @TableField("name")
    private String name;

    /**
     * 区县管理员走访次数
     */
    @TableField("vis_count")
    private Integer visCount;

    @TableField("admin_id")
    private Integer adminId;


}
