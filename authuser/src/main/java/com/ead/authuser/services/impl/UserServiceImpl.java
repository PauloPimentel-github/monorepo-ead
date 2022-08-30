package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.models.UserCourseModel;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserCourseRepository;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private CourseClient courseClient;

    @Override
    public List<UserModel> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return this.userRepository.findById(userId);
    }

    @Transactional
    @Override
    public void delete(UserModel userModel) {
        boolean isDeleteUserCourseInCourse = false;
        List<UserCourseModel> listUserCourseModel = this.userCourseRepository.findAllUserAndCourseId(userModel.getUserId());
        if (!listUserCourseModel.isEmpty()) {
            this.userCourseRepository.deleteAll(listUserCourseModel);
            isDeleteUserCourseInCourse = true;
        }
        this.userRepository.delete(userModel);
        if (isDeleteUserCourseInCourse) {
            this.courseClient.deleteUserInCourse(userModel.getUserId());
        }
    }

    @Override
    public void save(UserModel userModel) {
        this.userRepository.save(userModel);
    }

    @Override
    public boolean existsByUserName(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pegeable) {
        return this.userRepository.findAll(spec, pegeable);
    }
}
