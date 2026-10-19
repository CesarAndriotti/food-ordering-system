package com.food.ordering.system.domain.entity;

// Representa la raíz de un agregado en el contexto de DDD (Domain-Driven Design).
// Un agregado es un conjunto de objetos relacionados que se tratan como una unidad
// para fines de consistencia de datos. La raíz del agregado es la entidad principal que controla
// el acceso a las demás entidades dentro del agregado.
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {

}




