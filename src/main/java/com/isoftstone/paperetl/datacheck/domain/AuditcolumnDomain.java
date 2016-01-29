package com.isoftstone.paperetl.datacheck.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("auditcolumn")
public class AuditcolumnDomain {
	@XStreamAlias("columnIndex")
	private Integer columnIndex;
	@XStreamAlias("columnName")
	private String columnName;
	
	@XStreamAlias("checks")
	private ChecksDomain checks;

	public ChecksDomain getChecks() {
		return checks;
	}

	public void setChecks(ChecksDomain checks) {
		this.checks = checks;
	}
	
	public Integer getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(Integer columnIndex) {
		this.columnIndex = columnIndex;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	

}
