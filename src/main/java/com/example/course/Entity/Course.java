package com.example.course.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import java.util.Objects;

@Entity
@Table(name = "course_course")
@Data
@EqualsAndHashCode(callSuper = false)
public class Course extends BaseEntity {
    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "duration")
    private String duration;
    @Column(name = "description")
    private String description;
    @PrePersist
    @PreUpdate
    public void formatter(){
        if(Objects.nonNull(this.description))
            this.description=this.description.trim();
        this.name=this.name.trim();
    }
}