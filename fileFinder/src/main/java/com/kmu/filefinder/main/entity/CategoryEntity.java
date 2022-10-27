package com.kmu.filefinder.main.entity;

public class CategoryEntity {
	private int i_category;
	private String category_nm;
	private int category_order;
	private int category_top;
	private String category_path;
	
	public String getCategory_path() {
		return category_path;
	}
	public void setCategory_path(String category_path) {
		this.category_path = category_path;
	}
	public int getI_category() {
		return i_category;
	}
	public void setI_category(int i_category) {
		this.i_category = i_category;
	}
	public String getCategory_nm() {
		return category_nm;
	}
	public void setCategory_nm(String category_nm) {
		this.category_nm = category_nm;
	}
	public int getCategory_order() {
		return category_order;
	}
	public void setCategory_order(int category_order) {
		this.category_order = category_order;
	}
	public int getCategory_top() {
		return category_top;
	}
	public void setCategory_top(int category_top) {
		this.category_top = category_top;
	}
}
