package com.kmu.filefinder.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kmu.filefinder.user.dto.UserDTO;

@Mapper
public interface UserMapper {
	// read
	UserDTO getUserInfoByUserId(String user_id);
	UserDTO getUserInfoByUserEmail(String user_email);
	UserDTO checkUserExistence(UserDTO dto);
	List<UserDTO> getUserInfoList();
	List<UserDTO> getUserInfoListUnapproved();
	
	// create
	int insUser(UserDTO dto);
	
	
	// update
	int approvalUserRegistration(String id);
	
	// delete
	int unapprovalUserResistration(String id);
}
