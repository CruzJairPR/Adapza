package com.ejemplo.rentacarros.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentaDTO.class);
        RentaDTO rentaDTO1 = new RentaDTO();
        rentaDTO1.setId(1L);
        RentaDTO rentaDTO2 = new RentaDTO();
        assertThat(rentaDTO1).isNotEqualTo(rentaDTO2);
        rentaDTO2.setId(rentaDTO1.getId());
        assertThat(rentaDTO1).isEqualTo(rentaDTO2);
        rentaDTO2.setId(2L);
        assertThat(rentaDTO1).isNotEqualTo(rentaDTO2);
        rentaDTO1.setId(null);
        assertThat(rentaDTO1).isNotEqualTo(rentaDTO2);
    }
}
