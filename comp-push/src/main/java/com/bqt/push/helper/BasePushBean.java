package com.bqt.push.helper;

public class BasePushBean {
	public static final int TYPE_STRING = 1;
	public static final int TYPE_JSONOBJ = 2;
	
	public String msg;
	public int type;
	
	public BasePushBean(String msg, int type) {
		this.msg = msg;
		this.type = type;
	}
}
