package com.kmu.filefinder.file.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.service.FileServiceImpl;
import com.kmu.filefinder.main.dto.CategoryDTO;

@Controller
public class FileController {

	@Autowired
	private FileServiceImpl fileService;

	ResourceLoader resourceLoader;

	@Autowired
	public FileController(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@ResponseBody
	@PostMapping("/fileUpload")
	public int upload(MultipartHttpServletRequest files) {
		return fileService.fileUpload(files);
	}

	@ResponseBody
	@PostMapping("/filePath")
	public void filePath(@RequestBody String path) {
		path = path.replaceAll("\"", "");
		fileService.filePath(path);
	}

	@GetMapping("/fileOpen")
	public void fileOpen(HttpServletRequest req, HttpServletResponse resp, @RequestParam("fileName") String fileName, @RequestParam("extension") String extension) throws IOException  {
		fileService.fileOpen(req, resp, fileName, extension);
	}
	
	// 파일 다운로드 기능 - 구현중
	@ResponseBody
	@PostMapping("")
	public int fileDownload() {
		
		return fileService.fileDownload();
	}
}
