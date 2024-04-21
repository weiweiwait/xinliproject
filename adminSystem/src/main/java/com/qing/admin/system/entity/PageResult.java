package com.qing.admin.system.entity;

import java.util.List;

public class PageResult<T> {
    private Long total;
    private List<T> list;

    public PageResult(Long total, List<T> list) {
        this.list = list;
        this.total = total;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
