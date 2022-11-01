package com.kmu.filefinder.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.main.dao.CategoryDAO;
import com.kmu.filefinder.main.dto.CategoryDTO;

@Mapper
public interface MainMapper {
	public int createCategory(CategoryDTO dto);
	public List<CategoryDAO> getCategoryList();
	public CategoryDAO getCategoryByNm(String nm);
	public String getCategoryNmByTop(int category_top);
	public int deleteSmallCategory(int i_category);
	public int deleteLargeCategory(int i_category);
	public String getCategoryPathByIcategory(int i_category);
}
