package com.ead.notification.services.impl;

import com.ead.notification.enums.NotificationStatus;
import com.ead.notification.models.NotificationModel;
import com.ead.notification.repositories.NotificationRepository;
import com.ead.notification.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void saveNotification(NotificationModel notificationModel) {
        this.notificationRepository.save(notificationModel);
    }

    @Override
    public Page<NotificationModel> findAllNotificationsByUser(UUID userId, Pageable pegeable) {
        return this.notificationRepository.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pegeable);
    }

    @Override
    public Optional<NotificationModel> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return this.notificationRepository.findByNotificationIdAndUserId(notificationId, userId);
    }
}


