package com.uniajc.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uniajc.db.DatabaseConexion;

public class GrupoDAO {

    public boolean insertar(Grupo grupo) {
        String sql = "INSERT INTO grupos (id_materia, id_docente, aula, horario) VALUES (?, ?, ?, ?)";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, grupo.getIdMateria());
            ps.setInt(2, grupo.getIdDocente());
            ps.setString(3, grupo.getAula());
            ps.setString(4, grupo.getHorario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar grupo: " + e.getMessage(), e);
        }
    }

    public List<Grupo> listarTodos() {
        List<Grupo> lista = new ArrayList<>();
        String sql = "SELECT id_grupo, id_materia, id_docente, aula, horario FROM grupos ORDER BY id_grupo";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Grupo(
                    rs.getInt("id_grupo"),
                    rs.getInt("id_materia"),
                    rs.getInt("id_docente"),
                    rs.getString("aula"),
                    rs.getString("horario")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar grupos: " + e.getMessage(), e);
        }
        return lista;
    }

    public boolean eliminar(int idGrupo) {
        String sql = "DELETE FROM grupos WHERE id_grupo = ?";
        Connection conn = DatabaseConexion.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idGrupo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar grupo: " + e.getMessage(), e);
        }
    }
}
