package api;

import modelo.Aluno;
import dao.AlunoDAO;
import modelo.Sala;
import dao.SalaDAO;
import modelo.Avaliacao;
import dao.AvaliacaoDAO;
import modelo.SalaHorario;
import dao.SalaHorarioDAO;

public class Api {

    public static void main(String[] args) {

        Sala sala = new Sala();
        SalaDAO salaController = new SalaDAO();

        SalaHorarioDAO salaHorarioController = new SalaHorarioDAO();

        Aluno aluno = new Aluno();
        AlunoDAO alunoController = new AlunoDAO();
        Avaliacao avaliacao = new Avaliacao();
        AvaliacaoDAO avaliacaoController = new AvaliacaoDAO();

        sala.setSalaNome("9C - Química - Sala 208");
        salaController.cadastrarSala(sala);

        SalaHorario salahorario = new SalaHorario(salaController.getSalaId(sala.getSalaNome()), "Segunda", "16:00:00");
        salaHorarioController.CadastrarSalaHorario(salahorario);

        salahorario = new SalaHorario(salaController.getSalaId(sala.getSalaNome()), "Quarta", "14:00:00");
        salaHorarioController.CadastrarSalaHorario(salahorario);

        aluno.setAlunoNome("Joaquim Barbosa dos Santos");
        aluno.setAlunoSalaId(salaController.getSalaId(sala.getSalaNome()));
        alunoController.cadastrar(aluno);

        avaliacao.setAvaliacaoNome("Resenha sobre brás cubas");
        avaliacao.setAvaliacaoTipo("Redação");
        avaliacao.setAvaliacaoDataFinal("2023-05-01");
        avaliacao.setAvaliacaoConceito(3.25);
        avaliacao.setAvaliacaoSalaId(salaController.getSalaId(sala.getSalaNome()));

        sala.setSalaId(salaController.getSalaId(sala.getSalaNome()));
        avaliacaoController.cadastrar(avaliacao);

        System.out.println(salaController.buscarTodosAlunos(sala));

    }

}
