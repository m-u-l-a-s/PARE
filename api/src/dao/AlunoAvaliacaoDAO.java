package dao;
import factory.ConnectionFactory;
import modelo.AlunoAvaliacao;
import modelo.Avaliacao;
import modelo.Aluno;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class AlunoAvaliacaoDAO {
    private Connection connection;

    public AlunoAvaliacaoDAO(){
        this.connection = new ConnectionFactory().getConexaoMySQL();

    }

    public void cadastrarAlunoAvaliacao(Aluno aluno,Avaliacao avalicao){
        String sql = "INSERT INTO aluno_avaliacao(sala_id,avaliacao_id,aluno_id)"
        + "VALUES(?,?,?);";

    }


    
    
}
