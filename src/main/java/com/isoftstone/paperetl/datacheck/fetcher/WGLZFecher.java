package com.isoftstone.paperetl.datacheck.fetcher;

import java.util.Random;

import com.isoftstone.paperetl.datacheck.domain.ExecuteResult;
import com.isoftstone.paperetl.datacheck.domain.TaskParamDomain;

public class WGLZFecher extends RegexFetcher {

	private static Random rand = new Random();

	@Override
	public ExecuteResult fetcher(String data, TaskParamDomain taskParamDomain) {
		ExecuteResult result = super.fetcher(data, taskParamDomain);
		if (result.isFlag())
			result.getDomains().add(rand.nextInt(1000) + "");
		return result;
	}

	public static void main(String[] args) {
		System.out.println(rand.nextInt(1000));
	}

}
