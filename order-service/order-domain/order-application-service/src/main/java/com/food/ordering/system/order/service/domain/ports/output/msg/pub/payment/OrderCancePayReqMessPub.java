package com.food.ordering.system.order.service.domain.ports.output.msg.pub.payment;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderCancelledEvent;

public interface OrderCancePayReqMessPub extends DomainEventPublisher<OrderCancelledEvent> {


}
