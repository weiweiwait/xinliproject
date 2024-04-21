package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Admin;
import com.qing.admin.system.entity.PageResult;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService extends IService<Admin> {
    void addAdmin(Admin admin);

    String login(Admin admin);

    String load(MultipartFile file, Integer userId);

    PageResult getTeacherList(Integer no, Integer size);

    void authPass(Integer teacherId);

    void authFail(Integer teacherId);
}
