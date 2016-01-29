package com;

import java.io.InputStream;

import org.apache.hadoop.conf.Configuration;

import com.isoftstone.paperetl.datacheck.bootstrap.DataCheckBoot;
import com.isoftstone.paperetl.datacheck.domain.Result;

public class CheckTest {
	
	public  void check123() throws Exception
	{
		Configuration conf = new Configuration();
		
		InputStream is=this.getClass().getResourceAsStream("/datacheck.xml");
		String dataInput = "/lk";
		String dataOutput = "";
		Result result = DataCheckBoot.startCheck(conf, is, dataInput, dataOutput);
		
		System.out.println(result.toString());
	}

	public static void main(String[] args) throws Exception {
		CheckTest checkTest =  new CheckTest();
		checkTest.check123();
	}
}
