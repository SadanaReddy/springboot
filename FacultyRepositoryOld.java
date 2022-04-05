
package com.autonomus.jntu.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.autonomus.jntu.model.Faculty;


@Repository
public interface FacultyRepositoryOld {

	public List<Faculty> retriveAllDetails();
}
