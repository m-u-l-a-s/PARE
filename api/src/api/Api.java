
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
        Aluno aluno = new Aluno();
        AlunoDAO alunoController = new AlunoDAO();
        Avaliacao avaliacao = new Avaliacao();
        AvaliacaoDAO avaliacaoController = new AvaliacaoDAO();
      
        sala.setSalaNome("9C");        
        sala.setSalaDia("Segunda");
        sala.setSalaHorario("16:00:00");
        salaController.cadastrarSala(sala);
        
        aluno.setAlunoNome("Joaquim Barbosa dos Santos");
        aluno.setAlunoSalaId(sala.getSalaId());
        alunoController.cadastrar(aluno);
          
        
        avaliacao.setAvaliacaoNome("Resenha sobre brás cubas");
        avaliacao.setAvaliacaoTipo("Redação");
        avaliacao.setAvaliacaoDataFinal("2023-05-01");
        avaliacao.setAvaliacaoConceito(3.25);
        avaliacao.setAvaliacaoSalaId(1);
        
        sala.setSalaId(1);
        sala.setSalaNome("9A");
       avaliacaoController.cadastrar(avaliacao);
        
        
       System.out.println( salaController.buscarTodosAlunos(sala)); 
        
       
    }
    
    
    }
    

