package com.food.ordering.system.order.service.domain.ports.input.msg.listener.restappr;

import com.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;

public interface RestApprResMessList {

    void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse);
    void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse);

}
