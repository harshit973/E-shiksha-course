package com.example.course.Service.impl;

import com.example.course.Constants.ErrorMessages;
import com.example.course.Entity.Course;
import com.example.course.Entity.Page.CoursePage;
import com.example.course.Entity.SearchCriteria.CourseSearchCriteria;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Repository.CourseCriteriaRepository;
import com.example.course.Repository.CourseRepository;
import com.example.course.Service.CourseService;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepo;

    @Autowired
    CourseCriteriaRepository courseCriteriaRepo;

    @Override
    public IdDataDto createCourse(final CourseDataDto course) {
        Course createdCourse = this.courseRepo.save(new Course(course.getName(), 0, course.getDescription(), false, course.getEducator()));
        return new IdDataDto(createdCourse.getId());
    }
    @Override
    public Page<Course> filterCourse(final CoursePage coursePage, final CourseSearchCriteria courseSearchCriteria) {
        return courseCriteriaRepo.filter(coursePage,courseSearchCriteria);
    }

    @Override
    public CourseDataDto getCourse(final Long id) throws RecordNotExistsException {
        Optional<Course> optionalCourse = this.courseRepo.findById(id);
        if (!optionalCourse.isPresent() || optionalCourse.get().getDeleted()) {
            throw new RecordNotExistsException(ErrorMessages.COURSE_NOT_EXISTS);
        }
        Course course = optionalCourse.get();
        final String name = course.getName();
        final String description = course.getDescription();
        final Long educatorId = course.getEducator();
        return new CourseDataDto(name, description, educatorId);
    }

    private void updateCourseName(Course course, final String name) {
        if (StringUtils.hasText(name)) {
            course.setName(name.trim());
        }
    }

    private void updateCourseDescription(Course course, final String description) {
        if (StringUtils.hasText(description)) {
            course.setDescription(description.trim());
        }
    }

    private void updateCourseEducator(Course course, final Long educatorId) {
        if (Objects.nonNull(educatorId)) {
            course.setEducator(educatorId);
        }
    }

    @Override
    public IdDataDto updateCourse(final Long id, final CourseDataDto newCourseData) throws RecordNotExistsException {
        Optional<Course> optionalCourse = this.courseRepo.findById(id);
        if (!optionalCourse.isPresent() || optionalCourse.get().getDeleted()) {
            throw new RecordNotExistsException(ErrorMessages.COURSE_NOT_EXISTS);
        }
        Course courseData = optionalCourse.get();
        updateCourseName(courseData, newCourseData.getName());
        updateCourseDescription(courseData, newCourseData.getDescription());
        updateCourseEducator(courseData, newCourseData.getEducator());
        try{
            this.courseRepo.save(courseData);
        }catch (DataIntegrityViolationException exception){
            throw new RecordNotExistsException(ErrorMessages.EDUCATOR_NOT_EXISTS);
        }
        return new IdDataDto(id);
    }

    @Override
    public IdDataDto deleteCourse(final Long id) {
        this.courseRepo.deleteById(id);
        return new IdDataDto(id);
    }
}