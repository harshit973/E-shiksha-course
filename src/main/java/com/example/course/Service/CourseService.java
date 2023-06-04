package com.example.course.Service;

import com.example.course.Entity.Course;
import com.example.course.Entity.Page.CoursePage;
import com.example.course.Entity.SearchCriteria.CourseSearchCriteria;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;
import org.springframework.data.domain.Page;

public interface CourseService {
    IdDataDto createCourse(CourseDataDto course);

    CourseDataDto getCourse(Long id);
    Page<Course> filterCourse(final CoursePage coursePage, final CourseSearchCriteria courseSearchCriteria);
    IdDataDto updateCourse(Long id, CourseDataDto course);

    IdDataDto deleteCourse(Long id);
}
