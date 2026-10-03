package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.AggregateRoot;
import com.food.ordering.system.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {
    private final String username;

    public Customer(CustomerId customerId, String username) {
        super(customerId);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
