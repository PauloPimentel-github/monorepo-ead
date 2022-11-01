package com.phpimentel.notificationhexagonal.core.services;

import com.phpimentel.notificationhexagonal.core.domain.NotificationDomain;
import com.phpimentel.notificationhexagonal.core.domain.PageInfo;
import com.phpimentel.notificationhexagonal.core.domain.enums.NotificationStatus;
import com.phpimentel.notificationhexagonal.core.ports.NotificationPersistencePort;
import com.phpimentel.notificationhexagonal.core.ports.NotificationServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {

    private NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain notificationDomain) {
        return this.notificationPersistencePort.save(notificationDomain);
    }

    @Override
    public List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo) {
        return this.notificationPersistencePort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageInfo);
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return this.notificationPersistencePort.findByNotificationIdAndUserId(notificationId, userId);
    }
}


