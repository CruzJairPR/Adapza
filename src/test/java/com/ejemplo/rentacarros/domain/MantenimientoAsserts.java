package com.ejemplo.rentacarros.domain;

import static com.ejemplo.rentacarros.domain.AssertUtils.bigDecimalCompareTo;
import static com.ejemplo.rentacarros.domain.AssertUtils.zonedDataTimeSameInstant;
import static org.assertj.core.api.Assertions.assertThat;

public class MantenimientoAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMantenimientoAllPropertiesEquals(Mantenimiento expected, Mantenimiento actual) {
        assertMantenimientoAutoGeneratedPropertiesEquals(expected, actual);
        assertMantenimientoAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMantenimientoAllUpdatablePropertiesEquals(Mantenimiento expected, Mantenimiento actual) {
        assertMantenimientoUpdatableFieldsEquals(expected, actual);
        assertMantenimientoUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMantenimientoAutoGeneratedPropertiesEquals(Mantenimiento expected, Mantenimiento actual) {
        assertThat(actual)
            .as("Verify Mantenimiento auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMantenimientoUpdatableFieldsEquals(Mantenimiento expected, Mantenimiento actual) {
        assertThat(actual)
            .as("Verify Mantenimiento relevant properties")
            .satisfies(a -> assertThat(a.getTipo()).as("check tipo").isEqualTo(expected.getTipo()))
            .satisfies(a -> assertThat(a.getDescripcion()).as("check descripcion").isEqualTo(expected.getDescripcion()))
            .satisfies(a ->
                assertThat(a.getFecha()).as("check fecha").usingComparator(zonedDataTimeSameInstant).isEqualTo(expected.getFecha())
            )
            .satisfies(a -> assertThat(a.getCosto()).as("check costo").usingComparator(bigDecimalCompareTo).isEqualTo(expected.getCosto()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertMantenimientoUpdatableRelationshipsEquals(Mantenimiento expected, Mantenimiento actual) {
        assertThat(actual)
            .as("Verify Mantenimiento relationships")
            .satisfies(a -> assertThat(a.getCarro()).as("check carro").isEqualTo(expected.getCarro()));
    }
}
