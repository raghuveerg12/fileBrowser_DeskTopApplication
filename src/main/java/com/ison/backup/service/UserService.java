package com.ison.backup.service;

import java.util.List;

import com.ison.backup.model.User;


/**
 * UserService
 * Jeyaprakash Ganesan
 * 
 */
public interface UserService {
	
	User findById(long id);
	
	User findByName(String name);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserById(long id);

	List<User> findAllUsers(); 
	
	void deleteAllUsers();
	
	public boolean isUserExist(User user);
	
}
