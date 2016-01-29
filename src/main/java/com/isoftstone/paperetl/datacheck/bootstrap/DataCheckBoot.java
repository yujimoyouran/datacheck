package com.isoftstone.paperetl.datacheck.bootstrap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.log4j.Logger;

import com.isoftstone.paperetl.datacheck.domain.ConfigDomain;
import com.isoftstone.paperetl.datacheck.domain.DataCheckDomain;
import com.isoftstone.paperetl.datacheck.domain.Result;
import com.isoftstone.paperetl.datacheck.mr.DataCheckCore;
import com.isoftstone.paperetl.datacheck.util.PropertiesResolve;
import com.isoftstone.paperetl.datacheck.xml.CheckXMLTool;

public class DataCheckBoot {
	
	private static Logger log = Logger.getLogger(DataCheckBoot.class);

	private static String PRINCIPAL = "username.client.kerberos.principal";
	private static String KEYTAB = "username.client.keytab.file";
	
	private static PropertiesResolve resolve = new PropertiesResolve();

	public static Result startCheck(Configuration conf, InputStream input,
			String dataInput, String dataOutput) throws Exception {
		DataCheckDomain dataCheckDomain = CheckXMLTool
				.getDataCheckDomain(input);
		return startCheck(conf, dataCheckDomain, dataInput, dataOutput);
	}

	public static Result startCheck(Configuration conf,
			DataCheckDomain checkDomain, String dataInput, String dataOutput)
			throws Exception {
		if (conf == null)
			conf = new Configuration();

		if (StringUtils.isBlank(dataInput))
			return new Result().setFlag(false).setMessage(
					"Please check the input path");
		// 为了演示
		if (StringUtils.isBlank(dataOutput))
			dataOutput = "/user/paper/warehouse/output/";
		String suffix = System.currentTimeMillis() + "/";
		if (dataOutput.endsWith("/"))
			dataOutput = dataOutput + suffix;
		else
			dataOutput = dataOutput + "/" + suffix;

		if (StringUtils.isBlank(checkDomain.getAuditInfo().getTaskParam()
				.getOutputSplitPattern()))
			checkDomain.getAuditInfo().getTaskParam()
					.setOutputSplitPattern("\001");

		return DataCheckCore.startCheckData(conf, checkDomain, dataInput,
				dataOutput);
	}

	public static Result startCheck(Configuration conf, String configFile,
			String dataInput, String dataOutput) throws Exception {
		InputStream input = new FileInputStream(configFile);
		return startCheck(conf, input, dataInput, dataOutput);
	}

	
	
	
	public static Result startCheck( String dataInput, String dataOutput, String fiDir, String checkDir, String varEnvFile,
			String authDir) throws Exception {
		if (StringUtils.isBlank(fiDir))
			return new Result().setFlag(false).setMessage(
					"Please check the hadoop configuration file path");
		if (StringUtils.isBlank(checkDir))
			return new Result().setFlag(false).setMessage(
					"Please check the dataCheck.xml file path");
		
		ConfigDomain domain = null;
		//获取Configuration对象
		if (StringUtils.isBlank(authDir))
			domain = init(fiDir);
		else
			domain = init(fiDir, authDir, varEnvFile);
		
		//获取datacheck路径
		log.info("datacheck.properties:"+varEnvFile);
		if(!new File(varEnvFile).isFile())
			return new Result().setFlag(false).setMessage("datacheck.properties  does not exist");
		String dataCheckName = resolve.readMapByKey(varEnvFile, "DATACHECK_NAME");
		String dataCheckDir = checkDir+File.separator+dataCheckName;
		log.info("dataCheck = " + dataCheckDir);
		if(domain.isFlag())
			return startCheck(domain.getConf(), dataCheckDir, dataInput, dataOutput);
		else
			return new Result().setFlag(false).setMessage(domain.getMessage());
	}

	private static ConfigDomain init(String fiStr) throws FileNotFoundException {
		Configuration conf = new Configuration();
		ConfigDomain domain = new ConfigDomain();
		
		List<String> fiList = getFileDir(fiStr);
		if(CollectionUtils.isEmpty(fiList))
			return domain.setFlag(false).setMessage("fiDir must is directory");
		// 加载hadoop配置文件
		for (String str : fiList) {
			if(!new File(str).isFile())
				return domain.setFlag(false).setMessage(str+" does not a file");
			conf.addResource(new FileInputStream(str));
		}
		return domain.setFlag(true).setConf(conf).setMessage("get conf success");
	}

	/**
	 * init get a FileSystem instance
	 * 
	 * @param ketTab
	 * @param fiStr
	 * @throws FileNotFoundException 
	 * 
	 * @throws IOException
	 */
	private static ConfigDomain init(String fiStr, String authDir, String varEnvFile) throws FileNotFoundException{
		ConfigDomain domain = init(fiStr);
		log.info("auth init");
		if(!new File(authDir).isDirectory())
			return domain.setFlag(false).setMessage("authDir must is directory");
		// security mode
		if ("kerberos".equalsIgnoreCase(domain.getConf().get("hadoop.security.authentication"))) {
			String principals = resolve.readMapByKey(varEnvFile, "PRINCIPAL");
			domain.getConf().set(PRINCIPAL, principals);
			// keytab file
			String HDFS_KEYTABL = resolve.readMapByKey(varEnvFile, "HDFS_KEYTABL");
			String keytabDir = authDir + File.separator + HDFS_KEYTABL;
			if(!new File(keytabDir).isFile())
				return domain.setFlag(false).setMessage(keytabDir + "is not exist");
			domain.getConf().set(KEYTAB, keytabDir);

			// kerberos path
			String KRB5_CONF = resolve.readMapByKey(varEnvFile, "KRB5_CONF");
			String krbfilepath = authDir + File.separator + KRB5_CONF;
			if(!new File(krbfilepath).isFile())
				return domain.setFlag(false).setMessage(krbfilepath + "is not exist");
			System.setProperty("java.security.krb5.conf", krbfilepath);
		}
		return domain;
	}

	private static List<String> getFileDir(String dir) {
		File file = new File(dir);
		if (!file.isDirectory())
			return null;
		String[] array = file.list();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < array.length; i++) {
			list.add(dir + File.separator + array[i]);
		}
		return list;
	}


	public static void main(String[] args) throws Exception {
		Result result = new Result().setFlag(false).setMessage("Please check the parameter Settings");
		log.info("prizhiwenjian:"+args[4]);
		if(args.length==5)
			result = DataCheckBoot.startCheck(args[0], args[1], args[2], args[3], args[4], "");
		else if(args.length==6)
			result = DataCheckBoot.startCheck(args[0], args[1], args[2], args[3], args[4], args[5]);
		if(result.isFlag())
			log.info(result.toString());
		else
			log.info(result.getMessage());
	}
}
