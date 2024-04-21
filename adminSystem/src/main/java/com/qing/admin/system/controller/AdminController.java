package com.qing.admin.system.controller;

import com.qing.admin.system.controller.support.UserSupport;
import com.qing.admin.system.entity.Admin;
import com.qing.admin.system.entity.JsonResponse;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.entity.Teacher;
import com.qing.admin.system.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public JsonResponse<String> addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return JsonResponse.success();
    }

    @PostMapping("/login")
    public JsonResponse<String> login(@RequestBody Admin admin) {
        String token = adminService.login(admin);
        return JsonResponse.success(token);
    }

    @PostMapping("/avatar")
    public JsonResponse<String> load(@RequestBody MultipartFile file) {
        Integer userId = userSupport.getCurrentUserId();
        String url = adminService.load(file, userId);
        return new JsonResponse<>(url);
    }

    @GetMapping("/getTeacherList")
    public JsonResponse<PageResult<List<Teacher>>> getTeacherList(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer userId = userSupport.getCurrentUserId();
        PageResult result = adminService.getTeacherList(no, size);
        return new JsonResponse<>(result);
    }

    @PostMapping("/authPass")
    public JsonResponse<String> authPass(Integer teacherId) {
        Integer userId = userSupport.getCurrentUserId();
        adminService.authPass(teacherId);
        return JsonResponse.success();
    }

    @PostMapping("/authFail")
    public JsonResponse<String> authFail(Integer teacherId) {
        Integer userId = userSupport.getCurrentUserId();
        adminService.authFail(teacherId);
        return JsonResponse.success();
    }
}
