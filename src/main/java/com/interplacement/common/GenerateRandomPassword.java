package com.interplacement.common;

public class GenerateRandomPassword {

	public static String generateRandomPassword(int length) {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = (int) (Math.random() * chars.length());
			password.append(chars.charAt(randomIndex));
		}
		return password.toString();
	}

}
