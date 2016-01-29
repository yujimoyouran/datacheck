package com.isoftstone.paperetl.datacheck.domain;

import org.apache.hadoop.conf.Configuration;

public class ConfigDomain {
	private Configuration conf = null;
	private boolean flag = false;
	private String message;
	
	public Configuration getConf() {
		return conf;
	}
	public ConfigDomain setConf(Configuration conf) {
		this.conf = conf;
		return this;
	}
	public boolean isFlag() {
		return flag;
	}
	public ConfigDomain setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ConfigDomain setMessage(String message) {
		this.message = message;
		return this;
	}
	
}
