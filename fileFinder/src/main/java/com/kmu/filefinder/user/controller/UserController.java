package com.kmu.filefinder.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.user.dto.UserDTO;
import com.kmu.filefinder.user.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/user/login")
	public void login() {}
	
	@ResponseBody
	@PostMapping("/user/login")
	public int loginProc(@RequestBody UserDTO dto) {
		return userService.loginProc(dto);
	}
	
	@RequestMapping("/user/logout")
	public String logout() {
		userService.logout();
        return "redirect:/";
	}
	
	@GetMapping("/user/join")
	public void join() {}
	
	@ResponseBody
	@PostMapping("/user/join")
	public int joinProc(@RequestBody UserDTO dto) {
		return userService.joinProc(dto);
	}
	
	@GetMapping("/user/management")
	public ModelAndView UserMenagement() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("userInfoList", userService.getUserInfoList());
		mv.setViewName("user/management");
		return mv;
	}
	
	@GetMapping("/user/userRistration")
	public ModelAndView UserRistration() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("userInfoList", userService.getUserInfoListUnapproved());
		mv.setViewName("user/management");
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/user/approval")
	public int approval(@RequestBody String id) {
		return userService.approvalUserRegistration(id);
	}
	
	@ResponseBody
	@DeleteMapping("/user/unapproval/{id}")
	public int unapproval(@PathVariable("id") String id) {
		System.out.println("id : " + id);
		return userService.unapprovalUserResistration(id);
	}
}


