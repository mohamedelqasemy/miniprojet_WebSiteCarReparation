package com.ensas.equipementservice.util;

import com.ensas.equipementservice.entities.Equipment;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EquipmentSpecification {
    public static Specification<Equipment> hasName(String search) {
        return (root, query, builder) ->
                (search == null || search.isEmpty()) ? null : builder.like(builder.lower(root.get("name")), "%" + search.toLowerCase() + "%");
    }

    public static Specification<Equipment> hasCar(List<String> cars) {
        return (root, query, builder) ->
                (cars == null || cars.isEmpty()) ? null : root.get("car").in(cars);
    }

    public static Specification<Equipment> hasType(List<String> types) {
        return (root, query, builder) ->
                (types == null || types.isEmpty()) ? null : root.get("type").in(types);
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
