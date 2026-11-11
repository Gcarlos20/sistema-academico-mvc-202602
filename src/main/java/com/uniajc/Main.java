package com.uniajc;

import com.uniajc.db.DatabaseConexion;
import com.uniajc.vista.VistaEstudiante;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema Académico!");

        DatabaseConexion.getConnection();
        SwingUtilities.invokeLater(() -> new VistaEstudiante().mostrar());
    }
}