package com.isoftstone.paperetl.datacheck.mr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.log4j.Logger;

import com.isoftstone.paperetl.datacheck.checker.Checker;
import com.isoftstone.paperetl.datacheck.domain.AuditcolumnDomain;
import com.isoftstone.paperetl.datacheck.domain.CheckfunctionDomain;
import com.isoftstone.paperetl.datacheck.domain.DataCheckDomain;
import com.isoftstone.paperetl.datacheck.hdfsoper.FileOperator;
import com.isoftstone.paperetl.datacheck.hdfsoper.basic.FileOperatorByHdfs;


public class DataCheckAuxiliary {
	
	private Configuration conf;
	
	private static Logger log = Logger.getLogger(DataCheckAuxiliary.class);
	
	FileOperator operator = null;
	
	public DataCheckAuxiliary(Configuration conf){
		this.conf = conf;
	}
	
	/**
	 * 加载MR需要jar包
	 * 
	 * @param job
	 * @param dependJarPath
	 * @throws Exception
	 */
	public void addJarToClassPath(Job job, String dependJarPath) throws Exception
	{
		if(operator == null){
			operator = new FileOperatorByHdfs(conf);
			log.info("HdfsOparetor:" + operator);
		}
			
		List<String> depList = operator.lsPath(dependJarPath);
		for(String str : depList)
		{
			job.addFileToClassPath(new Path(str));
		}
	}
	

	/**
	 * 获取重新组合的数据
	 * 
	 * @param list
	 * @param outputPattern
	 * @return
	 */
	public String generateNewData(List<String> list, String outputPattern)
	{
		StringBuilder builder = new StringBuilder();
		int count = list.size();
		for (int i = 0; i < count; i++) {
			builder.append(list.get(i));
			if(i != count-1)
				builder.append(outputPattern);
		}
		return builder.toString();
		
	}
	
	/**
	 * 获得column检查对象
	 * 
	 * @param dataCheckDomain
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public Map<String, Checker> getColumnCheckTypeObject(DataCheckDomain dataCheckDomain) throws InstantiationException, IllegalAccessException, ClassNotFoundException
	{
		Map<String, Checker> map  = new HashMap<String, Checker>();
		Integer columnIndex = null;
		
		List<AuditcolumnDomain> list = dataCheckDomain.getAuditInfo().getCheckColumns().getAuditcolumn();
		if(CollectionUtils.isNotEmpty(list))
		{
			for(AuditcolumnDomain domain : list)
			{
				columnIndex = domain.getColumnIndex();
				List<CheckfunctionDomain> funList = domain.getChecks().getCheckfunction();
				for(CheckfunctionDomain domain2 : funList)
				{
					String name = domain2.getCheckerClassName();
					Checker checker = (Checker) Class.forName(name).newInstance();
					map.put(columnIndex + "_" + name, checker);
				}
			}
		}
		
		return map;
	}
}
