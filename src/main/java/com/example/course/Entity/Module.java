package com.example.course.Entity;

import com.example.course.Entity.Listners.ModuleListner;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "course_module")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
@EntityListeners(ModuleListner.class)
public class Module extends BaseEntity implements Serializable {

    @NonNull
    @Column(name = "name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course", referencedColumnName = "id")
    private Course course;

}