package com.kmu.filefinder.pdf.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.pdf.dao.PdfDAO;
import com.kmu.filefinder.pdf.dto.PdfDTO;

@Mapper
public interface PdfMapper {
	public int createFile(PdfDTO dto);
	public String getCategoryPathByIcategory(int i_category);
	public List<String> getFileNameList();
}

