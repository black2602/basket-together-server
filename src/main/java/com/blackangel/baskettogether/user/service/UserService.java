package com.blackangel.baskettogether.user.service;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.UserLoginSession;

public interface UserService {
	
	UserLoginSession issueSession();
	
	long signUp(User user);
	
	User login(User user) throws UserException;

}
