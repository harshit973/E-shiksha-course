package com.example.course.Repository;

import com.example.course.Constants.EntityConstants;
import com.example.course.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = EntityConstants.courseExistsQuery,nativeQuery = true)
    Integer courseExists(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = EntityConstants.deleteCourseQuery)
    void deleteById(final Long id);
}
