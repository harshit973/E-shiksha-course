package com.example.course.dto.request;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
public class CourseCreateRequest {
    private String name;
    private String duration;
    private String description;
    private Integer educator;

}
