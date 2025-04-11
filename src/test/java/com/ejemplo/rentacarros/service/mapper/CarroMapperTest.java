package com.ejemplo.rentacarros.service.mapper;

import static com.ejemplo.rentacarros.domain.CarroAsserts.*;
import static com.ejemplo.rentacarros.domain.CarroTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarroMapperTest {

    private CarroMapper carroMapper;

    @BeforeEach
    void setUp() {
        carroMapper = new CarroMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarroSample1();
        var actual = carroMapper.toEntity(carroMapper.toDto(expected));
        assertCarroAllPropertiesEquals(expected, actual);
    }
}
