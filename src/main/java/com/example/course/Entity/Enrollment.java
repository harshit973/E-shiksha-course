package com.example.course.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "course_enrollment")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Enrollment extends BaseEntity implements Serializable {

    @Column(name = "user_id")
    private Long user_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course", referencedColumnName = "id")
    private Course course;

}
