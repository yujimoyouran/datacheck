package com.isoftstone.paperetl.datacheck.domain;

public class CheckResult {
	private boolean flag;
	private String message;
	
	public CheckResult() {
	}
	
	public CheckResult(boolean flag, String message) {
		this.flag = flag;
		this.message = message;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	

}
