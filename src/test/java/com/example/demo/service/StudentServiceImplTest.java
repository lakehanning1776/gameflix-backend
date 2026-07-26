package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class StudentServiceImplTest {

    @Autowired
    private StudentRepository repository;

    @Test
    void getAllStudents() {
        List<Student> items = repository.findAll();

        assertEquals(1, items.size());
    }

    @Test
    void testFindOne() {
        Student student = repository.findById(3L).orElseThrow();

        assertEquals("Blake", student.getStudName());
    }
}