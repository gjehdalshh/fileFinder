package com.kmu.filefinder.main.controller;

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

import com.kmu.filefinder.common.utils.ConvertType;
import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.service.CategoryService;

@Controller
public class MainController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public ModelAndView home() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/home");
		mv.addObject("category",categoryService.getCategoryList());
		mv.addObject("totalNumberPosts", categoryService.getTotalNumberPosts());
		
		return mv;
	}
	
	// 카테고리 생성
	@ResponseBody
	@PostMapping("/createCategory")
	public Map<String, Object> createCategory(@RequestBody CategoryDTO dto) {
		
		Map<String, Object> val = new HashMap<String, Object>();
		val.put("category", categoryService.createCategory(dto));
		
		return val;
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
