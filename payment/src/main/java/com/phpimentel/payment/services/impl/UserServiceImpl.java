package com.phpimentel.payment.services.impl;

import com.phpimentel.payment.models.UserModel;
import com.phpimentel.payment.repositories.UserRepository;
import com.phpimentel.payment.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserModel save(UserModel userModel) {
        return this.userRepository.save(userModel);
    }

    @Transactional
    @Override
    public void delete(UUID userId) {
        this.userRepository.deleteById(userId);
    }
}
