package com.kmu.filefinder.main.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView home() {

		ModelAndView mv = new ModelAndView();
		
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
	public ModelAndView entireCategory() throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("fileCategoryInfoList", fileService.getFileCategoryInfoList());
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
}
