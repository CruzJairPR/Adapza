package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.domain.Ubicacion;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.dto.UbicacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ubicacion} and its DTO {@link UbicacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface UbicacionMapper extends EntityMapper<UbicacionDTO, Ubicacion> {
    @Mapping(target = "carro", source = "carro", qualifiedByName = "carroId")
    UbicacionDTO toDto(Ubicacion s);

    @Named("carroId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarroDTO toDtoCarroId(Carro carro);
}
