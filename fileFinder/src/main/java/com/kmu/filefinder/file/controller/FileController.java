package com.kmu.filefinder.file.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.file.service.FileServiceImpl;


@Controller
public class FileController {

	@Autowired
	private FileServiceImpl fileService;
	
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

	@SuppressWarnings("unchecked")
	@RequestMapping("/p") // 다운로드 시 다운되는 이름
	public ResponseEntity<Resource> resourceFileDownload(HttpServletResponse response) {

		FileInputStream fis = null;
		BufferedOutputStream bos = null;

		try {
			String pdfFileName = "C:\\Users\\User\\Desktop\\계명대학교\\4학년\\2학기\\소프트웨어공학\\강의자료2\\09(a) 설계원리.pdf";

			File pdfFile = new File(pdfFileName);

			// 클라이언트 브라우져에서 바로 보는 방법(헤더 변경)
			response.setContentType("application/pdf");

			// ★ 이 구문이 있으면 [다운로드], 이 구문이 없다면 바로 target 지정된 곳에 view 해줍니다.
			// response.addHeader("Content-Disposition", "attachment; filename=" +
			// pdfFile.getName() + ".pdf");

			fis = new FileInputStream(pdfFile);

			int size = fis.available(); // 지정 파일에서 읽을 수 있는 바이트 수를 반환

			byte[] buf = new byte[size]; // 버퍼설정

			int readCount = fis.read(buf);

			response.flushBuffer();

			bos = new BufferedOutputStream(response.getOutputStream());

			bos.write(buf, 0, readCount);

			bos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (ResponseEntity<Resource>) response;
	}
}
