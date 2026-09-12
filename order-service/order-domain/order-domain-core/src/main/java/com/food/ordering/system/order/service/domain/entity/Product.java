package com.food.ordering.system.order.service.domain.entity;

import com.food.ordering.system.domain.entity.BaseEntity;
import com.food.ordering.system.valueobject.Money;
import com.food.ordering.system.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {

    //Hay campos que se le pueden agregar usando el constructor del padre, o sea el super
    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    private String name;
    private Money price;

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}

