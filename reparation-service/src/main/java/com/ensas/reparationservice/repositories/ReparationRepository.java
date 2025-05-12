package com.ensas.reparationservice.repositories;

import com.ensas.reparationservice.entities.Reparation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReparationRepository extends JpaRepository<Reparation,Long> {
    Page<Reparation> findByTypeIgnoreCase(String type, Pageable pageable);

    // Sans filtre de type
    @Query("SELECT r FROM Reparation r WHERE " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reparation> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // Avec filtre de type
    @Query("SELECT r FROM Reparation r WHERE " +
            "LOWER(r.type) = LOWER(:type) AND " +
            "(LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Reparation> searchByKeywordAndType(@Param("keyword") String keyword,
                                            @Param("type") String type,
                                            Pageable pageable);

    // Recherche combinée (identique à ci-dessus mais plus courte)
    @Query("SELECT r FROM Reparation r WHERE LOWER(r.type) = LOWER(:type) AND " +
            "(LOWER(r.name) LIKE LOWER(CONCAT('%', :kw, '%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%', :kw, '%')))")
    Page<Reparation> search(@Param("type") String type,
                            @Param("kw") String keyword,
                            Pageable pageable);
}
