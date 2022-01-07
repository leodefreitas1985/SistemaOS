/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.telas;

import java.sql.*;

import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;

// a linha abaixo importa recusrsos da biblioteca rs2xml.jar
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void addCliente() {
        String sql = "insert into tbclientes (nomecli,endcli,fonecli,emailcli)values(?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText().toUpperCase());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliTelefone.getText());
            pst.setString(4, txtCliEmail.getText());

            if ((txtCliNome.getText().isEmpty()) || (txtCliTelefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Favor preencher todos os campos obrigatórios");
            } else {
                int adicionar = pst.executeUpdate();

                if (adicionar > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com Sucesso");
                    txtCliNome.setText(null);
                    txtCliTelefone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliEmail.setText(null);
                    txtCliId.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Cliente não adicionado");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void delCliente() {
        int sair = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja deletar o cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (sair == JOptionPane.YES_OPTION) {
            String sql = "delete from tbclientes where idcli=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());

                int deletar = pst.executeUpdate();

                if (deletar > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário removido com sucesso");
                    txtCliNome.setText(null);
                    txtCliEmail.setText(null);
                    txtCliTelefone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliId.setText(null);

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void atuCliente() {
        String sql = "update tbclientes set idcli=?, nomecli=?, endcli=?, fonecli=?, emailcli=? where idcli=?";
        try {

            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCliId.getText());
            pst.setString(2, txtCliNome.getText().toUpperCase());
            pst.setString(3, txtCliEndereco.getText());
            pst.setString(4, txtCliTelefone.getText());
            pst.setString(5, txtCliEmail.getText());
            pst.setString(6, txtCliId.getText());

            if ((txtCliNome.getText().isEmpty()) || (txtCliTelefone.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Favor preencher todos os campos obrigatórios");
            } else {

                int atualizado = pst.executeUpdate();

                if (atualizado > 0) {
                    JOptionPane.showMessageDialog(null, "Atualização executada com sucesso");
                    txtCliNome.setText(null);
                    txtCliTelefone.setText(null);
                    txtCliEndereco.setText(null);
                    txtCliEmail.setText(null);
                    txtCliId.setText(null);
                    txtCliPesquisar.setText(null);
                    btnCliAdicionar.setEnabled(true);

                    

                } else {
                    JOptionPane.showMessageDialog(null, "Atualização não executada");
                }
            }
            String setar = "";
            txtCliPesquisar.setText(setar);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesCliente() {
        String sql = "select  idcli As ID, nomecli As Nome, endcli As Endereço, emailcli As Email, fonecli As Telefone from tbclientes where nomecli like?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteúdo da caixa de pesquisa para o interroga
            // atenção ao '%' continuação da Srtind acima
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();

            // a linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela.
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metodo para setar os campos do formulário com o conteúdo da tabela
    public void setarCampos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliEmail.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliTelefone.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        txtCliPesquisar.setText(tblClientes.getModel().getValueAt(setar, 1).toString());

        // a linha abaixo desabilita o botão adicionar
        btnCliAdicionar.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliEmail = new javax.swing.JTextField();
        txtCliTelefone = new javax.swing.JTextField();
        btnCliAdicionar = new javax.swing.JButton();
        btnCliAtualizar = new javax.swing.JButton();
        btnCliDeletar = new javax.swing.JButton();
        txtCliPesquisar = new javax.swing.JTextField();
        btnCliPesquisar = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();

        jTextField1.setText("jTextField1");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setClosable(true);
        setTitle("Cadastro de Cliente");
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(682, 468));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cadastro de Cliente");

        jLabel1.setText("* Campos Obrigatórios");

        jLabel3.setText("* Nome");

        jLabel4.setText("Endereço");

        jLabel5.setText("Email");

        jLabel6.setText("* Telefone");

        btnCliAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/adduser.png"))); // NOI18N
        btnCliAdicionar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCliAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAdicionarActionPerformed(evt);
            }
        });

        btnCliAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/update ser.png"))); // NOI18N
        btnCliAtualizar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCliAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAtualizarActionPerformed(evt);
            }
        });

        btnCliDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/deleteuser.png"))); // NOI18N
        btnCliDeletar.setPreferredSize(new java.awt.Dimension(80, 80));
        btnCliDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliDeletarActionPerformed(evt);
            }
        });

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        btnCliPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/infox/icones/searchuser.png"))); // NOI18N

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblClientes);

        jLabel7.setText("ID");

        txtCliId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(112, 112, 112)
                .addComponent(btnCliAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnCliAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCliPesquisar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliEndereco)
                                    .addComponent(txtCliEmail)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtCliNome, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(btnCliPesquisar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCliEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCliTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCliAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCliDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(430, 430, 430))
        );

        setBounds(0, 0, 682, 455);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAdicionarActionPerformed
        addCliente();
    }//GEN-LAST:event_btnCliAdicionarActionPerformed

    private void btnCliDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliDeletarActionPerformed
        delCliente();
    }//GEN-LAST:event_btnCliDeletarActionPerformed

    private void btnCliAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAtualizarActionPerformed
        atuCliente();
    }//GEN-LAST:event_btnCliAtualizarActionPerformed
// o evento abaixo é do tipo "enquanto dor digitando"
    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        pesCliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliAdicionar;
    private javax.swing.JButton btnCliAtualizar;
    private javax.swing.JButton btnCliDeletar;
    private javax.swing.JLabel btnCliPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEmail;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliTelefone;
    // End of variables declaration//GEN-END:variables
}
