package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.domain.Combustible;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.dto.CombustibleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Combustible} and its DTO {@link CombustibleDTO}.
 */
@Mapper(componentModel = "spring")
public interface CombustibleMapper extends EntityMapper<CombustibleDTO, Combustible> {
    @Mapping(target = "carro", source = "carro", qualifiedByName = "carroId")
    CombustibleDTO toDto(Combustible s);

    @Named("carroId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarroDTO toDtoCarroId(Carro carro);
}
