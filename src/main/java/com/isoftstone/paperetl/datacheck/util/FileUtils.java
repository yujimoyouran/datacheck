package com.isoftstone.paperetl.datacheck.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class FileUtils {
	
	public static String readFile2String(InputStream inStream) throws IOException
    {
        return IOUtils.toString(inStream);
    }
}
