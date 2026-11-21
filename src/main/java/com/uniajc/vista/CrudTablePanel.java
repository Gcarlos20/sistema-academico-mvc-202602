package com.uniajc.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

/** Panel reutilizable de tabla y acciones CRUD para una entidad. */
public final class CrudTablePanel<T> extends JPanel {
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color TEXTO = new Color(35, 48, 65);
    private static final Color GRIS = new Color(103, 116, 132);
    private static final Color AZUL = new Color(37, 99, 175);
    private final Supplier<List<T>> listar;
    private final Function<T, Object[]> convertirFila;
    private final Function<T, T> mostrarFormulario;
    private final Predicate<T> crear;
    private final Predicate<T> actualizar;
    private final Predicate<T> eliminar;
    private final List<T> registros = new ArrayList<>();
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JLabel contador;

    public CrudTablePanel(String titulo, String descripcion, String[] columnas,
                          Supplier<List<T>> listar,
                          Function<T, Object[]> convertirFila,
                          Function<T, T> mostrarFormulario,
                          Predicate<T> crear,
                          Predicate<T> actualizar,
                          Predicate<T> eliminar) {
        super(new BorderLayout(0, 18));
        setBackground(FONDO);
        setBorder(BorderFactory.createEmptyBorder(22, 26, 24, 26));
        this.listar = listar;
        this.convertirFila = convertirFila;
        this.mostrarFormulario = mostrarFormulario;
        this.crear = crear;
        this.actualizar = actualizar;
        this.eliminar = eliminar;

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(38);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setSelectionBackground(new Color(224, 237, 252));
        tabla.setSelectionForeground(TEXTO);
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(true);
        tabla.setGridColor(new Color(232, 236, 242));
        tabla.setIntercellSpacing(new java.awt.Dimension(0, 1));
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getTableHeader().setForeground(GRIS);
        tabla.getTableHeader().setBackground(new Color(248, 250, 252));
        tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 40));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setDefaultRenderer(Object.class, new FilasRenderer());

        JPanel encabezado = new JPanel(new BorderLayout(12, 8));
        encabezado.setOpaque(false);
        JPanel textos = new JPanel(new BorderLayout(0, 5));
        textos.setOpaque(false);
        JLabel tituloModulo = new JLabel(titulo);
        tituloModulo.setFont(new Font("SansSerif", Font.BOLD, 23));
        tituloModulo.setForeground(TEXTO);
        JLabel detalle = new JLabel(descripcion);
        detalle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        detalle.setForeground(GRIS);
        textos.add(tituloModulo, BorderLayout.NORTH);
        textos.add(detalle, BorderLayout.CENTER);

        contador = new JLabel("0 registros");
        contador.setFont(new Font("SansSerif", Font.BOLD, 12));
        contador.setForeground(AZUL);
        contador.setOpaque(true);
        contador.setBackground(new Color(231, 241, 253));
        contador.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        contador.setHorizontalAlignment(SwingConstants.CENTER);
        encabezado.add(textos, BorderLayout.CENTER);
        encabezado.add(contador, BorderLayout.EAST);
        add(encabezado, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(226, 231, 238)));
        scroll.getViewport().setBackground(Color.WHITE);
        JPanel tarjetaTabla = new JPanel(new BorderLayout());
        tarjetaTabla.setBackground(Color.WHITE);
        tarjetaTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 238)),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        tarjetaTabla.add(scroll, BorderLayout.CENTER);
        add(tarjetaTabla, BorderLayout.CENTER);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 9, 0));
        acciones.setOpaque(false);
        JButton nuevo = boton("+  Nuevo registro", AZUL, Color.WHITE);
        nuevo.addActionListener(event -> crearRegistro());
        JButton editar = boton("Editar", Color.WHITE, TEXTO);
        editar.addActionListener(event -> editarRegistro());
        JButton borrar = boton("Eliminar", Color.WHITE, new Color(180, 55, 65));
        borrar.addActionListener(event -> eliminarRegistro());
        JButton recargar = boton("Actualizar", Color.WHITE, AZUL);
        recargar.addActionListener(event -> recargar());
        acciones.add(nuevo);
        acciones.add(editar);
        acciones.add(borrar);
        acciones.add(recargar);
        add(acciones, BorderLayout.SOUTH);
        recargar();
    }

    private JButton boton(String texto, Color fondo, Color frente) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 12));
        boton.setForeground(frente);
        boton.setBackground(fondo);
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(fondo.equals(Color.WHITE) ? new Color(220, 226, 234) : fondo),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)));
        boton.setMargin(new Insets(0, 0, 0, 0));
        return boton;
    }

    private static final class FilasRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
                                                       boolean focus, int row, int column) {
            Component componente = super.getTableCellRendererComponent(table, value, selected, focus, row, column);
            setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 8));
            if (!selected) {
                componente.setBackground(row % 2 == 0 ? Color.WHITE : new Color(249, 251, 253));
                componente.setForeground(TEXTO);
            }
            return componente;
        }
    }

    // Pide la lista al servicio y reemplaza las filas visibles de la tabla.
    private void recargar() {
        try {
            registros.clear();
            registros.addAll(listar.get());
            modelo.setRowCount(0);
            for (T registro : registros) modelo.addRow(convertirFila.apply(registro));
            contador.setText(registros.size() + (registros.size() == 1 ? " registro" : " registros"));
        } catch (RuntimeException error) {
            mostrarError(error);
        }
    }
// Pide al usuario que cree un nuevo registro y lo guarda en la base de datos.
    private void crearRegistro() {
        try {
            T registro = mostrarFormulario.apply(null);
            if (registro != null && crear.test(registro)) {
                recargar();
                JOptionPane.showMessageDialog(this, "Registro creado correctamente.");
            }
        } catch (RuntimeException error) {
            mostrarError(error);
        }
    }
// Pide al usuario que edite el registro seleccionado y lo actualiza en la base de datos.
    private void editarRegistro() {
        T seleccionado = seleccionado();
        if (seleccionado == null) return;
        try {
            T editado = mostrarFormulario.apply(seleccionado);
            if (editado != null && actualizar.test(editado)) {
                recargar();
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
            } else if (editado != null) {
                JOptionPane.showMessageDialog(this, "No se encontró el registro seleccionado.");
            }
        } catch (RuntimeException error) {
            mostrarError(error);
        }
    }
// Pide confirmación y elimina el registro seleccionado.
    private void eliminarRegistro() {
        T seleccionado = seleccionado();
        if (seleccionado == null) return;
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Eliminar el registro seleccionado?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (respuesta != JOptionPane.YES_OPTION) return;
        try {
            if (eliminar.test(seleccionado)) {
                recargar();
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro seleccionado.");
            }
        } catch (RuntimeException error) {
            mostrarError(error);
        }
    }
// Devuelve el registro seleccionado en la tabla, o null si no hay ninguno.
    private T seleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila primero.");
            return null;
        }
        return registros.get(fila);
    }
// Muestra un mensaje de error en un cuadro de diálogo.
    private void mostrarError(RuntimeException error) {
        JOptionPane.showMessageDialog(this, error.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
