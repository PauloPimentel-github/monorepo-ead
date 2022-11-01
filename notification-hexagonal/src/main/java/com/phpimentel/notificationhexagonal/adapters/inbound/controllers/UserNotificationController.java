package com.phpimentel.notificationhexagonal.adapters.inbound.controllers;

import com.phpimentel.notificationhexagonal.adapters.dtos.NotificationDto;
import com.phpimentel.notificationhexagonal.core.domain.NotificationDomain;
import com.phpimentel.notificationhexagonal.core.domain.PageInfo;
import com.phpimentel.notificationhexagonal.core.ports.NotificationServicePort;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationServicePort notificationServicePort;

    public UserNotificationController(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable UUID userId,
                                                                              @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pegeable) {
        PageInfo pageInfo = new PageInfo();
        BeanUtils.copyProperties(pegeable, pageInfo);
        List<NotificationDomain> listNotificationDomain = this.notificationServicePort.findAllNotificationsByUser(userId, pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<NotificationDomain>(listNotificationDomain, pegeable, listNotificationDomain.size()));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable UUID userId,
                                                     @PathVariable UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {
        Optional<NotificationDomain> notificationModelOptional =
                this.notificationServicePort.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        this.notificationServicePort.saveNotification(notificationModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());
    }
}
