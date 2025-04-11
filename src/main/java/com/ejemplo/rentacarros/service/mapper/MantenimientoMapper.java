package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.domain.Mantenimiento;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.dto.MantenimientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mantenimiento} and its DTO {@link MantenimientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MantenimientoMapper extends EntityMapper<MantenimientoDTO, Mantenimiento> {
    @Mapping(target = "carro", source = "carro", qualifiedByName = "carroId")
    MantenimientoDTO toDto(Mantenimiento s);

    @Named("carroId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarroDTO toDtoCarroId(Carro carro);
}
