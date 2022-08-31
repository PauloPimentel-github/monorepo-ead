package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import com.ead.course.validation.CourseValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    public static final String COURSE_NOT_FOUND = "Course Not Found";

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody CourseDto courseDto, Errors errors) {
        log.debug("POST saveCourse courseDto received {} ", courseDto.toString());
        this.courseValidator.validate(courseDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());
        }
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel.setCreationDate(OffsetDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(OffsetDateTime.now(ZoneId.of("UTC")));
        log.debug("POST saveCourse courseModel saved {}", courseModel.toString());
        log.info("Course saved successfully courseId {}", courseModel.getCourseId());
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID courseId) {
        log.debug("DELETE deleteCourse courseId received {}", courseId);
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(COURSE_NOT_FOUND);
        }
        this.courseService.delete(courseModelOptional.get());
        log.debug("DELETE deleteCourse courseId deleted {}", courseId);
        log.info("Course deleted successfully courseId {}", courseId);
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(@PathVariable UUID courseId,
                                               @RequestBody @Valid CourseDto courseDto) {
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(COURSE_NOT_FOUND);
        }
        var courseModel = courseModelOptional.get();
        courseModel.setLastUpdateDate(OffsetDateTime.now(ZoneId.of("UTC")));
        BeanUtils.copyProperties(courseDto, courseModel);
        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.save(courseModel));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC)
                                                                   Pageable pegeable,
                                                           @RequestParam(required = false) UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(this.courseService.findAll(spec, pegeable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable UUID courseId) {
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(COURSE_NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.OK).body(courseModelOptional.get());
    }
}
