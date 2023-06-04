package com.example.course.Service;

import com.example.course.Entity.Lecture;
import com.example.course.Entity.Page.LecturePage;
import com.example.course.Entity.SearchCriteria.LectureSearchCriteria;
import com.example.course.dto.*;
import org.springframework.data.domain.Page;

public interface LectureService {
    IdDataDto createLecture(AbstractLectureDataDTO lecture);

    LectureDataDTO getLecture(Long id);

    IdDataDto updateLecture(Long id, AbstractLectureDataDTO lecture);

    IdDataDto deleteLecture(Long id);
    Page<Lecture> filterLecture(final LecturePage lecturePage, final LectureSearchCriteria lectureSearchCriteria);
}
