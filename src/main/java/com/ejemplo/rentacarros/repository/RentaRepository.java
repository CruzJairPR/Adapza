package com.ejemplo.rentacarros.repository;

import com.ejemplo.rentacarros.domain.Renta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Renta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {}
