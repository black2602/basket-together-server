package com.blackangel.baskettogether.user.service;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import com.blackangel.baskettogether.common.exception.UserException;
import com.blackangel.baskettogether.common.util.EncryptUtil;
import com.blackangel.baskettogether.user.dao.UserDao;
import com.blackangel.baskettogether.user.dao.UserLoginSessionDao;
import com.blackangel.baskettogether.user.domain.User;
import com.blackangel.baskettogether.user.domain.UserLoginSession;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	
	@Autowired
	private UserLoginSessionDao userLoginSessionDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public long signUp(User user) {
		String salt = EncryptUtil.newRandomKey();
		String encryptedPw = EncryptUtil.pbkdf2(user.getPassword(), salt, 1000);
		
		user.setSalt(salt);
		user.setPassword(encryptedPw);
		
		return userDao.add(user);
	}

	@Override
	public User login(User user) throws UserException {
		User getUser = userDao.get(user.getUserId());
		
		String encryptedPw = EncryptUtil.pbkdf2(user.getPassword(), getUser.getSalt(), 1000);
		System.out.println("try login salt = " + getUser.getSalt() + ", enc pw = " + encryptedPw);
		
		if(!getUser.getPassword().equals(encryptedPw)) {
			throw new UserException("아이디 또는 비밀번호를 확인해주세요.");
		}
		
		getUser.setPassword(null);		// 로그인 성공 판별되면 보안을 위해 패스워드를 메모리에서 지운다.
		getUser.setLastLoginAt(new Timestamp(System.currentTimeMillis()));
		getUser.setDeviceId(user.getDeviceId());
		getUser.setDeviceType(user.getDeviceType());
		
		userDao.update(getUser);
		
		return getUser;		// 로그인 성공 
	}

	@Override
	public UserLoginSession issueSession() {
		UserLoginSession session = new UserLoginSession();
		
		long sessionId = userLoginSessionDao.add(session);
		return userLoginSessionDao.get(sessionId);
	}
	
	
}
