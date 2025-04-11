package com.ejemplo.rentacarros.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Combustible.
 */
@Entity
@Table(name = "combustible")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Combustible implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nivel_actual", nullable = false)
    private Float nivelActual;

    @Column(name = "tipo")
    private String tipo;

    @NotNull
    @Column(name = "fecha_registro", nullable = false)
    private ZonedDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "ubicaciones", "combustibles", "mantenimientos" }, allowSetters = true)
    private Carro carro;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Combustible id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getNivelActual() {
        return this.nivelActual;
    }

    public Combustible nivelActual(Float nivelActual) {
        this.setNivelActual(nivelActual);
        return this;
    }

    public void setNivelActual(Float nivelActual) {
        this.nivelActual = nivelActual;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Combustible tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ZonedDateTime getFechaRegistro() {
        return this.fechaRegistro;
    }

    public Combustible fechaRegistro(ZonedDateTime fechaRegistro) {
        this.setFechaRegistro(fechaRegistro);
        return this;
    }

    public void setFechaRegistro(ZonedDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Carro getCarro() {
        return this.carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Combustible carro(Carro carro) {
        this.setCarro(carro);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Combustible)) {
            return false;
        }
        return getId() != null && getId().equals(((Combustible) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Combustible{" +
            "id=" + getId() +
            ", nivelActual=" + getNivelActual() +
            ", tipo='" + getTipo() + "'" +
            ", fechaRegistro='" + getFechaRegistro() + "'" +
            "}";
    }
}
