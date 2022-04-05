package com.autonomus.jntu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Faculty {
	
	@Id
	@Column("ID")
	private int id;
	
	@Column("FIRST_NAME")
	private String firstName;
	
	@Column("LAST_NAME")
	private String lastName;
	
	@Column("DOB")
	private String dob;
	
	@Column("TEACHING")
	private boolean teaching;
	
	@Column("MOBILE_NO")
	private long mobileNo;

	
	public Faculty() {
		// TODO Auto-generated constructor stub
	}
	public Faculty(int id2, String firstName2, String lastName2, String dob2, long mobileNo2, boolean b) {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public boolean isTeaching() {
		return teaching;
	}
	public void setTeaching(boolean teaching) {
		this.teaching = teaching;
	}
	public long getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	public static void add(Faculty faculty) {

	}




}
