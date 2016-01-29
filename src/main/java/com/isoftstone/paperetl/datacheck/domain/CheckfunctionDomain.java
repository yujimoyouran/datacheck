package com.isoftstone.paperetl.datacheck.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("checkfunction")
public class CheckfunctionDomain {
	@XStreamAlias("funcFormula")
	private String funcFormula;
	@XStreamAlias("checkType")
	private String checkType;
	@XStreamAlias("checkerClassName")
	private String checkerClassName;
	
	public String getFuncFormula() {
		return funcFormula;
	}
	public void setFuncFormula(String funcFormula) {
		this.funcFormula = funcFormula;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckerClassName() {
		return checkerClassName;
	}
	public void setCheckerClassName(String checkerClassName) {
		this.checkerClassName = checkerClassName;
	}
	
	
}
