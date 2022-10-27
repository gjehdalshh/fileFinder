package com.kmu.filefinder.pdf.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.pdf.dto.PdfDTO;

@Mapper
public interface PdfMapper {
	public int createFile(PdfDTO dto);
	public String getCategoryPathByIcategory(int i_category);
}

