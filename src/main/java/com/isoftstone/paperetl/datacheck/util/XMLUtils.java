package com.isoftstone.paperetl.datacheck.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

public class XMLUtils {

    private XStream stream;

    public XMLUtils(Class[] classes){
        stream = new XStream();
        stream.autodetectAnnotations(true);
        stream.processAnnotations(classes);
    }

    public String toXml(Object obj){
       return stream.toXML(obj);
    }

    public <T> T fromXml(String xml,Class<T> clazz){
        return (T) stream.fromXML(xml);
    }
    
    public <T> T fromXml(InputStream input,Class<T> clazz) throws IOException{
    	return fromXml(IOUtils.toString(input, "utf-8"),clazz);
    }
}
