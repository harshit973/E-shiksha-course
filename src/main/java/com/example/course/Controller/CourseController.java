package com.example.course.Controller;

import com.example.course.Constants.ApiConstants;
import com.example.course.Entity.Course;
import com.example.course.Entity.Page.CoursePage;
import com.example.course.Entity.SearchCriteria.CourseSearchCriteria;
import com.example.course.Exception.ControllerException;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Service.CourseService;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.version + ApiConstants.separator + ApiConstants.course, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(originPatterns = "*")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping
    public ResponseEntity<IdDataDto> create(@RequestBody final CourseDataDto courseRequest) {
        return new ResponseEntity<>(this.courseService.createCourse(courseRequest), HttpStatus.CREATED);
    }

    @GetMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> get(@NonNull @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(this.courseService.getCourse(id), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Course>> filterCourse(@NonNull CoursePage coursePage, @NonNull CourseSearchCriteria courseSearchCriteria) {
        return new ResponseEntity<>(this.courseService.filterCourse(coursePage, courseSearchCriteria), HttpStatus.OK);
    }

    @GetMapping(value = "/trending")
    public ResponseEntity<List<Course>> trendingCourse() {
        CoursePage coursePage = new CoursePage(0,5, Sort.Direction.DESC,"rating");
        return new ResponseEntity<>(this.courseService.filterCourse(coursePage, new CourseSearchCriteria()).getContent(), HttpStatus.OK);
    }

    @PutMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> update(@NonNull @PathVariable final Long id, @RequestBody final CourseDataDto courseRequest) {
        try {
            return new ResponseEntity<>(this.courseService.updateCourse(id, courseRequest), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }

    }

    @DeleteMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<IdDataDto> delete(@NonNull @PathVariable final Long id) {
        return new ResponseEntity<>(this.courseService.deleteCourse(id), HttpStatus.OK);
    }

}
