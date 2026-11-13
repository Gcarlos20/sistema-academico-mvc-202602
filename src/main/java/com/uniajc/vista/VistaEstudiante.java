package com.uniajc.vista;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class VistaEstudiante {
    private final JFrame ventana;
    private final DefaultTableModel modeloTabla;

    public VistaEstudiante() {
        ventana = new JFrame("Sistema Académico - Estudiantes");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(700, 400);
        ventana.setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Listado de estudiantes", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        modeloTabla = new DefaultTableModel(
                new String[] { "ID", "Nombre", "Apellido", "Correo electrónico" }, 0);
        cargarEstudiantes();
        JTable tablaEstudiantes = new JTable(modeloTabla);
        tablaEstudiantes.setFillsViewportHeight(true);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(new JScrollPane(tablaEstudiantes), BorderLayout.CENTER);
        contenido.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));

        ventana.add(contenido);
    }

    public void mostrar() {
        ventana.setVisible(true);
    }

    private void cargarEstudiantes() {
        // Aquí se solicitarán los estudiantes al controlador; la consulta SQL debe ir en el DAO.
        // Luego se agregan las filas recibidas al modeloTabla.
    }
}
