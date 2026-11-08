package com.food.ordering.system.order.service.domain.dto.domain;

import com.food.ordering.system.order.service.domain.dto.domain.entity.Order;
import com.food.ordering.system.order.service.domain.dto.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.dto.domain.event.OrderPaidEvent;

import java.util.List;

/**
 * Operaciones de dominio para transiciones de estado del agregado Order.
 *
 * <p>Este contrato devuelve eventos de dominio para que la capa de aplicación
 * decida cuándo publicarlos tras persistir cambios.</p>
 */
public interface OrderDomainService {

    /**
     * Valida restaurante y orden, inicializa la orden y retorna el evento de creación.
     */
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    /**
     * Ejecuta la transición de pago y retorna el evento asociado.
     */
    OrderPaidEvent payOrder(Order order);

    /**
     * Aprueba una orden ya pagada.
     */
    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
