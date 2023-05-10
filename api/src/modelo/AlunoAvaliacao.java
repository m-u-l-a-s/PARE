package modelo;

public class AlunoAvaliacao {
    private String alunoNome;
    private int alunoAvaliacaoId;
    private int alunoId;
    private int avaliacaoId;
    private String alunoAvaliacaoData;
    private float alunoAvaliacaoNota;

     public AlunoAvaliacao(int alunoAvaliacaoId,int alunoID, int avaliacaoId, String Data, float nota)
    {
        this.alunoAvaliacaoId = alunoAvaliacaoId;
        this.alunoId = alunoID;
        this.avaliacaoId = avaliacaoId;
        this.alunoAvaliacaoData = Data;
        this.alunoAvaliacaoNota = nota;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }
    
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

     public void setAlunoAvaliacaoId(int id)
    {
        this.alunoAvaliacaoId = id;
    }

    public int getAlunoAvaliacaoId()
    {
        return this.alunoAvaliacaoId;
    }  
}
