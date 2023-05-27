package com.example.course.Entity;

import javax.persistence.*;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "course_course")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Course extends BaseEntity implements Serializable {

    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "duration")
    private Integer duration;
    @NonNull
    @Lob
    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
    @NonNull
    @Column(name = "active")
    private Boolean active;
    @NonNull
    @Column(name = "educator")
    private Long educator;
    @PrePersist
    public void createTrigger(){
        this.setActive(false);
        if(Objects.nonNull(this.description))
            this.description=this.description.trim();
        this.setName(this.getName().trim());
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }
    @PreUpdate
    public void updateTrigger(){
        if(Objects.nonNull(this.getDescription()))
            this.setDescription(this.getDescription().trim());
        if(Objects.nonNull(this.getName()))
            this.setName(this.getName().trim());
        this.setUpdatedAt(new Date());
    }

}