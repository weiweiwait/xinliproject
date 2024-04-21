package com.qing.admin.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qing.admin.system.entity.Article;
import com.qing.admin.system.entity.PageResult;

public interface ArticleService extends IService<Article> {
    void addArticle(Article article);

    PageResult getArticleList(Integer teacherId, Integer no, Integer size);

    PageResult getArticleList(Integer no, Integer size);
}
