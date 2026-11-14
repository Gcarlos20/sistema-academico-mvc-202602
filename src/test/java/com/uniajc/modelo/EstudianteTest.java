package com.uniajc.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EstudianteTest {
    @Test
    void constructorInicializaLosDatosDelEstudiante() {
        Estudiante estudiante = new Estudiante(12L, "Ana", "Pérez", "ana@example.com", 123456L, 3001234567L);

        assertEquals(12L, estudiante.getId());
        assertEquals("Ana", estudiante.getName());
        assertEquals("Pérez", estudiante.getLastname());
        assertEquals("ana@example.com", estudiante.getEmail());
        assertEquals(123456L, estudiante.getNit());
        assertEquals(3001234567L, estudiante.getNumcelular());
    }
}
