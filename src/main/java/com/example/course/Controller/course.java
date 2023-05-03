package com.example.course.Controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequestMapping(value = "/course")
public class course {
    public ResponseEntity<Map<?,?>> create(){
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.CREATED);
    }

}
