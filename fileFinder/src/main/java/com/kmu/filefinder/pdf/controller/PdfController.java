package com.kmu.filefinder.pdf.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kmu.filefinder.pdf.service.FileService;

@Controller
public class PdfController {

	@Autowired
	private FileService pdfService;
	
	@ResponseBody
	@PostMapping("/fileUpload")
	public int upload(MultipartHttpServletRequest files) {
		return pdfService.fileUpload(files);
	}
	
	@ResponseBody
	@PostMapping("/filePath")
	public void filePath(@RequestBody String path) {
		path = path.replaceAll("\"", "");
		pdfService.filePath(path);
	}

	@GetMapping("/pdf")
	public void pdf(Model model) throws IOException {

		File file = new File("");

		PDDocument document = PDDocument.load(file);

		// document.getPages().getCount() - 전체 페이지 수

		PDFTextStripper s = new PDFTextStripper();

		PDDocument d = new PDDocument();
		PDPage p = document.getPage(1);
		d.addPage(p);

		String content = s.getText(d);

		if (content.contains("자바")) {
			System.out.println("포함");
		}

		System.out.println("========docx text extractor =========");
		System.out.println(content);
		model.addAttribute("content", content);

		document.close();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/p") // 다운로드 시 다운되는 이름
	public ResponseEntity<Resource> resourceFileDownload(HttpServletResponse response) {

		FileInputStream fis = null;
		BufferedOutputStream bos = null;

		try {
			String pdfFileName = "";

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
