package com.example.course.Repository;

import com.example.course.Constants.EntityConstants;
import com.example.course.Entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {

    @Query(value = EntityConstants.lectureExistsQuery,nativeQuery = true)
    Integer lectureExists(Long id);

    @Transactional
    @Modifying
    @Query(value = EntityConstants.updateLectureQuery,nativeQuery = true)
    void updateLecture(String name,String source,Long module,Long id);
    @Transactional
    @Modifying
    @Query(value = EntityConstants.deleteLectureQuery,nativeQuery = true)
    void deleteLecture(Long id);

}
