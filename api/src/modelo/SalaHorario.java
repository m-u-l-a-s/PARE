/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author M.U.L.A.S.
 */
public class SalaHorario {
    
    private int salaHorarioID;
    
    private int salaID;
    
    private String salaHorarioDia;
    
    private String salaHorarioHora;
    
    public SalaHorario(int salaID, String salaHorarioDia, String salaHorarioHora)
    {
        this.salaID = salaID;
        this.salaHorarioDia = salaHorarioDia;
        this.salaHorarioHora = salaHorarioHora;
    }

    public int getSalaHorarioID() {
        return salaHorarioID;
    }

    public void setSalaHorarioID(int salaHorarioID) {
        this.salaHorarioID = salaHorarioID;
    }

    public int getSalaID() {
        return salaID;
    }

    public void setSalaID(int salaID) {
        this.salaID = salaID;
    }

    public String getSalaHorarioDia() {
        return salaHorarioDia;
    }

    public void setSalaHorarioDia(String salaHorarioDia) {
        this.salaHorarioDia = salaHorarioDia;
    }

    public String getSalaHorarioHora() {
        return salaHorarioHora;
    }

    public void setSalaHorarioHora(String salaHorarioHora) {
        this.salaHorarioHora = salaHorarioHora;
    }
    
    
}
