package com.example.course.Entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "course_lecture")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Lecture extends BaseEntity implements Serializable {

    @NonNull
    @Column(name = "name")
    private String name;
    @NonNull
    @Lob
    @Column(name = "source", columnDefinition = "TEXT")
    private String source;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "module", referencedColumnName = "id")
    private Module module;

}