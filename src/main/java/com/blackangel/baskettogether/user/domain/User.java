package com.blackangel.baskettogether.user.domain;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class User {
	
	public enum UserRegType {
		TYPE_APP(0),
	    TYPE_FACEBOOK(1);
		
		int regType;

		private UserRegType(int regType) {
			this.regType = regType;
		}
		
		public static UserRegType valueOf(int regType) {
			switch(regType) {
			case 0:
				return TYPE_APP;
			case 1:
				return TYPE_FACEBOOK;
			}
			
			return null;
		}

		public int getValue() {
			return regType;
		}
	}
	
	private long _id;
	private String userId;
	private String password;
	private String salt;
	private String nickname;
	private String country;
	private String phone;
	private int regType;
	private String snsId;
	private String photoUrl;
	private Date regDts;
	private Timestamp lastLoginAt;
	private String deviceId;
	private String deviceType;
	
	public User(String id, String password, String nickname, String snsId, UserRegType regType) {
		this(id, password);
		this.nickname = nickname;
		this.snsId = snsId;
		this.regType = regType.getValue();
	}

	public User(String id, String password) {
		this.userId = id;
		this.password = password;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getRegType() {
		return regType;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public String getSnsId() {
		return snsId;
	}

	public void setSnsId(String snsId) {
		this.snsId = snsId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Date getRegDts() {
		return regDts;
	}

	public void setRegDts(Date regDts) {
		this.regDts = regDts;
	}
	
	public Timestamp getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Timestamp lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	
	
	
	
	
}
