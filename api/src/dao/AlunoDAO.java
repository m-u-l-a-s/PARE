
package dao;

import factory.ConnectionFactory;
import modelo.Aluno;
import modelo.Sala;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import modelo.Avaliacao;

public class AlunoDAO {
    private Connection connection;
    private Aluno aluno;
    
    
    
    public AlunoDAO(){
    this.connection = new ConnectionFactory().getConexaoMySQL();
    
    }
    
    public void cadastrar(Aluno aluno){
        String sql = "INSERT INTO aluno(aluno_nome,sala_id,aluno_status) VALUES(?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,aluno.getAlunoNome());
            stmt.setInt(2, aluno.getAlunoSalaId());
            stmt.setInt(3,1);
            stmt.execute();
            // Pegar chave primaria gerada pelo banco de dados mysql
            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()){
                int id = keys.getInt(1);
                aluno.setAlunoId(id);
            }
            stmt.close();
        }
        catch(SQLException u){
            throw new RuntimeException(u);
        }
    }
    
    public void cadastrarAlunoEmAvaliacoes(Aluno aluno){
        AvaliacaoDAO avaliacao = new AvaliacaoDAO();
        AlunoAvaliacaoDAO alunoAvaliacao = new AlunoAvaliacaoDAO();
ArrayList<Avaliacao> avaliacoesDaSala = avaliacao.getAvaliacoesDaSala(aluno.getAlunoSalaId());
        
        for(int i = 0; i < avaliacoesDaSala.size();i++){
            alunoAvaliacao.cadastrarAlunoAvaliacao(aluno, avaliacoesDaSala.get(i));
   
        }        
    }
    
    public String getAlunoNome(int id)
    {
         String sql = "select aluno_nome from api.aluno where aluno_id = ?";
         String nome = "";
         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nome = rs.getString("aluno_nome");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nome;
    }

    public ArrayList getIDsAlunosSala(int id_sala) {
        ArrayList<Integer> idsAlunos = new ArrayList<Integer>();
        String sql = "select aluno_id from aluno WHERE sala_id = ? and aluno_status = 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_sala);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    idsAlunos.add(rs.getInt("aluno_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idsAlunos;
    }

  public int inativarAluno(String nome,int idSala) {
    String updateSql = "UPDATE aluno SET aluno_status = 0 WHERE aluno_nome = ? and sala_id = ?";
    String selectSql = "SELECT aluno_id FROM aluno WHERE aluno_nome = ? and sala_id = ?";
    int idAluno = -1;

    try (PreparedStatement updateStmt = connection.prepareStatement(updateSql);
         PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {

        // Execute the update statement
        updateStmt.setString(1, nome);
        updateStmt.setInt(2, idSala);
        updateStmt.executeUpdate();

        // Execute the select statement to retrieve the updated aluno_id
        selectStmt.setString(1, nome);
        selectStmt.setInt(2,idSala);
        try (ResultSet rs = selectStmt.executeQuery()) {
            if (rs.next()) {
                idAluno = rs.getInt("aluno_id");
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    return idAluno;
}
    public boolean alunoExiste(String nomeAluno) {
        boolean existe = false;
        String sql = "select * from api.aluno where aluno_nome = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeAluno);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existe;
    }


    
}

  

    

  
