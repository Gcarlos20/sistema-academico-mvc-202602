package com.uniajc.vista;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CrudTablePanelTest {
    @Test
    void normalizaMayusculasYAcentos() {
        assertEquals("ingenieria de sistemas", CrudTablePanel.normalizar("INGENIERÍA  DE Sistemas"));
        assertEquals("jose lopez", CrudTablePanel.normalizar("José López"));
    }

    @Test
    void puedeBuscarConPalabrasIniciales() {
        String texto = CrudTablePanel.normalizar("Carlos Andrés Martínez");
        assertTrue(texto.contains(CrudTablePanel.normalizar("car")));
        assertTrue(texto.contains(CrudTablePanel.normalizar("mar")));
    }
}
