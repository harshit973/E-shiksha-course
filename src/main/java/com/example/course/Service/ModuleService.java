package com.example.course.Service;

import com.example.course.Entity.Module;
import com.example.course.Entity.Page.ModulePage;
import com.example.course.Entity.SearchCriteria.ModuleSearchCriteria;
import com.example.course.dto.AbstractModuleDataDTO;
import com.example.course.dto.IdDataDto;
import com.example.course.dto.ModuleDataDTO;
import org.springframework.data.domain.Page;

public interface ModuleService {
    IdDataDto createModule(AbstractModuleDataDTO module);

    ModuleDataDTO getModule(Long id);

    IdDataDto updateModule(Long id, AbstractModuleDataDTO module);

    IdDataDto deleteModule(Long id);
    Page<Module> filterModule(final ModulePage modulePage, final ModuleSearchCriteria moduleSearchCriteria);

}
