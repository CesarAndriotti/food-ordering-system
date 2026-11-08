package com.food.ordering.system.order.service.domain.dto.domain;

import com.food.ordering.system.order.service.domain.dto.domain.entity.Order;
import com.food.ordering.system.order.service.domain.dto.domain.entity.Product;
import com.food.ordering.system.order.service.domain.dto.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderPaidEvent;
import lombok.extern.java.Log;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Log
/**
 * Implementación de reglas de transición del agregado Order.
 */
public class OrderDomainServiceImpl implements OrderDomainService {

    public static final String UTC = "UTC";
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProcuctInformation(order, restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order with id: " + order.getId().getValue() + " is initiated");
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    private void setOrderProcuctInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
            Product currentProduct = orderItem.getProduct();

            if(currentProduct.equals(restaurantProduct)) {
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(), restaurantProduct.getPrice());
            }
        }));
    }

    private void validateRestaurant(Restaurant restaurant) {
        if(!restaurant.isActive()) {
            throw new IllegalStateException("Restaurant with id " + restaurant.getId().getValue() + " is not active");
        }
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {

        order.pay();
        log.info("Order with id: " + order.getId().getValue() + " is paid");
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {

        order.approve();
        log.info("Order with id: " + order.getId().getValue() + " is approved");

    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {

        order.initCancel(failureMessages);
        log.info("Order with id: " + order.getId().getValue() + " is cancelling");

        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {

        order.cancel(failureMessages);
        log.info("Order with id: " + order.getId().getValue() + " is cancelled");

    }
}
