package com.ejemplo.rentacarros.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ejemplo.rentacarros.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MantenimientoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MantenimientoDTO.class);
        MantenimientoDTO mantenimientoDTO1 = new MantenimientoDTO();
        mantenimientoDTO1.setId(1L);
        MantenimientoDTO mantenimientoDTO2 = new MantenimientoDTO();
        assertThat(mantenimientoDTO1).isNotEqualTo(mantenimientoDTO2);
        mantenimientoDTO2.setId(mantenimientoDTO1.getId());
        assertThat(mantenimientoDTO1).isEqualTo(mantenimientoDTO2);
        mantenimientoDTO2.setId(2L);
        assertThat(mantenimientoDTO1).isNotEqualTo(mantenimientoDTO2);
        mantenimientoDTO1.setId(null);
        assertThat(mantenimientoDTO1).isNotEqualTo(mantenimientoDTO2);
    }
}
