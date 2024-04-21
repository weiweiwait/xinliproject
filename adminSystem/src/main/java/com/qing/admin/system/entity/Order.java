package com.qing.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("`order`")
public class Order {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("studentId")
    private Integer studentId;
    @TableField("teacherId")
    private Integer teacherId;
    @TableField("`status`")
    private Integer status;//0 未读 ；1 同意 ；2 拒绝 ；3 已结束
}
