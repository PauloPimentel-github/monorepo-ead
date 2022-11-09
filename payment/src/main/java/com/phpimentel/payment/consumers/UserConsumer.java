package com.phpimentel.payment.consumers;

import com.phpimentel.payment.dtos.UserEventDto;
import com.phpimentel.payment.enums.ActionType;
import com.phpimentel.payment.enums.PaymentStatus;
import com.phpimentel.payment.services.UserService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {

    @Autowired
    private UserService userService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.userEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true")
    ))
    public void listenerUserEvent(@Payload UserEventDto userEventDto) {
        var userModel = userEventDto.convertUserModel();

        switch (ActionType.valueOf(userEventDto.getActionType())) {
            case CREATE:
                userModel.setPaymentStatus(PaymentStatus.NOTSTARTED);
                this.userService.save(userModel);
                break;
            case UPDATE:
                this.userService.save(userModel);
                break;
            case DELETE:
                this.userService.delete(userEventDto.getUserId());
                break;
        }
    }
}
