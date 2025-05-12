package com.ensas.reservationservice.util;

import com.ensas.reservationservice.entities.Reservation;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ReservationSpecification {
    public static Specification<Reservation> hasName(String search) {
        return (root, query, builder) -> {
            if (search == null || search.isEmpty()) return null;

            // Join the list of service names
            Join<Reservation, String> servicesJoin = root.join("serviceNames");

            // Apply a LIKE filter on each element of the list
            return builder.like(builder.lower(servicesJoin), "%" + search.toLowerCase() + "%");
        };
    }
}
