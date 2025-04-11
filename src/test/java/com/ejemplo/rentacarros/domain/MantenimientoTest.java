package com.ejemplo.rentacarros.domain;

import static com.ejemplo.rentacarros.domain.CarroTestSamples.*;
import static com.ejemplo.rentacarros.domain.MantenimientoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MantenimientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mantenimiento.class);
        Mantenimiento mantenimiento1 = getMantenimientoSample1();
        Mantenimiento mantenimiento2 = new Mantenimiento();
        assertThat(mantenimiento1).isNotEqualTo(mantenimiento2);

        mantenimiento2.setId(mantenimiento1.getId());
        assertThat(mantenimiento1).isEqualTo(mantenimiento2);

        mantenimiento2 = getMantenimientoSample2();
        assertThat(mantenimiento1).isNotEqualTo(mantenimiento2);
    }

    @Test
    void carroTest() {
        Mantenimiento mantenimiento = getMantenimientoRandomSampleGenerator();
        Carro carroBack = getCarroRandomSampleGenerator();

        mantenimiento.setCarro(carroBack);
        assertThat(mantenimiento.getCarro()).isEqualTo(carroBack);

        mantenimiento.carro(null);
        assertThat(mantenimiento.getCarro()).isNull();
    }
}
