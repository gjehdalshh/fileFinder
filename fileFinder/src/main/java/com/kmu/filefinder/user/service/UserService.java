package com.kmu.filefinder.user.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.kmu.filefinder.user.dto.UserAuthManagementDTO;
import com.kmu.filefinder.user.dto.UserChangePwDTO;
import com.kmu.filefinder.user.dto.UserDTO;
import com.kmu.filefinder.user.mapper.UserMapper;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HttpSession hs;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	public int loginProc(UserDTO dto) {
		UserDTO vo = userMapper.checkUserExistence(dto);
		
		if (vo == null || !idPasswordCheck(dto)) {
			return 2;
		}
		if (vo.getUser_status() == 0) {
			return 3;
		}
		hs.setAttribute("user", vo);
		return 1;
	}

	private boolean idPasswordCheck(UserDTO dto) {
		UserDTO vo = userMapper.checkUserExistence(dto);

		if (!bcrypt.matches(dto.getUser_pw(), vo.getUser_pw())) {
			return false;
		}
		return true;
	}

	public void logout() {
		hs.invalidate();
	}

	public int joinProc(UserDTO dto) {
		UserDTO vo = userMapper.getUserInfoByUserId(dto.getUser_id());
		if (vo != null) {
			return 2;
		}
		vo = userMapper.getUserInfoByUserEmail(dto.getUser_email());
		if (vo != null) {
			return 3;
		}
		// 이메일 확인
		String pattern = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		if (!Pattern.matches(pattern, dto.getUser_email())) {
			return 4;
		}

		dto.setUser_pw(bcrypt.encode(dto.getUser_pw()));

		return userMapper.insUser(dto);
	}
	
	public ModelAndView OpenUserPage() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("user/management");
		
		return mv;
	}

	public List<UserDTO> getUserInfoList() {
		return userMapper.getUserInfoList();
	}
	
	public List<UserDTO> getUserInfoListUnapproved() {
		return userMapper.getUserInfoListUnapproved();
	}

	public int approvalUserRegistration(String id) {
		return userMapper.approvalUserRegistration(id);
	}

	public int unapprovalUserResistration(String id) {
		return userMapper.unapprovalUserResistration(id);
	}
	
	public int changeUserAuthority(UserAuthManagementDTO dto) {
		
		String authority = dto.getUser_authority();
		
		if(authority.equals("manager")) {
			return userMapper.changeUserAuthorityManager(dto.getI_user());
		} else if(authority.equals("member")) {
			return userMapper.changeUserAuthorityMember(dto.getI_user());
		} else if(authority.equals("delete")) {
			return userMapper.deleteUserByIUser(dto.getI_user());
		}
		return 2;
	}
	
	public String findUserId(UserDTO dto) {
		return userMapper.findUserId(dto);
	}
	public String findUserPw(UserDTO dto) {
		return userMapper.findUserPw(dto);
	}
	public int checkUserInfo(UserChangePwDTO dto) {
		
		String user_pw = userMapper.getUserPw(dto.getUser_id());
		if(user_pw == null) { // 아이디가 존재하지 않음
			return 2;
		}
		if(!dto.getChange_pw().equals(dto.getConfirm_change_pw())) {
			return 3;
		}
		System.out.println("확인");
		System.out.println(dto.getCurrent_pw());
		dto.setCurrent_pw(bcrypt.encode(dto.getChange_pw()));
		System.out.println("안됨");
		return userMapper.changeUserPw(dto);
	}
}
