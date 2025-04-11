package com.ejemplo.rentacarros.repository;

import com.ejemplo.rentacarros.domain.Mantenimiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mantenimiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {}
