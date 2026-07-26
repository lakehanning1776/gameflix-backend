package com.example.demo.service.impl;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {return studentRepository.findAll();
    }

    @Override
    public void saveStudent(Student student) {this.studentRepository.save(student);}

    @Override
    public Student getStudentById(long sid) {
        Optional<Student> optional = studentRepository.findById(sid);
        Student student = null;
        if (optional.isPresent()) {student = optional.get();}
        else {throw new RuntimeException("Student not found for id :: " + sid);}
        return student;
    }

    @Override
    public void deleteStudentById(long sid) {
        this.studentRepository.deleteById(sid);
    }

    @Override
    public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort;
        if (sortDirection.equalsIgnoreCase("asc")) {sort = Sort.by(sortField).ascending();
        }
        else
        {
            sort = Sort.by(sortField).descending();
        }
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return studentRepository.findAll(pageable);
    }
}