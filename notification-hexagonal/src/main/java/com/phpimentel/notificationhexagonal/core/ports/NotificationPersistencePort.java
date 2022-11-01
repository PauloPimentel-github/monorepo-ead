package com.phpimentel.notificationhexagonal.core.ports;

import com.phpimentel.notificationhexagonal.core.domain.NotificationDomain;
import com.phpimentel.notificationhexagonal.core.domain.PageInfo;
import com.phpimentel.notificationhexagonal.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain save(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo);

    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);
}
