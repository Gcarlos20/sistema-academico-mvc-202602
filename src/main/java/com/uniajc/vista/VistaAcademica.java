package com.uniajc.vista;

import com.uniajc.controlador.ControladorCurso;
import com.uniajc.controlador.ControladorDocente;
import com.uniajc.controlador.ControladorEstudiante;
import com.uniajc.controlador.ControladorGrupo;
import com.uniajc.controlador.ControladorInscripcion;
import com.uniajc.controlador.ControladorMateria;
import com.uniajc.modelo.Curso;
import com.uniajc.modelo.Docente;
import com.uniajc.modelo.Estudiante;
import com.uniajc.modelo.Grupo;
import com.uniajc.modelo.Inscripcion;
import com.uniajc.modelo.Materia;
import com.uniajc.modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/** Ventana principal con un módulo CRUD para cada entidad académica. */
public class VistaAcademica {
    private static final Color FONDO = new Color(245, 247, 250);
    private static final Color MARINO = new Color(20, 35, 58);
    private static final Color TEXTO = new Color(35, 48, 65);
    private static final Color GRIS = new Color(103, 116, 132);

    private final JFrame ventana = new JFrame("UNIAJC | Sistema académico");
    private final ControladorEstudiante estudiantes = new ControladorEstudiante();
    private final ControladorDocente docentes = new ControladorDocente();
    private final ControladorMateria materias = new ControladorMateria();
    private final ControladorGrupo grupos = new ControladorGrupo();
    private final ControladorInscripcion inscripciones = new ControladorInscripcion();
    private final ControladorCurso cursos = new ControladorCurso();
    // Constructor que inicializa la ventana y los módulos CRUD.
    public VistaAcademica(Usuario usuario) {
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setSize(1100, 650);
        ventana.setMinimumSize(new java.awt.Dimension(900, 580));
        ventana.setLocationRelativeTo(null);
        ventana.getContentPane().setBackground(FONDO);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(MARINO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(13, 22, 13, 22));

        JPanel marca = new JPanel(new GridLayout(2, 1, 0, 3));
        marca.setOpaque(false);
        JLabel nombreMarca = new JLabel("UNIAJC  |  SISTEMA ACADÉMICO");
        nombreMarca.setFont(new Font("SansSerif", Font.BOLD, 16));
        nombreMarca.setForeground(Color.WHITE);
        JLabel detalleMarca = new JLabel("GESTIÓN ACADÉMICA");
        detalleMarca.setFont(new Font("SansSerif", Font.PLAIN, 10));
        detalleMarca.setForeground(new Color(190, 204, 221));
        marca.add(nombreMarca);
        marca.add(detalleMarca);
        encabezado.add(marca, BorderLayout.WEST);

        JPanel sesion = new JPanel(new GridLayout(2, 1, 0, 3));
        sesion.setOpaque(false);
        JLabel nombreUsuario = new JLabel(usuario.nombre());
        nombreUsuario.setFont(new Font("SansSerif", Font.BOLD, 13));
        nombreUsuario.setForeground(Color.WHITE);
        nombreUsuario.setHorizontalAlignment(JLabel.RIGHT);
        JLabel rolUsuario = new JLabel(usuario.rol() == null ? "Sesión activa" : usuario.rol());
        rolUsuario.setFont(new Font("SansSerif", Font.PLAIN, 11));
        rolUsuario.setForeground(new Color(190, 204, 221));
        rolUsuario.setHorizontalAlignment(JLabel.RIGHT);
        sesion.add(nombreUsuario);
        sesion.add(rolUsuario);
        encabezado.add(sesion, BorderLayout.EAST);

        JTabbedPane modulos = new JTabbedPane();
        modulos.setFont(new Font("SansSerif", Font.BOLD, 13));
        modulos.setBackground(Color.WHITE);
        modulos.addTab("Inicio", panelInicio(modulos, usuario));
        modulos.addTab("Estudiantes", panelEstudiantes());
        modulos.addTab("Docentes", panelDocentes());
        modulos.addTab("Materias", panelMaterias());
        modulos.addTab("Cursos", panelCursos());
        modulos.addTab("Grupos", panelGrupos());
        modulos.addTab("Inscripciones", panelInscripciones());

        ventana.add(encabezado, BorderLayout.NORTH);
        ventana.add(modulos, BorderLayout.CENTER);
    }
// Muestra la ventana principal.
    public void mostrar() { ventana.setVisible(true); }

    private JPanel panelInicio(JTabbedPane modulos, Usuario usuario) {
        JPanel inicio = new JPanel(new BorderLayout(0, 20));
        inicio.setBackground(FONDO);
        inicio.setBorder(BorderFactory.createEmptyBorder(24, 26, 26, 26));
// Muestra un mensaje de bienvenida y una descripción de los módulos disponibles.
        JPanel bienvenida = new JPanel(new GridLayout(2, 1, 0, 6));
        bienvenida.setOpaque(false);
        JLabel titulo = new JLabel("Bienvenido/a, " + usuario.nombre());
        titulo.setFont(new Font("SansSerif", Font.BOLD, 25));
        titulo.setForeground(TEXTO);
        JLabel descripcion = new JLabel("Selecciona un módulo para consultar y administrar la información académica.");
        descripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descripcion.setForeground(GRIS);
        bienvenida.add(titulo);
        bienvenida.add(descripcion);
        inicio.add(bienvenida, BorderLayout.NORTH);

        JPanel tarjetas = new JPanel(new GridLayout(2, 3, 14, 14));
        tarjetas.setOpaque(false);
        tarjetas.add(tarjetaModulo(modulos, "Estudiantes", "Consulta los datos y registros de estudiantes.",
                new Color(48, 112, 190)));
        tarjetas.add(tarjetaModulo(modulos, "Docentes", "Administra docentes, especialidades y áreas.",
                new Color(18, 139, 128)));
        tarjetas.add(tarjetaModulo(modulos, "Materias", "Organiza materias y créditos académicos.",
                new Color(132, 93, 184)));
        tarjetas.add(tarjetaModulo(modulos, "Cursos", "Consulta las facultades y cursos registrados.",
                new Color(211, 135, 49)));
        tarjetas.add(tarjetaModulo(modulos, "Grupos", "Gestiona grupos, aulas y horarios.",
                new Color(45, 136, 91)));
        tarjetas.add(tarjetaModulo(modulos, "Inscripciones", "Revisa inscripciones, notas y estados.",
                new Color(191, 83, 91)));
        inicio.add(tarjetas, BorderLayout.CENTER);
        return inicio;
    }
// Crea una tarjeta de módulo con un botón para abrirlo en la pestaña correspondiente.
    private JPanel tarjetaModulo(JTabbedPane modulos, String nombre, String descripcion, Color acento) {
        JPanel tarjeta = new JPanel(new BorderLayout(12, 0));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 231, 238)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JPanel franja = new JPanel();
        franja.setBackground(acento);
        tarjeta.add(franja, BorderLayout.WEST);

        JPanel contenido = new JPanel(new BorderLayout(0, 10));
        contenido.setOpaque(false);
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 6));
        textos.setOpaque(false);
        JLabel titulo = new JLabel(nombre);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 17));
        titulo.setForeground(TEXTO);
        JLabel detalle = new JLabel("<html><body style='width: 190px'>"
                + descripcion + "</body></html>");
        detalle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        detalle.setForeground(GRIS);
        textos.add(titulo);
        textos.add(detalle);

        JButton abrir = new JButton("Abrir módulo");
        abrir.setFont(new Font("SansSerif", Font.BOLD, 12));
        abrir.setForeground(Color.WHITE);
        abrir.setBackground(acento);
        abrir.setOpaque(true);
        abrir.setFocusPainted(false);
        abrir.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 12));
        abrir.addActionListener(event -> modulos.setSelectedIndex(modulos.indexOfTab(nombre)));

        contenido.add(textos, BorderLayout.CENTER);
        contenido.add(abrir, BorderLayout.SOUTH);
        tarjeta.add(contenido, BorderLayout.CENTER);
        return tarjeta;
    }

    private CrudTablePanel<Estudiante> panelEstudiantes() {
        return new CrudTablePanel<>("Estudiantes", "Consulta y administra los datos de las personas registradas.",
                new String[] { "ID", "Nombre", "Apellido", "Correo", "NIT", "Celular" },
                estudiantes::listarEstudiantes,
                e -> new Object[] { e.getId(), e.getName(), e.getLastname(), e.getEmail(), e.getNit(), e.getNumcelular() },
                this::formularioEstudiante,
                estudiantes::crearEstudiante,
                estudiantes::actualizarEstudiante,
                e -> estudiantes.eliminarEstudiante(e.getId()));
    }

    private CrudTablePanel<Docente> panelDocentes() {
        return new CrudTablePanel<>("Docentes", "Organiza la información de docentes y sus áreas académicas.",
                new String[] { "ID", "Nombre", "Especialidad", "Área" },
                docentes::listar,
                d -> new Object[] { d.getIdDocente(), d.getNombre(), d.getEspecialidad(), d.getArea() },
                this::formularioDocente, docentes::crear, docentes::actualizar,
                d -> docentes.eliminar(d.getIdDocente()));
    }

    private CrudTablePanel<Materia> panelMaterias() {
        return new CrudTablePanel<>("Materias", "Administra asignaturas y créditos del programa.",
                new String[] { "ID", "Materia", "Créditos" },
                materias::listar,
                m -> new Object[] { m.getIdMateria(), m.getNombreMateria(), m.getCreditos() },
                this::formularioMateria, materias::crear, materias::actualizar,
                m -> materias.eliminar(m.getIdMateria()));
    }

    private CrudTablePanel<Curso> panelCursos() {
        return new CrudTablePanel<>("Cursos", "Consulta y administra las facultades y cursos registrados.",
                new String[] { "ID", "Facultad" }, cursos::listar,
                c -> new Object[] { c.getId(), c.getFacultad() }, this::formularioCurso,
                cursos::crear, cursos::actualizar, c -> cursos.eliminar(c.getId()));
    }

    private CrudTablePanel<Grupo> panelGrupos() {
        return new CrudTablePanel<>("Grupos", "Gestiona grupos, aulas y horarios de clase.",
                new String[] { "ID", "ID curso", "ID docente", "Aula", "Horario" },
                grupos::listar,
                g -> new Object[] { g.getIdGrupo(), g.getIdMateria(), g.getIdDocente(), g.getAula(), g.getHorario() },
                this::formularioGrupo, grupos::crear, grupos::actualizar,
                g -> grupos.eliminar(g.getIdGrupo()));
    }

    private CrudTablePanel<Inscripcion> panelInscripciones() {
        return new CrudTablePanel<>("Inscripciones", "Revisa estudiantes inscritos, notas y estados.",
                new String[] { "ID", "ID estudiante", "ID grupo", "Nota final", "Estado" },
                inscripciones::listar,
                i -> new Object[] { i.getIdInscripcion(), i.getIdEstudiante(), i.getIdGrupo(), i.getNotaFinal(), i.getEstado() },
                this::formularioInscripcion, inscripciones::crear, inscripciones::actualizar,
                i -> inscripciones.eliminar(i.getIdInscripcion()));
    }

    private Estudiante formularioEstudiante(Estudiante actual) {
        String[] valores = formulario("Estudiante", new String[] { "Nombre", "Apellido", "Correo", "NIT opcional", "Celular opcional" },
                actual == null ? new String[] { "", "", "", "", "" }
                        : new String[] { actual.getName(), actual.getLastname(), actual.getEmail(), texto(actual.getNit()), texto(actual.getNumcelular()) });
        if (valores == null) return null;
        return new Estudiante(valores[0], valores[1], valores[2], numeroOpcional(valores[3], "NIT"), numeroOpcional(valores[4], "Celular"));
    }

    private Docente formularioDocente(Docente actual) {
        String[] valores = formulario("Docente", new String[] { "Nombre", "Especialidad", "Área" },
                actual == null ? new String[] { "", "", "" }
                        : new String[] { actual.getNombre(), actual.getEspecialidad(), actual.getArea() });
        if (valores == null) return null;
        return new Docente(actual == null ? 0L : actual.getIdDocente(), valores[0], valores[1], valores[2]);
    }

    private Materia formularioMateria(Materia actual) {
        String[] valores = formulario("Materia", new String[] { "ID (entero)", "Nombre", "Créditos" },
                actual == null ? new String[] { "", "", "" }
                        : new String[] { String.valueOf(actual.getIdMateria()), actual.getNombreMateria(), String.valueOf(actual.getCreditos()) },
                actual != null ? new boolean[] { false, true, true } : null);
        if (valores == null) return null;
        int id = actual == null ? entero(valores[0], "ID") : actual.getIdMateria();
        return new Materia(id, valores[1], entero(valores[2], "créditos"));
    }

    private Curso formularioCurso(Curso actual) {
        String[] valores = formulario("Curso", new String[] { "Facultad" },
                actual == null ? new String[] { "" } : new String[] { actual.getFacultad() });
        if (valores == null) return null;
        return new Curso(actual == null ? 0L : actual.getId(), valores[0]);
    }

    private Grupo formularioGrupo(Grupo actual) {
        String[] valores = formulario("Grupo", new String[] { "ID del curso", "ID del docente", "Aula", "Horario" },
                actual == null ? new String[] { "", "", "", "" }
                        : new String[] { String.valueOf(actual.getIdMateria()), String.valueOf(actual.getIdDocente()), actual.getAula(), actual.getHorario() });
        if (valores == null) return null;
        return new Grupo(actual == null ? 0L : actual.getIdGrupo(), largo(valores[0], "ID curso"),
                largo(valores[1], "ID docente"), valores[2], valores[3]);
    }

    private Inscripcion formularioInscripcion(Inscripcion actual) {
        String[] valores = formulario("Inscripción", new String[] { "ID del estudiante", "ID del grupo", "Nota (0 a 5)", "Estado" },
                actual == null ? new String[] { "", "", "", "" }
                        : new String[] { String.valueOf(actual.getIdEstudiante()), String.valueOf(actual.getIdGrupo()), String.valueOf(actual.getNotaFinal()), actual.getEstado() });
        if (valores == null) return null;
        double nota;
        try {
            nota = Double.parseDouble(valores[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La nota debe ser un número entre 0 y 5.");
        }
        return new Inscripcion(actual == null ? 0 : actual.getIdInscripcion(), entero(valores[0], "ID estudiante"),
                entero(valores[1], "ID grupo"), nota, valores[3]);
    }

    private String[] formulario(String titulo, String[] etiquetas, String[] valores) {
        return formulario(titulo, etiquetas, valores, null);
    }

    // Muestra campos de edición y devuelve sus valores; un arreglo nulo significa cancelar.
    private String[] formulario(String titulo, String[] etiquetas, String[] valores, boolean[] editables) {
        JPanel panel = new JPanel(new GridLayout(etiquetas.length, 2, 8, 8));
        JTextField[] campos = new JTextField[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            campos[i] = new JTextField(valores[i] == null ? "" : valores[i]);
            if (editables != null) campos[i].setEditable(editables[i]);
            panel.add(new JLabel(etiquetas[i] + ":"));
            panel.add(campos[i]);
        }
        int respuesta = JOptionPane.showConfirmDialog((Component) ventana, panel, titulo,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (respuesta != JOptionPane.OK_OPTION) return null;
        String[] resultado = new String[campos.length];
        for (int i = 0; i < campos.length; i++) resultado[i] = campos[i].getText().trim();
        return resultado;
    }

    private int entero(String texto, String campo) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser un número entero.");
        }
    }

    private long largo(String texto, String campo) {
        try {
            return Long.parseLong(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe ser un número entero.");
        }
    }

    private Long numeroOpcional(String texto, String campo) {
        if (texto.isBlank()) return null;
        try {
            long valor = Long.parseLong(texto.trim());
            if (valor < 0) throw new NumberFormatException();
            return valor;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo " + campo + " debe tener solo dígitos.");
        }
    }

    private String texto(Long valor) { return valor == null ? "" : valor.toString(); }
}
