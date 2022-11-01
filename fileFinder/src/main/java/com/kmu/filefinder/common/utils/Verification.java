package com.kmu.filefinder.common.utils;

public class Verification {
	public static boolean checkNullByString(String data) {
		if(!data.equals("0")) {
			return false;
		}
		return true;
	}
}
