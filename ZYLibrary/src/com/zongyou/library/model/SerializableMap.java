package com.zongyou.library.model;

import java.io.Serializable;
import java.util.Map;

/**
 * 序列化map
 * 
 * @author Altas
 * @email Altas.Tutu@gmail.com
 * @time 2015-1-16 上午11:31:15
 */
public class SerializableMap<T>  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -713841397022492644L;
	private Map<String, T> map;
	public SerializableMap(Map<String,T> map){
		this.map = map;
	}

	public Map<String, T> getMap() {
		return map;
	}

	public void setMap(Map<String, T> map) {
		this.map = map;
	}
}
