package com.kmu.filefinder.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.main.dao.CategoryDAO;
import com.kmu.filefinder.main.dto.CategoryDTO;

@Mapper
public interface MainMapper {
	/* -------------- read -------------- */
	public List<CategoryDAO> getCategoryList();
	public CategoryDAO getCategoryByNm(String nm);
	public String getCategoryNmByTop(int category_top);
	public String getCategoryPathByIcategory(int i_category);
	public int getTotalNumberPosts();
	public CategoryDAO getCategoryByIcategory(int i_category);
	public int getCategoryIcategoryByCategoryTop(int category_top);
	
	/* -------------- create -------------- */
	public int createCategory(CategoryDTO dto);
	
	/* -------------- update -------------- */
	public void increaseSmallFileCount(int i_category);
	public void increaseLargeFileCount(int i_category);
	public void decreaseLargeFileCount(CategoryDAO dao);
	
	/* -------------- delete -------------- */
	public int deleteSmallCategory(int i_category);
	public int deleteLargeCategory(int i_category);

	
}
