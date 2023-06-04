package com.example.course.Entity.Listners;

import com.example.course.Entity.Course;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Objects;

public class CourseListner extends BaseListner{

    @PrePersist
    public void onCreate(Course entity) {
        super.onCreate(entity);
        entity.setActive(true);
        if (Objects.nonNull(entity.getDescription()))
            entity.setDescription(entity.getDescription().trim());
        entity.setName(entity.getName().trim());
    }

    @PreUpdate
    public void onUpdate(Course entity) {
        super.onUpdate(entity);
        entity.setDescription(entity.getDescription().trim());
        entity.setName(entity.getName().trim());
    }

}
