package com.ejemplo.rentacarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ejemplo.rentacarros.domain.Combustible} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CombustibleDTO implements Serializable {

    private Long id;

    @NotNull
    private Float nivelActual;

    private String tipo;

    @NotNull
    private ZonedDateTime fechaRegistro;

    private CarroDTO carro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNivelActual() {
        return nivelActual;
    }

    public void setNivelActual(Float nivelActual) {
        this.nivelActual = nivelActual;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(ZonedDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public CarroDTO getCarro() {
        return carro;
    }

    public void setCarro(CarroDTO carro) {
        this.carro = carro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CombustibleDTO)) {
            return false;
        }

        CombustibleDTO combustibleDTO = (CombustibleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, combustibleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CombustibleDTO{" +
            "id=" + getId() +
            ", nivelActual=" + getNivelActual() +
            ", tipo='" + getTipo() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            ", carro=" + getCarro() +
            "}";
    }
}
