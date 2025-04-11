package com.ejemplo.rentacarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ejemplo.rentacarros.domain.Carro} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarroDTO implements Serializable {

    private Long id;

    @NotNull
    private String marca;

    @NotNull
    private String modelo;

    @NotNull
    private Integer anio;

    @NotNull
    private String placas;

    private String color;

    private String tipo;

    @NotNull
    private String estado;

    private Integer kilometraje;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getPlacas() {
        return placas;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarroDTO)) {
            return false;
        }

        CarroDTO carroDTO = (CarroDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carroDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarroDTO{" +
            "id=" + getId() +
            ", marca='" + getMarca() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", anio=" + getAnio() +
            ", placas='" + getPlacas() + "'" +
            ", color='" + getColor() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", estado='" + getEstado() + "'" +
            ", kilometraje=" + getKilometraje() +
            "}";
    }
}
