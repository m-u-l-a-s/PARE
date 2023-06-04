package dao;

import factory.ConnectionFactory;
import modelo.AlunoAvaliacao;
import modelo.Avaliacao;
import modelo.Aluno;
import java.sql.*;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import dao.AlunoDAO;
import dao.AvaliacaoDAO;
import java.time.LocalDate;
import java.util.Objects;

import modelo.Sala;

public class AlunoAvaliacaoDAO {

    private Connection connection;

    public AlunoAvaliacaoDAO() {
        this.connection = new ConnectionFactory().getConexaoMySQL();

    }

    public void cadastrarTodasAvaliacoes(Avaliacao avaliacao) {
        ArrayList<Integer> idsAlunos = new AlunoDAO().getIDsAlunosSala(avaliacao.getAvaliacaoSalaId());
        for (int i = 0; i < idsAlunos.size(); i++) {
            Aluno aluno = new Aluno();
            aluno.setAlunoId(idsAlunos.get(i));
            cadastrarAlunoAvaliacao(aluno, avaliacao);
        }

    }

    public void cadastrarAlunoAvaliacao(Aluno aluno, Avaliacao avaliacao) {
        String sql = "INSERT INTO aluno_avaliacao(avaliacao_id,aluno_id,aluno_avaliacao_data_entrega,aluno_avaliacao_status)"
                + "VALUES(?,?,?,?);";

        try (PreparedStatement stmt = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, avaliacao.getAvaliacaoId());
            stmt.setInt(2, aluno.getAlunoId());
            stmt.setDate(3, java.sql.Date.valueOf(LocalDate.parse("9999-12-12")));
            stmt.setInt(4, 1);

            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    public void inativarAlunoAvaliacao(int alunoId) {
        String sql = "UPDATE aluno_avaliacao SET aluno_avaliacao_status = 0 WHERE aluno_id = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, alunoId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AlunoAvaliacao> buscarTodosAlunoAvaliacao(Avaliacao avaliacao) {
        List<AlunoAvaliacao> ListAlunoAvaliacao = new ArrayList<>();

        String sql = "select * from api.aluno_avaliacao where avaliacao_id = ? and aluno_avaliacao_status = 1 order by aluno_avaliacao_data_entrega desc;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, avaliacao.getAvaliacaoId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int aluno_avaliacaoid = rs.getInt("aluno_avaliacao_id");
                    //Não esquecer a data
                    float nota = rs.getFloat("aluno_avaliacao_nota");
                    int aluno_id = rs.getInt("aluno_id");
                    int avaliacao_id = rs.getInt("avaliacao_id");
                    String data = rs.getDate("aluno_avaliacao_data_entrega").toString();
                    AlunoAvaliacao alunoavaliacao = new AlunoAvaliacao(aluno_avaliacaoid, aluno_id, avaliacao_id, data, nota);
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
    public List<String> AlunosAtrasados(int id) {
        List<String> ListAtrasados = new ArrayList<>();

        String sql = "select aluno_avaliacao.aluno_id as Atrasado from "
                + "api.aluno_avaliacao, api.avaliacao where avaliacao.avaliacao_id = ? and aluno_avaliacao_status = ? and aluno_avaliacao.avaliacao_id = ? and aluno_avaliacao.aluno_avaliacao_data_entrega > avaliacao.avaliacao_data_final;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setInt(2, 1);
            stmt.setInt(3, id);

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

    public void UpdateAlunosAvaliacao(AlunoAvaliacao AA) {

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

    public void UpdateTodosAlunoAvaliacao(List<AlunoAvaliacao> ListAlunoAvaliacao) {
        for (int i = 0; i < ListAlunoAvaliacao.size(); i++) {
            UpdateAlunosAvaliacao(ListAlunoAvaliacao.get(i));
        }
    }

    // Converter data yyyy-mm-dd para dd/mm/yyyy:
    public static String formataData(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String outputDate = date.format(outputFormatter);
        //System.out.println(inputDate + "==> " + outputDate);
        return outputDate;
    }
    // Converter data dd/mm/yyyy para yyyy-mm-dd:
    public static String desformataData(String inputDate) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        String outputDate = date.format(outputFormatter);
        return outputDate;
    }

    //Função para buscar todos os alunosAvaliação de uma sala independente da avaliação
    public List<AlunoAvaliacao> buscarGeral(Sala sala) {
        List<AlunoAvaliacao> ListAlunoAvaliacao = new ArrayList<>();

        String sql = "select * from api.aluno_avaliacao where avaliacao_id in (select avaliacao_id from api.avaliacao where sala_id = ?) order by aluno_avaliacao_data_entrega desc,avaliacao_id;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, sala.getSalaId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int aluno_avaliacaoid = rs.getInt("aluno_avaliacao_id");
                    //Não esquecer a data
                    float nota = rs.getFloat("aluno_avaliacao_nota");
                    int aluno_id = rs.getInt("aluno_id");
                    int avaliacao_id = rs.getInt("avaliacao_id");
                    String data = rs.getDate("aluno_avaliacao_data_entrega").toString();
                    AlunoAvaliacao alunoavaliacao = new AlunoAvaliacao(aluno_avaliacaoid, aluno_id, avaliacao_id, data, nota);
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

    // Calcular e exibir rendimento geral da sala:
    public String[] calculaRendimentoGeral(int id) {
        int entregue = 0;
        int atrasado = 0;
        int pendente = 0;

        String nomeAvaliacao = new AvaliacaoDAO().getAvaliacaoNome(id);
        Avaliacao av = new AvaliacaoDAO().getAvaliacao(id);
        List<AlunoAvaliacao> ListAlunoAvaliacao = new AlunoAvaliacaoDAO().buscarTodosAlunoAvaliacao(av);

        int totalAlunos = ListAlunoAvaliacao.size();

        for (AlunoAvaliacao alunoAvaliacao : ListAlunoAvaliacao) {
            String dataAluno = alunoAvaliacao.getAlunoAvaliacaoData();
            String dataAlunoFormatada = AlunoAvaliacaoDAO.formataData(dataAluno);

            if (dataAlunoFormatada.contains("9999")) {
                pendente++;
            } else if (LocalDate.parse(dataAluno).isAfter(LocalDate.parse(av.getAvaliacaoDataFinal()))) {
                atrasado++;
                entregue++;
            } else {
                entregue++;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        String porcentagemEntregue = decimalFormat.format((double) (entregue * 100) / totalAlunos);
        String porcentagemAtrasado = decimalFormat.format((double) (atrasado * 100) / totalAlunos);
        String porcentagemPendente = decimalFormat.format((double) (pendente * 100) / totalAlunos);

        ArrayList<String> rendimentoGeral = new ArrayList<>();

        rendimentoGeral.add("Nome da Avaliação: " + nomeAvaliacao);
        rendimentoGeral.add("Entregue: " + entregue + " de " + totalAlunos + " (" + porcentagemEntregue + "%)");
        rendimentoGeral.add("Atrasado: " + atrasado + " de " + totalAlunos + " (" + porcentagemAtrasado + "%)");
        rendimentoGeral.add("Pendente: " + pendente + " de " + totalAlunos + " (" + porcentagemPendente + "%)");

        String[] rendimentoGeralArray = rendimentoGeral.toArray(new String[0]);

        return rendimentoGeralArray;
    }

    public String printarAprovados(Avaliacao avaliacao, List<AlunoAvaliacao> alunos) {

        System.out.println("Printando aprovados: ");

        int total = alunos.size();
        int aprovados = 0;
        for (AlunoAvaliacao aluno : alunos) {
            if (aluno.getAlunoAvaliacaoNota() >= avaliacao.getAvaliacaoConceito()) {
                aprovados++;
            }
        }
        return "Foram aprovados " + aprovados + " de " + total + ".";
    }
}
