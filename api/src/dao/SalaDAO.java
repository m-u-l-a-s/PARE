package dao;

import factory.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Aluno;
import modelo.Sala;

public class SalaDAO {
    private Connection connection;
    
    public SalaDAO(){
        this.connection = new ConnectionFactory().getConexaoMySQL();
    }
    
    public void cadastrarSala(Sala sala){
        String sqlQuerySala = "INSERT INTO sala(sala_nome) VALUES(?)";
        try (PreparedStatement stmtSala = connection.prepareStatement(sqlQuerySala, 
                Statement.RETURN_GENERATED_KEYS)){
            stmtSala.setString(1, sala.getSalaNome());
            stmtSala.execute();
            // Busca ID criado no banco
            ResultSet id = stmtSala.getGeneratedKeys();
            if (id.next()){
                int idGerado = id.getInt(1);
                sala.setSalaId(idGerado);
            }
            stmtSala.close();
        } catch(SQLException u){
            throw new RuntimeException(u);
        }
        
          //Alexandre - 27/03/2023 - comentando código para inserir na tabela  salahorario agora que a classe salahorario já foi feita
//        String sqlQueryHorario = "INSERT INTO sala_horario(sala_hora,sala_dia,sala_id)"
//                + " VALUES(?,?,?)";  
//        try (PreparedStatement stmtHorario = connection.prepareStatement(sqlQueryHorario)){
//            stmtHorario.setString(1, sala.getSalaHorario());
//            stmtHorario.setString(2, sala.getSalaDia());
//            stmtHorario.setInt(3, sala.getSalaId());
//            stmtHorario.execute();
//            stmtHorario.close();
//        } catch(SQLException u){
//            throw new RuntimeException(u);
//        }
    }
    
       //Michael - 13/04/2023 - Função para puxar nome de todas as salas e mostrar no comboxBox

    public List<String> buscarTodasSalas(){
        List<String> salasNome = new ArrayList<>();
        
        String sql = "select distinct sala_nome from api.sala order by sala_nome;";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String sala = rs.getString("sala_nome");
                    salasNome.add(sala);
                }
            }
        } catch (SQLException e){throw new RuntimeException(e);
        
        }
        return salasNome;
    
}
    
    public List<String> buscarTodosAlunos(Sala sala) {
        List<String> studentNames = new ArrayList<>();

        String sql = "select aluno_nome from api.aluno where sala_id = ? and aluno_status = ? ;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sala.getSalaId());
            stmt.setString(2,"M");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nome = rs.getString("aluno_nome");
                    studentNames.add(nome);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return studentNames;
    }
    
    public String buscarAluno(Sala sala,Aluno aluno){
        String sql =" SELECT aluno_nome from aluno join sala on sala_nome = ? "
                + "where aluno_nome = ?;";
        String nome;

        
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, sala.getSalaNome());
            stmt.setString(2, aluno.getAlunoNome());
           
            
            try(ResultSet rs = stmt.executeQuery()){
                 rs.next();
                 nome = rs.getString("aluno_nome");
                 stmt.close();
            }
            
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
        return nome;
        
    }
    
    //Alexandre - 27/03/2023 - Função para puxar o ID de uma sala a partir do nome
    public int getSalaId(String SalaNome)
    {
        String sql =" SELECT sala_id from sala where sala_nome = ?;";
        
        int salaID;

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, SalaNome); 
            
            try(ResultSet rs = stmt.executeQuery()){
                 rs.next();
                 salaID = rs.getInt("sala_id");
                 stmt.close();
            }
            
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
        return salaID;
    }    
}



    