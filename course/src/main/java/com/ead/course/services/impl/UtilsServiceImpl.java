package com.ead.course.services.impl;

import com.ead.course.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    public String createUrlGetAllUsersByCourse(String requestURL, UUID courseId, Pageable pageable) {
        StringBuilder endpoint = new StringBuilder(requestURL);
        endpoint.append("/users");
        endpoint.append("?courseId=" + courseId);
        endpoint.append("&page=" + pageable.getPageNumber());
        endpoint.append("&size=" + pageable.getPageSize());
        endpoint.append("&sort=" + pageable.getSort().toString().replaceAll(": ", ","));
        return endpoint.toString();
    }

    @Override
    public String createUrlGetOneUserById(String requestURL, UUID userId) {
        StringBuilder endpoint = new StringBuilder(requestURL);
        endpoint.append("/users/" + userId);
        return endpoint.toString();
    }

    @Override
    public String createUrlPostSubscriptionUserInCourse(String requestURL, UUID userId) {
        StringBuilder endpoint = new StringBuilder(requestURL);
        endpoint.append("/users/" + userId);
        endpoint.append("/courses/subscription");
        return endpoint.toString();
    }

    @Override
    public String createUrlDeleteCourseInAuthUser(String requestURL, UUID courseId) {
        StringBuilder endpoint = new StringBuilder(requestURL);
        endpoint.append("/users/courses/" + courseId);
        return endpoint.toString();
    }
}
