package com.food.ordering.system.order.service.domain.ports.output.msg.pub.restaurantapproval;

import com.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderPaidEvent;

public interface OrderPaidRestReqMessPub extends DomainEventPublisher<OrderPaidEvent> {
}
