package com.ozo.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String userAccount;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phone;

    /**
     * 0 正常 1 异常
     */
    private Integer userStatus;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户权限 0-普通用户 1-管理员
     */
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}