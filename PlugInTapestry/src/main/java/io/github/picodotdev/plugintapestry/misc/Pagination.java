package io.github.picodotdev.plugintapestry.misc;

import java.util.List;

public class Pagination {

	private int offset;
	private int num;
	private List<Sort> sort;

	public Pagination(int offset, int num, List<Sort> sort) {
		this.offset = offset;
		this.num = num;
		this.sort = sort;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<Sort> getSort() {
		return sort;
	}

	public void setSort(List<Sort> sort) {
		this.sort = sort;
	}
}