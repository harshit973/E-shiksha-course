package com.example.course.Entity;

import javax.persistence.*;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;

    @Column
    protected Boolean deleted;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date", nullable = false, updatable = false)
    @CreatedDate
    protected Date updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Date();
        this.deleted = false;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = new Date();
    }
}
