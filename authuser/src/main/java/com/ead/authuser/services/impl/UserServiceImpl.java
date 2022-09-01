package com.ead.authuser.services.impl;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.enums.ActionType;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.publishers.UserEventPublisher;
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
    private CourseClient courseClient;

    @Autowired
    private UserEventPublisher userEventPublisher;

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
        this.userRepository.delete(userModel);
    }

    @Override
    public UserModel save(UserModel userModel) {
        return this.userRepository.save(userModel);
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

    @Transactional
    @Override
    public UserModel saveUser(UserModel userModel) {
        userModel = this.save(userModel);
        this.userEventPublisher.publishUserEvet(userModel.convertToUserEvetDto(), ActionType.CREATE);
        return userModel;
    }

    @Transactional
    @Override
    public void deleteUser(UserModel userModel) {
        this.delete(userModel);
        this.userEventPublisher.publishUserEvet(userModel.convertToUserEvetDto(), ActionType.DELETE);
    }

    @Transactional
    @Override
    public UserModel updateUser(UserModel userModel) {
        userModel = this.save(userModel);
        this.userEventPublisher.publishUserEvet(userModel.convertToUserEvetDto(), ActionType.UPDATE);
        return userModel;
    }

    @Override
    public UserModel updatePassword(UserModel userModel) {
        return this.save(userModel);
    }
}
