package com.example.course.Entity.Listners;

import com.example.course.Entity.Module;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class ModuleListner extends BaseListner {

    @PrePersist
    public void onCreate(Module entity) {
        super.onCreate(entity);
        entity.setName(entity.getName().trim());
    }

    @PreUpdate
    public void onUpdate(Module entity) {
        super.onUpdate(entity);
        entity.setName(entity.getName().trim());
    }

}
