package com.ejemplo.rentacarros.domain;

import static com.ejemplo.rentacarros.domain.CarroTestSamples.*;
import static com.ejemplo.rentacarros.domain.CombustibleTestSamples.*;
import static com.ejemplo.rentacarros.domain.MantenimientoTestSamples.*;
import static com.ejemplo.rentacarros.domain.UbicacionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carro.class);
        Carro carro1 = getCarroSample1();
        Carro carro2 = new Carro();
        assertThat(carro1).isNotEqualTo(carro2);

        carro2.setId(carro1.getId());
        assertThat(carro1).isEqualTo(carro2);

        carro2 = getCarroSample2();
        assertThat(carro1).isNotEqualTo(carro2);
    }

    @Test
    void ubicacionesTest() {
        Carro carro = getCarroRandomSampleGenerator();
        Ubicacion ubicacionBack = getUbicacionRandomSampleGenerator();

        carro.addUbicaciones(ubicacionBack);
        assertThat(carro.getUbicaciones()).containsOnly(ubicacionBack);
        assertThat(ubicacionBack.getCarro()).isEqualTo(carro);

        carro.removeUbicaciones(ubicacionBack);
        assertThat(carro.getUbicaciones()).doesNotContain(ubicacionBack);
        assertThat(ubicacionBack.getCarro()).isNull();

        carro.ubicaciones(new HashSet<>(Set.of(ubicacionBack)));
        assertThat(carro.getUbicaciones()).containsOnly(ubicacionBack);
        assertThat(ubicacionBack.getCarro()).isEqualTo(carro);

        carro.setUbicaciones(new HashSet<>());
        assertThat(carro.getUbicaciones()).doesNotContain(ubicacionBack);
        assertThat(ubicacionBack.getCarro()).isNull();
    }

    @Test
    void combustiblesTest() {
        Carro carro = getCarroRandomSampleGenerator();
        Combustible combustibleBack = getCombustibleRandomSampleGenerator();

        carro.addCombustibles(combustibleBack);
        assertThat(carro.getCombustibles()).containsOnly(combustibleBack);
        assertThat(combustibleBack.getCarro()).isEqualTo(carro);

        carro.removeCombustibles(combustibleBack);
        assertThat(carro.getCombustibles()).doesNotContain(combustibleBack);
        assertThat(combustibleBack.getCarro()).isNull();

        carro.combustibles(new HashSet<>(Set.of(combustibleBack)));
        assertThat(carro.getCombustibles()).containsOnly(combustibleBack);
        assertThat(combustibleBack.getCarro()).isEqualTo(carro);

        carro.setCombustibles(new HashSet<>());
        assertThat(carro.getCombustibles()).doesNotContain(combustibleBack);
        assertThat(combustibleBack.getCarro()).isNull();
    }

    @Test
    void mantenimientosTest() {
        Carro carro = getCarroRandomSampleGenerator();
        Mantenimiento mantenimientoBack = getMantenimientoRandomSampleGenerator();

        carro.addMantenimientos(mantenimientoBack);
        assertThat(carro.getMantenimientos()).containsOnly(mantenimientoBack);
        assertThat(mantenimientoBack.getCarro()).isEqualTo(carro);

        carro.removeMantenimientos(mantenimientoBack);
        assertThat(carro.getMantenimientos()).doesNotContain(mantenimientoBack);
        assertThat(mantenimientoBack.getCarro()).isNull();

        carro.mantenimientos(new HashSet<>(Set.of(mantenimientoBack)));
        assertThat(carro.getMantenimientos()).containsOnly(mantenimientoBack);
        assertThat(mantenimientoBack.getCarro()).isEqualTo(carro);

        carro.setMantenimientos(new HashSet<>());
        assertThat(carro.getMantenimientos()).doesNotContain(mantenimientoBack);
        assertThat(mantenimientoBack.getCarro()).isNull();
    }
}
