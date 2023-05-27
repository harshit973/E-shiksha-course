package com.example.course.Service.impl;

import com.example.course.Constants.ApiConstants;
import com.example.course.Entity.Course;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Repository.CourseRepository;
import com.example.course.Service.CourseService;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseRepository courseRepo;
    @Autowired
    RestTemplate restTemplate;
    @Value("erp.user")
    private String fetchEducatorUrl;

    @Override
    public IdDataDto createCourse(final CourseDataDto course) {
        Course createdCourse = this.courseRepo.save(new Course(course.getName(), 0, course.getDescription(), false, course.getEducator()));
        return new IdDataDto(createdCourse.getId());
    }

    @Override
    public CourseDataDto getCourse(final Long id) throws RecordNotExistsException {
        Optional<Course> optionalCourse = this.courseRepo.findById(id);
        if (!optionalCourse.isPresent() || optionalCourse.get().getDeleted()) {
            throw new RecordNotExistsException("This course does not exists");
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
            throw new RecordNotExistsException("This course does not exists");
        }
        Course courseData = optionalCourse.get();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity entity = new HttpEntity<>(headers);
        this.restTemplate.exchange(fetchEducatorUrl + ApiConstants.separator + courseData.getEducator(), HttpMethod.GET, entity, Object.class);
        updateCourseName(courseData, newCourseData.getName());
        updateCourseDescription(courseData, newCourseData.getDescription());
        updateCourseEducator(courseData, newCourseData.getEducator());
        this.courseRepo.updateById(id, newCourseData.getName(), newCourseData.getDescription(), newCourseData.getEducator());
        return new IdDataDto(id);
    }

    @Override
    public IdDataDto deleteCourse(final Long id) {
        this.courseRepo.deleteById(id);
        return new IdDataDto(id);
    }
}