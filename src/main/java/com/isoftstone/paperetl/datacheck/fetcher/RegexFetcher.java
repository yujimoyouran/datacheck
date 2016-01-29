package com.isoftstone.paperetl.datacheck.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.isoftstone.paperetl.datacheck.domain.ExecuteResult;
import com.isoftstone.paperetl.datacheck.domain.TaskParamDomain;

public class RegexFetcher implements DataFetcher {

	private Pattern pattern;

	@Override
	public ExecuteResult fetcher(String data, TaskParamDomain taskParamDomain) {
		ExecuteResult defaultResult = new ExecuteResult(false,
				"Parameter does not match");
		ExecuteResult result = null;
		if(taskParamDomain == null)
			result = defaultResult;
		if (pattern == null)
			pattern = Pattern.compile(taskParamDomain.getInputPattern());
		Matcher matcher = pattern.matcher(data);
		List<String> list = null;
		if (matcher.matches()) {
			list = new ArrayList<String>();
			for (int i = 1; i <= matcher.groupCount(); i++) {
				list.add(matcher.group(i));
			}
			result = new ExecuteResult(true, list);
		}else{
			result = defaultResult;
		}
		if(taskParamDomain.getColumnNum() > 0)
		{
			if(taskParamDomain.getColumnNum()!=result.getDomains().size()){
				result = defaultResult;
			}
		}
		
		return result;
		
	}


}
