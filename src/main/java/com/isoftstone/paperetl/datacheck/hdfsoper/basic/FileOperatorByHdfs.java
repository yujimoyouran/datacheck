package com.isoftstone.paperetl.datacheck.hdfsoper.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.isoftstone.paperetl.datacheck.hdfsoper.FileOperator;

public class FileOperatorByHdfs implements FileOperator {

	private Configuration conf = null;

	private FileSystem fs = null;

	public FileOperatorByHdfs() {
		try {
			this.conf = new Configuration();
			this.fs = FileSystem.get(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public FileOperatorByHdfs(Configuration conf) {
		this.conf = conf;
		try {
			this.fs = FileSystem.get(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@Override
	public boolean appendToFile(String filePath, String str) throws Exception {
		BufferedWriter bw = null;
		try {
			FSDataOutputStream fileOutputStream = fs.append(new Path(filePath));
			bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			bw.append(str + System.lineSeparator());
		} finally {
			if (bw != null) {
				bw.close();
			}
		}
		return true;
	}

	@Override
	public boolean appendToFile(String filePath, String str, boolean isCreate) throws Exception {
		if (isCreate) {
			fs.create(new Path(filePath));
			appendToFile(filePath, str);
		} else {
			appendToFile(filePath, str);
		}
		return true;
	}

	@Override
	public boolean appendToFile(String filePath, InputStream in) throws Exception {
		BufferedWriter bw = null;
		BufferedReader br = null;
		try {
			FSDataOutputStream fileOutputStream = fs.append(new Path(filePath));
			bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
			br = new BufferedReader(new InputStreamReader(in));
			String tempString = null;
			while ((tempString = br.readLine()) != null) {
				bw.append(tempString + System.lineSeparator());
			}
		} finally {
			if (bw != null) {
				bw.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return true;
	}

	@Override
	public boolean appendToFile(String filePath, InputStream in, boolean isCreateFile) throws Exception {
		if (isCreateFile) {
			fs.create(new Path(filePath));
			appendToFile(filePath, in);
		} else {
			appendToFile(filePath, in);
		}
		return true;
	}

	@Override
	public String catFile(String filePath) throws Exception {
		BufferedReader br = null;
		StringBuilder sb = null;
		try {
			FSDataInputStream fileOutputStream = fs.open(new Path(filePath));
			sb = new StringBuilder();
			br = new BufferedReader(new InputStreamReader(fileOutputStream));
			String tempString = null;
			while ((tempString = br.readLine()) != null) {
				sb.append(tempString + System.lineSeparator());
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
		return sb.toString();
	}

	@Override
	public List<Object> catFile(String filePath, long limit) throws Exception {
		FileStatus[] status = fs.listStatus(new Path(filePath));
		BufferedReader br = null;
		List<Object> list = new ArrayList<Object>();
		try {
			ok: 
			for (FileStatus file : status) {
				if (file.isDirectory())
					continue;
				FSDataInputStream fileOutputStream = fs.open(file.getPath());
				br = new BufferedReader(new InputStreamReader(fileOutputStream));
				String tempString = null;

				while ((tempString = br.readLine()) != null) {
					list.add(tempString);
					/*
					 * sb.append(tempString + System.lineSeparator()); li++;
					 */
					if (list.size() == limit)
						break ok;
				}
			}
		}finally {
			if (br != null) {
				br.close();
			}
		}

		return list;
	}

	@Override
	public String catFile(String fileDirectoryPath, String fileName, boolean wildCard, boolean cascade)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean copyFromLocal(String sourceFilePath, String targetFilePath) throws Exception {
		boolean result = false;
		if (StringUtils.isNotEmpty(sourceFilePath) && StringUtils.isNotEmpty(targetFilePath)) {
			Path srcPath = new Path(sourceFilePath);
			Path tarPath = new Path(targetFilePath);
			fs.copyFromLocalFile(srcPath, tarPath);
			result = true;
		}
		return result;
	}

	@Override
	public boolean copyFromLocal(String sourceFilePath, String targetFilePath, boolean isCover) throws Exception {
		boolean result = false;
		if (StringUtils.isNotEmpty(sourceFilePath) && StringUtils.isNotEmpty(targetFilePath)) {
			Path srcPath = new Path(sourceFilePath);
			Path tarPath = new Path(targetFilePath);
			fs.copyFromLocalFile(false, isCover, srcPath, tarPath);
			result = true;
		}
		return result;
	}

	// zhanglt 2015/11/27 start
	@Override
	public boolean mkdirDirectory(String directory) throws Exception {
		// TODO Auto-generated method stub
		fs.mkdirs(new Path(directory));
		return true;
	}

	@Override
	public boolean mkdirDirectory(String directory, boolean isCascade) throws Exception {
		// TODO Auto-generated method stub
		if (isCascade) {
			String[] direc = directory.split("/");
			StringBuilder sb = new StringBuilder();
			for (int i = 1; i < direc.length; i++) {
				sb.append("/" + direc[i]);
				Path path2 = new Path(sb.toString());
				if (fs.exists(path2) && !fs.isDirectory(path2)) {
					mkdirDirectory(sb.toString());
				} else
					mkdirDirectory(sb.toString());
			}
		} else
			mkdirDirectory(directory);
		return true;
	}

	@Override
	public boolean mvPath(String oldPath, String newPath) throws Exception {
		// TODO Auto-generated method stub
		fs.rename(new Path(oldPath), new Path(newPath));
		return true;
	}

	@Override
	public boolean mvPath(String oldPath, String newPath, boolean isCover) throws Exception {
		// TODO Auto-generated method stub
		if (isCover) {
			fs.deleteOnExit(new Path(newPath));
			mvPath(oldPath, newPath);
		} else {
			mvPath(oldPath, newPath);
		}
		return true;
	}

	// zhanglt 2015/11/27 end

	@Override
	public boolean putFile(String sourceFilePath, String targetFilePath) throws Exception {
		return this.copyFromLocal(sourceFilePath, targetFilePath);
	}

	@Override
	public boolean putFile(String sourceFilePath, String targetFilePath, boolean isCover) throws Exception {
		return this.copyFromLocal(sourceFilePath, targetFilePath, isCover);
	}

	@Override
	public boolean rm(String filePath) throws Exception {
		if (fs.isFile(new Path(filePath))) {
			return fs.delete(new Path(filePath), false);
		}
		return false;
	}

	@Override
	public boolean rmdir(String directoryPath) throws Exception {
		if (!fs.isFile(new Path(directoryPath))) {
			if (fs.listStatus(new Path(directoryPath)).length > 0) {
				return false;
			}
			return fs.delete(new Path(directoryPath), false);
		}
		return fs.delete(new Path(directoryPath), false);
	}

	@Override
	public boolean rmdir(String directoryPath, boolean isCascade) throws Exception {
		if (isCascade) {
			return fs.delete(new Path(directoryPath), isCascade);
		} else {
			return rmdir(directoryPath);
		}
	}

	@Override
	public List<String> lsPath(String path) throws Exception {
		FileStatus[] stats = fs.listStatus(new Path(path));
		List<String> str = new ArrayList<String>();
		int size = stats.length;
		for (int i = 0; i < size; i++) {
			str.add(stats[i].getPath().toString());

		}
		return str;
	}

	@Override
	public List<String> lsPath(String path, boolean isCascade) throws Exception {
		FileStatus[] s = fs.listStatus(new Path(path));
		List<String> datamap = new ArrayList<String>();
		for (int i = 0; i < s.length; i++) {
			FileStatus fileStatus = s[i];
			datamap.add(s[i].getPath().toString());
			if (!fileStatus.isFile()) {
				List<String> cl = lsPath(fileStatus.getPath().toString(), isCascade);
				datamap.addAll(cl);
			}
		}

		return datamap;
	}

	@Override
	public void getFile(String path) throws Exception {
	}

	@Override
	public void getFile(String sourcePath, String targetPath) throws Exception {
		BufferedReader br = null;
		PrintWriter output = null;
		Path path = new Path(sourcePath);
		FileStatus status[] = fs.listStatus(path);
		File file = new File(targetPath);
		output = new PrintWriter(new FileOutputStream(file, true));
		String line = null;
		for (int i = 0; i < status.length; i++) {
			System.out.println(status[i].getPath().toString());
			br = new BufferedReader(new InputStreamReader(fs.open(new Path(status[i].getPath().toString()))));
			while ((line = br.readLine()) != null) {
				output.println(line);
			}
			output.flush();
		}
		br.close();
		output.close();
	}

	@Override
	public boolean exists(String directory) throws Exception {
		return fs.exists(new Path(directory));
		
	}

	@Override
	public void write(String path, String content,String encode) throws Exception {
        FSDataOutputStream out = fs.create(new Path(path));   //创建文件
        //两个方法都用于文件写入，好像一般多使用后者
        out.write(content.getBytes()); 
        out.close();
		
	}
}
