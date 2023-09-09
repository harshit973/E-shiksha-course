package com.example.course.Service.impl;

import com.example.course.Constants.ErrorMessages;
import com.example.course.Entity.Course;
import com.example.course.Entity.Enrollment;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Repository.CourseRepository;
import com.example.course.Repository.EnrollmentRepository;
import com.example.course.Service.EnrollmentService;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.EnrollmentDataDto;
import com.example.course.dto.EnrollmentCourseDto;
import com.example.course.dto.IdDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    @Autowired
    EnrollmentRepository enrollmentRepo;
    @Autowired
    CourseRepository courseRepo;

    @Override
    public IdDataDto createEnrollment(final EnrollmentDataDto enrollment) {
        final Optional<Course> optionalCourse=this.courseRepo.findById(enrollment.getCourseId());
        if(!optionalCourse.isPresent() || optionalCourse.get().getDeleted()){
            throw new RecordNotExistsException(ErrorMessages.COURSE_NOT_EXISTS);
        }
        try{
            final Enrollment createdEnrollment = this.enrollmentRepo.save(new Enrollment(enrollment.getUserId(),optionalCourse.get()));
            return new IdDataDto(createdEnrollment.getId());
        }catch (DataIntegrityViolationException exception){
            throw new RecordNotExistsException(ErrorMessages.USER_NOT_EXISTS);
        }
    }

    @Override
    public EnrollmentCourseDto getEnrollment(final Long id) throws RecordNotExistsException {
        final Optional<Enrollment> optionalEnrollment = this.enrollmentRepo.findById(id);
        if (!optionalEnrollment.isPresent() || optionalEnrollment.get().getDeleted()) {
            throw new RecordNotExistsException(ErrorMessages.ENROLLMENT_NOT_EXISTS);
        }
        final Enrollment enrollment = optionalEnrollment.get();
        final Course course=enrollment.getCourse();
        return new EnrollmentCourseDto(enrollment.getUser_id(),new CourseDataDto(course.getName(),course.getDescription(), course.getEducator(),course.getRating(),course.getThumbnail(),course.getPrice()));
    }

    @Override
    public IdDataDto deleteEnrollment(final Long id) {
        this.enrollmentRepo.deleteById(id);
        return new IdDataDto(id);
    }
}