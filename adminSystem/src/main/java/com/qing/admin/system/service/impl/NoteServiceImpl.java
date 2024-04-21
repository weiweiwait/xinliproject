package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.admin.system.entity.Note;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.mapper.NoteMapper;
import com.qing.admin.system.service.NoteService;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Override
    public PageResult getNoteList(Integer studentId, Integer no, Integer size) {
        Page<Note> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Note>().eq(Note::getStudentId, studentId).orderByDesc(Note::getCreateTime));
        return new PageResult(page.getTotal(), page.getRecords());
    }
}
