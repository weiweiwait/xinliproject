package com.qing.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qing.admin.system.entity.Article;
import com.qing.admin.system.entity.PageResult;
import com.qing.admin.system.mapper.ArticleMapper;
import com.qing.admin.system.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public void addArticle(Article article) {
        article.setCreateTime(new Date());
        save(article);
    }

    @Override
    public PageResult getArticleList(Integer teacherId, Integer no, Integer size) {
        Page<Article> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Article>().eq(Article::getTeacherId, teacherId).orderByDesc(Article::getCreateTime));
        return new PageResult(page.getTotal(), page.getRecords());
    }

    @Override
    public PageResult getArticleList(Integer no, Integer size) {
        Page<Article> page = new Page<>(no, size);
        page = page(page, new LambdaQueryWrapper<Article>().orderByDesc(Article::getCreateTime));
        return new PageResult(page.getTotal(), page.getRecords());
    }
}
