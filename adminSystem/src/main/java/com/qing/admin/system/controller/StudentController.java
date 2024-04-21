package com.qing.admin.system.controller;

import com.qing.admin.system.controller.support.UserSupport;
import com.qing.admin.system.entity.*;
import com.qing.admin.system.service.MessageService;
import com.qing.admin.system.service.NoteService;
import com.qing.admin.system.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private StudentService studentService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private NoteService noteService;

    @PostMapping("/register")
    public JsonResponse<String> addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
        return JsonResponse.success();
    }

    @PostMapping("/avatar")
    public JsonResponse<String> load(@RequestBody MultipartFile file) {
        Integer userId = userSupport.getCurrentUserId();
        String url = studentService.load(file, userId);
        return new JsonResponse<>(url);
    }

    @PostMapping("/login")
    public JsonResponse<String> login(@RequestBody Student student) {
        String token = studentService.login(student);
        return JsonResponse.success(token);
    }

    @PutMapping("/update")
    public JsonResponse<String> update(@RequestBody Student student) {
        Integer studentId = userSupport.getCurrentUserId();
        student.setId(studentId);
        studentService.updateById(student);
        return JsonResponse.success();
    }

    @GetMapping("/getFreeTeacher")
    public JsonResponse<PageResult<List<Teacher>>> getFreeTeacher(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        userSupport.getCurrentUserId();
        PageResult result = studentService.getFreeTeacher(no, size);
        return new JsonResponse<>(result);
    }

    @PostMapping("/orderTo")
    public JsonResponse<String> orderTo(@RequestParam("teacherId") Integer teacherId) {
        Integer studentId = userSupport.getCurrentUserId();
        studentService.orderTo(studentId, teacherId);
        return JsonResponse.success();
    }

    @GetMapping("/getOrders")
    public JsonResponse<PageResult<List<Order>>> getOrders(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer studentId = userSupport.getCurrentUserId();
        PageResult result = studentService.getOrders(studentId, no, size);
        return new JsonResponse<>(result);
    }

    @GetMapping("/article")
    public JsonResponse<PageResult<List<Article>>> getArticleList(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer studentId = userSupport.getCurrentUserId();
        PageResult result = studentService.getArticleList(no, size);
        return new JsonResponse<>(result);
    }

    @PostMapping("/history")
    public JsonResponse<PageResult<List<Message>>> getHistory(@RequestParam("date") String date, @RequestParam("teacherId") Integer teacherId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer studentId = userSupport.getCurrentUserId();
        PageResult result = messageService.getHistory(teacherId, studentId, d);
        return new JsonResponse<>(result);
    }

    @PostMapping("/note")
    public JsonResponse<String> addNote(@RequestBody Note note) {
        Integer studentId = userSupport.getCurrentUserId();
        note.setStudentId(studentId);
        note.setCreateTime(new Date());
        noteService.save(note);
        return JsonResponse.success();
    }

    @GetMapping("/note")
    public JsonResponse<PageResult<List<Note>>> getNoteList(@RequestParam("no") Integer no, @RequestParam("size") Integer size) {
        Integer studentId = userSupport.getCurrentUserId();
        PageResult result = noteService.getNoteList(studentId, no, size);
        return new JsonResponse<>(result);
    }

    @DeleteMapping("/note")
    public JsonResponse<String> deleteNote(@RequestParam("id") Integer id) {
        Integer studentId = userSupport.getCurrentUserId();
        Note note = new Note();
        note.setId(id);
        noteService.removeById(note);
        return JsonResponse.success();
    }

    @PutMapping("/note")
    public JsonResponse<String> updateNote(@RequestBody Note note) {
        Integer studentId = userSupport.getCurrentUserId();
        noteService.updateById(note);
        return JsonResponse.success();
    }
}
