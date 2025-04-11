package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UbicacionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ubicacion getUbicacionSample1() {
        return new Ubicacion().id(1L);
    }

    public static Ubicacion getUbicacionSample2() {
        return new Ubicacion().id(2L);
    }

    public static Ubicacion getUbicacionRandomSampleGenerator() {
        return new Ubicacion().id(longCount.incrementAndGet());
    }
}
