package com.kmu.filefinder.main.service;

import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.main.dto.CategoryDTO;

public interface CategoryService {
	/* ---------- business logic ---------- */
	boolean validateFolderNameExistence(CategoryDTO dto);
	String createFile(CategoryDTO dto);
	void deleteLocalCategory(String path);
	ModelAndView homeCategoryInfo(ModelAndView mv);
}
