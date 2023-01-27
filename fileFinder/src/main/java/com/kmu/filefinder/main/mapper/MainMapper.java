package com.kmu.filefinder.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.main.dto.CategoryDTO;

@Mapper
public interface MainMapper {
	/* -------------- read -------------- */
	public List<CategoryDTO> getCategoryList();
	public CategoryDTO getCategoryByNm(CategoryDTO dto);
	public String getCategoryNmByTop(int category_top);
	public String getCategoryPathByIcategory(int i_category);
	public int getTotalNumberPosts();
	public int getLargeNumberPosts(int i_category);
	public int getSmallNumberPosts(int i_category);
	public CategoryDTO getCategoryByIcategory(int i_category);
	public int getCategoryIcategoryByCategoryTop(int category_top);
	public List<Integer> getICategoryByIcategoryTop(int i_category);
	
	/* -------------- create -------------- */
	public int createCategory(CategoryDTO dto);
	
	/* -------------- update -------------- */
	public void increaseSmallFileCount(int i_category);
	public void increaseLargeFileCount(int i_category);
	public void decreaseLargeFileCount(CategoryDTO dao);
	public void decreaseFileCountByFileDelete(int i_category);
	
	/* -------------- delete -------------- */
	public int deleteSmallCategory(int i_category);
	public int deleteLargeCategory(int i_category);
}
