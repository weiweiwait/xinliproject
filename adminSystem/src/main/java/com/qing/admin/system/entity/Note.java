package com.qing.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("note")
public class Note {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("studentId")
    private Integer studentId;
    @TableField("notes")
    private String notes;
    @TableField("createTime")
    private Date createTime;
    @TableField("title")
    private String title;
}
