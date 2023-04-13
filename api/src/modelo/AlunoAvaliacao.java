package modelo;

public class AlunoAvaliacao {
    private int alunoId;
    private int avaliacaoId;
    private String alunoAvaliacaoData;
    private float alunoAvaliacaoNota;

    
    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }
    public void setAvaliacaoId(int avaliacaoId) {
        this.avaliacaoId = avaliacaoId;
    }
    public void setAlunoAvaliacaoData(String alunoAvaliacaoData) {
        this.alunoAvaliacaoData = alunoAvaliacaoData;
    }
    public void setAlunoAvaliacaoNota(float alunoAvaliacaoNota) {
        this.alunoAvaliacaoNota = alunoAvaliacaoNota;
    }
    public int getAlunoId() {
        return alunoId;
    }
    public int getAvaliacaoId() {
        return avaliacaoId;
    }
    public String getAlunoAvaliacaoData() {
        return alunoAvaliacaoData;
    }
    public float getAlunoAvaliacaoNota() {
        return alunoAvaliacaoNota;
    }

       
   
    
}
