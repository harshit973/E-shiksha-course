package com.example.course.Entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "course_course")
@Data
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity implements Serializable {

    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Column(name = "duration")
    private String duration;
    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "educator",referencedColumnName = "id")
    private String educator;
    @PrePersist
    @PreUpdate
    public void formatter(){
        if(Objects.nonNull(this.description))
            this.description=this.description.trim();
        this.name=this.name.trim();
    }
}