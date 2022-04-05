package com.autonomus.jntu.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Library {

	@Id
	@Column("BOOK_ID")
	private int bookId;
	@Column("BOOK_NAME")
	private String bookName;
	@Column("BOOK_AUTHOR")
	private String bookAuthor;
	@Column("DEPT_BOOK")
	private String deptBook;
	@Column("AVALIABLE")
	private boolean avaliable;
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public String getDeptBook() {
		return deptBook;
	}
	public void setDeptBook(String deptBook) {
		this.deptBook = deptBook;
	}
	public boolean isAvaliable() {
		return avaliable;
	}
	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}
	

}
