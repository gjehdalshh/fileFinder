package com.kmu.filefinder.user.dto;

import lombok.Data;

@Data
public class UserChangePwDTO {
	private String user_id;
	private String current_pw;
	private String change_pw;
	private String confirm_change_pw;
}
