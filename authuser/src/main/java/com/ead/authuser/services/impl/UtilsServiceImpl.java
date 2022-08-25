package com.ead.authuser.services.impl;

import com.ead.authuser.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    private static final String REQUEST_URI = "http://localhost:8082";

    public String createEndpoint(UUID userId, Pageable pageable) {
        StringBuilder endpoint = new StringBuilder(REQUEST_URI);
        endpoint.append("/courses");
        endpoint.append("?courses?userId=" + userId);
        endpoint.append("&page=" + pageable);
        endpoint.append("&size=" + pageable.getPageSize());
        endpoint.append("&sort=" + pageable.getSort().toString().replaceAll(": ", ","));
        return endpoint.toString();
    }
}
