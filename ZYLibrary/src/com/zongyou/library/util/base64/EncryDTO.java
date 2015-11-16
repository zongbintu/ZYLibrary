package com.zongyou.library.util.base64;

import java.io.Serializable;

/**
 * 加密后的实体
 * 
 * @author Altas
 * @email Altas.Tu@qq.com
 * @date 2014-3-18 上午10:32:29
 */
public class EncryDTO implements Serializable {

	public EncryDTO() {
	}

	public EncryDTO(String k, String c) {
		key = k;
		cipherText = c;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7874451320383187153L;
	private String id = "A";
	private String key;
	private String cipherText;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCipherText() {
		return cipherText;
	}

	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
}