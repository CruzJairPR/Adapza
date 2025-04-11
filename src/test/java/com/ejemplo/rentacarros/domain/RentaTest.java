package com.ejemplo.rentacarros.domain;

import static com.ejemplo.rentacarros.domain.CarroTestSamples.*;
import static com.ejemplo.rentacarros.domain.ClienteTestSamples.*;
import static com.ejemplo.rentacarros.domain.RentaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Renta.class);
        Renta renta1 = getRentaSample1();
        Renta renta2 = new Renta();
        assertThat(renta1).isNotEqualTo(renta2);

        renta2.setId(renta1.getId());
        assertThat(renta1).isEqualTo(renta2);

        renta2 = getRentaSample2();
        assertThat(renta1).isNotEqualTo(renta2);
    }

    @Test
    void carroTest() {
        Renta renta = getRentaRandomSampleGenerator();
        Carro carroBack = getCarroRandomSampleGenerator();

        renta.setCarro(carroBack);
        assertThat(renta.getCarro()).isEqualTo(carroBack);

        renta.carro(null);
        assertThat(renta.getCarro()).isNull();
    }

    @Test
    void clienteTest() {
        Renta renta = getRentaRandomSampleGenerator();
        Cliente clienteBack = getClienteRandomSampleGenerator();

        renta.setCliente(clienteBack);
        assertThat(renta.getCliente()).isEqualTo(clienteBack);

        renta.cliente(null);
        assertThat(renta.getCliente()).isNull();
    }
}
