package com.example.course.Controller;

import com.example.course.Constants.ApiConstants;
import com.example.course.dto.request.CourseCreateRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.version+ApiConstants.separator+ApiConstants.course,produces = MediaType.APPLICATION_JSON_VALUE)
public class Course {

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody final CourseCreateRequest courseRequest){

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
