package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Message;
import com.qing.admin.system.entity.PageResult;

import java.util.Date;

public interface MessageService extends IService<Message> {
    PageResult getHistory(Integer teacherId, Integer studentId, Date date);

}
