package com.ejemplo.rentacarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ejemplo.rentacarros.domain.Mantenimiento} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MantenimientoDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    private String descripcion;

    @NotNull
    private ZonedDateTime fecha;

    private BigDecimal costo;

    private CarroDTO carro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ZonedDateTime getFecha() {
        return fecha;
    }

    public void setFecha(ZonedDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
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
        if (!(o instanceof MantenimientoDTO)) {
            return false;
        }

        MantenimientoDTO mantenimientoDTO = (MantenimientoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, mantenimientoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MantenimientoDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fecha='" + getFecha() + "'" +
            ", costo=" + getCosto() +
            ", carro=" + getCarro() +
            "}";
    }
}
