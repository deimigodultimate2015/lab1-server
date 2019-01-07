package com.example.demo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/student")
public class StudentApi {
	
	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/create")
	public Student insertStudent(@RequestBody Student student) {
		
		Student _student = studentRepository.save(new Student(student.getName(), student.getMark(), student.getMajor()));
		return _student;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") long id) {
		
		studentRepository.deleteById(id);
		
		return "Student was deleted";
	}
	
	@GetMapping("/get/{id}")
	public Student getStudentById(@PathVariable("id") long id) {
		return studentRepository.findById(id).get();
	}
	
	@GetMapping("/get") 
	public List<Student> getAllStudent() {
		List<Student> list = new ArrayList<Student>();
		studentRepository.findAll().forEach(list::add);
		return list;
	}
	
	@PutMapping("/edit/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
		Optional<Student> opStudent = studentRepository.findById(id);
		
		if(opStudent.isPresent()) {
			Student _student = opStudent.get();
			_student.setName(student.getName());
			_student.setMark(student.getMark());
			_student.setMajor(student.getMajor());
			return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
