package com.example.course.Service;

import com.example.course.dto.EnrollmentDataDto;
import com.example.course.dto.EnrollmentCourseDto;
import com.example.course.dto.IdDataDto;

public interface EnrollmentService {
    IdDataDto createEnrollment(EnrollmentDataDto enrollment);

    EnrollmentCourseDto getEnrollment(Long id);

    IdDataDto deleteEnrollment(Long id);

}
