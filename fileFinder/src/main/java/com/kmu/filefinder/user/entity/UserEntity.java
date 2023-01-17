package com.kmu.filefinder.user.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
	int i_user;
	String user_id;
	String user_pw;
	String user_nm;
	String user_email;
	// 0은 관리자, 1은 회원
	int user_authority;
	String user_department;
	// 0은 회원가입 대기 상태, 1은 회원 상태
	int user_status;
}
