package com.example.course.Repository;

import com.example.course.Constants.EntityConstants;
import com.example.course.Entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
    @Query(nativeQuery = true,value = EntityConstants.moduleExistsQuery)
    Integer exists(Long id);
    @Transactional
    @Modifying
    @Query(value = EntityConstants.deleteModuleQuery,nativeQuery = true)
    void deleteModule(Long id);

}
