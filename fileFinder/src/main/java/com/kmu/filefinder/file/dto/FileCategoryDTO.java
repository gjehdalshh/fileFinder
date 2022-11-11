package com.kmu.filefinder.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileCategoryDTO {
	private int i_file;
	private String file_nm;
	private String extension;
	private String r_dt;
	private int i_cateogry;
	private String file_path;
	private String category_nm;
	private String summaryText;
}
