
package api;
import modelo.Aluno;
import dao.AlunoDAO;
import modelo.Sala;
import dao.SalaDAO;
import modelo.Avaliacao;
import dao.AvaliacaoDAO;

public class Api {

  
    public static void main(String[] args) {
        
        
        
        Sala sala = new Sala();
        SalaDAO salaController = new SalaDAO();
      
        sala.setSalaNome("5C");        
        sala.setSalaDia("Segunda");
        sala.setSalaHorario("16:00:00");
        
        salaController.cadastrarSala(sala);
        
        
        Aluno aluno = new Aluno();
        AlunoDAO alunoController = new AlunoDAO();
        
        aluno.setAlunoNome("Bruno");
        aluno.setAlunoSala("5C");
        
        alunoController.cadastrar(aluno);
        
        
        Avaliacao avaliacao = new Avaliacao();
        AvaliacaoDAO avaliacaoController = new AvaliacaoDAO();
        
        avaliacao.setAvaliacaoNome("Resenha sobre brás cubas");
        avaliacao.setAvaliacaoTipo("Redação");
        avaliacao.setAvaliacaoDataFinal("2023-05-01");
        avaliacao.setAvaliacaoConceito(3.25);
        avaliacao.setAvaliacaoSalaId(1);
        
        avaliacaoController.cadastrar(avaliacao);
        
        
        //System.out.println( salaController.buscarTodosAlunos(sala)); 
        
        
        // BUSCAR ALUNO ESPECIFICO
        aluno.setAlunoNome("Alexandre Jonas");
        sala.setSalaNome("8A");
        System.out.println(aluno.getAlunoNome());
        System.out.println(sala.getSalaNome());
        salaController.buscarAluno(sala, aluno);
    }
    
    
    }
    

