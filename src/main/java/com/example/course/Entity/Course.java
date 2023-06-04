package com.example.course.Entity;

import com.example.course.Entity.Listners.CourseListner;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "course_course")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EntityListeners(CourseListner.class)
public class Course extends BaseEntity implements Serializable {

    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "duration")
    private Integer duration;
    @NonNull
    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @NonNull
    @Column(name = "active")
    private Boolean active;
    @NonNull
    @Column(name = "educator")
    private Long educator;

}