package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Empleado;
import com.ejemplo.rentacarros.service.dto.EmpleadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empleado} and its DTO {@link EmpleadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpleadoMapper extends EntityMapper<EmpleadoDTO, Empleado> {}
