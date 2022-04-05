package com.autonomus.jntu.exceptions;

public class LibraryException extends RuntimeException {

	private String errCode;
	private String errMsg;

	public LibraryException() {

	}


	public LibraryException(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}



}
