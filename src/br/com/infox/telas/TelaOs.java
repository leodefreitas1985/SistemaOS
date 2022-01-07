/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Leonardo
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // variável usada para o radio.
    String tipo;

    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void imprimir_os() {

        // imprimindo uma OS.
        int confirmacao = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta OS?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
               // usando a classe HashMap para criar um filtro.
               
               HashMap filtro = new HashMap();
               filtro.put("os",Integer.parseInt(txtOsOs.getText()));
                
                JasperPrint print = JasperFillManager.fillReport("C:/relatorios/os.jasper", filtro, conexao);

                // a linha abaixo exibe o relatório através da classe JasperViewer
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void excluir_os() {
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar a OS?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOsOs.getText());

                int deletar = pst.executeUpdate();

                if (deletar > 0) {
                    JOptionPane.showMessageDialog(null, "OS removida com sucesso.");

                    btnAddOs.setEnabled(true);
                    txtOsCliente.setVisible(true);
                    tblOsClientes.setVisible(true);
                    txtOsDefeito.setText(null);
                    txtOsCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValor.setText(null);
                    txtOsNome.setText(null);
                    txtOsTelefone.setText(null);
                    txtOsData.setText(null);
                    txtOsOs.setText(null);
                    rbtOrc.setSelected(true);
                    cboOsSituacao.setSelectedIndex(0);

                } else {
                    JOptionPane.showMessageDialog(null, "OS não removida.");
                }
            } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e) {
                //System.out.println(e);
                JOptionPane.showMessageDialog(null, "OS inválida");
            } catch (Exception e2) {
                JOptionPane.showMessageDialog(null, e2);
            }
        }

    }

    private void atualizar_os() {
        if ((txtOsValor.getText().isEmpty())) {
            txtOsValor.setText("0");
        }
        String sql = "update tbos set os=?, servico=?, tecnico=?, valor=?, tipo=?, situacao=?, defeito=?, equipamento=? where os=?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtOsOs.getText());
            pst.setString(2, txtOsServico.getText());
            pst.setString(3, txtOsTecnico.getText());
            pst.setString(4, txtOsValor.getText());
            pst.setString(6, cboOsSituacao.getSelectedItem().toString());
            pst.setString(5, tipo);
            pst.setString(7, txtOsDefeito.getText());
            pst.setString(8, txtOsEquipamento.getText());
            pst.setString(9, txtOsOs.getText());

            if ((txtOsEquipamento.getText().isEmpty()) || (txtOsTecnico.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Favor preencher todos os campos obrigatórios");
            } else {

                int atualizacao = pst.executeUpdate();

                if (atualizacao > 0) {
                    JOptionPane.showMessageDialog(null, "Atualização exetucada com sucesso");
                    btnAddOs.setEnabled(true);
                    txtOsCliente.setVisible(true);
                    tblOsClientes.setVisible(true);
                    txtOsDefeito.setText(null);
                    txtOsCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValor.setText(null);
                    txtOsNome.setText(null);
                    txtOsTelefone.setText((null));
                    txtOsData.setText(null);
                    txtOsOs.setText(null);
                    rbtOrc.setSelected(true);
                    cboOsSituacao.setSelectedIndex(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Atualização não exetucada");
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_os() {
        String num_os = JOptionPane.showInputDialog("Numero da OS");

        String sql = "select os.data_os, os.os , os.equipamento, os.defeito, os.servico, os.tecnico, "
                + "os.valor, os.idcli, os.tipo, os.situacao, c.nomecli, c.fonecli from tbos os join tbclientes c "
                + "on c.idcli = os.idcli where os.os=" + num_os;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                btnAddOs.setEnabled(false);
                txtOsCliente.setVisible(false);
                tblOsClientes.setVisible(false);
                txtOsOs.setText(rs.getString(2));
                txtOsData.setText(rs.getString(1));
                txtOsEquipamento.setText(rs.getString(3));
                txtOsDefeito.setText(rs.getString(4));
                txtOsTecnico.setText(rs.getString(6));
                txtOsValor.setText(rs.getString(7));
                txtOsCliId.setText(rs.getString(8));
                txtOsServico.setText(rs.getString(5));
                txtOsNome.setText(rs.getString(11));
                txtOsTelefone.setText(rs.getString(12));
                //setando os radio buttons
                String rbtTipo = rs.getString(9);
                if (rbtTipo.equals("Ordem de Serviço")) {
                    rbtOs.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    rbtOrc.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOsSituacao.setSelectedItem(rs.getString(10));

            } else {
                JOptionPane.showMessageDialog(null, "OS Não Cadastrada");
            }

        } catch (Exception e) {

            System.out.println(e);
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void criarOs() {
        if ((txtOsValor.getText().isEmpty())) {
            txtOsValor.setText("0");

        }
        String sql = "insert into tbos (equipamento, defeito, servico, tecnico, valor, idcli, tipo, situacao) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtOsEquipamento.getText());
            pst.setString(2, txtOsDefeito.getText());
            pst.setString(3, txtOsServico.getText());
            pst.setString(4, txtOsTecnico.getText());

            // Replace faz a troca de virgula por ponto automaticamente.
            pst.setString(5, txtOsValor.getText().replace(",", "."));
            pst.setString(6, txtOsCliId.getText());
            pst.setString(7, tipo);
            pst.setString(8, cboOsSituacao.getSelectedItem().toString());

            if ((txtOsEquipamento.getText().isEmpty()) || (txtOsDefeito.getText().isEmpty()) || (txtOsCliId.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Favor preencher todos os campos obrigatórios");
            } else {

                int atualizado = pst.executeUpdate();

                if (atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "OS Emitida com Sucesso");

                    txtOsDefeito.setText(null);
                    txtOsCliId.setText(null);
                    txtOsEquipamento.setText(null);
                    txtOsServico.setText(null);
                    txtOsTecnico.setText(null);
                    txtOsValor.setText(null);
                    txtOsNome.setText(null);
                    txtOsTelefone.setText((null));
                    txtOsData.setText(null);
                    txtOsOs.setText(null);
                    rbtOrc.setSelected(true);
                    cboOsSituacao.setSelectedIndex(0);

                } else {
                    JOptionPane.showMessageDialog(null, "Atualização não executada");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void pesquisar_cliente() {
        String sql = "select  idcli As ID, nomecli As Nome, fonecli As Telefone from tbclientes where nomecli like?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteúdo da caixa de pesquisa para o interroga
            // atenção ao '%' continuação da Srtind acima
            pst.setString(1, txtOsCliente.getText() + "%");
            rs = pst.executeQuery();

            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
            tblOsClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_campos() {
        int setar = tblOsClientes.getSelectedRow();
        txtOsCliId.setText(tblOsClientes.getModel().getValueAt(setar, 0).toString());
        txtOsNome.setText(tblOsClientes.getModel().getValueAt(setar, 1).toString());
        txtOsTelefone.setText(tblOsClientes.getModel().getValueAt(setar, 2).toString());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOsOs = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        rbtOrc = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtOsCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtOsCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOsClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtOsEquipamento = new javax.swing.JTextField();
        txtOsDefeito = new javax.swing.JTextField();
        txtOsServico = new javax.swing.JTextField();
        txtOsTecnico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnAddOs = new javax.swing.JButton();
        btnPesOs = new javax.swing.JButton();
        btnAtuOs = new javax.swing.JButton();
        btnDelOs = new javax.swing.JButton();
        btnPrintOs = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtOsNome = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtOsTelefone = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("OS");
        setPreferredSize(new java.awt.Dimension(682, 468));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOsOs.setEditable(false);
        txtOsOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsOsActionPerformed(evt);
            }
        });

        txtOsData.setEditable(false);
        txtOsData.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtOsData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOsDataActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOrc);
        rbtOrc.setText("Orçamento");
        rbtOrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrc)
                        .addGap(37, 37, 37)
                        .addComponent(rbtOs))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsOs, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOsOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrc)
                    .addComponent(rbtOs))
                .addGap(15, 15, 15))
        );

        jLabel3.setText("Situação");

        cboOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancada", "Entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));
        cboOsSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOsSituacaoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtOsCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtOsClienteKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/searchuser.png"))); // NOI18N
        jLabel4.setMaximumSize(new java.awt.Dimension(28, 28));
        jLabel4.setMinimumSize(new java.awt.Dimension(28, 28));
        jLabel4.setPreferredSize(new java.awt.Dimension(28, 28));

        jLabel5.setText("*ID");

        txtOsCliId.setEditable(false);

        tblOsClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Fone"
            }
        ));
        tblOsClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOsClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOsClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtOsCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtOsCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOsCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtOsCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor Total");

        btnAddOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adduser.png"))); // NOI18N
        btnAddOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddOs.setPreferredSize(new java.awt.Dimension(60, 60));
        btnAddOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOsActionPerformed(evt);
            }
        });

        btnPesOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/searchuser.png"))); // NOI18N
        btnPesOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesOs.setPreferredSize(new java.awt.Dimension(60, 60));
        btnPesOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesOsActionPerformed(evt);
            }
        });

        btnAtuOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update ser.png"))); // NOI18N
        btnAtuOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtuOs.setPreferredSize(new java.awt.Dimension(60, 60));
        btnAtuOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtuOsActionPerformed(evt);
            }
        });

        btnDelOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deleteuser.png"))); // NOI18N
        btnDelOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelOs.setPreferredSize(new java.awt.Dimension(60, 60));
        btnDelOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelOsActionPerformed(evt);
            }
        });

        btnPrintOs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/print-32.png"))); // NOI18N
        btnPrintOs.setToolTipText("Imprimir OS");
        btnPrintOs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrintOs.setPreferredSize(new java.awt.Dimension(60, 60));
        btnPrintOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintOsActionPerformed(evt);
            }
        });

        jLabel11.setText("Nome Cliente");

        txtOsNome.setEditable(false);

        jLabel12.setText("Fone");

        txtOsTelefone.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(btnAddOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnPesOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnAtuOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnDelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btnPrintOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(37, 37, 37)
                                .addComponent(cboOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel11)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsValor))
                            .addComponent(txtOsServico)
                            .addComponent(txtOsDefeito)
                            .addComponent(txtOsEquipamento)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsNome, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(12, 12, 12)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtOsNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOsTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtuOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrintOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        setBounds(0, 0, 682, 455);
    }// </editor-fold>//GEN-END:initComponents

    private void txtOsOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsOsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsOsActionPerformed

    private void txtOsDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOsDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOsDataActionPerformed

    private void btnAddOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOsActionPerformed
        criarOs();
    }//GEN-LAST:event_btnAddOsActionPerformed

    private void cboOsSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOsSituacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboOsSituacaoActionPerformed

    private void txtOsClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOsClienteKeyReleased
        pesquisar_cliente();
    }//GEN-LAST:event_txtOsClienteKeyReleased

    private void tblOsClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOsClientesMouseClicked
        setar_campos();

    }//GEN-LAST:event_tblOsClientesMouseClicked

    private void rbtOrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcActionPerformed
        // a linha abaixo atribui um texto a variável tipo se o radio button estiver selecionado.
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtOrcActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // a linha abaixo atribui um texto a variável tipo se o radio button estiver selecionado.
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o form marcar o radio button.
        rbtOrc.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnPesOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesOsActionPerformed
        pesquisar_os();
    }//GEN-LAST:event_btnPesOsActionPerformed

    private void btnAtuOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtuOsActionPerformed
        atualizar_os();
    }//GEN-LAST:event_btnAtuOsActionPerformed

    private void btnDelOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelOsActionPerformed
        excluir_os();
    }//GEN-LAST:event_btnDelOsActionPerformed

    private void btnPrintOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintOsActionPerformed
        imprimir_os();
    }//GEN-LAST:event_btnPrintOsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOs;
    private javax.swing.JButton btnAtuOs;
    private javax.swing.JButton btnDelOs;
    private javax.swing.JButton btnPesOs;
    private javax.swing.JButton btnPrintOs;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOrc;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JTable tblOsClientes;
    private javax.swing.JTextField txtOsCliId;
    private javax.swing.JTextField txtOsCliente;
    private javax.swing.JTextField txtOsData;
    private javax.swing.JTextField txtOsDefeito;
    private javax.swing.JTextField txtOsEquipamento;
    private javax.swing.JTextField txtOsNome;
    private javax.swing.JTextField txtOsOs;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsTecnico;
    private javax.swing.JTextField txtOsTelefone;
    private javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
