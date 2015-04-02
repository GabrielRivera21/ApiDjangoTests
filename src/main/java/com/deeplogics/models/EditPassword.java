package com.deeplogics.models;

public class EditPassword {
	
	private String currentPassword;
	
	private String password1;
	
	private String password2;

	public EditPassword(String currentPassword, String password1, String password2) {
		this.currentPassword = currentPassword;
		this.password1 = password1;
		this.password2 = password2;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
}
