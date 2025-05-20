package com.ensas.userservice.util;

import com.ensas.userservice.entities.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> hasName(String search) {
        return (root, query, builder) -> {
            if (search == null || search.trim().isEmpty()) {
                return null;
            }

            String likePattern = "%" + search.toLowerCase() + "%";

            return builder.or(
                    builder.like(builder.lower(root.get("lastname")), likePattern),
                    builder.like(builder.lower(root.get("firstname")), likePattern)
            );
        };
    }

}
