package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RentaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Renta getRentaSample1() {
        return new Renta().id(1L).estado("estado1");
    }

    public static Renta getRentaSample2() {
        return new Renta().id(2L).estado("estado2");
    }

    public static Renta getRentaRandomSampleGenerator() {
        return new Renta().id(longCount.incrementAndGet()).estado(UUID.randomUUID().toString());
    }
}
