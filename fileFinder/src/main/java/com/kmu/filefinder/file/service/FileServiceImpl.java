package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.Files;
import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.common.paging.Pagination;
import com.kmu.filefinder.common.paging.PagingResponse;
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
	private int count = 0;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increaseCount() {
		this.count++;
	}

	// @Override
	public void filePath(String path) {
		System.out.println(path);
		this.path = ConvertType.convertStringToInt(path);
	}

	// 파일 업로드
	// @Override
	public int fileUpload(MultipartHttpServletRequest files) {
		if (path == 0) {
			return 2;
		}

		String urlPath = fileMapper.getCategoryPathByIcategory(path);
		String uploadFolder = urlPath + "\\";
		List<MultipartFile> list = files.getFiles("files");

		if (!checkFileExistence(list)) { // 파일이 존재한다면
			return 3;
		}

		for (int i = 0; i < list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());
			increaseFileCount();
			int extensionLength = 4;
			if(fileExtension.equals(".docx")) {
				extensionLength = 5;
			}
			String parseFileName = fileRealName.substring(0, fileRealName.length() - extensionLength);
			String filePath = uploadFolder + fileRealName;
			createFile(parseFileName, fileExtension, filePath); // DB 저장
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
			String fileRealName = item.getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."), fileRealName.length());
			int extensionLength = 4; // pdf
			if(fileExtension.equals(".docx")) {
				extensionLength = 5;
			}
			String parseFileName = fileRealName.substring(0, fileRealName.length() - extensionLength);
			fileNameList.add(parseFileName);
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
	public List<FileCategoryDTO> getFileCategoryInfoList(PagingVO pagingVo) throws IOException {
		
		int count = mainMapper.getTotalNumberPosts();
		if(count < 1) {
			return null;
		}
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);
		List<FileCategoryDTO> list = fileMapper.getFileCategoryInfoList(pagingVo);
		addText(list);
	
		return list;
	}

	// 대분류 카테고리 정보 가져오기
	public List<List<FileCategoryDTO>> getLargeFileInfoList(String category_nm, PagingVO pagingVo) throws IOException {
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		List<Integer> list_int = fileMapper.getIcategoryByCategoryTop(i_category);
		List<List<FileCategoryDTO>> l = new ArrayList<List<FileCategoryDTO>>();
		int count = mainMapper.getTotalNumberPosts();
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);
		for (Integer i : list_int) {
			List<FileCategoryDTO> list = fileMapper.getFileInfoList(i, pagingVo);
			System.out.println("테스트");
			addText(list);
			l.add(list);
		}
		return l;
	}

	// 소분류 카테고리 정보 가져오기
	public List<FileCategoryDTO> getSmallFileInfoList(String category_nm, PagingVO pagingVo) throws IOException { // 소분류 클릭 시 소분류 리스트 보기
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		int count = mainMapper.getTotalNumberPosts();
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);
		List<FileCategoryDTO> list = fileMapper.getFileInfoList(i_category, pagingVo);
		addText(list);
		
		return list;
	}

	@Override
	public List<FileCategoryDTO> addText(List<FileCategoryDTO> list) throws IOException { // 간단한 내용 담기

		int i = 0;
		for (FileCategoryDTO d : list) {
			String text = fileExtractionServiceImpl.extractSummary(d);
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

	/* 검색을 통한 리스트 불러오기 */
	public List<FileCategoryDTO> getFileSearchInfoList(String category, String content) throws IOException {
		count = 0;

		List<FileCategoryDTO> fileList = new ArrayList<FileCategoryDTO>();
		// 제목 리스트를 불러와 입력한 제목이 포함되어 있다면
		if (category.equals("searchTitle")) {
			return getSearchByTitle(fileList, content);
		} else if (category.equals("searchCategory")) {
			return getSearchByContent(fileList, content);
		}
		return fileList;
	}

	// 제목으로 검색
	public List<FileCategoryDTO> getSearchByTitle(List<FileCategoryDTO> fileList, String content) throws IOException {
		List<String> list = fileMapper.getFileNameList();

		for (String str : list) {
			String lowerStr = str.toLowerCase();
			String upperStr = str.toUpperCase();
			if (str.contains(content) || lowerStr.contains(content) || upperStr.contains(content)) { // 대소문자 가능
				count++;
				FileCategoryDTO dto = fileMapper.getFileSearchInfoListByFileName(str);
				fileList.add(dto);
				addText(fileList);
			}
		}
		return fileList;
	}

	// 내용으로 검색
	public List<FileCategoryDTO> getSearchByContent(List<FileCategoryDTO> fileList, String content) throws IOException {

		fileList = fileMapper.getFileSearchInfoList(); // 모든 file을 가져옴
		List<FileCategoryDTO> temp = new ArrayList<FileCategoryDTO>();
		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();

		for (FileCategoryDTO file : fileList) {
			temp = fileExtractionServiceImpl.extractContent(file, content);
			for (FileCategoryDTO dto : temp) {
				list.add(dto);
			}
		}
		return list;
	}

	// 파일 열기
	@Override
	public void fileOpen(HttpServletRequest req, HttpServletResponse resp, String fileName) throws IOException {
		String filePath = fileMapper.getFilePathByFileName(fileName);
		String extension = fileMapper.getExtensionByFileName(fileName);
		if (extension.equals(".pdf")) {
			fileOpenPdf(resp, filePath);
		} else if (extension.equals(".docx")) {
			fileOpenDocx(resp, filePath);
		}
	}

	// pdf로 열기
	public void fileOpenPdf(HttpServletResponse resp, String filePath) {
		File file = new File(filePath);
		resp.setHeader("Content-Type", "application/pdf");
		resp.setHeader("Content-Length", String.valueOf(file.length()));
		resp.setHeader("Content-Disposition", "inline");

		try {
			Files.copy(file, resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// docx로 열기
	public void fileOpenDocx(HttpServletResponse resp, String filePath) throws IOException {
	}

	// 파일 다운로드 기능 - 구현중
	public int fileDownload() {
		File file = new File("d:\\example\\file.txt");
		 
        try {
            if (file.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		return 0;
	}
}
