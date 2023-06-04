package com.example.course.Entity.Listners;

import com.example.course.Entity.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class BaseListner {
    @PrePersist
    protected void onCreate(BaseEntity entity) {
        entity.setCreatedAt(new Date());
        entity.setDeleted(false);
    }

    @PreUpdate
    protected void onUpdate(BaseEntity entity) {
        entity.setUpdatedAt(new Date());
    }

}
