package com.tuxt.learn.exception;

/**
 * 自定义异常类
 * @author lipan
 *
 */
public class BusiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 260306311701422290L;
	private String exceptionId;
	/**
	 * 系统级异常，不需要返回给用户显示
	 */
	public static final String SYS_ERR="9999";
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
	public BusiException(String message) {
		super(message);
	}
	public BusiException(Throwable cause) {
		super(cause);
		this.setStackTrace(cause.getStackTrace());
	}
	public BusiException(String message,Throwable cause) {
		super(message,cause);
		this.setStackTrace(cause.getStackTrace());
	}
	public BusiException(String exceptionId,String message) {
		super(message);
		this.exceptionId=exceptionId;
	}
	public BusiException(String exceptionId,String message,Throwable cause) {
		super(message,cause);
		this.exceptionId=exceptionId;
		this.setStackTrace(cause.getStackTrace());
	}
}
