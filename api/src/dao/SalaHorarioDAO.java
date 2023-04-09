/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.SalaHorario;
import modelo.Sala;

public class SalaHorarioDAO {
    
    private Connection connection;
    
    public SalaHorarioDAO(){
        this.connection = new ConnectionFactory().getConexaoMySQL();
    }
    
    public void CadastrarSalaHorario(SalaHorario salaHorario)
    {
        String sqlQueryHorario = "INSERT INTO sala_horario(sala_hora,sala_dia,sala_id)"
                + " VALUES(?,?,?)";  
        try (PreparedStatement stmtHorario = connection.prepareStatement(sqlQueryHorario)){
            stmtHorario.setString(1, salaHorario.getSalaHorarioHora());
            stmtHorario.setString(2, salaHorario.getSalaHorarioDia());
            stmtHorario.setInt(3, salaHorario.getSalaID());
            stmtHorario.execute();
            stmtHorario.close();
        } catch(SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public List<SalaHorario> buscarTodosHorarios(Sala sala) {
        List<SalaHorario> ListSalaHorario = new ArrayList<>();

        String sql = "select * from api.sala_horario where sala_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int salaID = new SalaDAO().getSalaId(sala.getSalaNome());
            stmt.setInt(1, salaID );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String dia = rs.getString("sala_dia");
                    String hora = rs.getString("sala_hora");
                    SalaHorario salaHorario = new SalaHorario(salaID, dia, hora);
                    ListSalaHorario.add(salaHorario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ListSalaHorario;
    }
    
}
