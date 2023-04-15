/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
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
        
        //Desabilita lista de trabalhos no lado esquerdo da tela para não sobreescrever as linhas destacadas quando o usuário clica
        listTrabalhos.setEnabled(false);
        //populaLista: Popula lista de trabalhos de uma sala no lado esquerdo da tela
        populaLista(ComboSala.getSelectedIndex());
        //separaCor: Destaca as linhas relacionadas ao título do trabalho
        separaCor();

        //Dados mockados pra função de pegar os horarios pq lol
        Sala salin = new Sala();
        salin.setSalaNome("9C - Química - Sala 208");
        //populaSalaHorario: Popula lista de horarios da sala atual
        populaSalaHorario(salin);
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

    public void PopulaTabela(int id) {
        //id = index da sala selecionada no combo
        //id = 0 -> sala 9C - Química - Sala 208 selecionada
        if (id == 0) {
            DefaultTableModel model = (DefaultTableModel) tableAvaliacoesAluno.getModel();
            model.setRowCount(0);
            tableAvaliacoesAluno.setModel(model);

            model.addRow(new Object[]{"Alexandre", "16/05"});
            tableAvaliacoesAluno.setModel(model);

            model.addRow(new Object[]{"Samuel", "13/05"});
            tableAvaliacoesAluno.setModel(model);
        } else {
            DefaultTableModel model = (DefaultTableModel) tableAvaliacoesAluno.getModel();
            model.setRowCount(0);
            tableAvaliacoesAluno.setModel(model);

            model.addRow(new Object[]{"Jonas", "29/04"});
            tableAvaliacoesAluno.setModel(model);

            model.addRow(new Object[]{"Gomes", "17/05"});
            tableAvaliacoesAluno.setModel(model);
        }

    }

    public void separaCor() {
        int[] indices = {0, 5, 10};
        listTrabalhos.setSelectedIndices(indices);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnBuscar = new javax.swing.JButton();
        btnCadastrar = new javax.swing.JButton();
        btnSalas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnNovoTrabalho = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSalaHorario = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAvaliacoesAluno = new javax.swing.JTable();
        labelAvaliacaoNome = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        listTrabalhos = new javax.swing.JList<>();
        ComboSala = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);

        btnCadastrar.setText("Cadastrar");
        btnCadastrar.setBorder(null);

        btnSalas.setText("Salas");
        btnSalas.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(btnSalas, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        btnNovoTrabalho.setText("Novo Trabalho");
        btnNovoTrabalho.setBorder(null);

        jScrollPane1.setViewportView(listSalaHorario);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelAvaliacaoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnNovoTrabalho;
    private javax.swing.JButton btnSalas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel labelAvaliacaoNome;
    private javax.swing.JList<String> listSalaHorario;
    private javax.swing.JList<String> listTrabalhos;
    private javax.swing.JTable tableAvaliacoesAluno;
    // End of variables declaration//GEN-END:variables
}
