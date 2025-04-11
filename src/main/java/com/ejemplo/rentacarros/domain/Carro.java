package com.ejemplo.rentacarros.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carro.
 */
@Entity
@Table(name = "carro")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Carro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "marca", nullable = false)
    private String marca;

    @NotNull
    @Column(name = "modelo", nullable = false)
    private String modelo;

    @NotNull
    @Column(name = "anio", nullable = false)
    private Integer anio;

    @NotNull
    @Column(name = "placas", nullable = false)
    private String placas;

    @Column(name = "color")
    private String color;

    @Column(name = "tipo")
    private String tipo;

    @NotNull
    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "kilometraje")
    private Integer kilometraje;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "carro" }, allowSetters = true)
    private Set<Ubicacion> ubicaciones = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "carro" }, allowSetters = true)
    private Set<Combustible> combustibles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "carro")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "carro" }, allowSetters = true)
    private Set<Mantenimiento> mantenimientos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carro id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return this.marca;
    }

    public Carro marca(String marca) {
        this.setMarca(marca);
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public Carro modelo(String modelo) {
        this.setModelo(modelo);
        return this;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Integer getAnio() {
        return this.anio;
    }

    public Carro anio(Integer anio) {
        this.setAnio(anio);
        return this;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getPlacas() {
        return this.placas;
    }

    public Carro placas(String placas) {
        this.setPlacas(placas);
        return this;
    }

    public void setPlacas(String placas) {
        this.placas = placas;
    }

    public String getColor() {
        return this.color;
    }

    public Carro color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return this.tipo;
    }

    public Carro tipo(String tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return this.estado;
    }

    public Carro estado(String estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getKilometraje() {
        return this.kilometraje;
    }

    public Carro kilometraje(Integer kilometraje) {
        this.setKilometraje(kilometraje);
        return this;
    }

    public void setKilometraje(Integer kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Set<Ubicacion> getUbicaciones() {
        return this.ubicaciones;
    }

    public void setUbicaciones(Set<Ubicacion> ubicacions) {
        if (this.ubicaciones != null) {
            this.ubicaciones.forEach(i -> i.setCarro(null));
        }
        if (ubicacions != null) {
            ubicacions.forEach(i -> i.setCarro(this));
        }
        this.ubicaciones = ubicacions;
    }

    public Carro ubicaciones(Set<Ubicacion> ubicacions) {
        this.setUbicaciones(ubicacions);
        return this;
    }

    public Carro addUbicaciones(Ubicacion ubicacion) {
        this.ubicaciones.add(ubicacion);
        ubicacion.setCarro(this);
        return this;
    }

    public Carro removeUbicaciones(Ubicacion ubicacion) {
        this.ubicaciones.remove(ubicacion);
        ubicacion.setCarro(null);
        return this;
    }

    public Set<Combustible> getCombustibles() {
        return this.combustibles;
    }

    public void setCombustibles(Set<Combustible> combustibles) {
        if (this.combustibles != null) {
            this.combustibles.forEach(i -> i.setCarro(null));
        }
        if (combustibles != null) {
            combustibles.forEach(i -> i.setCarro(this));
        }
        this.combustibles = combustibles;
    }

    public Carro combustibles(Set<Combustible> combustibles) {
        this.setCombustibles(combustibles);
        return this;
    }

    public Carro addCombustibles(Combustible combustible) {
        this.combustibles.add(combustible);
        combustible.setCarro(this);
        return this;
    }

    public Carro removeCombustibles(Combustible combustible) {
        this.combustibles.remove(combustible);
        combustible.setCarro(null);
        return this;
    }

    public Set<Mantenimiento> getMantenimientos() {
        return this.mantenimientos;
    }

    public void setMantenimientos(Set<Mantenimiento> mantenimientos) {
        if (this.mantenimientos != null) {
            this.mantenimientos.forEach(i -> i.setCarro(null));
        }
        if (mantenimientos != null) {
            mantenimientos.forEach(i -> i.setCarro(this));
        }
        this.mantenimientos = mantenimientos;
    }

    public Carro mantenimientos(Set<Mantenimiento> mantenimientos) {
        this.setMantenimientos(mantenimientos);
        return this;
    }

    public Carro addMantenimientos(Mantenimiento mantenimiento) {
        this.mantenimientos.add(mantenimiento);
        mantenimiento.setCarro(this);
        return this;
    }

    public Carro removeMantenimientos(Mantenimiento mantenimiento) {
        this.mantenimientos.remove(mantenimiento);
        mantenimiento.setCarro(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carro)) {
            return false;
        }
        return getId() != null && getId().equals(((Carro) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carro{" +
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
