
package com.autonomus.jntu.repository;

import java.util.List;

import com.autonomus.jntu.model.Faculty;

public interface FacultyRepository {


	List<Faculty> retriveAllDetails();
	Faculty retriveAllFacultyDetailsById(int facultyId);
	List<Faculty> findAll();
	int deleteById(int id);
	int deleteAll();
	void save(List<Faculty> facultyList);
	List<Faculty> update(List<Faculty> facultyList);
	List<Faculty> findByFirstNameContaining(String firstName);
	List<Faculty> deleteByPartialFirstName(String matchString);
	int updateSpecificFieldById(int id, String fieldName, String updateField);


}
