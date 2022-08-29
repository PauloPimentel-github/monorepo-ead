package com.ead.course.services;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrlGetAllUsersByCourse(String requestURI, UUID userId, Pageable pageable);

    String createUrlGetOneUserById(String requestURI, UUID userId);

    String postSubscriptionUserInCourse(String requestURI, UUID userId);
}
