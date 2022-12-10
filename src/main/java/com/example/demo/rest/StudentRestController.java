package com.example.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.service.IProductService;

@RestController
@RequestMapping("/students")
public class StudentRestController {

	@Autowired
	private IProductService service;

	@PostMapping()
	public ResponseEntity<String> saveStudent(
			@RequestBody Student student){
		Integer id =  service.saveStudent(student);
		return new ResponseEntity<String>("Student saved"+ id, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOneStudent(
			@PathVariable Integer id)
	{ 
		ResponseEntity<?> resp= null;
		try {
			Student s = service.getOneStudent(id);
			resp = new ResponseEntity<Student>(s, HttpStatus.OK);

		} catch (Exception e) {
			resp = new ResponseEntity<String>("Unable to fetch student", 
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;

	}

}
