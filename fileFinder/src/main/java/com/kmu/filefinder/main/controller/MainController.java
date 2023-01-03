package com.kmu.filefinder.main.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.common.dto.PagingVO;
import com.kmu.filefinder.file.service.FileServiceImpl;
import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.service.CategoryServiceImpl;

@Controller
public class MainController {

	@Autowired
	private CategoryServiceImpl categoryService;

	@Autowired
	private FileServiceImpl fileService;

	@GetMapping("/")
	public ModelAndView home(@ModelAttribute("pagingVO") PagingVO pagingVo) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getFileCategoryInfoList(pagingVo));
		mv.addObject("currentPath", "mainCategory");
		return categoryService.homeCategoryInfo(mv);
		
	}

	// 카테고리 생성
	@ResponseBody
	@PostMapping("/createCategory")
	public Map<String, Object> createCategory(@RequestBody CategoryDTO dto) {

		Map<String, Object> val = new HashMap<String, Object>();
		val.put("category", categoryService.createCategory(dto));

		return val;
	}

	// 전체 카테고리 뿌리기
	@GetMapping("/category")
	public ModelAndView entireCategory(@ModelAttribute("pagingVO") PagingVO pagingVo) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getFileCategoryInfoList(pagingVo));
		mv.addObject("currentPath", "entireCategory");
		return categoryService.homeCategoryInfo(mv);
	}

	// 대분류 카테고리 뿌리기
	@ResponseBody
	@GetMapping("/category/{largeCategory}")
	public ModelAndView largeCategory(@PathVariable("largeCategory") String largeCategory, @ModelAttribute("pagingVO") PagingVO pagingVo) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getLargeFileInfoList(largeCategory, pagingVo));
		mv.addObject("currentPath", "largeCategory");
		return categoryService.homeCategoryInfo(mv);
	}

	// 소분류 카테고리 뿌리기
	@ResponseBody
	@GetMapping("/category/{largeCategory}/{smallCategory}")
	public ModelAndView smallCategory(@PathVariable("largeCategory") String largeCategory,
			@PathVariable("smallCategory") String smallCategory, @ModelAttribute("pagingVO") PagingVO pagingVo) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getSmallFileInfoList(smallCategory, pagingVo));
		mv.addObject("currentPath", "smallCategory");
		return categoryService.homeCategoryInfo(mv);
	}

	// 소분류 삭제
	@ResponseBody
	@DeleteMapping("/deleteSmallCategory/{id}")
	public int deleteSmallCategory(@PathVariable("id") String id) {
		return categoryService.deleteSmallCategory(id);
	}

	// 대분류 삭제
	@ResponseBody
	@DeleteMapping("/deleteLargeCategory/{id}")
	public int deleteLargeCategory(@PathVariable("id") String id) {
		return categoryService.deleteLargeCategory(id);
	}

	@ResponseBody
	@GetMapping("/search")
	public ModelAndView test(@RequestParam("category") String category, @RequestParam("content") String content, @ModelAttribute("params") final PagingVO pagingVo)
			throws IOException {

		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getFileSearchInfoList(category, content));
		mv.addObject("currentPath", content);
		mv.addObject("searchCount", fileService.getCount());

		return categoryService.homeCategoryInfo(mv);
	}

	@GetMapping("/pdf")
	
	
	public static void createWordDocument() {
		
		String doc = "C:\\pdf\\테스트\\ㅁㄴㅇ\\Japan's reign of terror in Korea.docx";
		String pdf = "C:\\pdf\\테스트\\ㅁㄴㅇ\\Japan's reign of terror in Korea.pdf";
		
		try {
            InputStream templateInputStream = new FileInputStream(doc);
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();

            String outputfilepath = pdf;
            FileOutputStream os = new FileOutputStream(outputfilepath);
            Docx4J.toPDF(wordMLPackage,os);
            os.flush();
            os.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
	}
	
	@GetMapping("/p")
	public void aa() throws IOException {
		File file = new File("C:\\Users\\User\\Desktop\\계명대학교\\4학년\\2학기\\소프트웨어공학\\강의자료2\\09(a) 설계원리.pdf");

		PDDocument document = PDDocument.load(file);
		System.out.println("test");
		PDFTextStripper pdfTextStripper = new PDFTextStripper();
		PDDocument pdDocument = new PDDocument();
		
		String text = pdfTextStripper.getText(pdDocument);
		
		System.out.println(text);
	}
}
