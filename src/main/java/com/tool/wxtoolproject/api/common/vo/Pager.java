package com.tool.wxtoolproject.api.common.vo;

import com.github.pagehelper.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页插件
 * 
 * @author 张帅
 *
 * @param <E>
 */
@Data
public class Pager<E> {

	private List<E> list;
	
	private int pageSize = 0;
	private int pageNum = 0;
	private long total = 0;
	private int pages = 0;
	
	public Pager() {
		super();
	}
	
	public Pager(Page<E> page) {
		super();
		this.list = page.getResult();
		this.pageSize = page.getPageSize();
		this.pageNum = page.getPageNum();
		this.total = page.getTotal();
		this.pages = page.getPages();
	}

	public Pager(List<E> list, int pageSize, int pageNum, int total, int pages) {
		super();
		this.list = list;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.total = total;
		this.pages = pages;
	}
}
