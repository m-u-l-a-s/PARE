/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import dao.SalaHorarioDAO;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import dao.*;

public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {

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
        populaLista(ComboSala.getSelectedIndex());
        //separaCor: Destaca as linhas relacionadas ao título do trabalho
        separaCor();
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

    public void populaLista(int id) {
        //id = index da sala selecionada no combo
        //id = 0 -> sala 9C - Química - Sala 208 selecionada
        if (id == 0) {
            String[] Lista
                    = {
                        "Trabalho 1 - Aberto (15/03)",
                        "Entregues:",
                        "Não Entregues: ",
                        "Tarefa criada em: 10/02/2023",
                        "Encerrada em: 15/03/2023",
                        "Trabalho 2 - Encerrado (10/02)",
                        "Entregues: 27/30",
                        "Não Entregues: 3/30",
                        "Tarefa criada em: 10/01/2023",
                        "Encerrada em: 10/02/2023",
                        "Avaliação 1 - Encerrado (2/03)",
                        "Entregues: 25/30",
                        "Não Entregues: 5/30",
                        "Tarefa criada em: 1/03/2023",
                        "Encerrada em: 2/03/2023"
                    };
            listTrabalhos.setListData(Lista);
        } else {
            String[] Lista
                    = {
                        "Trabalho Ácidos e Bases - Aberto (29/06)",
                        "Entregues:",
                        "Não Entregues: ",
                        "Tarefa criada em: 1/06/2023",
                        "Encerrada em: 29/06/2023",
                        "Trabalho Mols - Encerrado (25/05)",
                        "Entregues: 25/25",
                        "Não Entregues: 0/25",
                        "Tarefa criada em: 10/04/2023",
                        "Encerrada em: 25/05/2023",
                        "Avaliação Mensal - Encerrado (10/04)",
                        "Entregues: 24/25",
                        "Não Entregues: 1/25",
                        "Tarefa criada em: 20/03/2023",
                        "Encerrada em: 10/04/2023"
                    };
            listTrabalhos.setListData(Lista);
        }

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
              
              
              model.addRow(new Object[] {nome, dataAluno});
              tableAvaliacoesAluno.setModel(model);
          }

    }

    public void separaCor() {
        int[] indices = {0, 5, 10};
        listTrabalhos.setSelectedIndices(indices);
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
        btnCadastrar = new javax.swing.JButton();
        ComboSala = new javax.swing.JComboBox<>();
        btnNovoTrabalho = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSalaHorario = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1022, 700));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        tableAvaliacoesAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Data"
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
        labelAvaliacaoNome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelAvaliacaoNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvaliacaoNome.setText("Trabalho 2");
        labelAvaliacaoNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        listTrabalhos.setRequestFocusEnabled(false);
        listTrabalhos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listTrabalhosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(listTrabalhos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setBorder(null);
        btnCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarActionPerformed(evt);
            }
        });

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

        btnNovoTrabalho.setText("Novo Trabalho");
        btnNovoTrabalho.setBorder(null);
        btnNovoTrabalho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoTrabalhoActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listSalaHorario);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(428, 428, 428)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
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
        populaLista(ComboSala.getSelectedIndex());
        separaCor();
        
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
    private javax.swing.JList<String> listSalaHorario;
    private javax.swing.JList<String> listTrabalhos;
    private javax.swing.JTable tableAvaliacoesAluno;
    // End of variables declaration//GEN-END:variables

}
