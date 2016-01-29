package com.isoftstone.paperetl.datacheck.domain;

public class Result {
	private long total;
	private long success;
	private long error;
	private String successPath;
	private String errorPath;
	private boolean flag;
	private String message;
	
	public Result(long total, long success, long error, String successPath, String errorPath) {
		this.total = total;
		this.error = error;
		this.errorPath = errorPath;
		this.success = success;
		this.successPath = successPath;
	}
	
	public Result() {
	}
	
	public long getTotal() {
		return total;
	}
	public Result setTotal(long total) {
		this.total = total;
		return this;
	}
	public long getSuccess() {
		return success;
	}
	public Result setSuccess(long success) {
		this.success = success;
		return this;
	}
	public long getError() {
		return error;
	}
	public Result setError(long error) {
		this.error = error;
		return this;
	}
	public String getSuccessPath() {
		return successPath;
	}
	public Result setSuccessPath(String successPath) {
		this.successPath = successPath;
		return this;
	}
	public String getErrorPath() {
		return errorPath;
	}
	public Result setErrorPath(String errorPath) {
		this.errorPath = errorPath;
		return this;
	}

	public boolean isFlag() {
		return flag;
	}

	public Result setFlag(boolean flag) {
		this.flag = flag;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public String toString()
	{
		return "success="+this.success + ",successPath="+this.successPath + ",error="+this.error + ",errorPath="+this.errorPath+
				",total="+this.total;
	}
	
	
}
