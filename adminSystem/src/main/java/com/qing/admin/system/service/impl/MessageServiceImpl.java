package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.admin.system.entity.Message;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.mapper.MessageMapper;
import com.qing.admin.system.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {


    @Override
    public PageResult getHistory(Integer teacherId, Integer studentId, Date date) {
        Page<Message> page = new Page<>(1, 10);
        page = page(page, new LambdaQueryWrapper<Message>().eq(Message::getTeacherId, teacherId).eq(Message::getStudentId, studentId).lt(Message::getCreateTime, date).orderByDesc(Message::getCreateTime));
        return new PageResult(page.getTotal(), page.getRecords());
    }

}
