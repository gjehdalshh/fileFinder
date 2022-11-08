package com.kmu.filefinder.main.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
	private int i_category;
	private String category_nm;
	private int category_order;
	private int category_top;
	private String category_path;
	private int category_count;
}
