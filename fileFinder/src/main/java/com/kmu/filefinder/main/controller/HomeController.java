package com.kmu.filefinder.main.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.main.dto.CategoryDTO;
import com.kmu.filefinder.main.service.CategoryService;

@Controller
public class HomeController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public ModelAndView home() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main/home");
		mv.addObject("category",categoryService.getCategoryList());
		
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/createCategory")
	public Map<String, Object> createCategory(@RequestBody CategoryDTO dto) {
		
		Map<String, Object> val = new HashMap<String, Object>();
		val.put("category", categoryService.createCategory(dto));
		
		return val;
	}
}
