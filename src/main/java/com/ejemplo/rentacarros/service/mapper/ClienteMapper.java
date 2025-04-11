package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Cliente;
import com.ejemplo.rentacarros.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {}
