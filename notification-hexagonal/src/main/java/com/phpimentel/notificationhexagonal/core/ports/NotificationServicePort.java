package com.phpimentel.notificationhexagonal.core.ports;

import com.phpimentel.notificationhexagonal.core.domain.NotificationDomain;
import com.phpimentel.notificationhexagonal.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationServicePort {

    NotificationDomain saveNotification(NotificationDomain notificationModel);

    List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo);

    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
