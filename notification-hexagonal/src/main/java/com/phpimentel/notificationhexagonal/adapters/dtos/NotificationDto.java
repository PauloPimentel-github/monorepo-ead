package com.phpimentel.notificationhexagonal.adapters.dtos;

import com.phpimentel.notificationhexagonal.core.domain.enums.NotificationStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NotificationDto {

    @NotNull
    private NotificationStatus notificationStatus;
}
