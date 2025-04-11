package com.ejemplo.rentacarros.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.ejemplo.rentacarros.domain.Ubicacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UbicacionDTO implements Serializable {

    private Long id;

    @NotNull
    private Double latitud;

    @NotNull
    private Double longitud;

    @NotNull
    private ZonedDateTime timestamp;

    private CarroDTO carro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
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
        if (!(o instanceof UbicacionDTO)) {
            return false;
        }

        UbicacionDTO ubicacionDTO = (UbicacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ubicacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UbicacionDTO{" +
            "id=" + getId() +
            ", latitud=" + getLatitud() +
            ", longitud=" + getLongitud() +
            ", timestamp='" + getTimestamp() + "'" +
            ", carro=" + getCarro() +
            "}";
    }
}
