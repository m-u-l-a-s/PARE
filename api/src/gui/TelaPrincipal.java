/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import dao.*;
import java.awt.Color;

public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        getContentPane().setBackground(Color.decode("#658EA9"));
        getContentPane().setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N

        
        

        initComponents();
        
        //Popula Tabela: Popula a tabela de AlunoAvaliação no lado direito da tela
        PopulaTabela(ComboSala.getSelectedIndex());
        //PopulaCombo: Popula combo com os nomes das salas
        PopulaCombo();

        // setCombo: Altera valor do combo box para sala atual.
        setCombo();

        //Desabilita lista de trabalhos no lado esquerdo da tela para não sobreescrever as linhas destacadas quando o usuário clica
        listTrabalhos.setEnabled(false);
        //populaLista: Popula lista de trabalhos de uma sala no lado esquerdo da tela
        populaLista();
    }

    public void populaSalaHorario(Sala sala) {
        //Busca horarios de uma sala em formato de lista
        List<SalaHorario> ListSalaHorario = new SalaHorarioDAO().buscarTodosHorarios(sala);
        String[] listHorarios = new String[ListSalaHorario.size()];
        //for para juntar e armazenar na lista as strings de dia e horario
        for (int i = 0; i < ListSalaHorario.size(); i++) {
            listHorarios[i] = (ListSalaHorario.get(i).getSalaHorarioDia() + " - " + ListSalaHorario.get(i).getSalaHorarioHora());
        }
        listSalaHorario.setListData(listHorarios);
    }

    public void populaLista() {

//        Campos:
//        "Trabalho 1 - Nome: ",
//        "Entreguaram: ",
//        "Não Entregaram: ",
//        "Data de entrega: "
        AvaliacaoDAO avaliacaoController = new AvaliacaoDAO();
        int id_sala = new SalaDAO().getSalaId(ComboSala.getSelectedItem().toString());
        ArrayList<Avaliacao> avaliacoes = avaliacaoController.getAvaliacoesDaSala(id_sala);
        String[] linhasTabela = new String[avaliacoes.size() * 4];
        int[] cor = new int[avaliacoes.size()];
        AlunoAvaliacaoDAO alunoController = new AlunoAvaliacaoDAO();

        int control = 0;

        for (int i = 0; i < avaliacoes.size(); i++) {
            linhasTabela[i + control] = "Nome da Avaliação: " + avaliacoes.get(i).getAvaliacaoNome();
            linhasTabela[i + control + 1] = "Entregaram: " + "0 / 30";
            linhasTabela[i + control + 2] = "Não entregaram: " + String.valueOf(alunoController.AlunosAtrasados(avaliacoes.get(i).getAvaliacaoId()).size());
            linhasTabela[i + control + 3] = "Data final: " + avaliacoes.get(i).getAvaliacaoDataFinal();
            
            cor[i] = i + control;
            
            control += 3;
        }

        listTrabalhos.setListData(linhasTabela);
        listTrabalhos.setSelectedIndices(cor);

    }

    public void PopulaCombo() {
        
        List<String> ListSalas = new SalaDAO().buscarTodasSalas();
           
        for(int i = 0; i < ListSalas.size(); i++){
            ComboSala.addItem(ListSalas.get(i));
                
        }
    }

    public void setCombo() {
        ComboSala.setSelectedItem(salaHorarioController.getSalaAtual());
    }

    public void PopulaTabela(int id) {
        DefaultTableModel model = (DefaultTableModel) tableAvaliacoesAluno.getModel();
        model.setRowCount(0);
        tableAvaliacoesAluno.setModel(model);
        
        String AvaliacaoNome = new AvaliacaoDAO().getAvaliacaoNome(3);
        labelAvaliacaoNome.setText(AvaliacaoNome);
        
        //Mockando ID por enquanto pq lol
        Avaliacao av = new Avaliacao();
        av.setAvaliacaoId(3);
        av.setAvaliacaoDataFinal("2023-03-20");
        
        List<AlunoAvaliacao> ListAlunoAvaliacao = new AlunoAvaliacaoDAO().buscarTodosAlunoAvaliacao(av);
          for(int i = 0; i< ListAlunoAvaliacao.size(); i++)
          {
              String nome = new AlunoDAO().getAlunoNome(ListAlunoAvaliacao.get(i).getAlunoId());
              String dataAluno = ListAlunoAvaliacao.get(i).getAlunoAvaliacaoData();
              
//              if (dataAluno > av.getAvaliacaoDataFinal())
//              {
//                  System.out.println(nome); 
//              }
              
              if (dataAluno.contains("9999"))
              {
                  dataAluno = "-";
              }
              model.addRow(new Object[] {nome, dataAluno});
              tableAvaliacoesAluno.setModel(model);
          }

    }

    SalaHorarioDAO salaHorarioController = new SalaHorarioDAO();

    public String getDiaEHora() {
        int hora = LocalDateTime.now().getHour();
        int minuto = LocalDateTime.now().getMinute();
        DayOfWeek dia = LocalDateTime.now().getDayOfWeek();
        String horaFormatada = Integer.toString(hora) + ":" + Integer.toString(minuto);
        String diaSemana = "";

        switch (dia) {
            case MONDAY:
                diaSemana = "Segunda";
                break;
            case TUESDAY:
                diaSemana = "Terça";
                break;
            case WEDNESDAY:
                diaSemana = "Quarta";
                break;
            case THURSDAY:
                diaSemana = "Quinta";
                break;
            case FRIDAY:
                diaSemana = "Sexta";
                break;
            case SATURDAY:
                diaSemana = "Sábado";
                break;
            case SUNDAY:
                diaSemana = "Domingo";
                break;
        }

        return diaSemana + ", " + horaFormatada;
    }

    String turma_e_horario = salaHorarioController.getSalaAtual().equals("")
            ? getDiaEHora() + ". Não há turmas neste horário."
            : getDiaEHora() + ". Estamos na sala " + salaHorarioController.getSalaAtual();

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAvaliacoesAluno = new javax.swing.JTable();
        labelAvaliacaoNome = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listTrabalhos = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSalaHorario = new javax.swing.JList<>();
        labelAvaliacaoNome1 = new javax.swing.JLabel();
        btnCadastrar = new javax.swing.JButton();
        ComboSala = new javax.swing.JComboBox<>();
        btnNovoTrabalho = new javax.swing.JButton();
        labelAvaliacaoNome2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(101, 142, 169));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(101, 142, 169));

        tableAvaliacoesAluno.setFont(new java.awt.Font("Dubai", 0, 14));
        tableAvaliacoesAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Data de Entrega"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAvaliacoesAluno.getTableHeader().setResizingAllowed(false);
        tableAvaliacoesAluno.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableAvaliacoesAluno);

        labelAvaliacaoNome.setBackground(new java.awt.Color(255, 255, 255));
        labelAvaliacaoNome.setFont(new java.awt.Font("Dubai", 0, 21));
        labelAvaliacaoNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvaliacaoNome.setText("Trabalho 2");
        labelAvaliacaoNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        listTrabalhos.setFont(new java.awt.Font("Dubai", 0, 14));
        listTrabalhos.setRequestFocusEnabled(false);
        listTrabalhos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTrabalhosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listTrabalhos);

        listSalaHorario.setFont(new java.awt.Font("Dubai", 0, 16));
        jScrollPane1.setViewportView(listSalaHorario);

        labelAvaliacaoNome1.setBackground(new java.awt.Color(255, 255, 255));
        labelAvaliacaoNome1.setFont(new java.awt.Font("Dubai", 0, 21));
        labelAvaliacaoNome1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvaliacaoNome1.setText("Horários da Turma");
        labelAvaliacaoNome1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelAvaliacaoNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labelAvaliacaoNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 81, Short.MAX_VALUE))
        );

        btnCadastrar.setBackground(new java.awt.Color(101, 142, 169));
        btnCadastrar.setFont(new java.awt.Font("Dubai", 1, 14));
        btnCadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/api/icones/aluna.png"))); // NOI18N
        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setBorder(null);
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

        ComboSala.setFont(new java.awt.Font("Dubai", 0, 21));
        ComboSala.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboSalaActionPerformed(evt);
            }
        });
        ComboSala.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ComboSalaPropertyChange(evt);
            }
        });

        btnNovoTrabalho.setBackground(new java.awt.Color(101, 142, 169));
        btnNovoTrabalho.setFont(new java.awt.Font("Dubai", 1, 14));
        btnNovoTrabalho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/api/icones/tarefa.png"))); // NOI18N
        btnNovoTrabalho.setText("Novo Trabalho");
        btnNovoTrabalho.setBorder(null);
        btnNovoTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoTrabalhoActionPerformed(evt);
            }
        });

        labelAvaliacaoNome2.setBackground(new java.awt.Color(255, 255, 255));
        labelAvaliacaoNome2.setFont(new java.awt.Font("Dubai", 0, 21));
        labelAvaliacaoNome2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvaliacaoNome2.setText(turma_e_horario);
        labelAvaliacaoNome2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAvaliacaoNome2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(labelAvaliacaoNome2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void listTrabalhosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTrabalhosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_listTrabalhosMouseClicked

    private void ComboSalaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ComboSalaPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_ComboSalaPropertyChange

    private void ComboSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboSalaActionPerformed
        //Função executada quando o valor do combo muda:
        PopulaTabela(ComboSala.getSelectedIndex());
        populaLista();
        
        //Pega o texto do valor atual do combo e salva na classe sala, que então é usada como parâmetro
        //para popular a lista de horários
        Sala salin = new Sala();
        salin.setSalaNome(ComboSala.getSelectedItem().toString());
        populaSalaHorario(salin);
    }//GEN-LAST:event_ComboSalaActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
    new CadastroAluno().setVisible(true); dispose();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnNovoTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoTrabalhoActionPerformed
        // TODO add your handling code here:
        PopupCadastroAvaliacao f2= new PopupCadastroAvaliacao();
        f2.setVisible(true);
    }//GEN-LAST:event_btnNovoTrabalhoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboSala;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnNovoTrabalho;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelAvaliacaoNome;
    private javax.swing.JLabel labelAvaliacaoNome1;
    private javax.swing.JLabel labelAvaliacaoNome2;
    private javax.swing.JList<String> listSalaHorario;
    private javax.swing.JList<String> listTrabalhos;
    private javax.swing.JTable tableAvaliacoesAluno;
    // End of variables declaration//GEN-END:variables

}
