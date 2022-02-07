package com.ead.course.controllers;

import com.ead.course.dtos.CourseDto;
import com.ead.course.models.CourseModel;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    public static final String COURSE_NOT_FOUND = "Course Not Found";

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseDto courseDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseDto, courseModel);
        courseModel.setCreationDate(OffsetDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(OffsetDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.courseService.save(courseModel));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable UUID courseId) {
        Optional<CourseModel> courseModelOptional = this.courseService.findById(courseId);
        if (courseModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(COURSE_NOT_FOUND);
        }
        this.courseService.delete(courseModelOptional.get());
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
                                                                   Pageable pegeable) {
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
