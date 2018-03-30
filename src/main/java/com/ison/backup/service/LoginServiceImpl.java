package com.ison.backup.service;

import org.springframework.stereotype.Service;

import com.ison.backup.model.User;

/**
 * Implementation class for LoginService
 * Jeyaprakash Ganesan
 * 
 */
@Service("loginService")
public class LoginServiceImpl implements LoginService{

	public boolean isUserExist(User user) {
		return true;
	}

}
