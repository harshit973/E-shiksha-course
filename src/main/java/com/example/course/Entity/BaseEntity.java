package com.example.course.Entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private Boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false, updatable = false)
    @CreatedDate
    private Date updatedAt;

    @PrePersist
    public void onCreate(){
        this.createdAt=new Date();
    }
    @PreUpdate
    public void onUpdate(){
        this.updatedAt=new Date();
    }
}
