package com.phpimentel.payment.services;


import com.phpimentel.payment.models.UserModel;

import java.util.UUID;

public interface UserService {

    UserModel save(UserModel userModel);

    void delete(UUID userId);

}
