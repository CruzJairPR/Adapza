package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CarroTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Carro getCarroSample1() {
        return new Carro()
            .id(1L)
            .marca("marca1")
            .modelo("modelo1")
            .anio(1)
            .placas("placas1")
            .color("color1")
            .tipo("tipo1")
            .estado("estado1")
            .kilometraje(1);
    }

    public static Carro getCarroSample2() {
        return new Carro()
            .id(2L)
            .marca("marca2")
            .modelo("modelo2")
            .anio(2)
            .placas("placas2")
            .color("color2")
            .tipo("tipo2")
            .estado("estado2")
            .kilometraje(2);
    }

    public static Carro getCarroRandomSampleGenerator() {
        return new Carro()
            .id(longCount.incrementAndGet())
            .marca(UUID.randomUUID().toString())
            .modelo(UUID.randomUUID().toString())
            .anio(intCount.incrementAndGet())
            .placas(UUID.randomUUID().toString())
            .color(UUID.randomUUID().toString())
            .tipo(UUID.randomUUID().toString())
            .estado(UUID.randomUUID().toString())
            .kilometraje(intCount.incrementAndGet());
    }
}
