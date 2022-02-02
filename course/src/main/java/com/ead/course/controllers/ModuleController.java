package com.ead.course.controllers;

import com.ead.course.dtos.ModuleDto;
import com.ead.course.models.CourseModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    public static final String MODULE_NOT_FOUND_FOR_THIS_COURSE = "Module Not Found for this course";

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/courses/{courseId}/modules")
    public ResponseEntity<Object> saveModule(@PathVariable UUID courseId,
                                             @RequestBody @Valid ModuleDto moduleDto) {
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found");
        }
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleDto, moduleModel);
        moduleModel.setCreationDate(OffsetDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.moduleService.save(moduleModel));
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> deleteModule(@PathVariable UUID courseId,
                                               @PathVariable UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND_FOR_THIS_COURSE);
        }
        this.moduleService.delete(moduleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> updateModule(@PathVariable UUID courseId,
                                               @PathVariable UUID moduleId,
                                               @RequestBody @Valid ModuleDto courseDto) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND_FOR_THIS_COURSE);
        }
        var moduleModel = moduleModelOptional.get();
        BeanUtils.copyProperties(courseDto, moduleModel);
        return ResponseEntity.status(HttpStatus.OK).body(this.moduleService.save(moduleModel));
    }

    @GetMapping("/courses/{courseId}/modules/")
    public ResponseEntity<List<ModuleModel>> getAllModules(@PathVariable UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(this.moduleService.findAllByCourse(courseId));
    }

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    public ResponseEntity<Object> getOneModule(@PathVariable UUID courseId,
                                                           @PathVariable UUID moduleId) {
        Optional<ModuleModel> moduleModelOptional = this.moduleService.findModuleIntoCourse(courseId, moduleId);
        if (moduleModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MODULE_NOT_FOUND_FOR_THIS_COURSE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(moduleModelOptional.get());
    }
}
