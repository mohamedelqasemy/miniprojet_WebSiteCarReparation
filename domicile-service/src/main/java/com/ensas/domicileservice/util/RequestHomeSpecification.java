package com.ensas.domicileservice.util;

import com.ensas.domicileservice.entities.RequestHome;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class RequestHomeSpecification {
    public static Specification<RequestHome> hasName(String search) {
        return (root, query, builder) -> {
            if (search == null || search.isEmpty()) return null;

            // Join the list of service names
            Join<RequestHome, String> servicesJoin = root.join("serviceNames");

            // Apply a LIKE filter on each element of the list
            return builder.like(builder.lower(servicesJoin), "%" + search.toLowerCase() + "%");
        };
    }
}
