package com.ejemplo.rentacarros.service.mapper;

import static com.ejemplo.rentacarros.domain.RentaAsserts.*;
import static com.ejemplo.rentacarros.domain.RentaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentaMapperTest {

    private RentaMapper rentaMapper;

    @BeforeEach
    void setUp() {
        rentaMapper = new RentaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentaSample1();
        var actual = rentaMapper.toEntity(rentaMapper.toDto(expected));
        assertRentaAllPropertiesEquals(expected, actual);
    }
}
