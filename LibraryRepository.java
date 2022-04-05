package com.autonomus.jntu.repository;

import java.util.List;

import com.autonomus.jntu.model.Library;

public interface LibraryRepository {

	public List<Library> retriveAllBooksDetails();
	List<Library> findAll();
	Library findById(int id);
	int deleteAll();
	void save(List<Library> libraryList);
	int deleteById(int id);
	List<Library> update(List<Library> libraryList);
	int updateBookNamebasedOnID(int id, String BookName);
	List<Library> findByMatchingBookName(String bookName);
	List<Library> findByPartialBookDetails(String matchString);
	int updateSpecificFieldById(int id, String fieldName, String updateField);

}



