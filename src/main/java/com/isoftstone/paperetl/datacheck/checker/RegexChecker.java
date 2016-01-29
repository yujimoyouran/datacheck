package com.isoftstone.paperetl.datacheck.checker;

import java.io.IOException;
import java.io.InputStream;

import com.isoftstone.paperetl.datacheck.domain.CheckResult;
import com.isoftstone.paperetl.datacheck.domain.DataCheckDomain;
import com.isoftstone.paperetl.datacheck.xml.CheckXMLTool;


public class RegexChecker implements Checker{

	@Override
	public CheckResult check(String data, String regexMatch) {
		if(data.matches(regexMatch))
			return new CheckResult(true, data);
		return new CheckResult(false, "ColumuParameter does not match");
	}
	
	public static void main(String[] args) throws IOException {
		RegexChecker check = new RegexChecker();
		InputStream is=RegexChecker.class.getResourceAsStream("/datacheck.xml");
		DataCheckDomain dataCheckDomain  = CheckXMLTool.getDataCheckDomain(is);
		CheckResult ex = check.check("20", dataCheckDomain.getAuditInfo().getCheckColumns().getAuditcolumn().get(1).getChecks().getCheckfunction().get(0).getFuncFormula());
		System.out.println(ex.getMessage()+ ex.isFlag());

	}
}
