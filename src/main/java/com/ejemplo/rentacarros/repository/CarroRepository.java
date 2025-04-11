package com.ejemplo.rentacarros.repository;

import com.ejemplo.rentacarros.domain.Carro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Carro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {}
