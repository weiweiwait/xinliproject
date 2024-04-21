package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.qing.admin.system.entity.Admin;
import com.qing.admin.system.entity.ConditionException;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.mapper.AdminMapper;
import com.qing.admin.system.service.AdminService;
import com.qing.admin.system.service.TeacherService;
import com.qing.admin.system.util.MD5Util;
import com.qing.admin.system.util.QiNiuUtil;
import com.qing.admin.system.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private TeacherService teacherService;

    @Override
    public void addAdmin(Admin admin) {
        if(StringUtils.isNullOrEmpty(admin.getUsername())) {
            throw new ConditionException("用户名不能为空！");
        }
        Admin admin1 = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, admin.getUsername()));
        if(admin1 != null) {
            throw new ConditionException("该用户名已被注册！");
        }
        admin.setPassword(MD5Util.sign(admin.getPassword(), MD5Util.salt, "UTF-8"));
        save(admin);
    }

    @Override
    public String login(Admin admin) {
        if(StringUtils.isNullOrEmpty(admin.getUsername()) || StringUtils.isNullOrEmpty(admin.getPassword())) {
            throw new ConditionException("参数不合法！");
        }
        Admin admin1 = adminMapper.selectOne(new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, admin.getUsername()));
        if(admin1 == null) {
            throw new ConditionException("用户不存在！");
        }
        if(!MD5Util.verify(admin.getPassword(), admin1.getPassword(), MD5Util.salt, "UTF-8")) {
            throw new ConditionException("密码错误！");
        }
        String token = TokenUtil.generateToken(admin1.getId());
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
        update(new LambdaUpdateWrapper<Admin>().eq(Admin::getId, userId).set(Admin::getAvatar, url));
        return url;
    }

    @Override
    public PageResult getTeacherList(Integer no, Integer size) {
        return teacherService.getTeacherList(no, size);
    }

    @Override
    public void authPass(Integer teacherId) {
        teacherService.authPass(teacherId);
    }

    @Override
    public void authFail(Integer teacherId) {
        teacherService.authFail(teacherId);
    }
}
