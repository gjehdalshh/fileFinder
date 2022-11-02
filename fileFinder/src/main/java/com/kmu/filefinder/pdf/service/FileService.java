package com.kmu.filefinder.pdf.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.common.utils.ConvertType;
import com.kmu.filefinder.pdf.dao.PdfDAO;
import com.kmu.filefinder.pdf.dto.PdfDTO;
import com.kmu.filefinder.pdf.mapper.PdfMapper;

@Service
public class FileService {

	@Autowired
	private PdfMapper pdfMapper;
	
	private int path = 0;
	
	public void filePath(String path) {
		this.path = ConvertType.convertStringToInt(path);
	}
	
	// 파일 업로드
	public int fileUpload(MultipartHttpServletRequest files) {
		String urlPath = getCategoryPathByIcategory(path);
		String uploadFolder = urlPath + "\\";
		List<MultipartFile> list = files.getFiles("files");
		
		if(!checkFileExistence(list)) { // 파일이 존재한다면
			return 2;
		}
		
		for(int i = 0; i<list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
			
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
	
	private boolean checkFileExistence(List<MultipartFile> list) { // insert 전 파일 존재 유무 확인
		List<String> dao = getFileNameList();
		List<String> fileNameList = new ArrayList<String>();
		for(MultipartFile item : list) { // 문자열 리스트
			fileNameList.add(item.getOriginalFilename());
		}
		for(String item : fileNameList) { // 존재 유무 확인
		    if(dao.contains(item)) {
		       return false;
		    }
		}
		return true;
	}
	
	private List<String> getFileNameList() { // 파일 이름 리스트 가져오기
		return pdfMapper.getFileNameList();
	}
	
	// DB 저장
	private void createFile(String fileRealName, String fileExtension) {
		PdfDTO dto = new PdfDTO();
		dto.setI_category(this.path);
		dto.setPdf_nm(fileRealName);
		dto.setPdf_extension(fileExtension);
		
		pdfMapper.createFile(dto);
	}
	
	// 카테고리 넘버로 파일 경로를 가져옴
	private String getCategoryPathByIcategory(int path) {
		return pdfMapper.getCategoryPathByIcategory(path);
	}
}
