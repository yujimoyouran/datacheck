package com.isoftstone.paperetl.datacheck.checker;

import com.isoftstone.paperetl.datacheck.domain.CheckResult;



public interface Checker {

	public CheckResult check(String data, String regexMatch);
}
