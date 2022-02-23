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
 * @since 2022-02-23
 */
@Getter
@Setter
@TableName("admin_info")
public class AdminInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
      @TableId(value = "man_id", type = IdType.AUTO)
    private Integer manId;

    /**
     * 管理员登录用户名
     */
    @TableField("name")
    private String name;

    /**
     * 管理员登录密码
     */
    @TableField("password")
    private String password;

    /**
     * 管理员权限id
     */
    @TableField("per_id")
    private Integer perId;


}
