package com.kmu.filefinder.file.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.file.dto.FileDTO;

@Mapper
public interface FileMapper {
	/* -------------- read -------------- */
	public String getCategoryPathByIcategory(int i_category);
	public List<String> getFileNameList();
	
	/* -------------- create -------------- */
	public int createFile(FileDTO dto);
	
	/* -------------- update -------------- */
	
	/* -------------- delete -------------- */
	

}

