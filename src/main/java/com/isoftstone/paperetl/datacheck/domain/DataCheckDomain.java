package com.isoftstone.paperetl.datacheck.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("datacheck")
public class DataCheckDomain {
	@XStreamAlias("taskId")
	private String taskId;
	@XStreamAlias("auditInfo")
	private AuditInfoDomain auditInfo;
	@XStreamAlias("dependJarPath")
	private String dependJarPath;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public AuditInfoDomain getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfoDomain auditInfo) {
		this.auditInfo = auditInfo;
	}
	public String getDependJarPath() {
		return dependJarPath;
	}
	public void setDependJarPath(String dependJarPath) {
		this.dependJarPath = dependJarPath;
	}
}
