
package modelo;

public class Avaliacao {
    
    private int avaliacaoId;
    private int avaliacaoSalaId;
    private String avaliacaoNome;
    private String avaliacaoDataFinal;
    private String avaliacaoTipo;
    private double avaliacaoConceito;
    
    public Avaliacao(int avaliacaoId, String nome, String dataFinal,String tipo, double conceito, int salaId)
    {
        this.avaliacaoId = avaliacaoId;
        this.avaliacaoNome = nome;
        this.avaliacaoDataFinal = dataFinal;
        this.avaliacaoTipo = tipo;
        this.avaliacaoConceito = conceito;
        this.avaliacaoSalaId = salaId;
    }
    
    public Avaliacao()
    {
        
    }

    public int getAvaliacaoSalaId() {
        return avaliacaoSalaId;
    }

    public void setAvaliacaoSalaId(int avaliacaoSalaId) {
        this.avaliacaoSalaId = avaliacaoSalaId;
    }
    

    public int getAvaliacaoId() {
        return avaliacaoId;
    }

    public void setAvaliacaoId(int avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }

    public String getAvaliacaoNome() {
        return avaliacaoNome;
    }

    public void setAvaliacaoNome(String avaliacaoNome) {
        this.avaliacaoNome = avaliacaoNome;
    }

    public String getAvaliacaoDataFinal() {
        return avaliacaoDataFinal;
    }

    public void setAvaliacaoDataFinal(String avaliacaoDataFinal) {
        this.avaliacaoDataFinal = avaliacaoDataFinal;

    }

    public String getAvaliacaoTipo() {
        return avaliacaoTipo;
    }

    public void setAvaliacaoTipo(String avaliacaoTipo) {
        this.avaliacaoTipo = avaliacaoTipo;
    }

    public double getAvaliacaoConceito() {
        return avaliacaoConceito;
    }

    public void setAvaliacaoConceito(double avaliacaoConceito) {
        this.avaliacaoConceito = avaliacaoConceito;
    }
    
}
