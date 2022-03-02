package com.wit.baojims.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@TableName("admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
      @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

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
     * 保持token值
     */
    @TableField("sa_token")
    private String saToken;

    /**
     * 权限名称
     */
    @TableField("per_name")
    private String perName;


    /**
     * 管理区域名
     */
    @TableField("manage_area")
    private String manageArea;

}
