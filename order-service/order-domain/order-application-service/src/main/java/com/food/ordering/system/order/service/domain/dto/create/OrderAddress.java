package com.food.ordering.system.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;


@Builder
@AllArgsConstructor
@Getter
public class OrderAddress {

    @NonNull
    private final String street;
    @NonNull
    private final String postalCode;
    @NonNull
    private final String city;
}
