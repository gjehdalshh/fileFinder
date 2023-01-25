package com.kmu.filefinder.file.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.Files;
import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.common.paging.Pagination;
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
	
	@Value("${local.pdf.path}")
	private String localPath;

	private int path = 0;
	private int count = 0;
	private String currentCategoryName = "";
	private String searchCategoryName = "";
	private String searchContent = "";

	public int getCount() {
		return count;
	}
	
	public String getCurrentCategoryName() {
		return currentCategoryName;
	}
	public String getSearchCategoryName() {
		return searchCategoryName;
	}
	public String getSearchContent() {
		return searchContent;
	}

	public void increaseCount() {
		this.count++;
	}

	// @Override
	public void filePath(String path) {
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
			if (fileExtension.equals(".docx")) {
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
			if (fileExtension.equals(".docx")) {
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
		if (count < 1) {
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
		// 파일 전체 다운로드 시 사용
		this.currentCategoryName = category_nm;
		
		int i_category = fileMapper.getIcategoryByCategoryNm(category_nm);
		List<List<FileCategoryDTO>> l = new ArrayList<List<FileCategoryDTO>>();

		int count = mainMapper.getLargeNumberPosts(i_category);
		this.count = count;
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);

		List<FileCategoryDTO> list = fileMapper.getFileLargeInfoList(i_category, pagingVo);

		addText(list);
		l.add(list);

		return l;
	}

	// 소분류 카테고리 정보 가져오기
	public List<FileCategoryDTO> getSmallFileInfoList(String largeCateogryNm, String category_nm, PagingVO pagingVo) throws IOException {
		// 파일 전체 다운로드 시 사용
		this.currentCategoryName = category_nm;
		System.out.println("category_nm : " + category_nm);
		int large_i_category = fileMapper.getIcategoryByCategoryNm(largeCateogryNm);
		int i_category = fileMapper.getIcategorySmallByCategoryNm(large_i_category, category_nm);
		System.out.println("들어오나");
		int count = mainMapper.getSmallNumberPosts(i_category);
		System.out.println("확인");
		this.count = count;
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);
		List<FileCategoryDTO> list = fileMapper.getFileSmallInfoList(i_category, pagingVo);
		addText(list);

		return list;
	}

	// 텍스트 추출 후 담기
	@Override
	public List<FileCategoryDTO> addText(List<FileCategoryDTO> list) throws IOException {

		int i = 0;
		for (FileCategoryDTO d : list) {
			String text = fileExtractionServiceImpl.extractText(d);
			list.get(i++).setSummaryText(text);
		}
		return list;
	}

	// 내용 검색 시 텍스트 추출 후 담기
	@Override
	public List<FileCategoryDTO> addTextBySearch(List<FileCategoryDTO> list) throws IOException {

		int i = 0;
		for (FileCategoryDTO d : list) {
			String text = fileExtractionServiceImpl.extractText(d);
			list.get(i++).setFullText(text);
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
	public List<FileCategoryDTO> getFileSearchInfoList(String category, String content, PagingVO pagingVo)
			throws IOException {
		this.searchCategoryName = category;
		this.searchContent = content;
		
		List<FileCategoryDTO> fileList = new ArrayList<FileCategoryDTO>();

		// 제목 리스트를 불러와 입력한 제목이 포함되어 있다면
		if (category.equals("searchTitle")) {
			return getSearchByTitle(fileList, content, pagingVo);
		} else if (category.equals("searchCategory")) {
			return getSearchByContent(fileList, content, pagingVo);
		}
		return fileList;
	}

	// 제목으로 검색
	public List<FileCategoryDTO> getSearchByTitle(List<FileCategoryDTO> fileList, String content, PagingVO pagingVo)
			throws IOException {
		int count = fileMapper.getFileListCountByName(content);
		this.count = count;
		Pagination pagination = new Pagination(count, pagingVo);
		pagingVo.setPagination(pagination);
		List<FileCategoryDTO> list = fileMapper.getFileListByName(content, pagingVo);

		addText(list);

		return list;
	}

	// 내용으로 검색
	public List<FileCategoryDTO> getSearchByContent(List<FileCategoryDTO> fileList, String content, PagingVO pagingVo)
			throws IOException {

		fileList = fileMapper.getFileSearchInfoList(); // 모든 file을 가져옴
		List<FileCategoryDTO> temp = new ArrayList<FileCategoryDTO>();
		List<FileCategoryDTO> list = new ArrayList<FileCategoryDTO>();

		for (FileCategoryDTO file : fileList) {
			temp = fileExtractionServiceImpl.extractSummaryText(file, content);
			for (FileCategoryDTO dto : temp) {
				list.add(dto);
			}
		}
		addTextBySearch(list);
		this.count = list.size();
		Pagination pagination = new Pagination(list.size(), pagingVo);
		pagingVo.setPagination(pagination);

		// 마지막 페이지가 10개가 안된다면
		if (pagingVo.getPagination().getTotalRecordCount() < pagingVo.getPagination().getLimitStart() + 10) {
			return list.subList(pagingVo.getPagination().getLimitStart(),
					pagingVo.getPagination().getTotalRecordCount());
		}
		return list.subList(pagingVo.getPagination().getLimitStart(), pagingVo.getPagination().getLimitStart() + 10);
	}

	// 파일 열기
	@Override
	public void fileOpen(HttpServletResponse resp, String fileName, String extension) throws IOException {
		String filePath = fileMapper.getFilePathByFileName(fileName);
		// 현재 docx 파일은 모달창으로 오픈하기 때문에 구현을 하지 않음
		// 무조건 pdf만 들어갈 것
		if (extension.equals(".pdf")) {
			fileOpenPdf(resp, filePath);
		} else if (extension.equals(".docx")) {
			fileOpenDocx(filePath);
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
	public void fileOpenDocx(String filePath) throws IOException {}
	
	
	// 파일 삭제
	public int fileDelete(int i_file) {
		
		// 로컬 파일 삭제
		FileCategoryDTO dto = fileMapper.getFileByIFile(i_file);
		System.out.println("dto : " + dto);
		CategoryDTO cateDTO = mainMapper.getCategoryByIcategory(dto.getI_category());
		System.out.println("cateDTO : " + cateDTO);
		int iCategory = mainMapper.getCategoryIcategoryByCategoryTop(cateDTO.getCategory_top());
		System.out.println("i_cate : " + iCategory);
		mainMapper.decreaseFileCountByFileDelete(cateDTO.getI_category());
		mainMapper.decreaseFileCountByFileDelete(iCategory);
		
		String deleteFilePath = dto.getFile_path();
		File file = new File(deleteFilePath);
		if(file.exists()) { // 파일이 존재하면
			file.delete(); // 파일 삭제	
			System.out.println("파일이 삭제됨");
		}
		
		// db 파일 삭제
		return fileMapper.fileDelete(i_file);
	}
}
