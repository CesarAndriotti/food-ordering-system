package com.food.ordering.system.domain.entity;

/**
 * Marca una entidad como raíz de agregado.
 *
 * <p>La raíz define el límite transaccional y las invariantes del agregado,
 * evitando cambios directos sobre entidades internas desde fuera del dominio.</p>
 */
public abstract class AggregateRoot<ID> extends BaseEntity<ID> {

}




