package com.phpimentel.notificationhexagonal.adapters.inbound.consumers;

import com.phpimentel.notificationhexagonal.adapters.dtos.NotificationCommandDto;
import com.phpimentel.notificationhexagonal.core.domain.NotificationDomain;
import com.phpimentel.notificationhexagonal.core.domain.enums.NotificationStatus;
import com.phpimentel.notificationhexagonal.core.ports.NotificationServicePort;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Component
public class NotificationConsumer {

    private final NotificationServicePort notificationServicePort;

    public NotificationConsumer(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${ead.broker.key.notificationCommandKey}")
    )
    public void listenener(@Payload NotificationCommandDto notificationCommandDto) {
        var notificationDomain = new NotificationDomain();
        BeanUtils.copyProperties(notificationCommandDto, notificationDomain);
        notificationDomain.setCreationDate(OffsetDateTime.now(ZoneId.of("UTC")));
        notificationDomain.setNotificationStatus(NotificationStatus.CREATED);
        this.notificationServicePort.saveNotification(notificationDomain);
    }
}
