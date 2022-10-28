package com.kmu.filefinder.main.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kmu.filefinder.main.dao.CategoryDAO;
import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.mapper.MainMapper;

@Service
public class CategoryService {

	@Autowired
	private MainMapper mainMapper;

	@Value("${local.pdf.path}")
	private String path;

	// 카테고리 리스트 불러오기
	public List<CategoryDAO> getCategoryList() {
		return mainMapper.getCategoryList();
	}

	// 카테고리 생성
	public int createCategory(CategoryDTO dto) {
		if (!validateFolderNameExistence(dto.getCategory_nm())) {
			return 2;
		}
		if(dto.getCategory_nm().equals("")) {
			return 3;
		}
		String urlPath = createFile(dto);
		dto.setCategory_path(urlPath);
		return mainMapper.createCategory(dto);
	}

	// 이름 존재 유무 검색
	private boolean validateFolderNameExistence(String nm) {
		if (mainMapper.getCategoryByNm(nm) != null) {
			return false;
		}
		return true;
	}

	// 로컬 디렉토리 파일 생성
	private String createFile(CategoryDTO dto) {
		
		String urlPath = "";
		
		if (dto.getCategory_order() == 1) {
			urlPath = path + dto.getCategory_nm();
			File folder = new File(urlPath);
			folder.mkdirs();
		} else {
			urlPath = path + mainMapper.getCategoryNmByTop(dto.getCategory_top());
			urlPath += "\\" + dto.getCategory_nm();
			File folder = new File(urlPath);
			folder.mkdirs();
		}
		
		return urlPath;
	}
	
	public int deleteSmallCategory(int i_category) {
		return mainMapper.deleteSmallCategory(i_category);
	}
	
	public int deleteLargeCategory(int i_category) {
		mainMapper.deleteLargeCategory(i_category);
		return mainMapper.deleteSmallCategory(i_category);
	}
}
