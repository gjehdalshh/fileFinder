package com.kmu.filefinder.file.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.file.dto.FileCategoryDTO;
import com.kmu.filefinder.file.service.FileDownloadServiceImpl;
import com.kmu.filefinder.file.service.FileServiceImpl;

@Controller
public class FileController {

	@Autowired
	private FileServiceImpl fileService;
	
	@Autowired
	private FileDownloadServiceImpl fileDownloadServiceImpl;

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
	public void fileOpen(HttpServletResponse resp, @RequestParam("fileName") String fileName, @RequestParam("extension") String extension) throws IOException  {
		fileService.fileOpen(resp, fileName, extension);
	}
	
	// 파일 다운로드 기능 - 구현중
	@ResponseBody
	@PostMapping("/fileDownload")
	public int fileDownload(@RequestBody FileCategoryDTO fileDTO) throws IOException {
		return fileDownloadServiceImpl.fileDownload(fileDTO);
	}
	
	@ResponseBody
	@PostMapping("/totalFileDownload")
	public int totalFileDownload(@RequestBody FileCategoryDTO fileDTO) throws IOException {
		return fileDownloadServiceImpl.totalFileDownload(fileDTO);
	}
	
	// 파일 삭제
	@ResponseBody
	@DeleteMapping("/fileDelete/{i_file}")
	public int fileDelete(@PathVariable("i_file") int i_file) {
		System.out.println("확인 : " + i_file);
		return fileService.fileDelete(i_file);
	}
}
