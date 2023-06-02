/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import modelo.*;
import dao.*;
import java.awt.Color;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;

public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    List<AlunoAvaliacao> GlobalListAlunoAvaliacao;

    public TelaPrincipal(boolean flag) {
        getContentPane().setBackground(Color.decode("#658EA9"));
        getContentPane().setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N

        initComponents();
//        labelAvaliacaoNome3.setVisible(false);
//        listRendimentoGeral.setVisible(false);
//        jScrollPane5.setVisible(false);
        tableAvaliacoesAluno.getColumnModel().getColumn(4).setResizable(true);
        //PopulaCombo: Popula combo com os nomes das salas
        PopulaCombo();
        
        // setCombo: Altera valor do combo box para sala atual.
        setCombo();
        PopulaComboAvaliacao();
        
        // verificar o sistema de aviso
        verificaAlerta(flag);

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
        String[] linhasTabela = new String[avaliacoes.size()];
        int[] cor = new int[avaliacoes.size()];
        AlunoAvaliacaoDAO alunoController = new AlunoAvaliacaoDAO();

        int control = 0;
        Sala sala = new Sala();
        sala.setSalaId(id_sala);
        int numAlunos = new SalaDAO().buscarTodosAlunos(sala).size();

        for (int i = 0; i < avaliacoes.size(); i++) {

            int numAtrasados = alunoController.AlunosAtrasados(avaliacoes.get(i).getAvaliacaoId()).size();
            String status = " (Aberto)";
            if (LocalDate.now().isAfter(LocalDate.parse(avaliacoes.get(i).getAvaliacaoDataFinal()))) {
                status = " (Encerrado)";
            }

            linhasTabela[i] = "<html>Nome da Avaliação: " + avaliacoes.get(i).getAvaliacaoNome() + status
                    + "<br>Entregaram: " + String.valueOf(numAlunos - numAtrasados) + " / " + String.valueOf(numAlunos)
                    + "<br>Não entregaram: " + String.valueOf(numAtrasados) + " / " + String.valueOf(numAlunos)
                    + "<br>Data final: " + alunoController.formataData(avaliacoes.get(i).getAvaliacaoDataFinal())
                    + "<br><br>";
        }

        listTrabalhos.setListData(linhasTabela);
    }

    public void PopulaCombo() {

        List<String> ListSalas = new SalaDAO().buscarTodasSalas();

        for (int i = 0; i < ListSalas.size(); i++) {
            ComboSala.addItem(ListSalas.get(i));

        }
    }

    public void PopulaComboAvaliacao() {
        comboAvaliacao.removeAllItems();
        comboAvaliacao.addItem("Geral");
        AvaliacaoDAO avaliacaoController = new AvaliacaoDAO();
        int id_sala = new SalaDAO().getSalaId(ComboSala.getSelectedItem().toString());
        ArrayList<Avaliacao> avaliacoes = avaliacaoController.getAvaliacoesDaSala(id_sala);

        for (int i = 0; i < avaliacoes.size(); i++) {
            comboAvaliacao.addItem(avaliacoes.get(i).getAvaliacaoNome());
        }
    }

    public void setCombo() {
        ComboSala.setSelectedItem(salaHorarioController.getSalaAtual());
    }

    public void PopulaTabela() {

        DefaultTableModel model = (DefaultTableModel) tableAvaliacoesAluno.getModel();
        model.setRowCount(0);
        tableAvaliacoesAluno.setModel(model);

        int id = new AvaliacaoDAO().getAvaliacaoID(comboAvaliacao.getSelectedItem().toString());
        Avaliacao av = new AvaliacaoDAO().getAvaliacao(id);
        List<AlunoAvaliacao> ListAlunoAvaliacao;

        tableAvaliacoesAluno.getColumnModel().getColumn(4).setWidth(0);
        tableAvaliacoesAluno.getColumnModel().getColumn(4).setMaxWidth(0);
        tableAvaliacoesAluno.getColumnModel().getColumn(4).setMinWidth(0);
        ListAlunoAvaliacao = new AlunoAvaliacaoDAO().buscarTodosAlunoAvaliacao(av);
        if (Objects.equals(comboAvaliacao.getSelectedItem().toString(), "Geral")) {
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setWidth(94);
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setMaxWidth(94);
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setMinWidth(94);

            Sala salin = new Sala();
            salin.setSalaId(new SalaDAO().getSalaId(ComboSala.getSelectedItem().toString()));

            ListAlunoAvaliacao = new AlunoAvaliacaoDAO().buscarGeral(salin);
        }

        if (ordenarPorNome.isSelected()) {
            Collections.sort(ListAlunoAvaliacao, new Comparator<AlunoAvaliacao>() {
                @Override
                public int compare(AlunoAvaliacao aluno1, AlunoAvaliacao aluno2) {
                    return aluno1.getAlunoNome().compareTo(aluno2.getAlunoNome());
                }
            ;
        }
        );
        }
        
        GlobalListAlunoAvaliacao = ListAlunoAvaliacao;

        for (int i = 0; i < ListAlunoAvaliacao.size(); i++) {
            String AvaliacaoNome = "";
            String nome = new AlunoDAO().getAlunoNome(ListAlunoAvaliacao.get(i).getAlunoId());
            String dataAluno = ListAlunoAvaliacao.get(i).getAlunoAvaliacaoData();
            //Aparentemente Comentando a linha de formatação de data o update volta a funcionar com o sort por nome
            String dataAlunoFormatada = dataAluno;
            //String dataAlunoFormatada = AlunoAvaliacaoDAO.formataData(dataAluno);
            String status = "Entregue";
            float nota = ListAlunoAvaliacao.get(i).getAlunoAvaliacaoNota();

            if (comboAvaliacao.getSelectedItem().toString() == "Geral") {
                av = new AvaliacaoDAO().getAvaliacao(ListAlunoAvaliacao.get(i).getAvaliacaoId());
                AvaliacaoNome = av.getAvaliacaoNome();
            }

            if (LocalDate.parse(dataAluno).isAfter(LocalDate.parse(av.getAvaliacaoDataFinal()))) {
                status = "Atrasado";
            }
            if (dataAlunoFormatada.contains("9999")) {
                dataAlunoFormatada = "-";
                status = "Pendente";
            }

            model.addRow(new Object[]{nome, dataAlunoFormatada, nota, status, AvaliacaoNome});
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

    public void verificaAlerta(boolean flag){    
        if (flag!=false){
            sistemaAlerta();
        }
//        else{
//            System.out.println("DEU CERTO");
//        }
    }
    
    
    public void sistemaAlerta(){
        //listas com as datas e nomes dos trabalhos
//       List<String> listaHorarioAvaliacao = new AvaliacaoDAO().listaHorarioFinal();
       List<String> listaNomeTrabalho = new AvaliacaoDAO().listaNomeTrabalho();
       //for do pop-up alerta
       for (int i=0;i<listaNomeTrabalho.size();i++){
           JOptionPane.showMessageDialog(null, "O trabalho " + listaNomeTrabalho.get(i)+ " deve ser entregue hoje");
       }
   
       // VERSÃO ANTIGA - comparação de todos os trabalho c data local pelo java
       
       //variavel para registrar o dia/mês/ano (já no formato do sql) do comp.
       //LocalDate ld=LocalDate.now();
       //String dataLocal=ld.toString();
       // for que compara lista das datas com a data local
       //for (int i=0;i<listaHorarioAvaliacao.size();i++){
       //    if(dataLocal == null ? listaHorarioAvaliacao.get(i) == null : dataLocal.equals(listaHorarioAvaliacao.get(i))){
//               System.out.println(listaNomeTrabalho.get(i));
       //        JOptionPane.showMessageDialog(null, "O trabalho " + listaNomeTrabalho.get(i)+ " deve ser entregue hoje");
       //    }
//           else{
//               System.out.println("Teste");
//           }
//       }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ordenarTarefas = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableAvaliacoesAluno = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        listTrabalhos = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listSalaHorario = new javax.swing.JList<>();
        labelAvaliacaoNome1 = new javax.swing.JLabel();
        comboAvaliacao = new javax.swing.JComboBox<>();
        btnSalvar = new javax.swing.JButton();
        ordenarPorData = new javax.swing.JRadioButton();
        ordenarPorNome = new javax.swing.JRadioButton();
        labelAvaliacaoNome3 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        listRendimentoGeral = new javax.swing.JList<>();
        btnCadastrar = new javax.swing.JButton();
        ComboSala = new javax.swing.JComboBox<>();
        btnNovoTrabalho = new javax.swing.JButton();
        labelAvaliacaoNome2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(101, 142, 169));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jPanel2.setBackground(new java.awt.Color(101, 142, 169));

        tableAvaliacoesAluno.setFont(new java.awt.Font("Dubai", 0, 14));
        tableAvaliacoesAluno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nome", "Data de Entrega", "Nota", "Status", "Avaliação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, false
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

        listTrabalhos.setFont(new java.awt.Font("Dubai", 0, 14));
        listTrabalhos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listTrabalhos.setFocusable(false);
        listTrabalhos.setRequestFocusEnabled(false);
        listTrabalhos.setVerifyInputWhenFocusTarget(false);
        listTrabalhos.setVisibleRowCount(4);
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

        comboAvaliacao.setFont(new java.awt.Font("Dubai", 0, 21));
        comboAvaliacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAvaliacaoActionPerformed(evt);
            }
        });

        btnSalvar.setBackground(new java.awt.Color(101, 142, 169));
        btnSalvar.setFont(new java.awt.Font("Dubai", 1, 14));
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/api/icones/aluna.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setBorder(null);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        ordenarTarefas.add(ordenarPorData);
        ordenarPorData.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        ordenarPorData.setSelected(true);
        ordenarPorData.setText("Ordenar por Data");
        ordenarPorData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenarPorDataActionPerformed(evt);
            }
        });

        ordenarTarefas.add(ordenarPorNome);
        ordenarPorNome.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        ordenarPorNome.setText("Ordenar por Nome");
        ordenarPorNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenarPorNomeActionPerformed(evt);
            }
        });

        labelAvaliacaoNome3.setBackground(new java.awt.Color(255, 255, 255));
        labelAvaliacaoNome3.setFont(new java.awt.Font("Dubai", 0, 21));
        labelAvaliacaoNome3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelAvaliacaoNome3.setText("Rendimento da Turma");
        labelAvaliacaoNome3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        listRendimentoGeral.setFont(new java.awt.Font("Dubai", 0, 16));
        jScrollPane5.setViewportView(listRendimentoGeral);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAvaliacaoNome3, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(ordenarPorData)
                        .addGap(18, 18, 18)
                        .addComponent(ordenarPorNome)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboAvaliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelAvaliacaoNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(comboAvaliacao, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ordenarPorData)
                            .addComponent(ordenarPorNome)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelAvaliacaoNome1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAvaliacaoNome3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 21, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ComboSala, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnNovoTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelAvaliacaoNome2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
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

    private void ComboSalaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ComboSalaPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_ComboSalaPropertyChange

    private void ComboSalaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboSalaActionPerformed
        //Função executada quando o valor do combo muda:
        populaLista();
        PopulaComboAvaliacao();
        //Pega o texto do valor atual do combo e salva na classe sala, que então é usada como parâmetro
        //para popular a lista de horários
        Sala salin = new Sala();
        salin.setSalaNome(ComboSala.getSelectedItem().toString());
        populaSalaHorario(salin);

    }//GEN-LAST:event_ComboSalaActionPerformed

    private void btnCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarActionPerformed
        new NovaTelaCadastro().setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCadastrarActionPerformed

    private void btnNovoTrabalhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoTrabalhoActionPerformed
        // TODO add your handling code here:
        int sala_id = new SalaDAO().getSalaId(ComboSala.getSelectedItem().toString());
        String sala_nome = ComboSala.getSelectedItem().toString();
        PopupCadastroAvaliacao f2 = new PopupCadastroAvaliacao(sala_id, sala_nome);
        f2.setVisible(true);
  
    }//GEN-LAST:event_btnNovoTrabalhoActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // TODO add your handling code here:
        int aux = comboAvaliacao.getSelectedIndex();
        int auxLista = listTrabalhos.getSelectedIndex();
        populaLista();
        PopulaComboAvaliacao();
        listTrabalhos.setSelectedIndex(auxLista);
        comboAvaliacao.setSelectedIndex(aux);

    }//GEN-LAST:event_formWindowGainedFocus

    private void ordenarPorNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenarPorNomeActionPerformed
        PopulaTabela();
    }//GEN-LAST:event_ordenarPorNomeActionPerformed

    private void ordenarPorDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenarPorDataActionPerformed
        PopulaTabela();
    }//GEN-LAST:event_ordenarPorDataActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        int colunaNota = 2;
        int colunaData = 1;
        List<AlunoAvaliacao> ListAlunoAvaliacao = new ArrayList<>();
        for (int i = 0; i < tableAvaliacoesAluno.getRowCount(); i++) {
            AlunoAvaliacao Aux = GlobalListAlunoAvaliacao.get(i);
            if ((tableAvaliacoesAluno.getValueAt(i, colunaData) != "-") && ((tableAvaliacoesAluno.getValueAt(i, colunaData) != Aux.getAlunoAvaliacaoData())
                    || (tableAvaliacoesAluno.getValueAt(i, colunaNota) != String.valueOf(Aux.getAlunoAvaliacaoNota())))) {

                Aux.setAlunoAvaliacaoNota(Float.valueOf(String.valueOf(tableAvaliacoesAluno.getValueAt(i, colunaNota))));
                Aux.setAlunoAvaliacaoData(String.valueOf(tableAvaliacoesAluno.getValueAt(i, colunaData)));
                ListAlunoAvaliacao.add(Aux);
            }

        }
        if (ListAlunoAvaliacao.size() > 0) {
            new AlunoAvaliacaoDAO().UpdateTodosAlunoAvaliacao(ListAlunoAvaliacao);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void comboAvaliacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAvaliacaoActionPerformed
        // TODO add your handling code here:
        if (comboAvaliacao.getSelectedIndex() != -1) {
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setWidth(0);
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setMaxWidth(0);
            tableAvaliacoesAluno.getColumnModel().getColumn(4).setMinWidth(0);
            PopulaTabela();
            if (comboAvaliacao.getSelectedItem().toString().equals("Geral")) {
                String[] txt = {"Escolha uma avaliação para ver mais detalhes."};
                listRendimentoGeral.setListData(txt);
            } else {
                int id = new AvaliacaoDAO().getAvaliacaoID(Objects.requireNonNull(comboAvaliacao.getSelectedItem()).toString());
                // popula listRendimentoGeral
                listRendimentoGeral.setListData(new AlunoAvaliacaoDAO().calculaRendimentoGeral(id));
            }

        }

    }//GEN-LAST:event_comboAvaliacaoActionPerformed

    private void listTrabalhosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listTrabalhosMouseClicked

        String nome = listTrabalhos.getSelectedValue();
        nome = nome.substring(25, listTrabalhos.getSelectedValue().indexOf(" ("));
        comboAvaliacao.setSelectedItem(nome);
    }//GEN-LAST:event_listTrabalhosMouseClicked

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
                new TelaPrincipal(true).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboSala;
    private javax.swing.JButton btnCadastrar;
    private javax.swing.JButton btnNovoTrabalho;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> comboAvaliacao;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel labelAvaliacaoNome1;
    private javax.swing.JLabel labelAvaliacaoNome2;
    private javax.swing.JLabel labelAvaliacaoNome3;
    private javax.swing.JList<String> listRendimentoGeral;
    private javax.swing.JList<String> listSalaHorario;
    private javax.swing.JList<String> listTrabalhos;
    private javax.swing.JRadioButton ordenarPorData;
    private javax.swing.JRadioButton ordenarPorNome;
    private javax.swing.ButtonGroup ordenarTarefas;
    private javax.swing.JTable tableAvaliacoesAluno;
    // End of variables declaration//GEN-END:variables

}
