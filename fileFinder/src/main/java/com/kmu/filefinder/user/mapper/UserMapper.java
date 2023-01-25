package com.kmu.filefinder.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.user.dto.UserChangePwDTO;
import com.kmu.filefinder.user.dto.UserDTO;

@Mapper
public interface UserMapper {
	// read
	UserDTO getUserInfoByUserId(String user_id);
	UserDTO getUserInfoByUserEmail(String user_email);
	UserDTO checkUserExistence(UserDTO dto);
	List<UserDTO> getUserInfoList();
	List<UserDTO> getUserInfoListUnapproved();
	String findUserId(UserDTO dto);
	String findUserPw(UserDTO dto);
	String getUserPw(String user_id);
	
	
	
	// create
	int insUser(UserDTO dto);
	
	
	// update
	int approvalUserRegistration(String id);
	int changeUserAuthorityManager(int i_user);
	int changeUserAuthorityMember(int i_user);
	int changeUserPw(UserChangePwDTO dto);
	
	// delete
	int unapprovalUserResistration(String id);
	int deleteUserByIUser(int i_user);
}
