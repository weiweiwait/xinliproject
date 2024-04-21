package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Order;
import com.qing.admin.system.entity.PageResult;

public interface OrderService extends IService<Order> {
    void orderTo(Integer studentId, Integer teacherId);

    PageResult getOrdersByStudent(Integer studentId, Integer no, Integer size);

    PageResult getOrdersByTeacher(Integer teacherId, Integer no, Integer size);

    void refuseOrder(Integer teacherId, Integer studentId);

    void agreeOrder(Integer teacherId, Integer studentId);

    void endOrder(Integer teacherId);
}
