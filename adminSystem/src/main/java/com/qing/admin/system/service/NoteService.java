package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Note;
import com.qing.admin.system.entity.PageResult;

public interface NoteService extends IService<Note> {

    PageResult getNoteList(Integer studentId, Integer no, Integer size);
}
