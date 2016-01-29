package com.isoftstone.paperetl.datacheck.domain;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("checks")
public class ChecksDomain {
	@XStreamImplicit
	private List<CheckfunctionDomain> checkfunction;

	public List<CheckfunctionDomain> getCheckfunction() {
		return checkfunction;
	}

	public void setCheckfunction(List<CheckfunctionDomain> checkfunction) {
		this.checkfunction = checkfunction;
	}
	
	
}
