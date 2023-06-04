package com.example.course.Entity.Page;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class ModulePage {
    private int pageNumber=0;
    private int pageSize=0;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy= "id";
}
