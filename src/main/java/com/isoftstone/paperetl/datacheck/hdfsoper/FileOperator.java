package com.isoftstone.paperetl.datacheck.hdfsoper;

import java.io.InputStream;
import java.util.List;

public interface FileOperator {

	/**
	 * 
	 * @param filePath 文件路径
	 * @param str   追加的字符串
	 * @return 返回操作否成功
	 */
	boolean appendToFile(String filePath,String str)throws Exception;
	
	/**
	 * 
	 * @param filePath 文件路径
	 * @param str   追加的字符串
	 * @param isCreateFile  文件不存是否创建空文件在添加内容
	 * @return 返回操作否成功
	 */
	boolean appendToFile(String filePath,String str,boolean isCreate)throws Exception;
	
	/**
	 * hdfs文件追加内容
	 * 
	 * @param filePath 文件路径
	 * @param in   追加数据流
	 * @return 返回操作否成功
	 */
	boolean appendToFile(String filePath,InputStream in) throws Exception;
	
	
	/**
	 * 
	 * @param filePath 
	 * @param in
	 * @param isCreateFile  文件不存是否创建空文件在添加内容
	 * @return
	 * @throws Exception
	 */
	boolean appendToFile(String filePath,InputStream in,boolean isCreateFile) throws Exception;
	
	/**
	 * 查看hdfs文件内容
	 * 
	 * @param filePath  文件路径
	 * @return   返回hdfs文件字符串
	 */
	String catFile(String filePath) throws Exception;
	
	/**
	 * 读取文件
	 * @param filePath 文件路径
	 * @param limit 行数
	 * @return
	 * @throws Exception
	 */
	List<Object> catFile(String filePath,long limit) throws Exception; 
	
	/**
	 * * 查看hdfs文件夹中文件内容（可返回多个文件  第一行为文件名称） 
	 * 
	 * @param fileDirectoryPath  文件夹路径
	 * @param fileName 文件名
	 * @param wildCard  是否使用通配
	 * @param cascade  是否级联查看
	 * @return
	 * @throws Exception
	 */
	String catFile(String fileDirectoryPath,String fileName,boolean wildCard,boolean cascade) throws Exception;
	
	
	/**
	 * 将本地文件copy到hdfs文件
	 * 
	 * @param sourceFilePath 源文件路径
	 * @param targetFilePath  目标文件路径
	 * @return 是否上传成功
	 */
	boolean copyFromLocal(String sourceFilePath,String targetFilePath) throws Exception;
	
	
	/**
	 * 将本地文件copy到hdfs文件
	 * 
	 * @param sourceFilePath 源文件路径
	 * @param targetFilePath  目标文件路径
	 * @param isCover  是否覆盖hdfs文件
	 * @return 是否上传成功
	 */
	boolean copyFromLocal(String sourceFilePath,String targetFilePath,boolean isCover) throws Exception;
	
	/**
	 * 创建hdfs目录
	 * 
	 * @param directory   要创建的hdfs目录路径
	 * @return 返回是否执行成功
	 */
	boolean mkdirDirectory(String directory)throws Exception;
	
	
	/**
	 * 创建hdfs目录
	 * 
	 * @param directory   要创建的目录路径
	 * @param isCascade  级联创建
	 * @return 返回是否执行成功
	 */
	boolean mkdirDirectory(String directory,boolean isCascade)throws Exception;
	
	/**
	 * 判断目录是否存在
	 * 
	 * @param directory   要创建的目录路径
	 * @return 返回是否执行成功
	 */
	boolean exists(String directory)throws Exception;
	
	/**
	 * 移动文件和文件夹 
	 *  
	 * @param oldPath  文件夹或者文件
	 * @param newPath  文件夹或者文件
	 * @return 返回是否执行成功
	 */
	boolean mvPath(String oldPath,String newPath)throws Exception;
	
	/**
	 * 移动文件和文件夹 
	 *  
	 * @param oldPath  文件夹或者文件
	 * @param newPath  文件夹或者文件
	 * @param isCover  是否覆盖
	 * @return 返回是否执行成功
	 */
	boolean mvPath(String oldPath,String newPath,boolean isCover)throws Exception;
	
	/**
	 * 文件上传
	 * 
	 * @param sourceFilePath 本地文件名
	 * @param targetFilePath hdfs文件路径
	 * @return
	 */
	boolean putFile(String sourceFilePath,String targetFilePath)throws Exception;
	
	/**
	 * 文件上传
	 * 
	 * @param sourceFilePath 本地文件名
	 * @param targetFilePath hdfs文件路径
	 * @param isCover hdfs文件存在是否覆盖
	 * @param isCreatePath hdfs文件路径不存在是否创建
	 * @return
	 */
	boolean putFile(String sourceFilePath,String targetFilePath,boolean isCover)throws Exception;
	
	
	
	/**
	 * s删除文件
	 * 
	 * @param filePath 文件名称
	 * @return
	 */
	
	boolean rm(String filePath) throws Exception ;
	
	/**
	 *  删除文件夹
	 * @param directoryPath
	 * @return
	 */
	boolean rmdir(String directoryPath)throws Exception;
	
	
	/**
	 *  删除文件夹
	 * @param directoryPath
	 * @param isCascade 级联删除子文件夹
	 * @return
	 */
	boolean rmdir(String directoryPath,boolean isCascade)throws Exception;
	
	/**
	 * 查看文件和问价夹
	 * @param filePath  
	 * @return
	 * @throws Exception
	 */
	List<String> lsPath(String path) throws Exception;
	
	/**
	 * 级联查看文件和文件夹
	 * @param filePath
	 * @param isCascade 是否级联查看
	 * @return
	 * @throws Exception
	 */
	List<String> lsPath(String path,boolean isCascade) throws Exception;
	
	/**
	 * 获取文件到当前路径
	 * @param path
	 * @throws Exception
	 */
	void getFile(String path) throws Exception;
	
	/**
	 * 获取文件到当前路径
	 * @param path
	 * @throws Exception
	 */
	void getFile(String sourcePath,String targetPath) throws Exception;
	
	void write(String path, String content,String encode) throws Exception;
	
}
