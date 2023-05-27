package com.example.course.Repository;

import com.example.course.Constants.EntityConstants;
import com.example.course.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CourseRepository extends JpaRepository<Course,Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = EntityConstants.updateCourseQuery)
    void updateById(final Long id,final String name,final String description,final Long educator);
    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = EntityConstants.deleteCourseQuery)
    void deleteById(final Long id);
}
