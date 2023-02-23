package com.kmu.filefinder.main.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.common.utils.ConvertType;
import com.kmu.filefinder.common.utils.Verification;
import com.kmu.filefinder.file.mapper.FileMapper;
import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.mapper.MainMapper;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private MainMapper mainMapper;
	
	@Autowired
	private FileMapper fileMapper;

	@Value("${local.pdf.path}")
	private String path;

	// 카테고리 리스트 불러오기
	public List<CategoryDTO> getCategoryList() {
		return mainMapper.getCategoryList();
	}

	// 이름으로 카테고리를 가져옴
	public CategoryDTO getCategoryByNm(CategoryDTO dto) {
		return mainMapper.getCategoryByNm(dto);
	}

	// category_top으로 CategoryName을 가져옴
	public String getCategoryNmByTop(int category_top) {
		return mainMapper.getCategoryNmByTop(category_top);
	}

	// 전체 게시글 수 가져오기
	public int getTotalNumberPosts() {
		return mainMapper.getTotalNumberPosts();
	}

	// i_category로 categoryDTO를 가져옴
	public CategoryDTO getCategoryByIcategory(int i_category) {
		return mainMapper.getCategoryByIcategory(i_category);
	}

	// category_top 으로 category.i_category를 가져옴
	public int getCategoryIcategoryByCategoryTop(int category_top) {
		return mainMapper.getCategoryIcategoryByCategoryTop(category_top);
	}

	// 카테고리 생성
	public int createCategory(CategoryDTO dto) {
		if (!validateFolderNameExistence(dto)) { //  폴더 이름 존재 유무 검사
			return 2;
		}
		if (dto.getCategory_nm().equals("")) {
			return 3;
		}
		String urlPath = createFile(dto);
		dto.setCategory_path(urlPath);
		return mainMapper.createCategory(dto);
	}
	
	// 이름 존재 유무 검색
		public boolean validateFolderNameExistence(CategoryDTO dto) {
			if (getCategoryByNm(dto) != null) { // 이미 파일이 존재함
				return false;
			}
			return true;
		}

	// DB 대분류 카테고리 개수 감소
	public void decreaseLargeFileCount(int i_category) {
		CategoryDTO dao = getCategoryByIcategory(i_category);
		mainMapper.decreaseLargeFileCount(dao);
	}

	// DB 소분류 카테고리 삭제
	public int deleteSmallCategory(String id) {
		int i_category = ConvertType.convertStringToInt(id);
		if (Verification.checkNullByString(id)) { // null 체크
			return 2;
		}
		decreaseLargeFileCount(i_category);
		String path = mainMapper.getCategoryPathByIcategory(i_category);
		deleteLocalCategory(path); // 로컬에서 삭제
		fileMapper.deleteFile(i_category); // db 소분류 내 파일 삭제
		
		// db에서 소분류 삭제
		return mainMapper.deleteSmallCategory(i_category);
	}

	// DB 대분류 카테고리 삭제
	public int deleteLargeCategory(String id) {
		int i_category = ConvertType.convertStringToInt(id);
		if (Verification.checkNullByString(id)) {
			return 2;
		}
		String path = mainMapper.getCategoryPathByIcategory(i_category);
		deleteLocalCategory(path);
		
		List<Integer> list = mainMapper.getICategoryByIcategoryTop(i_category);
		for(Integer l : list) {
			fileMapper.deleteFile(l);
		}
		
		mainMapper.deleteLargeCategory(i_category); // 대분류 삭제
		return mainMapper.deleteSmallCategory(i_category); // 소분류 삭제
	}

	// 로컬 디렉토리 파일 생성
	@Override
	public String createFile(CategoryDTO dto) {
		String urlPath = "";

		if (dto.getCategory_order() == 1) {
			urlPath = path + dto.getCategory_nm();
			File folder = new File(urlPath);
			folder.mkdirs();
		} else {
			urlPath = path + getCategoryNmByTop(dto.getCategory_top());
			urlPath += "\\" + dto.getCategory_nm();
			File folder = new File(urlPath);
			folder.mkdirs();
		}
		

		return urlPath;
	}
	int a = 0;
	// 로컬 디렉토리 삭제
	@Override
	public void deleteLocalCategory(String path) {
		File folder = new File(path);
		try {
			if (folder.exists()) {
				File[] folder_list = folder.listFiles(); // 파일리스트 얻어오기
				for (int i = 0; i < folder_list.length; i++) {
					if (folder_list[i].isFile()) {
						folder_list[i].delete();
					} else { // 폴더안에 폴더가 있을 경우
						deleteLocalCategory(folder_list[i].getPath()); // 재귀함수호출
					}
					boolean fileDeleted = folder_list[i].delete();
					System.out.println(fileDeleted);
				}
				 boolean directoryDeleted = folder.delete(); // 폴더 삭제
				 //System.out.println(directoryDeleted);
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	// main/home에 기본적으로 담길 내용
	@Override
	public ModelAndView homeCategoryInfo(ModelAndView mv) {
		
		mv.setViewName("main/home");
		mv.addObject("category", getCategoryList());
		mv.addObject("totalNumberPosts", getTotalNumberPosts());
		
		return mv;
	}
}
