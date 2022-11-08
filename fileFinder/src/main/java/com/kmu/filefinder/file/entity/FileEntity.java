package com.kmu.filefinder.file.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
	private int i_file;
	private String file_nm;
	private String file_extension;
	private String r_dt;
	private int i_category;
	private String file_path;
}
