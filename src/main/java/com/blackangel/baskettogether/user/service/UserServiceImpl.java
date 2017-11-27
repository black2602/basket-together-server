package com.blackangel.baskettogether.user.service;

import java.sql.Date;
import java.sql.Timestamp;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.user.dao.UserDao;
import com.blackangel.baskettogether.user.domain.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public long signUp(User user) {
		return userDao.add(user);
	}

	@Override
	public User login(User user) throws UserException {
		User getUser = userDao.get(user.getUserId());
		
		if(getUser == null) {
			throw new UserException("존재하지 않는 사용자입니다.");
		}
		else if(!user.getPassword().equals(getUser.getPassword())) {
			throw new UserException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
		getUser.setPassword(null);		// 로그인 성공 판별되면 보안을 위해 패스워드를 메모리에서 지운다.
		getUser.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
		getUser.setDeviceId(user.getDeviceId());
		getUser.setDeviceType(user.getDeviceType());
		
		userDao.update(getUser);
		
		return getUser;		// 로그인 성공 
	}
	
	
}
