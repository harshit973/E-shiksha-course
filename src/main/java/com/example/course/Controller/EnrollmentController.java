package com.example.course.Controller;

import com.example.course.Constants.ApiConstants;
import com.example.course.Exception.ControllerException;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Service.EnrollmentService;
import com.example.course.dto.EnrollmentDataDto;
import com.example.course.dto.IdDataDto;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.version + ApiConstants.separator + ApiConstants.enroll, produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody final EnrollmentDataDto enrollmentRequest) {
        try{
            return new ResponseEntity<>(this.enrollmentService.createEnrollment(enrollmentRequest), HttpStatus.CREATED);
        }catch (RecordNotExistsException exception){
            ControllerException controllerException=new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(controllerException,controllerException.getStatus());
        }
    }

    @GetMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> get(@NonNull @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(this.enrollmentService.getEnrollment(id), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }

    @DeleteMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<IdDataDto> delete(@NonNull @PathVariable final Long id) {
        return new ResponseEntity<>(this.enrollmentService.deleteEnrollment(id), HttpStatus.OK);
    }

}
