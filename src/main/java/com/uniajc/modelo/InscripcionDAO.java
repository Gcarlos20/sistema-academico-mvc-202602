package com.uniajc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uniajc.db.DatabaseConexion;

public class InscripcionDAO {

    public boolean insertar(Inscripcion ins) {
        String sql = "INSERT INTO inscripcion (id_estiante, id_grupo, nota_final, estado) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ins.getIdEstudiante());
            ps.setInt(2, ins.getIdGrupo());
            ps.setDouble(3, ins.getNotaFinal());
            ps.setString(4, ins.getEstado());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar inscripción: " + e.getMessage(), e);
        }
    }

    public List<Inscripcion> listarTodos() {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT id_inscripcion, id_estiante, id_grupo, nota_final, estado FROM inscripcion ORDER BY id_inscripcion";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Inscripcion(
                    rs.getInt("id_inscripcion"),
                    rs.getInt("id_estiante"),
                    rs.getInt("id_grupo"),
                    rs.getDouble("nota_final"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar inscripciones: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean actualizarNotaYEstado(int idInscripcion, double notaFinal, String estado) {
        String sql = "UPDATE inscripcion SET nota_final = ?, estado = ? WHERE id_inscripcion = ?";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, notaFinal);
            ps.setString(2, estado);
            ps.setInt(3, idInscripcion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar inscripción: " + e.getMessage(), e);
        }
    }

    public boolean eliminar(int idInscripcion) {
        String sql = "DELETE FROM inscripcion WHERE id_inscripcion = ?";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idInscripcion);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar inscripción: " + e.getMessage(), e);
        }
    }
}

