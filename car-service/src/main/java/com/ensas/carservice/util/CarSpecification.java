package com.ensas.carservice.util;

import com.ensas.carservice.entities.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {
    public static Specification<Car> hasName(String search) {
        return (root, query, builder) -> {
            if (search == null || search.trim().isEmpty()) {
                return null;
            }

            String likePattern = "%" + search.toLowerCase() + "%";

            return builder.or(
                    builder.like(builder.lower(root.get("licensePlate")), likePattern),
                    builder.like(builder.lower(root.get("brand")), likePattern),
                    builder.like(builder.lower(root.get("model")), likePattern),
                    builder.like(builder.lower(root.get("motorisation")), likePattern)
            );
        };
    }

}
