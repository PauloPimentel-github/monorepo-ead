package com.ead.authuser.services;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createEndpoint(String requestURLCourse, UUID userId, Pageable pageable);
}
