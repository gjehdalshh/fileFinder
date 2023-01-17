package com.kmu.filefinder.file.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.file.dto.FileCategoryDTO;

public interface FileService {
	void filePath(String path);

	int fileUpload(MultipartHttpServletRequest files);

	boolean checkFileExistence(List<MultipartFile> list);

	List<FileCategoryDTO> addText(List<FileCategoryDTO> list) throws IOException;
	List<FileCategoryDTO> addTextBySearch(List<FileCategoryDTO> list) throws IOException;

	void fileOpen(HttpServletResponse resp, String fileName, String extension) throws FileNotFoundException, IOException;
}
