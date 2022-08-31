package com.ead.course.specifications;

import com.ead.course.models.CourseModel;
import com.ead.course.models.LessonModel;
import com.ead.course.models.ModuleModel;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @And({
            @Spec(path = "title", spec = Like.class)
    })
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @And({
            @Spec(path = "title", spec = Like.class)
    })
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<ModuleModel> moduleModelRoot = root;
            Root<CourseModel> courseModelRoot = query.from(CourseModel.class);
            Expression<Collection<ModuleModel>> courseModules = courseModelRoot.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(courseModelRoot.get("courseId"), courseId), criteriaBuilder.isMember(moduleModelRoot, courseModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<LessonModel> lessonModelRoot = root;
            Root<ModuleModel> moduleModelRoot = query.from(ModuleModel.class);
            Expression<Collection<LessonModel>> moduleLessons = moduleModelRoot.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(moduleModelRoot.get("moduleId"), moduleId), criteriaBuilder.isMember(lessonModelRoot, moduleLessons));
        };
    }
}
