package com.ead.authuser.services;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrlGetAllCoursesByUser(String requestURL, UUID userId, Pageable pageable);

    String createUrlDeleteUserInCourse(String requestURL, UUID userId);
}
