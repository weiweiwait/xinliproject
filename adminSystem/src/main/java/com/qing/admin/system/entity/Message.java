package com.qing.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("message")
public class Message {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("teacherId")
    private Integer teacherId;
    @TableField("studentId")
    private Integer studentId;
    @TableField("message")
    private String message;
    @TableField("createTime")
    private Date createTime;
    @TableField("`from`")
    private Integer from; //0 老师 ；1 学生
}
