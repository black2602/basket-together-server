package com.blackangel.baskettogether.user.domain;

import java.sql.Timestamp;

import com.blackangel.baskettogether.common.util.EncryptUtil;

public class UserLoginSession {
	private long sessionId;
	private String pbk;
	private String pvk;
	private long uid;
	private Timestamp regDts;
	
	
	public UserLoginSession(long sessionId, String pbk, String pvk, long uid, Timestamp regDts) {
		super();
		this.sessionId = sessionId;
		this.pbk = pbk;
		this.pvk = pvk;
		this.uid = uid;
		this.regDts = regDts;
	}

	public UserLoginSession() {
		this.pbk = EncryptUtil.newRandomKey();
		this.pvk = EncryptUtil.newRandomKey();
		this.regDts = new Timestamp(System.currentTimeMillis());
	}
	
	public long getSessionId() {
		return sessionId;
	}
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	public String getPbk() {
		return pbk;
	}
	public void setPbk(String pbk) {
		this.pbk = pbk;
	}
	public String getPvk() {
		return pvk;
	}
	public void setPvk(String pvk) {
		this.pvk = pvk;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public Timestamp getRegDts() {
		return regDts;
	}
	public void setRegDts(Timestamp regDts) {
		this.regDts = regDts;
	}
	
	
}
