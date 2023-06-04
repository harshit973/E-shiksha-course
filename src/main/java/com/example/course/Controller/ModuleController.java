package com.example.course.Controller;

import com.example.course.Constants.ApiConstants;
import com.example.course.Entity.Module;
import com.example.course.Entity.Page.ModulePage;
import com.example.course.Entity.SearchCriteria.ModuleSearchCriteria;
import com.example.course.Exception.ControllerException;
import com.example.course.Exception.RecordNotExistsException;
import com.example.course.Service.ModuleService;
import com.example.course.dto.AbstractModuleDataDTO;
import com.example.course.dto.IdDataDto;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = ApiConstants.version + ApiConstants.separator + ApiConstants.module, produces = MediaType.APPLICATION_JSON_VALUE)
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody final AbstractModuleDataDTO moduleData) {
        try{
            return new ResponseEntity<>(this.moduleService.createModule(moduleData), HttpStatus.CREATED);
        }catch (RecordNotExistsException exception){
            ControllerException controllerException=new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(controllerException,controllerException.getStatus());
        }
    }

    @GetMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> get(@NonNull @PathVariable final Long id) {
        try {
            return new ResponseEntity<>(this.moduleService.getModule(id), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }

    @GetMapping
    public ResponseEntity<Page<Module>> get(@NonNull ModulePage modulePage, @NonNull ModuleSearchCriteria moduleSearchCriteria) {
        return new ResponseEntity<>(this.moduleService.filterModule(modulePage,moduleSearchCriteria), HttpStatus.OK);
    }

    @PutMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<?> update(@NonNull @PathVariable final Long id, @RequestBody final AbstractModuleDataDTO moduleRequest) {
        try {
            return new ResponseEntity<>(this.moduleService.updateModule(id, moduleRequest), HttpStatus.OK);
        } catch (RecordNotExistsException exception) {
            ControllerException ce = new ControllerException(exception.getMessage(), exception.getStatus());
            return new ResponseEntity<>(ce, ce.getStatus());
        }
    }

    @DeleteMapping(ApiConstants.separator + ApiConstants.idUrlParam)
    public ResponseEntity<IdDataDto> delete(@NonNull @PathVariable final Long id) {
        log.debug("processing...");
        return new ResponseEntity<>(this.moduleService.deleteModule(id), HttpStatus.OK);
    }

}
