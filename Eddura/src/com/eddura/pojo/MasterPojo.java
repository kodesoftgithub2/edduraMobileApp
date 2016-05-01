package com.eddura.pojo;

import java.io.Serializable;

public class MasterPojo implements Serializable{
	/**
	 * 
	 */
	String response;
    String msg;
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}
