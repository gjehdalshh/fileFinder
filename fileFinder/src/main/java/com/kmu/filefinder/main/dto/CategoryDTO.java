package com.kmu.filefinder.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryDTO{
	private int i_category;
	private String category_nm;
	private int category_order;
	private int category_top;
	private String category_path;
	private int category_count;
}
