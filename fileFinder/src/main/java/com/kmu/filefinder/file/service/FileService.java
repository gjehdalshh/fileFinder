package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.common.utils.ConvertType;
import com.kmu.filefinder.file.dto.FileDTO;
import com.kmu.filefinder.file.mapper.FileMapper;
import com.kmu.filefinder.main.dao.CategoryDAO;
import com.kmu.filefinder.main.mapper.MainMapper;

@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private MainMapper mainMapper;

	private int path = 0;

	public void filePath(String path) {
		this.path = ConvertType.convertStringToInt(path);
	}

	// 파일 업로드
	public int fileUpload(MultipartHttpServletRequest files) {
		String urlPath = getCategoryPathByIcategory(path);
		String uploadFolder = urlPath + "\\";
		List<MultipartFile> list = files.getFiles("files");

		if (!checkFileExistence(list)) { // 파일이 존재한다면
			return 2;
		}

		for (int i = 0; i < list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());

			increaseFileCount();
			createFile(fileRealName, fileExtension); // DB 저장
			File saveFile = new File(uploadFolder + "\\" + fileRealName);
			try {
				list.get(i).transferTo(saveFile); // 로컬 저장
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	// DB 분류별 카테고리 개수 증가
	private void increaseFileCount() {
		mainMapper.increaseSmallFileCount(path); // DB 소분류 카테고리 개수 증가
		CategoryDAO dao = mainMapper.getCategoryByIcategory(path);
		int i_category = mainMapper.getCategoryIcategoryByCategoryTop(dao.getCategory_top());
		mainMapper.increaseLargeFileCount(i_category); // DB 대분류 카테고리 개수 증가
	}

	// 파일 존재 유무 확인
	private boolean checkFileExistence(List<MultipartFile> list) { // insert 전 파일 존재 유무 확인
		List<String> dao = getFileNameList();
		List<String> fileNameList = new ArrayList<String>();
		for (MultipartFile item : list) { // 문자열 리스트
			fileNameList.add(item.getOriginalFilename());
		}
		for (String item : fileNameList) { // 존재 유무 확인
			if (dao.contains(item)) {
				return false;
			}
		}
		return true;
	}

	// 파일 이름 리스트 가져오기
	private List<String> getFileNameList() {
		return fileMapper.getFileNameList();
	}

	// DB 저장
	private void createFile(String fileRealName, String fileExtension) {
		FileDTO dto = new FileDTO();
		dto.setI_category(this.path);
		dto.setFile_nm(fileRealName);
		dto.setFile_extension(fileExtension);

		fileMapper.createFile(dto);
	}

	// 카테고리 넘버로 파일 경로를 가져옴
	private String getCategoryPathByIcategory(int path) {
		return fileMapper.getCategoryPathByIcategory(path);
	}
}
