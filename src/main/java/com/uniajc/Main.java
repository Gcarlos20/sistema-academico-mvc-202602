package com.uniajc;

import com.uniajc.db.DatabaseConexion;
import com.uniajc.vista.VistaEstudiante;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al Sistema Académico!");

        // Abre la conexión con las credenciales configuradas antes de mostrar la interfaz.
        DatabaseConexion.getConnection();
        // Crea y muestra la ventana desde el hilo de eventos de Swing.
        SwingUtilities.invokeLater(() -> new VistaEstudiante().mostrar());
    }
}
