package com.isoftstone.paperetl.datacheck.fetcher;

import com.isoftstone.paperetl.datacheck.domain.ExecuteResult;
import com.isoftstone.paperetl.datacheck.domain.TaskParamDomain;

public interface DataFetcher {

	public ExecuteResult fetcher(String data, TaskParamDomain taskParamDomain);
}
