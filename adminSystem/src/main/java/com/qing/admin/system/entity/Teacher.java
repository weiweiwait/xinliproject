package com.qing.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("avatar")
    private String avatar;
    @TableField("status")
    private Integer status; // 0 空闲 ；1 繁忙
    @TableField("description")
    private String description;
    @TableField("auth")
    private Integer auth; //0 未审核 ：1 审核成功 ： 2 审核失败
    @TableField("image")
    private String image;
}
