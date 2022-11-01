package com.phpimentel.notificationhexagonal.core.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.phpimentel.notificationhexagonal.core.domain.enums.NotificationStatus;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class NotificationDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID notificationId;
    private UUID userId;
    private String title;
    private String message;
    private OffsetDateTime creationDate;
    private NotificationStatus notificationStatus;

    public UUID getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(UUID notificationId) {
        this.notificationId = notificationId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}
