package dao;

import factory.ConnectionFactory;
import modelo.Avaliacao;
import java.sql.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

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
    
    public String getAvaliacaoNome(int id)
    {
        String sql = "select avaliacao_nome from api.avaliacao where avaliacao_id = ? ";
         String nome = "";
         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    nome = rs.getString("avaliacao_nome");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return nome;
    }
    
    public int getAvaliacaoID(String nome)
    {
        String sql = "select avaliacao_id from api.avaliacao where avaliacao_nome = ?";
         int id = -1;
         try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setString(1, nome );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    id = rs.getInt("avaliacao_id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    public ArrayList<Avaliacao> getAvaliacoesDaSala(int sala_id) {
        String sql = "SELECT * FROM avaliacao WHERE sala_id = ? ORDER BY avaliacao_data_final;";

        ArrayList<Avaliacao> avaliacoes = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, sala_id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int avaliacaoId = rs.getInt("avaliacao_id");
                    String nome = rs.getString("avaliacao_nome");
                    String dataFinal = rs.getDate("avaliacao_data_final").toString();
                    String tipo = rs.getString("avaliacao_tipo");
                    double conceito = rs.getDouble("avaliacao_conceito");
                    int salaId = rs.getInt("sala_id");

                    Avaliacao avaliacao = new Avaliacao(avaliacaoId, nome, dataFinal, tipo, conceito, salaId);
                    if (!avaliacoes.contains(avaliacao)) avaliacoes.add(avaliacao);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return avaliacoes;

    }
    
    public boolean avaliacaoExiste(String nomeAvaliacao) {
        boolean existe = false;
        String sql = "select * from api.avaliacao where avaliacao_nome = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, nomeAvaliacao);

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
