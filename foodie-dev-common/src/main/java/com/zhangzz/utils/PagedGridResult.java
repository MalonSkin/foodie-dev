package com.zhangzz.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

/**
 * @author
 * @Title: PagedGridResult.java
 * @Package com.zhangzz.utils
 * @Description: 用来返回分页Grid的数据格式
 * Copyright: Copyright (c) 2019
 */
@Data
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

}
