package com.example.course.Service;

import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;

public interface CourseService {
    IdDataDto createCourse(CourseDataDto course);
    CourseDataDto getCourse(Long id);
    IdDataDto updateCourse(Long id,CourseDataDto course);
    IdDataDto deleteCourse(Long id);
}
