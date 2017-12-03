package com.blackangel.baskettogether.user.dao;

import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.UserLoginSession;

public interface UserLoginSessionDao {

	long add(UserLoginSession userLoginSession);
	
	UserLoginSession get(long sessionId);

	int count();
	
	void deleteAll();
}
