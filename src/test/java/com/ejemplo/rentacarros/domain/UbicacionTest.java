package com.ejemplo.rentacarros.domain;

import static com.ejemplo.rentacarros.domain.CarroTestSamples.*;
import static com.ejemplo.rentacarros.domain.UbicacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UbicacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ubicacion.class);
        Ubicacion ubicacion1 = getUbicacionSample1();
        Ubicacion ubicacion2 = new Ubicacion();
        assertThat(ubicacion1).isNotEqualTo(ubicacion2);

        ubicacion2.setId(ubicacion1.getId());
        assertThat(ubicacion1).isEqualTo(ubicacion2);

        ubicacion2 = getUbicacionSample2();
        assertThat(ubicacion1).isNotEqualTo(ubicacion2);
    }

    @Test
    void carroTest() {
        Ubicacion ubicacion = getUbicacionRandomSampleGenerator();
        Carro carroBack = getCarroRandomSampleGenerator();

        ubicacion.setCarro(carroBack);
        assertThat(ubicacion.getCarro()).isEqualTo(carroBack);

        ubicacion.carro(null);
        assertThat(ubicacion.getCarro()).isNull();
    }
}
