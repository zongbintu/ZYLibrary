package com.zongyou.library.util.base64;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class DeResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5933312778025220282L;

	
	public int code;
	public String msg;
	public String token;
	public String key;
	public JSONObject data;
	public DeResult(EnResult info){
		code = info.code;
		msg = info.msg;
		token = info.token;
		key = info.key;
		try {
			data = new JSONObject(info.data);
		} catch (JSONException e) {
		}
				
	}
}
