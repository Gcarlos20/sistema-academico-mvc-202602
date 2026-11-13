package com.uniajc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uniajc.db.DatabaseConexion;

public class EstudianteDAO {

    public boolean insertar(Estudiante est) {
        String sql = "INSERT INTO estudiante (name, lastname, email, nit, numcelular) VALUES (?, ?, ?, ?, ?)";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, est.getName());
            ps.setString(2, est.getLastname());
            ps.setString(3, est.getEmail());
            ps.setLong(4, est.getNit());
            ps.setLong(5, est.getNumcelular());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar estudiante: " + e.getMessage(), e);
        }
    }

    public List<Estudiante> listarTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT id, name, lastname, email, nit, numcelular FROM estudiante ORDER BY id";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estudiante(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("lastname"),
                    rs.getString("email"),
                    rs.getLong("nit"),
                    rs.getLong("numcelular")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar estudiantes: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean actualizar(Estudiante est) {
        String sql = "UPDATE estudiante SET name = ?, lastname = ?, email = ?, nit = ?, numcelular = ? WHERE id = ?";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, est.getName());
            ps.setString(2, est.getLastname());
            ps.setString(3, est.getEmail());
            ps.setLong(4, est.getNit());
            ps.setLong(5, est.getNumcelular());
            ps.setLong(6, est.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estudiante: " + e.getMessage(), e);
        }
    }

    public boolean eliminar(long id) {
        String sql = "DELETE FROM estudiante WHERE id = ?";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar estudiante: " + e.getMessage(), e);
        }
    }
}

