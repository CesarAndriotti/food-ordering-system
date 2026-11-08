package com.food.ordering.system.domain.entity;

import java.util.Objects;

/**
 * Clase base para entidades del dominio.
 *
 * <p>Centraliza el identificador y la semántica de igualdad por identidad,
 * alineada con DDD: dos entidades son iguales si comparten el mismo id y tipo.</p>
 */
public abstract class BaseEntity<ID> {

    private ID id;
    public ID getId() {
        return id;
    }

    /**
     * Permite inicializar la identidad de la entidad desde constructores o fábricas del dominio.
     */
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}


