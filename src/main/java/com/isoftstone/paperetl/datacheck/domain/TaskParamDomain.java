package com.isoftstone.paperetl.datacheck.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("taskParam")
public class TaskParamDomain {
	@XStreamAlias("checkType")
	private String checkType;
	@XStreamAlias("inputPattern")
	private String inputPattern;
	@XStreamAlias("outputSplitPattern")
	private String outputSplitPattern;
	@XStreamAlias("columnNum")
	private Integer columnNum = 0;
	
	
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getInputPattern() {
		return inputPattern;
	}
	public void setInputPattern(String inputPattern) {
		this.inputPattern = inputPattern;
	}
	public String getOutputSplitPattern() {
		return outputSplitPattern;
	}
	public void setOutputSplitPattern(String outputSplitPattern) {
		this.outputSplitPattern = outputSplitPattern;
	}
	public Integer getColumnNum() {
		return columnNum;
	}
	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}
	
	
	
	
}
