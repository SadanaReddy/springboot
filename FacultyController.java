package com.autonomus.jntu.controllers;

import java.io.IOException;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonomus.jntu.exceptions.ArrayIsEmpty;
import com.autonomus.jntu.exceptions.FacultyException;
import com.autonomus.jntu.exceptions.InvalidNewFacultyException;
import com.autonomus.jntu.exceptions.LibraryException;
import com.autonomus.jntu.model.Faculty;
import com.autonomus.jntu.service.FacultyService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FacultyController {

	private static final Logger LOGGER=LoggerFactory.getLogger(FacultyController.class);

	@Autowired
	private FacultyService facultyService;

	@ApiOperation(value = "This API gets all the faculty details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully returns all the faculty details"),
			@ApiResponse(code = 204, message = "Content Not found"),
			@ApiResponse(code = 400, message = "Unauthorized User")
	})
	@GetMapping("/getAllFacultyDetails")
	public ResponseEntity<?> getAllFacultyDetails() {
		LOGGER.info("Request came into getAllFacultyDetails");
		List<Faculty> facultyList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyList = facultyService.getAllFacultyDetails();
			LOGGER.info("Display all faculty details");
			return new ResponseEntity<>(facultyList, headers, HttpStatus.OK);

		}catch(ArrayIsEmpty e) {
			e.printStackTrace();
			LOGGER.info("FacultyList is EMPTY");
			return new ResponseEntity<>(facultyList, headers, HttpStatus.NO_CONTENT); //I'm not passing the arguments

		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.error(" The method is not working");
			return new ResponseEntity<>(facultyList, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "This API gets all the faculty details for a given faculty Id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully returned faculty details for given id "),
			@ApiResponse(code = 204, message = "Content Not found"),
			@ApiResponse(code = 410, message = "Client Error ")
	})
	@GetMapping("/getFacultyDetails/{id}")
	public ResponseEntity<?> getFacultyDetailsById(@ApiParam(name =  "id", type = "String", value = "faculty idggg", example = "98765", required = true)
	@PathVariable int id) {
		LOGGER.trace("Request came into getFacultyDetailsById" , id);
		Faculty facultyList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			 facultyService.getFacultyDetailsById(id);
			LOGGER.info(" {} facultyList", facultyList);
			return new ResponseEntity<>(facultyList, headers, HttpStatus.OK);
		}catch(NumberFormatException e) {
			e.printStackTrace();
			LOGGER.debug("given id is wrong");
			return new ResponseEntity<>("The given id is wrong", headers, HttpStatus.NOT_FOUND);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.GONE);
		}
	}

	@ApiOperation(value = "This API gets create new faculty details")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully returned faculty details for given id "),
			@ApiResponse(code = 507, message = "INSUFFICIENT_STORAGE"),
			@ApiResponse(code = 424, message = "FAILED_DEPENDENCY "),
			@ApiResponse(code = 208, message = "ALREADY_REPORTED ")
	})
	@RequestMapping(method=RequestMethod.POST, value ="/postFacultyDetails") // We can create the record
	public ResponseEntity<?> createFacultyDetail(
			//@ApiParam(name =  "String", type = "String", value = "faculty", example = "name", required = true)
			@Validated @RequestBody List<Faculty> facultyList) {
		LOGGER.info("Request came into createFacultyDetail");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyService.insertFacultyDetail(facultyList);
			return new ResponseEntity<>(facultyList,headers,HttpStatus.CREATED);
		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			LOGGER.debug("The memory is full", headers, e);
			return new ResponseEntity<>(" The memory is full",headers,HttpStatus.INSUFFICIENT_STORAGE);
		}catch(InvalidNewFacultyException e) {
			e.printStackTrace();
			LOGGER.error("e.getErrId()", "e.getErrMsg()", "Expected Msg");
			return new ResponseEntity<>(e.getErrId().concat(" ").concat(e.getErrMsg().concat(e.getExpectedMsg())),headers,HttpStatus.FAILED_DEPENDENCY);
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("id", "faculty", headers, e);
			return new ResponseEntity<>(facultyList,headers,HttpStatus.ALREADY_REPORTED);
		}
	}

	@ApiOperation(value = "This API gets update or create the faculty details")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "We can modify the old faculty details or create new one"),
			@ApiResponse(code = 411, message = "Length Required"),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@RequestMapping(method=RequestMethod.PUT, value ="/putFacultyDetails")  //We can update or create the record in PUT
	public ResponseEntity<?> updateFacultyDetail(
			@ApiParam(name =  "String", type = "String", value = "faculty", example = "name", required = true)
			@Validated @RequestBody List<Faculty> facultyList ) {
		LOGGER.info("Request came into updateFacultyDetail ");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyService.updateFacultyDetail(facultyList);
			LOGGER.info("The Faculty is updated or inserted");
			return new ResponseEntity<>(facultyList, headers,HttpStatus.ACCEPTED);
		}catch(ArrayStoreException e) {
			LOGGER.debug("The faculty is not updated", e);
			return new ResponseEntity<>("The faculty is not updated", headers,HttpStatus.LENGTH_REQUIRED);
		}catch(Exception e) {
			return new ResponseEntity<>(facultyList, headers,HttpStatus.EXPECTATION_FAILED);
		}
	}

	@ApiOperation(value = "This API gets update the specfic fields in faculty list")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "updated the specific fieldNmae "),
			@ApiResponse(code = 417, message = "Expectation Failed "),
			@ApiResponse(code = 103, message = "INFORMATIONAL ")
	})
	@RequestMapping(method=RequestMethod.PATCH, value ="/patchFacultyDetails")  //We can Update the Specific record in PATCH
	public ResponseEntity<?> patchFacultyDetail(
			@ApiParam(name =  "id", type = "String", value = "faculty", example = "98765", required = true) @RequestParam int id,
			@ApiParam(name =  "fieldName", type = "String", value = "FirstName", example = "ABC", required = true) @RequestParam String fieldName,
			@ApiParam(name =  "updateField", type = "String", value = "FirstName", example = "XYZ", required = true)  @RequestParam String updateField)
					throws Exception {
		LOGGER.info("Request came into patchFacultyDetail");
		List<Faculty> facultyList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			facultyList =	facultyService.patchFacultyDetail(id, fieldName, updateField);
			LOGGER.info("The updated Faculty is {}",facultyList);
			return new ResponseEntity<>(facultyList, headers, HttpStatus.OK );

		}catch(FacultyException e) {
			LOGGER.error("Error occurred while updating the updateField patchFacultyDetail method");
			return new ResponseEntity<>(e.getErrCode().concat(" ").concat(e.getErrMsg()), headers, HttpStatus.CHECKPOINT);
		} 
		catch(IOException e) {
			LOGGER.info("The FiledName is not Updated");

			return new ResponseEntity<>(facultyList, headers, HttpStatus.IM_USED );
		}
	}

	@ApiOperation(value = "This API gets delete the specfic id in the faculty list")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Accepted "),
			@ApiResponse(code = 424, message = "Failed Dependency  "),
			@ApiResponse(code = 507, message = "Insufficient Storage "),
			@ApiResponse(code = 204, message = "No Content "),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@RequestMapping(method=RequestMethod.DELETE, value ="/deleteFacultyDetail/{id}")
	public ResponseEntity<?> deleteFacultyDetailById(@ApiParam(name =  "id", type = "String", value = "faculty", example = "34567", required = true)
	@PathVariable int id) {
		LOGGER.info("Request came into deleteFacultyDetailById");
		int facultyList = 0;
		HttpHeaders headers =  new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyList =  facultyService.deleteFacultyDetailById(id);
			LOGGER.info("Faculty id is {}", facultyList);
			return new ResponseEntity<>("facultyList", headers, HttpStatus.ACCEPTED);
		}catch(AccountNotFoundException e) {
			LOGGER.debug("Debug the Given deleteFacultyDetailById");
			return new ResponseEntity<>("The Faculty Record List Is Empty", headers, HttpStatus.FAILED_DEPENDENCY);
		}catch(LibraryException e) {   //Customized Exception
			LOGGER.error("Error occurred while processing deleteFacultyDetailById method");
			return new ResponseEntity<>(e.getErrCode().concat(" ").concat(e.getErrMsg()), headers, HttpStatus.INSUFFICIENT_STORAGE);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(facultyList, headers, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "This API gets delete all faculty details")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = " Delete all Faculty Details"),
			@ApiResponse(code = 502, message = "Bad Request"),
	})
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllFacultyDetails")
	public ResponseEntity<?> deleteAllFacultyDetails() {
		LOGGER.info("Request came to deleteAllFacultyDetails");
		HttpHeaders  headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyService.deleteAllFacultyDetails();
			return new ResponseEntity<>("clear",headers,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			LOGGER.info("The Faculty List is not empty");
			e.printStackTrace();
			return new ResponseEntity<>("Not Empty",headers,HttpStatus.BAD_GATEWAY);
		}
	}

	@ApiOperation(value = "This API gets delete Faculty by given FirstName")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "delete the specific fieldName"),
			@ApiResponse(code = 507, message = "Insufficient Storage "),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@RequestMapping(method=RequestMethod.DELETE, value ="/deleteFacultyByPartialFirstName")
	public ResponseEntity<?> deleteMatchFaculty(@ApiParam(name =  "firstName", type = "String", value = "faculty", example = "name", required = true)
	@RequestParam String matchstring) {
		LOGGER.info("Request came to deleteMatchFaculty method");
		List<Faculty> facultyList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("AppName", "University app");
		try {
			facultyService.deleteMatchFaculty(matchstring);
			LOGGER.info("Faculty size is {}",facultyList );
			return new ResponseEntity<>(facultyList, headers, HttpStatus.CREATED);

		} catch(FacultyException e) {   //Customized Exception
			LOGGER.error("Error occurred while processing deleteMatchFaculty method");
			return new ResponseEntity<>(e.getErrCode().concat(" ").concat(e.getErrMsg()), headers, HttpStatus.INSUFFICIENT_STORAGE);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@ApiOperation(value = "This API gets find Faculty by given FirstName")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "delete the specific fieldName"),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@GetMapping("/getFirstNameOnly")
	public ResponseEntity<?> findByFirstName(@ApiParam(name =  "firstName", type = "String", value = "faculty", example = "name", required = true)
	@RequestParam String firstName) {
		LOGGER.info("Request came to findByFirstName method");
		List<Faculty> facultyList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			facultyList = facultyService.findByFirstNameFaculty(firstName);
			return new ResponseEntity<>(facultyList, headers, HttpStatus.CREATED);

		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.EXPECTATION_FAILED);

		}

	}



}
