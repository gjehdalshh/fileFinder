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
import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.dto.FileDTO;
import com.kmu.filefinder.file.mapper.FileMapper;
import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.mapper.MainMapper;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private FileMapper fileMapper;

	@Autowired
	private MainMapper mainMapper;

	@Autowired
	private FileExtractionServiceImpl fileExtractionServiceImpl;

	private int path = 0;

	// @Override
	public void filePath(String path) {
		System.out.println(path);
		this.path = ConvertType.convertStringToInt(path);
	}

	// 파일 업로드
	// @Override
	public int fileUpload(MultipartHttpServletRequest files) {
		String urlPath = fileMapper.getCategoryPathByIcategory(path);
		String uploadFolder = urlPath + "\\";
		List<MultipartFile> list = files.getFiles("files");

		if (!checkFileExistence(list)) { // 파일이 존재한다면
			return 2;
		}

		for (int i = 0; i < list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());
			increaseFileCount();

			String filePath = uploadFolder + fileRealName;
			createFile(fileRealName, fileExtension, filePath); // DB 저장
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

	// 파일 존재 유무 확인
	@Override
	public boolean checkFileExistence(List<MultipartFile> list) { // insert 전 파일 존재 유무 확인
		List<String> dao = fileMapper.getFileNameList();
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

	// DB 분류별 카테고리 개수 증가
	public void increaseFileCount() {
		mainMapper.increaseSmallFileCount(path); // DB 소분류 카테고리 개수 증가
		CategoryDTO dto = mainMapper.getCategoryByIcategory(path);
		int i_category = mainMapper.getCategoryIcategoryByCategoryTop(dto.getCategory_top());
		mainMapper.increaseLargeFileCount(i_category); // DB 대분류 카테고리 개수 증가
	}

	// DB 저장
	public void createFile(String fileRealName, String fileExtension, String filePath) {
		FileDTO dto = FileDTO.builder().i_category(this.path).file_nm(fileRealName).file_extension(fileExtension)
				.file_path(filePath).build();

		fileMapper.createFile(dto);
	}

	// 전체 파일과 카테고리 정보 가져오기
	public List<FileCategoryDTO> getFileCategoryInfoList() throws IOException {
		System.out.println("aaaaaaaaaaaaaaaa");
		List<FileCategoryDTO> list = fileMapper.getFileCategoryInfoList();
	
		int i = 0;
		for (FileCategoryDTO d : list) {
			String text = fileExtractionServiceImpl.extractSummaryTextByPDF(d.getFile_path());
			list.get(i++).setSummaryText(text);
		}

		return list;
	}
	
	//  대분류 클릭 시 세부 소분류 전체 보기
	public List<List<FileCategoryDTO>> getLargeFileInfoList(String category_nm) throws IOException {
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		List<Integer> list_int = fileMapper.getIcategoryByCategoryTop(i_category);
		
		List<List<FileCategoryDTO>> l = new ArrayList<List<FileCategoryDTO>>();
		
		for (Integer i : list_int) {
			List<FileCategoryDTO> list = fileMapper.getFileInfoList(i);
		
			int index = 0;
			for (FileCategoryDTO d : list) {
				String text = fileExtractionServiceImpl.extractSummaryTextByPDF(d.getFile_path());
				list.get(index++).setSummaryText(text);
			}
			
			l.add(list);
		}
		
		return l;
	}
	
	public List<FileCategoryDTO> getSmallFileInfoList(String category_nm) throws IOException {
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		List<FileCategoryDTO> list = fileMapper.getFileInfoList(i_category);
		
		int i = 0;
		for (FileCategoryDTO d : list) {
			String text = fileExtractionServiceImpl.extractSummaryTextByPDF(d.getFile_path());
			list.get(i++).setSummaryText(text);
		}
		
		return list;
	}

	// 파일 이름 리스트 가져오기
	public List<String> getFileNameList() {
		return fileMapper.getFileNameList();
	}

	// 카테고리 넘버로 파일 경로를 가져옴
	public String getCategoryPathByIcategory(int path) {
		return fileMapper.getCategoryPathByIcategory(path);
	}
}
