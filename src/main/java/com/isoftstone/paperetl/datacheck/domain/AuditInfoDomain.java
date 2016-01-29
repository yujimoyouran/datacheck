package com.isoftstone.paperetl.datacheck.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("auditInfo")
public class AuditInfoDomain {
	@XStreamAlias("auditInfo")
	private TaskParamDomain taskParam;
	@XStreamAlias("checkColumns")
	private CheckColumnsDomain checkColumns;
	
	
	public TaskParamDomain getTaskParam() {
		return taskParam;
	}
	public void setTaskParam(TaskParamDomain taskParam) {
		this.taskParam = taskParam;
	}
	public CheckColumnsDomain getCheckColumns() {
		return checkColumns;
	}
	public void setCheckColumns(CheckColumnsDomain checkColumns) {
		this.checkColumns = checkColumns;
	}
	
	
	
	
	
}
