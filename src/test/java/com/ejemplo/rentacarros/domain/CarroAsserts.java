package com.ejemplo.rentacarros.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CarroAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarroAllPropertiesEquals(Carro expected, Carro actual) {
        assertCarroAutoGeneratedPropertiesEquals(expected, actual);
        assertCarroAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarroAllUpdatablePropertiesEquals(Carro expected, Carro actual) {
        assertCarroUpdatableFieldsEquals(expected, actual);
        assertCarroUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarroAutoGeneratedPropertiesEquals(Carro expected, Carro actual) {
        assertThat(actual)
            .as("Verify Carro auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarroUpdatableFieldsEquals(Carro expected, Carro actual) {
        assertThat(actual)
            .as("Verify Carro relevant properties")
            .satisfies(a -> assertThat(a.getMarca()).as("check marca").isEqualTo(expected.getMarca()))
            .satisfies(a -> assertThat(a.getModelo()).as("check modelo").isEqualTo(expected.getModelo()))
            .satisfies(a -> assertThat(a.getAnio()).as("check anio").isEqualTo(expected.getAnio()))
            .satisfies(a -> assertThat(a.getPlacas()).as("check placas").isEqualTo(expected.getPlacas()))
            .satisfies(a -> assertThat(a.getColor()).as("check color").isEqualTo(expected.getColor()))
            .satisfies(a -> assertThat(a.getTipo()).as("check tipo").isEqualTo(expected.getTipo()))
            .satisfies(a -> assertThat(a.getEstado()).as("check estado").isEqualTo(expected.getEstado()))
            .satisfies(a -> assertThat(a.getKilometraje()).as("check kilometraje").isEqualTo(expected.getKilometraje()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCarroUpdatableRelationshipsEquals(Carro expected, Carro actual) {
        // empty method
    }
}
