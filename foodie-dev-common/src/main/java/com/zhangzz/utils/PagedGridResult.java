package com.zhangzz.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * @author
 * @Title: PagedGridResult.java
 * @Package com.zhangzz.utils
 * @Description: 用来返回分页Grid的数据格式
 * Copyright: Copyright (c) 2019
 */
public class PagedGridResult {

    /** 当前页数 */
    private long page;
    /** 总页数 */
    private long total;
    /** 总记录数 */
    private long records;
    /** 每行显示的内容 */
    private List<?> rows;

	public PagedGridResult fromPage(IPage<?> page) {
		this.page = page.getCurrent();
		this.total = page.getPages();
		this.records = page.getTotal();
		this.rows = page.getRecords();
		return this;
	}

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PagedGridResult{" +
                "page=" + page +
                ", total=" + total +
                ", records=" + records +
                ", rows=" + rows +
                '}';
    }
}
