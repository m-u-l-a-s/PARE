/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.time.DayOfWeek;
//import static java.time.DayOfWeek.*;
import java.time.LocalDateTime;
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
                    int id = rs.getInt("sala_horario_id");
                    SalaHorario salaHorario = new SalaHorario(salaID, dia, hora);
                    salaHorario.setSalaHorarioID(id);
                    ListSalaHorario.add(salaHorario);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ListSalaHorario;
    }
    
        public String getSalaAtual() {

            // Pegar a hora do sistema:
            DayOfWeek localDay = LocalDateTime.now().getDayOfWeek();
            String diaSemana = "";
            String turma_e_Horario = "";

            switch (localDay) {
                case MONDAY:
                    diaSemana = "Segunda";
                    break;
                case TUESDAY:
                    diaSemana = "Terça";
                    break;
                case WEDNESDAY:
                    diaSemana = "Quarta";
                    break;
                case THURSDAY:
                    diaSemana = "Quinta";
                    break;
                case FRIDAY:
                    diaSemana = "Sexta";
                    break;
                case SATURDAY:
                    diaSemana = "Sábado";
                    break;
                case SUNDAY:
                    diaSemana = "Domingo";
                    break;
            }

            int hora = LocalDateTime.now().getHour();
            String horaFormatada = Integer.toString(hora) + ":00";

            // Pesquisando BD pela disciplina do horário atual:
            String sql = "SELECT sala_hora, sala_nome FROM sala_horario, sala WHERE sala_dia = \"" + diaSemana + ""
                    + "\" AND sala_hora <= \"" + horaFormatada + "\" AND sala.sala_id = sala_horario.sala_id ORDER BY sala_hora DESC;";

            String salaNome = "";
            try (
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery(sql)) {

                if (rs.next()) {
                    salaNome = rs.getString("sala_nome");
                    Time horaDisciplina = rs.getTime("sala_hora");
                }

            } catch (SQLException e) {
                System.out.println(e);
            }

            return salaNome;
        }

    public void deleteSalaHorario(int id){
        String sqlQueryHorario = "delete from api.sala_horario where sala_horario_id = ?";  
        try (PreparedStatement stmtHorario = connection.prepareStatement(sqlQueryHorario)){
            stmtHorario.setInt(1, id);
            stmtHorario.execute();
            stmtHorario.close();
        } catch(SQLException u){
            throw new RuntimeException(u);
        }
    }

}
