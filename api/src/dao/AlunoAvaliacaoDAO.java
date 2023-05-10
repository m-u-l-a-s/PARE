package dao;
import factory.ConnectionFactory;
import modelo.AlunoAvaliacao;
import modelo.Avaliacao;
import modelo.Aluno;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import dao.AlunoDAO;
import dao.AvaliacaoDAO;
import java.time.LocalDate;

public class AlunoAvaliacaoDAO {
    private Connection connection;

    public AlunoAvaliacaoDAO(){
        this.connection = new ConnectionFactory().getConexaoMySQL();

    }
    
    public void cadastrarTodasAvaliacoes(Avaliacao avaliacao){
        ArrayList<Integer> idsAlunos = new AlunoDAO().getIDsAlunosSala(avaliacao.getAvaliacaoSalaId());
        for (int i=0; i<idsAlunos.size(); i++){
            Aluno aluno = new Aluno();
            aluno.setAlunoId(idsAlunos.get(i));
                cadastrarAlunoAvaliacao(aluno, avaliacao);
        }
    
    }

    public void cadastrarAlunoAvaliacao(Aluno aluno,Avaliacao avaliacao){
        String sql = "INSERT INTO aluno_avaliacao(avaliacao_id,aluno_id,aluno_avaliacao_data_entrega)"
        + "VALUES(?,?,?);";
        
        try(PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)){
                           
            stmt.setInt(1, avaliacao.getAvaliacaoId());
            stmt.setInt(2, aluno.getAlunoId());
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.parse("9999-12-12")));
            
            stmt.execute();           
            stmt.close();
        }catch(SQLException u){throw new RuntimeException(u);}

    }

    public List<AlunoAvaliacao> buscarTodosAlunoAvaliacao(Avaliacao avaliacao) {
        List<AlunoAvaliacao> ListAlunoAvaliacao = new ArrayList<>();

        String sql = "select * from api.aluno_avaliacao where avaliacao_id = ? order by aluno_avaliacao_data_entrega desc;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, avaliacao.getAvaliacaoId() );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int aluno_avaliacaoid = rs.getInt("aluno_avaliacao_id");
                    //Não esquecer a data
                    float nota = rs.getFloat("aluno_avaliacao_nota");
                    int aluno_id = rs.getInt("aluno_id");
                    int avaliacao_id = rs.getInt("avaliacao_id");
                    String data = rs.getDate("aluno_avaliacao_data_entrega").toString();
                    AlunoAvaliacao alunoavaliacao = new AlunoAvaliacao(aluno_avaliacaoid ,aluno_id,avaliacao_id,data,nota);
                    String nome = new AlunoDAO().getAlunoNome(aluno_id);
                    alunoavaliacao.setAlunoNome(nome);
                    ListAlunoAvaliacao.add(alunoavaliacao);
                    
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ListAlunoAvaliacao;
    }
    
    //Função para retornar os alunos atrasados de acordo com a variavel id da avaliação
    public List<String> AlunosAtrasados(int id)
    {
        List<String> ListAtrasados = new ArrayList<>();

        String sql = "select aluno_avaliacao.aluno_id as Atrasado from api.aluno_avaliacao, api.avaliacao where avaliacao.avaliacao_id = ? and aluno_avaliacao.avaliacao_id = ? and aluno_avaliacao.aluno_avaliacao_data_entrega > avaliacao.avaliacao_data_final;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setInt(1, id );
            stmt.setInt(2, id );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    String AlunoAtrasado = rs.getString("Atrasado");
                    ListAtrasados.add(AlunoAtrasado);
                    
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ListAtrasados;
    }
    
    
    public void UpdateAlunosAvaliacao(AlunoAvaliacao AA)
    {

        String sql = "update api.aluno_avaliacao set aluno_avaliacao_data_entrega = ?, aluno_avaliacao_nota = ? where aluno_avaliacao_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(LocalDate.parse(AA.getAlunoAvaliacaoData())));
            stmt.setFloat(2, AA.getAlunoAvaliacaoNota());
            stmt.setInt(3, AA.getAlunoAvaliacaoId());
            
            stmt.execute();           
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void UpdateTodosAlunoAvaliacao( List<AlunoAvaliacao> ListAlunoAvaliacao)
    {
         for (int i=0; i<ListAlunoAvaliacao.size(); i++){
                UpdateAlunosAvaliacao(ListAlunoAvaliacao.get(i));
        }
    }
}
