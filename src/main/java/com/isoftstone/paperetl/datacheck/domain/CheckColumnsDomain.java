package com.isoftstone.paperetl.datacheck.domain;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("checkColumns")
public class CheckColumnsDomain {
	@XStreamImplicit
	private List<AuditcolumnDomain> auditcolumn;
	
	public List<AuditcolumnDomain> getAuditcolumn() {
		return auditcolumn;
	}

	public void setAuditcolumn(List<AuditcolumnDomain> auditcolumn) {
		this.auditcolumn = auditcolumn;
	}
	
	

}
