package com.autonomus.jntu.controllers;

import java.io.IOException;
import java.util.EmptyStackException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.autonomus.jntu.exceptions.FacultyException;
import com.autonomus.jntu.model.Faculty;
import com.autonomus.jntu.model.Library;
import com.autonomus.jntu.service.LibraryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
public class LibraryController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryController.class);

	@Autowired
	private LibraryService libraryService;

	@ApiOperation(value = "This API gets all the Library Booka details")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully returns all the library books details"),
			@ApiResponse(code = 410, message = "Client error"),
			@ApiResponse(code = 417, message = "EXPECTATION_FAILED")
	})
	@GetMapping("/getAllBooksDetails")
	public ResponseEntity<?> getAllBooksDetails() {
		LOGGER.info("Request came into getAllBooksDetails");
		List<Library> libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryList = libraryService.getAllBookDetails();
			LOGGER.info("Display all books Details");
			return new ResponseEntity<> (libraryList, headers,HttpStatus.OK);
		}catch(EmptyStackException e) {
			e.printStackTrace();
			return new ResponseEntity<> ("Empty LibraryList", headers,HttpStatus.GONE);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<> (e.getMessage(), headers,HttpStatus.EXPECTATION_FAILED);

		}
	}

	@ApiOperation(value = "This API gets given Book Id Details")
	@ApiResponses(value = {
			@ApiResponse(code = 200 , message = "OK "),
			@ApiResponse(code = 208, message = "Already Reported ")
	})
	@GetMapping("/getLibraryBooksDetailsById/{id}")
	public ResponseEntity<?> getLibraryBooksDetailsById(
			@ApiParam(example = "12345", name = "The id of the object", value = "Library", required = true) @PathVariable int id
			//@ApiParam(name =  "Book Id", type = "String", value = "Book1", example = "12345", required = true) @PathVariable int id			
			) {
		LOGGER.info("The Request came into getLibraryBooksDetailsById", id);
		Library libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryList = libraryService.retriveBookDetailsId(id);
			LOGGER.info("couldn't find book for {} id", libraryList);
			return new ResponseEntity<>(libraryList, headers, HttpStatus.OK);

		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(libraryList, headers,HttpStatus.ALREADY_REPORTED);

		}
	}

	@ApiOperation(value = "This API gets deteteAllLibraryBooksDetails")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = " Successful"),
			@ApiResponse(code = 404, message = "NOT_FOUND"),
			@ApiResponse(code = 507, message = "SERVER_ERROR")
	})
	//@RequestMapping(value = "/deleteAllLibraryBooksDetails", method = RequestMethod.DELETE)
	//@DeleteMapping("/deleteAllLibraryBooksDetails")
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllLibraryBooksDetails")

	public ResponseEntity<?> deleteAllLibraryBooksDetails() {
		LOGGER.info("The Request came into deleteAllLibraryBooksDetails");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryService.deleteAllBooksDetails();
			return new ResponseEntity<>("clear", headers, HttpStatus.ACCEPTED);
		}
		catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("The Faculty list is not empty");
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.INSUFFICIENT_STORAGE);
		}

	}



	@ApiOperation(value = "This API gets create new Library details")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully returned faculty details for given id "),
			@ApiResponse(code = 507, message = "INSUFFICIENT_STORAGE"),
			@ApiResponse(code = 208, message = "ALREADY_REPORTED ")
	})
	//@PostMapping("/postLibraryBooksDetails")
	@RequestMapping(method=RequestMethod.POST, value ="/postLibraryBooksDetails") 
	public ResponseEntity<?>  postLibraryBooksDetails(
			//@ApiParam(example = "12345", name = "The id of the object", value = "Library", required = true)
			@Validated @RequestBody List<Library> libraryList) {
		LOGGER.info("The Request came into postLibraryBooksDetails");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryService.createbookDetails(libraryList);
			LOGGER.info("The new LibraryBook details are {}",libraryList);
			return new ResponseEntity<>(libraryList, headers,HttpStatus.CREATED);

		}catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			LOGGER.debug("The memory is full", headers, e);
			return new ResponseEntity<>(" The memory is full",headers,HttpStatus.INSUFFICIENT_STORAGE);
		}catch(Exception e) {
			e.printStackTrace();
			LOGGER.info("id", "library", headers, e);
			return new ResponseEntity<>(libraryList,headers,HttpStatus.ALREADY_REPORTED);
		}
	}

	@ApiOperation(value = "This API gets delete the specfic id in the library list")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success "),
			@ApiResponse(code = 400, message = "Bad Request")
	})
	@DeleteMapping("/deleteLibraryBookDetailsById/{id}")
	public ResponseEntity<?> deleteLibraryBookDetailsById(@ApiParam(name =  "id", type = "String", value = "library", example = "34567", required = true)@PathVariable int id) {
		LOGGER.info("The request came into deleteLibraryBookDetailsById ");
		List<Library> libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryList = libraryService.deleteBookDetailsById(id);
			LOGGER.info("The given id is:{}  ",id);
			return new ResponseEntity<>(libraryList, headers, HttpStatus.OK);

		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(libraryList, headers, HttpStatus.BAD_REQUEST);

		}
	}


	@ApiOperation(value = "This API gets update or create the faculty details")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = " Successful"),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@PutMapping("/putLibraryBookDetails")
	public ResponseEntity<?> putUpdateFacultyDetail(@RequestBody List<Library> libraryList) {
		//@ApiParam(name =  "id", type = "String", value = "faculty", example = "9876", required = true)
		//@ApiParam(name =  "String", type = "String", value = "faculty", example = "name", required = true)
		LOGGER.info("Request came into putUpdateFacultyDetail ");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryService.updateLibraryBooksDetails(libraryList);
			LOGGER.info("the given update faculty is{}: ", libraryList);
			return new ResponseEntity<>(libraryList, headers,HttpStatus.ACCEPTED);

		}catch(Exception e) {
			return new ResponseEntity<>(libraryList, headers,HttpStatus.EXPECTATION_FAILED);
		}
	}

	@ApiOperation(value = "This API gets find Library by given BookName")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "delete the specific fieldName"),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@GetMapping("/getBookNameOnly")
	public ResponseEntity<?> findByBookName(
			@ApiParam(name =  "bookName", type = "String", value = "library", example = "name", required = true)
	@RequestParam String bookName) {
		LOGGER.info("Request came to findByBookName method");
		//List<Library> libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			List<Library> libraryList = libraryService.findByBookName(bookName);
			LOGGER.info("the findByBookName is:{}", libraryList);
			return new ResponseEntity<>(libraryList, headers, HttpStatus.CREATED);

		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.EXPECTATION_FAILED);

		}
	}
	
	@ApiOperation(value = "This API gets delete Faculty by given FirstName")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "delete the specific fieldName"),
			@ApiResponse(code = 507, message = "Insufficient Storage "),
			@ApiResponse(code = 417, message = "Expectation Failed ")
	})
	@RequestMapping(method=RequestMethod.DELETE, value ="/deleteLibraryByPartialBookAuthor")
	public ResponseEntity<?> deleteMatchBookAuthor(@ApiParam(name =  "bookAuthor", type = "String", value = "Library", example = "name", required = true)
	@RequestParam String matchstring) {
		LOGGER.info("Request came to deleteMatchBookAuthor method");
		List<Library> libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		try {
			libraryList = libraryService.findMatchLibraryBookDetails(matchstring);
			LOGGER.info("Library size is {}", libraryList.size());
			return new ResponseEntity<>(libraryList, headers, HttpStatus.CREATED);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), headers, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@ApiOperation(value = "This API gets update the specfic fields in libraryyBook list")
	@ApiResponses(value = {
			@ApiResponse(code = 226, message = "updated the specific fieldNmae "),
			@ApiResponse(code = 417, message = "Expectation Failed "),
			@ApiResponse(code = 103, message = "INFORMATIONAL ")
	})
	@RequestMapping(method=RequestMethod.PATCH, value ="/patchLibraryDetails")  //We can Update the Specific record in PATCH
	public ResponseEntity<?> patchLibraryDetail(
			@ApiParam(name =  "id", type = "String", value = "library", example = "98765", required = true) @RequestParam int id,
			@ApiParam(name =  "fieldName", type = "String", value = "FirstName", example = "ABC", required = true) @RequestParam String fieldName,
			@ApiParam(name =  "updateField", type = "String", value = "FirstName", example = "XYZ", required = true)  @RequestParam String updateField)
					throws Exception {
		LOGGER.info("Request came into patchLibraryDetail");
		List<Library> libraryList = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			libraryList =	libraryService.patchFacultyDetail(id, fieldName, updateField);
			LOGGER.info("The updated Library is {}",libraryList);
			return new ResponseEntity<>(libraryList, headers, HttpStatus.OK );

		}catch(FacultyException e) {
			LOGGER.error("Error occurred while updating the updateField patchLibraryDetail method");
			return new ResponseEntity<>(e.getErrCode().concat(" ").concat(e.getErrMsg()), headers, HttpStatus.CHECKPOINT);
		} 
		catch(IOException e) {
			LOGGER.info("The FiledName is not Updated");

			return new ResponseEntity<>(libraryList, headers, HttpStatus.IM_USED );
		}
	}

	
}
