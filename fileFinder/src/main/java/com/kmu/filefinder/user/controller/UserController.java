package com.kmu.filefinder.user.controller;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.user.dto.UserAuthManagementDTO;
import com.kmu.filefinder.user.dto.UserChangePwDTO;
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
	
	@GetMapping("/user/findId")
	public ModelAndView findId() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("find", "id");
		mv.setViewName("/user/find");
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/user/findId")
	public Map<String, Object> findIdProc(@RequestBody UserDTO dto) {
		
		Map<String, Object> val = new HashedMap<String, Object>();
		val.put("result", userService.findUserId(dto));
		
		return val;
	}
	
	@GetMapping("/user/findPw")
	public ModelAndView findPw() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("find", "pw");
		mv.setViewName("/user/find");
		return mv;
	}
	
	@ResponseBody
	@PostMapping("/user/findPw")
	public Map<String, Object> findPwProc(@RequestBody UserDTO dto) {
		
		Map<String, Object> val = new HashedMap<String, Object>();
		val.put("result", userService.findUserPw(dto));
		
		return val;
	}
	
	@ResponseBody
	@PostMapping("/user/changePw")
	public Map<String, Object> changePw(@RequestBody UserChangePwDTO dto) {
		
		Map<String, Object> val = new HashedMap<String, Object>();
		val.put("result", userService.checkUserInfo(dto));
		
		return val;
	}
	
	// 회원 정보 리스트
	@GetMapping("/user/management")
	public ModelAndView UserMenagement() {
		ModelAndView mv = userService.OpenUserPage();
		mv.addObject("userInfoList", userService.getUserInfoList());
		mv.addObject("current_title", "Member Information");
		return mv;
	}
	
	// 승인 대기 유저 리스트
	@GetMapping("/user/userRistration")
	public ModelAndView UserRistration() {
		ModelAndView mv = userService.OpenUserPage();
		mv.addObject("userInfoList", userService.getUserInfoListUnapproved());
		mv.addObject("current_title", "Request for membership");
		return mv;
	}
	
	// 회원가입 승인
	@ResponseBody
	@PostMapping("/user/approval")
	public int approval(@RequestBody String id) {
		return userService.approvalUserRegistration(id);
	}
	
	// 회원가입 미승인
	@ResponseBody
	@DeleteMapping("/user/unapproval/{id}")
	public int unapproval(@PathVariable("id") String id) {
		return userService.unapprovalUserResistration(id);
	}
	
	// 유저 권한 변경
	@ResponseBody
	@PutMapping("/user/changeUserAuthority")
	public int changeUserAuthority(@RequestBody UserAuthManagementDTO dto) {
		return userService.changeUserAuthority(dto);
	}
}


