package com.ejemplo.rentacarros.service.mapper;

import static com.ejemplo.rentacarros.domain.MantenimientoAsserts.*;
import static com.ejemplo.rentacarros.domain.MantenimientoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MantenimientoMapperTest {

    private MantenimientoMapper mantenimientoMapper;

    @BeforeEach
    void setUp() {
        mantenimientoMapper = new MantenimientoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMantenimientoSample1();
        var actual = mantenimientoMapper.toEntity(mantenimientoMapper.toDto(expected));
        assertMantenimientoAllPropertiesEquals(expected, actual);
    }
}
