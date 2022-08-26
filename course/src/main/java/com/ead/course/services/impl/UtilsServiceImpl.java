package com.ead.course.services.impl;

import com.ead.course.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    public String createUrlGetAllUsersByCourse(String requestURI, UUID courseId, Pageable pageable) {
        StringBuilder endpoint = new StringBuilder(requestURI);
        endpoint.append("/users");
        endpoint.append("?courseId=" + courseId);
        endpoint.append("&page=" + pageable.getPageNumber());
        endpoint.append("&size=" + pageable.getPageSize());
        endpoint.append("&sort=" + pageable.getSort().toString().replaceAll(": ", ","));
        return endpoint.toString();
    }
}
