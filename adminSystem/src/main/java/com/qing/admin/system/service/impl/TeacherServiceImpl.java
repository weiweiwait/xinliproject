package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.qing.admin.system.entity.Article;
import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.entity.Teacher;
import com.qing.admin.system.mapper.TeacherMapper;
import com.qing.admin.system.service.ArticleService;
import com.qing.admin.system.service.OrderService;
import com.qing.admin.system.service.TeacherService;
import com.qing.admin.system.util.MD5Util;
import com.qing.admin.system.util.QiNiuUtil;
import com.qing.admin.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ArticleService articleService;

    @Override
    public void addTeacher(Teacher teacher) {
        if(StringUtils.isNullOrEmpty(teacher.getUsername())) {
            throw new ConditionException("用户名不能为空！");
        }
        Teacher teacher1 = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getUsername, teacher.getUsername()));
        if(teacher1 != null) {
            throw new ConditionException("该用户名已被注册！");
        }
        teacher.setPassword(MD5Util.sign(teacher.getPassword(), MD5Util.salt, "UTF-8"));
        teacher.setAuth(0);
        teacher.setStatus(0);
        save(teacher);
    }

    @Override
    public String login(Teacher teacher) {
        if(StringUtils.isNullOrEmpty(teacher.getUsername()) || StringUtils.isNullOrEmpty(teacher.getPassword())) {
            throw new ConditionException("参数不合法！");
        }
        Teacher teacher1 = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getUsername, teacher.getUsername()));
        if(teacher1 == null) {
            throw new ConditionException("用户不存在！");
        }
        if(!MD5Util.verify(teacher.getPassword(), teacher1.getPassword(), MD5Util.salt, "UTF-8")) {
            throw new ConditionException("密码错误！");
        }
        String token = TokenUtil.generateToken(teacher1.getId());
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
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, userId).set(Teacher::getAvatar, url));
        return url;
    }

    @Override
    public PageResult getTeacherList(Integer no, Integer size) {
        Page<Teacher> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Teacher>());
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public void authPass(Integer teacherId) {
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, teacherId).set(Teacher::getAuth, 1));
    }

    @Override
    public void authFail(Integer teacherId) {
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, teacherId).set(Teacher::getAuth, 2));
    }

    @Override
    public PageResult getFreeTeacher(Integer no, Integer size) {
        Page<Teacher> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Teacher>().eq(Teacher::getStatus, 0));
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public PageResult getOrders(Integer teacherId, Integer no, Integer size) {
        return orderService.getOrdersByTeacher(teacherId, no, size);
    }

    @Override
    public void refuseOrder(Integer teacherId, Integer studentId) {
        orderService.refuseOrder(teacherId, studentId);
    }

    @Override
    public void agreeOrder(Integer teacherId, Integer studentId) {
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, teacherId).set(Teacher::getStatus, 1));
        orderService.agreeOrder(teacherId, studentId);
    }

    @Override
    public String loadProve(MultipartFile file, Integer userId) {
        String url = null;
        try {
            url = QiNiuUtil.saveImage(file);
        } catch (IOException e) {
            throw new ConditionException("上传失败！");
        }
        url = "http://" + url;
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, userId).set(Teacher::getImage, url));
        return url;
    }

    @Override
    public void addArticle(Article article) {
        articleService.addArticle(article);
    }

    @Override
    public PageResult getArticleList(Integer teacherId, Integer no, Integer size) {
        return articleService.getArticleList(teacherId, no, size);
    }

    @Override
    public void free(Integer teacherId) {
        update(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getId, teacherId).set(Teacher::getStatus, 0));
        orderService.endOrder(teacherId);
    }
}
