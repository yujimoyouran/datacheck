package com.isoftstone.paperetl.datacheck.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 解析properties文件
 * 
 * @author doublejia
 *
 */
public class PropertiesResolve {

	/**
	 * 读取文件中所有属性
	 * 
	 * @param url
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<HashMap<String, Object>> readMapAllKey(String url) {
		InputStream ins = this.getClass().getResourceAsStream(url);
		BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
		Properties p = new Properties();
		try {
			p.load(bf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<HashMap<String, Object>> properties = new ArrayList<HashMap<String, Object>>();
		Enumeration en = p.propertyNames();
		while (en.hasMoreElements()) {
			HashMap<String, Object> promap = new HashMap<String, Object>();
			String key = (String) en.nextElement();
			String Property = p.getProperty(key);
			promap.put("key", key);
			promap.put("value", Property);
			properties.add(promap);
		}
		Collections.reverse(properties);
		return properties;
	}

	/**
	 * 通过KEY获取值
	 * 
	 * @param url
	 * @param key
	 * @return
	 * @throws FileNotFoundException 
	 */
	public String readMapByKey(String url, String key) throws FileNotFoundException {
//		InputStream ins = this.getClass().getResourceAsStream(url);
		InputStream ins = new FileInputStream(url);
		BufferedReader bf = new BufferedReader(new InputStreamReader(ins));
		Properties p = new Properties();
		try {
			p.load(bf);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String Property = p.getProperty(key);
		return Property;
	}


}
