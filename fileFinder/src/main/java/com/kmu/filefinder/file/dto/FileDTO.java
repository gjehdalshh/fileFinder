package com.kmu.filefinder.file.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FileDTO{
	private String file_nm;
	private String file_extension;
	private int i_category;
	private String file_path;
}
