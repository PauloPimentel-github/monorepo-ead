package com.ead.authuser.services.impl;

import com.ead.authuser.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    public String createEndpoint(String requestURLCourse, UUID userId, Pageable pageable) {
        StringBuilder endpoint = new StringBuilder(requestURLCourse);
        endpoint.append("/courses");
        endpoint.append("?userId=" + userId);
        endpoint.append("&page=" + pageable.getPageNumber());
        endpoint.append("&size=" + pageable.getPageSize());
        endpoint.append("&sort=" + pageable.getSort().toString().replaceAll(": ", ","));
        return endpoint.toString();
    }
}
