package com.example.course.Entity.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePage {
    private int pageNumber=0;
    private int pageSize=0;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy= "id";
}
