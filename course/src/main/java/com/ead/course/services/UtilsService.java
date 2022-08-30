package com.ead.course.services;


import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrlGetAllUsersByCourse(String requestURL, UUID userId, Pageable pageable);

    String createUrlGetOneUserById(String requestURL, UUID userId);

    String postSubscriptionUserInCourse(String requestURL, UUID userId);
}
