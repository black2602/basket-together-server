package com.blackangel.baskettogether.common.exception;

import org.springframework.dao.DataAccessException;

public class UserException extends DataAccessException {

	public UserException(String msg) {
		super(msg);
	}

}
