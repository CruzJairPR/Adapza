package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MantenimientoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Mantenimiento getMantenimientoSample1() {
        return new Mantenimiento().id(1L).tipo("tipo1").descripcion("descripcion1");
    }

    public static Mantenimiento getMantenimientoSample2() {
        return new Mantenimiento().id(2L).tipo("tipo2").descripcion("descripcion2");
    }

    public static Mantenimiento getMantenimientoRandomSampleGenerator() {
        return new Mantenimiento()
            .id(longCount.incrementAndGet())
            .tipo(UUID.randomUUID().toString())
            .descripcion(UUID.randomUUID().toString());
    }
}
