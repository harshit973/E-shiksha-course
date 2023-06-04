package com.example.course.Service.impl;

import com.example.course.Constants.ErrorMessages;
import com.example.course.Entity.Course;
import com.example.course.Entity.Module;
import com.example.course.Entity.Page.ModulePage;
import com.example.course.Entity.SearchCriteria.ModuleSearchCriteria;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Repository.CourseRepository;
import com.example.course.Repository.ModuleCriteriaRepository;
import com.example.course.Repository.ModuleRepository;
import com.example.course.Service.ModuleService;
import com.example.course.dto.AbstractModuleDataDTO;
import com.example.course.dto.CourseDataDto;
import com.example.course.dto.IdDataDto;
import com.example.course.dto.ModuleDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepo;

    @Autowired
    CourseRepository courseRepo;
    @Autowired
    ModuleCriteriaRepository moduleCriteriaRepository;

    private Boolean courseExists(Long id){
        if(Objects.isNull(id) || this.courseRepo.courseExists(id)==0){
            return false;
        }
        return true;
    }
    @Override
    public IdDataDto createModule(AbstractModuleDataDTO module) {
        if(!StringUtils.hasText(module.getName())){
            throw new RecordNotExistsException(ErrorMessages.MODULE_NAME_NOT_EXISTS);
        }else if(!this.courseExists(module.getCourse())){
            throw new RecordNotExistsException(ErrorMessages.COURSE_NOT_EXISTS);
        }
        Module createdModule=this.moduleRepo.save(new Module(module.getName(), this.courseRepo.getReferenceById(module.getCourse())));
        return new IdDataDto(createdModule.getId());
    }

    @Override
    public ModuleDataDTO getModule(Long id) {
        Optional<Module> optionalModule=this.moduleRepo.findById(id);
        if(!optionalModule.isPresent() || optionalModule.get().getDeleted()){
            throw new RecordNotExistsException(ErrorMessages.MODULE_NOT_EXISTS);
        }
        Module module=optionalModule.get();
        Course course=module.getCourse();
        return new ModuleDataDTO(module.getName(), new CourseDataDto(course.getName(), course.getDescription(), course.getEducator()));
    }

    @Override
    public IdDataDto updateModule(Long id, AbstractModuleDataDTO module) {
        Optional<Module> optionalModule=this.moduleRepo.findById(id);
        if(!optionalModule.isPresent() || optionalModule.get().getDeleted()){
            throw new RecordNotExistsException(ErrorMessages.MODULE_NOT_EXISTS);
        }
        if(Objects.nonNull(module.getCourse()) && this.courseRepo.courseExists(module.getCourse())==0){
            throw new RecordNotExistsException(ErrorMessages.COURSE_NOT_EXISTS);
        }
        Module moduleEntity=optionalModule.get();
        if(StringUtils.hasText(module.getName())){
            moduleEntity.setName(module.getName());
        }
        if(Objects.nonNull(module.getCourse())){
            moduleEntity.setCourse(this.courseRepo.getReferenceById(module.getCourse()));
        }
        this.moduleRepo.save(moduleEntity);
        return new IdDataDto(id);
    }

    @Override
    public IdDataDto deleteModule(Long id) {
        this.moduleRepo.deleteModule(id);
        return new IdDataDto(id);
    }

    @Override
    public Page<Module> filterModule(ModulePage modulePage, ModuleSearchCriteria moduleSearchCriteria) {
        return this.moduleCriteriaRepository.filter(modulePage,moduleSearchCriteria);
    }
}
