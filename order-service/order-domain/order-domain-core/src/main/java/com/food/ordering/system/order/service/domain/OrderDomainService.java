package com.food.ordering.system.order.service.domain;

import com.food.ordering.system.order.service.domain.entity.Order;
import com.food.ordering.system.order.service.domain.entity.Restaurant;
import com.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.domain.event.OrderPaidEvent;

import java.util.List;

//El proceso de activacion de eventos estara en el servicio de llamada
//Que será el servicio de aplicacion. Debido a que la operacion antes de dispararse debe persistirse en la base de datos.

//Naturalmente los eventos son creados en las entidades de dominio, pero el disparo de los eventos se hace en el servicio de aplicacion.
public interface OrderDomainService {

    // Este metodo se encarga de validar y procesar un pedido.
    // Toma un objeto Order y un objeto Restaurant como parámetros, realiza las validaciones necesarias
    // y devuelve un OrderCreatedEvent que indica que el pedido ha sido creado con éxito.
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

    // Este método se encarga de procesar el pago de un pedido.
    // Toma un objeto Order como parámetro, realiza las operaciones necesarias
    // para marcar el pedido como pagado y devuelve un OrderPaidEvent que indica
    // que el pedido ha sido pagado con éxito.
    OrderPaidEvent payOrder(Order order);

    //Este método se encarga de aprobar un pedido.
    //Toma un objeto Order como parámetro y realiza las operaciones necesarias
    //para marcar el pedido como aprobado.
    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
