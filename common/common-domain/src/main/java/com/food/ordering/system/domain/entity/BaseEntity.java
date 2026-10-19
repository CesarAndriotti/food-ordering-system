package com.food.ordering.system.domain.entity;

import java.util.Objects;

// Esta clase es la clase base para todas las entidades del sistema, es una clase generica que recibe
// un tipo de dato generico ID, que es el tipo de dato del id de la entidad
// El objetivo de esta clase es que todas las entidades del sistema tengan un id unico
// y que se pueda comparar con otras entidades
public abstract class BaseEntity<ID> {

    private ID id;
    public ID getId() {
        return id;
    }

    // Este metodo es usado para setear el id de la entidad, se usa en los constructores de las entidades
    public void setId(ID id) {
        this.id = id;
    }

    //Estos metodos son los contratos que se usan para comparar los objetos en java
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


