package com.kmu.filefinder.file.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileDTO{
	private String file_nm;
	private String file_extension;
	private int i_category;
	private String file_path;
}
