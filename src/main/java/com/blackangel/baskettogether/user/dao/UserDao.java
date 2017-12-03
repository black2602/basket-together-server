package com.blackangel.baskettogether.user.dao;

import java.util.List;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.user.domain.User;

public interface UserDao {
	
	long add(User user);
	
	User get(String userId) throws UserException;
	
	User get(String userId, String password);
	
	List<User> getAll();
	
	void delete(String id);
	
	void update(User user);

	void deleteAll();
	
}
