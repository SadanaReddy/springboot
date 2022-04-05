package com.autonomus.jntu.exceptions;

public class InvalidNewFacultyException extends Exception {
	private String errId;
	private String errMsg;
	private String expectedMsg;


	public InvalidNewFacultyException() {
		super();
	}
	public InvalidNewFacultyException(String errId, String errMsg, String expectedMsg) {
		super();
		this.errId = errId;
		this.errMsg = errMsg;
		this.expectedMsg = expectedMsg;
	}
	public String getErrId() {
		return errId;
	}
	public void setErrId(String errId) {
		this.errId = errId;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getExpectedMsg() {
		return expectedMsg;
	}
	public void setExpectedMsg(String expectedMsg) {
		this.expectedMsg = expectedMsg;
	}



}



