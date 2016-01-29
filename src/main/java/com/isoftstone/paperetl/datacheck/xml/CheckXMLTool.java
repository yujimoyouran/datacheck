package com.isoftstone.paperetl.datacheck.xml;

import java.io.IOException;
import java.io.InputStream;

import com.isoftstone.paperetl.datacheck.domain.DataCheckDomain;
import com.isoftstone.paperetl.datacheck.util.XMLUtils;

public class CheckXMLTool {

	private static XMLUtils xmlUtil = new XMLUtils(new Class[]{DataCheckDomain.class});
	
	public static XMLUtils getXMLTool(){
		return xmlUtil;
	}
	
	public static DataCheckDomain getDataCheckDomain(InputStream input) throws IOException{
		return xmlUtil.fromXml(input, DataCheckDomain.class);
	}
	
	public static String toXML(DataCheckDomain dataCheckDomain)
	{
		return xmlUtil.toXml(dataCheckDomain);
	}
	
	public static DataCheckDomain getDataCheckDomain(String xml)
	{
		return xmlUtil.fromXml(xml, DataCheckDomain.class);
	}
}
