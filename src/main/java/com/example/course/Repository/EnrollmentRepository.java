package com.example.course.Repository;

import com.example.course.Constants.EntityConstants;
import com.example.course.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = EntityConstants.updateCourseQuery)
    void updateById(final Long id, final String name, final String description, final Long educator);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = EntityConstants.deleteEnrollmentQuery)
    void deleteById(final Long id);
}
