package com.ejemplo.rentacarros.service.mapper;

import com.ejemplo.rentacarros.domain.Carro;
import com.ejemplo.rentacarros.domain.Cliente;
import com.ejemplo.rentacarros.domain.Renta;
import com.ejemplo.rentacarros.service.dto.CarroDTO;
import com.ejemplo.rentacarros.service.dto.ClienteDTO;
import com.ejemplo.rentacarros.service.dto.RentaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Renta} and its DTO {@link RentaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentaMapper extends EntityMapper<RentaDTO, Renta> {
    @Mapping(target = "carro", source = "carro", qualifiedByName = "carroId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteId")
    RentaDTO toDto(Renta s);

    @Named("carroId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarroDTO toDtoCarroId(Carro carro);

    @Named("clienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteDTO toDtoClienteId(Cliente cliente);
}
