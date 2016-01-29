package com.isoftstone.paperetl.datacheck.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.isoftstone.paperetl.datacheck.domain.ExecuteResult;
import com.isoftstone.paperetl.datacheck.domain.TaskParamDomain;

public class SplitFetcher implements DataFetcher{

	@Override
	public ExecuteResult fetcher(String data, TaskParamDomain taskParamDomain) {
		
		StringTokenizer tokenizer = new StringTokenizer(data,taskParamDomain.getInputPattern());
		List<String> list = null;
		if(tokenizer.countTokens() == taskParamDomain.getColumnNum())
		{
			list = new ArrayList<String>();
			while(tokenizer.hasMoreElements())
			{
				list.add(tokenizer.nextToken());
			}
			return new ExecuteResult(true, list);
		}
		return new ExecuteResult(false, "Parameter does not match");
	}
}
