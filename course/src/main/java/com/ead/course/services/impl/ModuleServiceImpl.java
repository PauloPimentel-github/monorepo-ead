package com.ead.course.services.impl;

import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import com.ead.course.repositories.LessonRepository;
import com.ead.course.repositories.ModuleRepository;
import com.ead.course.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> listLessonModule = this.lessonRepository.findAllLessonsIntoCourse(moduleModel.getModuleId());
        if (!listLessonModule.isEmpty()) {
            this.lessonRepository.deleteAll(listLessonModule);
        }
        this.moduleRepository.delete(moduleModel);
    }

    @Override
    public ModuleModel save(ModuleModel moduleModel) {
        return this.moduleRepository.save(moduleModel);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        return this.moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    @Override
    public List<ModuleModel> findAllByCourse(UUID courseId) {
        return this.moduleRepository.findAllModulesIntoCourse(courseId);
    }
}
