package com.zongyou.library.util.base64;

import java.io.Serializable;


public class EnResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5933312778025220282L;

	
	public int code;
	public String msg;
	public String token;
	public String key;
	public String data;
	public int userId;
}
