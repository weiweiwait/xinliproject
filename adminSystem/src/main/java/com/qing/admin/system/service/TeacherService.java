package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Article;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.entity.Teacher;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService extends IService<Teacher> {
    void addTeacher(Teacher teacher);

    String login(Teacher teacher);

    String load(MultipartFile file, Integer userId);

    PageResult getTeacherList(Integer no, Integer size);

    void authPass(Integer teacherId);

    void authFail(Integer teacherId);

    PageResult getFreeTeacher(Integer no, Integer size);

    PageResult getOrders(Integer teacherId, Integer no, Integer size);

    void refuseOrder(Integer teacherId, Integer studentId);

    void agreeOrder(Integer teacherId, Integer studentId);

    String loadProve(MultipartFile file, Integer userId);

    void addArticle(Article article);

    PageResult getArticleList(Integer teacherId, Integer no, Integer size);

    void free(Integer teacherId);
}
