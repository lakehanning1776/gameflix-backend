package com.example.demo.service;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
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
class CourseServiceImplTest {

    @Autowired
    private CourseRepository repository;

    @Test
    void getAllCourses() {
        List<Course> items = repository.findAll();

        assertEquals(3, items.size());
    }

    @Test
    void testFindOne() {
        Course course = repository.findById(2L).orElseThrow();

        assertEquals("IST", course.getCourseDept());
        assertEquals("420", course.getCourseNum());
    }
}