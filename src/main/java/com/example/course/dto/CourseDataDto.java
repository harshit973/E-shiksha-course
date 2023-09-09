package com.example.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDataDto {
    private String name;
    private String description;
    private Long educator;
    private Integer rating;
    private String thumbnail;
    private Integer price;

}
