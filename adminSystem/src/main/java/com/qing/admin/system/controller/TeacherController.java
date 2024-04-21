package com.qing.admin.system.controller;

import com.qing.admin.system.controller.support.UserSupport;
import com.qing.admin.system.entity.*;
import com.qing.admin.system.service.MessageService;
import com.qing.admin.system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public JsonResponse<String> addTeacher(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
        return JsonResponse.success();
    }

    @PostMapping("/avatar")
    public JsonResponse<String> load(@RequestBody MultipartFile file) {
        Integer userId = userSupport.getCurrentUserId();
        String url = teacherService.load(file, userId);
        return new JsonResponse<>(url);
    }

    @PostMapping("/image")
    public JsonResponse<String> loadProve(@RequestBody MultipartFile file) {
        Integer userId = userSupport.getCurrentUserId();
        String url = teacherService.loadProve(file, userId);
        return new JsonResponse<>(url);
    }

    @PostMapping("/login")
    public JsonResponse<String> login(@RequestBody Teacher teacher) {
        String token = teacherService.login(teacher);
        return JsonResponse.success(token);
    }

    @GetMapping("/getOrders")
    public JsonResponse<PageResult<List<Order>>> getOrders(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer teacherId = userSupport.getCurrentUserId();
        PageResult result = teacherService.getOrders(teacherId, no, size);
        return new JsonResponse<>(result);
    }

    @PostMapping("/refuseOrder")
    public JsonResponse<String> refuseOrder(@RequestParam("studentId") Integer studentId) {
        Integer teacherId = userSupport.getCurrentUserId();
        teacherService.refuseOrder(teacherId, studentId);
        return JsonResponse.success();
    }

    @PostMapping("/agreeOrder")
    public JsonResponse<String> agreeOrder(@RequestParam("studentId") Integer studentId) {
        Integer teacherId = userSupport.getCurrentUserId();
        teacherService.agreeOrder(teacherId, studentId);
        return JsonResponse.success();
    }

    @PostMapping("/infos")
    public JsonResponse<String> infos(@RequestBody Teacher teacher) {
        Integer teacherId = userSupport.getCurrentUserId();
        teacher.setId(teacherId);
        teacherService.updateById(teacher);
        return JsonResponse.success();
    }

    @PostMapping("/article")
    public JsonResponse<String> addArticle(@RequestBody Article article) {
        Integer teacherId = userSupport.getCurrentUserId();
        article.setTeacherId(teacherId);
        teacherService.addArticle(article);
        return JsonResponse.success();
    }

    @GetMapping("/article")
    public JsonResponse<PageResult<List<Article>>> getArticleList(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer teacherId = userSupport.getCurrentUserId();
        PageResult result = teacherService.getArticleList(teacherId, no, size);
        return new JsonResponse<>(result);
    }

    @GetMapping("/status")
    public JsonResponse<Teacher> getStatus() {
        Integer teacherId = userSupport.getCurrentUserId();
        Teacher teacher = teacherService.getById(teacherId);
        return new JsonResponse<>(teacher);
    }

    @PostMapping("/free")
    public JsonResponse<String> free() {
        Integer teacherId = userSupport.getCurrentUserId();
        teacherService.free(teacherId);
        return JsonResponse.success();
    }

    @PostMapping("/history")
    public JsonResponse<PageResult<List<Message>>> getHistory(@RequestParam("date") String date, @RequestParam("studentId") Integer studentId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer teacherId = userSupport.getCurrentUserId();
        PageResult result = messageService.getHistory(teacherId, studentId, d);
        return new JsonResponse<>(result);
    }
}
