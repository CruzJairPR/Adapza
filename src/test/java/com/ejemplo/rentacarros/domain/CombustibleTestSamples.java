package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CombustibleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Combustible getCombustibleSample1() {
        return new Combustible().id(1L).tipo("tipo1");
    }

    public static Combustible getCombustibleSample2() {
        return new Combustible().id(2L).tipo("tipo2");
    }

    public static Combustible getCombustibleRandomSampleGenerator() {
        return new Combustible().id(longCount.incrementAndGet()).tipo(UUID.randomUUID().toString());
    }
}
