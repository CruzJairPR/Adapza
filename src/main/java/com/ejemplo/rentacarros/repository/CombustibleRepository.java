package com.ejemplo.rentacarros.repository;

import com.ejemplo.rentacarros.domain.Combustible;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Combustible entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CombustibleRepository extends JpaRepository<Combustible, Long> {}
