package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.Order;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.mapper.OrderMapper;
import com.qing.admin.system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void orderTo(Integer studentId, Integer teacherId) {
        Order one = orderMapper.selectOne(new LambdaQueryWrapper<Order>().eq(Order::getStudentId, studentId).eq(Order::getTeacherId, teacherId));
        if(one == null) {
            Order order = new Order();
            order.setStudentId(studentId);
            order.setTeacherId(teacherId);
            order.setStatus(0);
            save(order);
        } else if(one != null && one.getStatus() == 1) {
            throw new ConditionException("您已成功预约！");
        } else if(one != null && one.getStatus() == 0) {
            throw new ConditionException("预约中！");
        } else { // status == 2 || status == 3
            update(new LambdaUpdateWrapper<Order>().eq(Order::getStudentId, studentId).eq(Order::getTeacherId, teacherId).set(Order::getStatus, 0));
        }
    }

    @Override
    public PageResult getOrdersByStudent(Integer studentId, Integer no, Integer size) {
        Page<Order> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Order>().eq(Order::getStudentId, studentId));
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public PageResult getOrdersByTeacher(Integer teacherId, Integer no, Integer size) {
        Page<Order> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Order>().eq(Order::getTeacherId, teacherId));
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public void refuseOrder(Integer teacherId, Integer studentId) {
        update(new LambdaUpdateWrapper<Order>().eq(Order::getStudentId, studentId).eq(Order::getTeacherId, teacherId).set(Order::getStatus, 2));
    }

    @Override
    public void agreeOrder(Integer teacherId, Integer studentId) {
        update(new LambdaUpdateWrapper<Order>().eq(Order::getStudentId, studentId).eq(Order::getTeacherId, teacherId).set(Order::getStatus, 1));
    }

    @Override
    public void endOrder(Integer teacherId) {
        update(new LambdaUpdateWrapper<Order>().eq(Order::getTeacherId, teacherId).eq(Order::getStatus, 1).set(Order::getStatus, 3));
    }
}
