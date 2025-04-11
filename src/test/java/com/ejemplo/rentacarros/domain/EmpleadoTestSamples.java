package com.ejemplo.rentacarros.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpleadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Empleado getEmpleadoSample1() {
        return new Empleado().id(1L).nombre("nombre1").puesto("puesto1").correo("correo1").telefono("telefono1");
    }

    public static Empleado getEmpleadoSample2() {
        return new Empleado().id(2L).nombre("nombre2").puesto("puesto2").correo("correo2").telefono("telefono2");
    }

    public static Empleado getEmpleadoRandomSampleGenerator() {
        return new Empleado()
            .id(longCount.incrementAndGet())
            .nombre(UUID.randomUUID().toString())
            .puesto(UUID.randomUUID().toString())
            .correo(UUID.randomUUID().toString())
            .telefono(UUID.randomUUID().toString());
    }
}
