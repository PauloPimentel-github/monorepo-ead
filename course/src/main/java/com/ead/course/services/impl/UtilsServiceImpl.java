package com.ead.course.services.impl;

import com.ead.course.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    private static final String REQUEST_URI = "http://localhost:8081";

    public String createEndpoint(UUID courseId, Pageable pageable) {
        StringBuilder endpoint = new StringBuilder(REQUEST_URI);
        endpoint.append("/users");
        endpoint.append("?courseId=" + courseId);
        endpoint.append("&page=" + pageable.getPageNumber());
        endpoint.append("&size=" + pageable.getPageSize());
        endpoint.append("&sort=" + pageable.getSort().toString().replaceAll(": ", ","));
        return endpoint.toString();
    }
}
