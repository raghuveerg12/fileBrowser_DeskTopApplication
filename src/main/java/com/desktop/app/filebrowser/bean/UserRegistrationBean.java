package com.desktop.app.filebrowser.bean;

import java.io.Serializable;

public class UserRegistrationBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 10000L;
	private String firstName;
	private String lastName;
	private String passwords;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getMailId() {
		return MailId;
	}
	public void setMailId(String mailId) {
		MailId = mailId;
	}
	public String getRememberMe() {
		return RememberMe;
	}
	public void setRememberMe(String rememberMe) {
		RememberMe = rememberMe;
	}
	private String MailId;
	private String RememberMe;
	

}
