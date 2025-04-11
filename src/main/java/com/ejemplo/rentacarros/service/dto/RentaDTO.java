package com.ejemplo.rentacarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ejemplo.rentacarros.domain.Renta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime fechaInicio;

    private ZonedDateTime fechaFin;

    @NotNull
    private BigDecimal precioTotal;

    @NotNull
    private String estado;

    private CarroDTO carro;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(ZonedDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public ZonedDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(ZonedDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(BigDecimal precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public CarroDTO getCarro() {
        return carro;
    }

    public void setCarro(CarroDTO carro) {
        this.carro = carro;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentaDTO)) {
            return false;
        }

        RentaDTO rentaDTO = (RentaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentaDTO{" +
            "id=" + getId() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", precioTotal=" + getPrecioTotal() +
            ", estado='" + getEstado() + "'" +
            ", carro=" + getCarro() +
            ", cliente=" + getCliente() +
            "}";
    }
}
