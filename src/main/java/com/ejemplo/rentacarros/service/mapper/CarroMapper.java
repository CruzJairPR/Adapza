package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Carro} and its DTO {@link CarroDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarroMapper extends EntityMapper<CarroDTO, Carro> {}
