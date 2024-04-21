package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.entity.Student;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService extends IService<Student> {
    void addStudent(Student student);

    String login(Student student);

    String load(MultipartFile file, Integer userId);

    PageResult getFreeTeacher(Integer no, Integer size);

    void orderTo(Integer studentId, Integer teacherId);

    PageResult getOrders(Integer studentId, Integer no, Integer size);

    PageResult getArticleList(Integer no, Integer size);
}
