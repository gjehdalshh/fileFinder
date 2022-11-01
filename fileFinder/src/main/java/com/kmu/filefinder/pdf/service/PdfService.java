package com.kmu.filefinder.pdf.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.common.utils.ConvertType;
import com.kmu.filefinder.pdf.dto.PdfDTO;
import com.kmu.filefinder.pdf.mapper.PdfMapper;

@Service
public class PdfService {

	@Autowired
	private PdfMapper pdfMapper;
	
	private int path = 0;
	
	public void filePath(String path) {
		System.out.println("확인1 : " + path);
		this.path = ConvertType.convertStringToInt(path);
	}
	
	public int fileUpload(MultipartHttpServletRequest files) {
		if(path == 0) {
			return 2;
		}
		
		System.out.println("확인2 : " + path);
		String urlPath = getCategoryPathByIcategory(path);
		System.out.println(urlPath);
		String uploadFolder = urlPath + "\\";
		List<MultipartFile> list = files.getFiles("files");
		for(int i = 0; i<list.size(); i++) {
			String fileRealName = list.get(i).getOriginalFilename();
			String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
			
			createFile(fileRealName, fileExtension);
			File saveFile = new File(uploadFolder + "\\" + fileRealName);
			try {
				list.get(i).transferTo(saveFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
	
	public void createFile(String fileRealName, String fileExtension) {
		PdfDTO dto = new PdfDTO();
		dto.setI_category(this.path);
		dto.setPdf_nm(fileRealName);
		dto.setPdf_extension(fileExtension);
		
		pdfMapper.createFile(dto);
	}
	
	public String getCategoryPathByIcategory(int path) {
		return pdfMapper.getCategoryPathByIcategory(path);
	}
}
