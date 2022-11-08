package com.kmu.filefinder.file.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileService {
	void filePath(String path);
	int fileUpload(MultipartHttpServletRequest files);
	boolean checkFileExistence(List<MultipartFile> list);
}
