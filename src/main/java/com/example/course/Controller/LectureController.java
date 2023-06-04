package com.example.course.Controller;

import com.example.course.Constants.ApiConstants;
import com.example.course.Entity.Lecture;
import com.example.course.Entity.Page.LecturePage;
import com.example.course.Entity.SearchCriteria.LectureSearchCriteria;
import com.example.course.Exception.ControllerException;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Service.LectureService;
import com.example.course.dto.AbstractLectureDataDTO;
import com.example.course.dto.IdDataDto;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.version + ApiConstants.separator + ApiConstants.lecture, produces = MediaType.APPLICATION_JSON_VALUE)
public class LectureController {

    @Autowired
    LectureService lectureService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody final AbstractLectureDataDTO lectureData) {
        try{
            return new ResponseEntity<>(this.lectureService.createLecture(lectureData), HttpStatus.CREATED);
        }catch (RecordNotExistsException exception){
            ControllerException controllerException=new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(controllerException,controllerException.getStatus());
        }
    }

    @GetMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> get(@NonNull @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(this.lectureService.getLecture(id), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }
    @GetMapping
    public ResponseEntity<Page<Lecture>> get(@NonNull LecturePage lecturePage, @NonNull LectureSearchCriteria lectureSearchCriteria) {
        return new ResponseEntity<>(this.lectureService.filterLecture(lecturePage,lectureSearchCriteria), HttpStatus.OK);
    }

    @PutMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> update(@NonNull @PathVariable final Long id, @RequestBody final AbstractLectureDataDTO lectureRequest) {
        try {
            return new ResponseEntity<>(this.lectureService.updateLecture(id, lectureRequest), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }

    @DeleteMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<IdDataDto> delete(@NonNull @PathVariable final Long id) {
        return new ResponseEntity<>(this.lectureService.deleteLecture(id), HttpStatus.OK);
    }

}
