package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.entity.Student;
import com.qing.admin.system.mapper.StudentMapper;
import com.qing.admin.system.service.ArticleService;
import com.qing.admin.system.service.OrderService;
import com.qing.admin.system.service.StudentService;
import com.qing.admin.system.service.TeacherService;
import com.qing.admin.system.util.MD5Util;
import com.qing.admin.system.util.QiNiuUtil;
import com.qing.admin.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ArticleService articleService;

    @Override
    public void addStudent(Student student) {
        if(StringUtils.isNullOrEmpty(student.getUsername())) {
            throw new ConditionException("用户名不能为空！");
        }
        Student student1 = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getUsername, student.getUsername()));
        if(student1 != null) {
            throw new ConditionException("该用户名已被注册！");
        }
        student.setPassword(MD5Util.sign(student.getPassword(), MD5Util.salt, "UTF-8"));
        save(student);
    }

    @Override
    public String login(Student student) {
        if(StringUtils.isNullOrEmpty(student.getUsername()) || StringUtils.isNullOrEmpty(student.getPassword())) {
            throw new ConditionException("参数不合法！");
        }
        Student student1 = studentMapper.selectOne(new LambdaQueryWrapper<Student>().eq(Student::getUsername, student.getUsername()));
        if(student1 == null) {
            throw new ConditionException("用户不存在！");
        }
        if(!MD5Util.verify(student.getPassword(), student1.getPassword(), MD5Util.salt, "UTF-8")) {
            throw new ConditionException("密码错误！");
        }
        String token = TokenUtil.generateToken(student1.getId());
        return token;
    }

    @Override
    public String load(MultipartFile file, Integer userId) {
        String url = null;
        try {
            url = QiNiuUtil.saveImage(file);
        } catch (IOException e) {
            throw new ConditionException("上传失败！");
        }
        url = "http://" + url;
        update(new LambdaUpdateWrapper<Student>().eq(Student::getId, userId).set(Student::getAvatar, url));
        return url;
    }

    @Override
    public PageResult getFreeTeacher(Integer no, Integer size) {
        return teacherService.getFreeTeacher(no, size);
    }

    @Override
    public void orderTo(Integer studentId, Integer teacherId) {
        orderService.orderTo(studentId, teacherId);
    }

    @Override
    public PageResult getOrders(Integer studentId, Integer no, Integer size) {
        return orderService.getOrdersByStudent(studentId, no, size);
    }

    @Override
    public PageResult getArticleList(Integer no, Integer size) {
        return articleService.getArticleList(no, size);
    }
}
