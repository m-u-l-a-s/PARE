package dao;

import factory.ConnectionFactory;
import modelo.Avaliacao;
import java.sql.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class AvaliacaoDAO {
    private Connection connection;
    
    public AvaliacaoDAO(){
        this.connection = new ConnectionFactory().getConexaoMySQL();
    }
    
    public void cadastrar(Avaliacao avaliacao){
        String sqlQuery = "INSERT INTO avaliacao(avaliacao_nome,avaliacao_data_final,"
                + "avaliacao_tipo,avaliacao_conceito,sala_id) VALUES(?,?,?,?,?)";
        
        try(PreparedStatement stmt = connection.prepareStatement(sqlQuery,
                Statement.RETURN_GENERATED_KEYS)){
            
                
            
            stmt.setString(1, avaliacao.getAvaliacaoNome());
stmt.setDate(2, java.sql.Date.valueOf(LocalDate.parse(avaliacao.getAvaliacaoDataFinal())));
            stmt.setString(3, avaliacao.getAvaliacaoTipo());
            stmt.setDouble(4, avaliacao.getAvaliacaoConceito());
            stmt.setInt(5,avaliacao.getAvaliacaoSalaId());
            
            stmt.execute();
            
            // Busca ID criado no banco
            ResultSet id = stmt.getGeneratedKeys();
            if (id.next()){
                int idGerado = id.getInt(1);
                avaliacao.setAvaliacaoId(idGerado);
            }
            stmt.close();
        }catch(SQLException u){throw new RuntimeException(u);}
    }
    
    
}
