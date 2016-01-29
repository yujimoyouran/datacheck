package com.isoftstone.paperetl.datacheck.domain;

import java.util.ArrayList;
import java.util.List;

public class ExecuteResult {

	private boolean flag;
	private String message;
	private List<String> domains = new ArrayList<String>();
	
	public ExecuteResult() {
	}
	
	public ExecuteResult(boolean flag, String message)
	{
		this.flag = flag;
		this.message = message;
	}
	public ExecuteResult(boolean flag, List<String> domains)
	{
		this.flag = flag;
		this.domains = domains;
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

	public List<String> getDomains() {
		return domains;
	}

	public void setDomains(List<String> domains) {
		this.domains = domains;
	}
	
	
	
}
