package com.example.course.Service.impl;

import com.example.course.Constants.ErrorMessages;
import com.example.course.Entity.Course;
import com.example.course.Entity.Lecture;
import com.example.course.Entity.Module;
import com.example.course.Entity.Page.LecturePage;
import com.example.course.Entity.SearchCriteria.LectureSearchCriteria;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Repository.LectureCriteriaRepository;
import com.example.course.Repository.LectureRepository;
import com.example.course.Repository.ModuleRepository;
import com.example.course.Service.LectureService;
import com.example.course.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class LectureServiceImpl implements LectureService {
    @Autowired
    LectureRepository lectureRepo;
    @Autowired
    LectureCriteriaRepository lectureCriteriaRepository;

    @Autowired
    ModuleRepository moduleRepo;

    private Boolean moduleExists(Long module) {
        if (Objects.isNull(module) || this.moduleRepo.exists(module)==0) {
            return false;
        }
        return true;
    }

    @Override
    public IdDataDto createLecture(AbstractLectureDataDTO lecture) {
        if (!StringUtils.hasText(lecture.getName())) {
            throw new RecordNotExistsException(ErrorMessages.LECTURE_NAME_NOT_EXISTS);
        } else if (!StringUtils.hasText(lecture.getSource())) {
            throw new RecordNotExistsException(ErrorMessages.LECTURE_SOURCE_NOT_EXISTS);
        } else if (!this.moduleExists(lecture.getModule())) {
            throw new RecordNotExistsException(ErrorMessages.MODULE_NOT_EXISTS);
        }
        try {
            Lecture lectureObj = this.lectureRepo.save(new Lecture(lecture.getName(), lecture.getSource(), moduleRepo.getReferenceById(lecture.getModule())));
            return new IdDataDto(lectureObj.getId());
        } catch (DataIntegrityViolationException exception) {
            throw new RecordNotExistsException(ErrorMessages.MODULE_NOT_EXISTS);
        }
    }

    @Override
    public LectureDataDTO getLecture(Long id) {
        final Optional<Lecture> optionalLecture = this.lectureRepo.findById(id);
        if (!optionalLecture.isPresent()) {
            throw new RecordNotExistsException(ErrorMessages.LECTURE_NOT_EXISTS);
        }
        final Lecture lecture = optionalLecture.get();
        final Module module = lecture.getModule();
        final Course course = lecture.getModule().getCourse();
        if (lecture.getDeleted()) {
            throw new RecordNotExistsException(ErrorMessages.LECTURE_NOT_EXISTS);
        }
        return new LectureDataDTO(lecture.getName(), lecture.getSource(), new ModuleDataDTO(module.getName(), new CourseDataDto(course.getName(), course.getDescription(), course.getEducator())));
    }

    @Override
    public IdDataDto updateLecture(Long id, AbstractLectureDataDTO lecture) {
        if (Objects.nonNull(lecture.getModule()) && this.moduleRepo.exists(lecture.getModule())==0) {
            throw new RecordNotExistsException(ErrorMessages.MODULE_NOT_EXISTS);
        }
        final Optional<Lecture> optionalLecture = this.lectureRepo.findById(id);
        if (!optionalLecture.isPresent()) {
            throw new RecordNotExistsException(ErrorMessages.LECTURE_NOT_EXISTS);
        }
        Lecture lectureEntity=optionalLecture.get();
        if(StringUtils.hasText(lecture.getName())){
            lectureEntity.setName(lecture.getName());
        }
        if(StringUtils.hasText(lecture.getSource())){
            lectureEntity.setSource(lecture.getSource());
        }
        if(Objects.nonNull(lecture.getModule())){
            lectureEntity.setModule(this.moduleRepo.getReferenceById(lecture.getModule()));
        }
        this.lectureRepo.save(lectureEntity);
        return new IdDataDto(id);
    }

    @Override
    public IdDataDto deleteLecture(Long id) {
        this.lectureRepo.deleteLecture(id);
        return new IdDataDto(id);
    }

    @Override
    public Page<Lecture> filterLecture(LecturePage lecturePage, LectureSearchCriteria lectureSearchCriteria) {
        return this.lectureCriteriaRepository.filter(lecturePage,lectureSearchCriteria);
    }
}
