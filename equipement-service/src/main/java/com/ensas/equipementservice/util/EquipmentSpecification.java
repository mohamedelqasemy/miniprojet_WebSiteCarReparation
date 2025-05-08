package com.ensas.equipementservice.util;

import com.ensas.equipementservice.entities.Equipment;
import org.springframework.data.jpa.domain.Specification;

public class EquipmentSpecification {

    public static Specification<Equipment> hasCar(String car) {
        return (root, query, builder) ->
                car == null ? null : builder.equal(root.get("car"), car);
    }

    public static Specification<Equipment> hasType(String type) {
        return (root, query, builder) ->
                type == null ? null : builder.equal(root.get("type"), type);
    }

    public static Specification<Equipment> priceBetween(Double minPrice, Double maxPrice) {
        return (root, query, builder) -> {
            if (minPrice == null && maxPrice == null) return null;
            if (minPrice == null) return builder.lessThanOrEqualTo(root.get("price"), maxPrice);
            if (maxPrice == null) return builder.greaterThanOrEqualTo(root.get("price"), minPrice);
            return builder.between(root.get("price"), minPrice, maxPrice);
        };
    }
}

