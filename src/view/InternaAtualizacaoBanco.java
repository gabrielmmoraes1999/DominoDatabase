package view;

import database.Firebird;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import modelo.Backup;
import modelo.BackupDAO;
import modelo.NovidadeDAO;
import modelo.VersaoBancoDAO;
import util.Config;
import util.Sistema;
import static util.Sistema.decrypt;

/**
 *
 * @author Gabriel Moraes
 */
public final class InternaAtualizacaoBanco extends javax.swing.JInternalFrame {

    Sistema s = new Sistema();
    VersaoBancoDAO vbDAO = new VersaoBancoDAO();
    NovidadeDAO nDAO = new NovidadeDAO();
    
    public InternaAtualizacaoBanco() {
        setFrameIcon(new javax.swing.ImageIcon(new Config().getIcon()));
        initComponents();
        carregar();
    }
    
    public void carregar() {
        //jLabel3.setText(String.valueOf(Config.codPublic));
        jLabel6.setText(String.valueOf(Config.versaoBancoDados));
        jLabel5.setText(String.valueOf(vbDAO.retornaVersaoAtualBanco()));
    }
    
    public void atualizar() {
        int versaoAtual = vbDAO.retornaVersaoAtualBanco();
        Pattern pattern = Pattern.compile("([^;])+");
        
        Savepoint sp = null;
        Firebird fb = new Firebird();
        
        try {
            fb.connect();
            Statement stmt = Firebird.conn.createStatement();
            
            while(versaoAtual < Config.versaoBancoDados) {
                File diretorio = new File(System.getProperty("user.dir"));
                
                for (File arquivo : diretorio.listFiles()) {
                    if(arquivo.getPath().endsWith("db"+s.completeToLeft(String.valueOf(versaoAtual+1), '0', 4)+".ver")) {
                        //Executar o comando de atualização
                        String sql = decrypt(Files.readAllBytes(Paths.get(arquivo.getAbsolutePath())));
                        Matcher matcher = pattern.matcher(sql);
                        
                        while(matcher.find()) {
                            String sqlSeparado = sql.substring(matcher.start(), matcher.end());
                            stmt.execute(sqlSeparado);
                            
                            if(sqlSeparado.toLowerCase().contains("CREATE TABLE".toLowerCase()) || sqlSeparado.toLowerCase().contains("ALTER".toLowerCase())) {
                                Firebird.conn.commit();
                            }
                        }
                        
                        sp = Firebird.conn.setSavepoint();
                        break;
                    }
                }

                versaoAtual++;
            }
            
            Firebird.conn.commit();
        } catch (SQLException ex) {
            try {
                Firebird.conn.rollback(sp);
                Firebird.conn.commit();
                JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a atualização do banco de dados!"
                        + "\nRollback foi aplicado!", "Rollback!", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            } catch (SQLException ex1) {
                if(sp != null) {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a atualização do banco de dados!"
                            + "\nNão foi possível realizar o Rollback!\nPor favor, restaure o último backup.", "Falha Crítica!", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Ocorreu um erro durante a atualização do banco de dados!"
                        + "\nAs modificações não foram aplicadas!", "Erro!", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            }
        } catch (Exception ex) {
            new TelaErro(this, 1, ex.getStackTrace()).setVisible(true);
        }
        
        fb.disconnect();
        
        JOptionPane.showMessageDialog(this, "Atualização realizada com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
        dispose();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setTitle("Atualização do Banco de Dados");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Versões:"));

        jLabel2.setText("Versão atual do Banco:");

        jLabel4.setText("Nova Versão Banco:");
        jLabel4.setToolTipText("");

        jLabel5.setForeground(new java.awt.Color(204, 0, 0));
        jLabel5.setText("Versão:");

        jLabel6.setForeground(new java.awt.Color(0, 153, 0));
        jLabel6.setText("Versão:");

        jButton1.setText("Iniciar Atualização");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Fechar");
        jButton2.setFocusable(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Iniciar Backup");
        jButton3.setFocusable(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("OBS: Antes de realizar a atualização do banco recomendados que realize o backup!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        atualizar();
        
        //Abri janela de novidades apos atualização
        InternaNoticias in = new InternaNoticias(nDAO.carregarUltimaNovidade());
        TelaPrincipal.jPainelPrincipal.add(in);
        in.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(Config.developer) {
            dispose();
        } else {
            System.exit(0);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Sistema.backup();
        Backup b = new Backup();
        BackupDAO bDAO = new BackupDAO();
        Timestamp ts = new Timestamp(new Date().getTime());
        b.setData(ts);
        bDAO.inserir(b);
        JOptionPane.showMessageDialog(this, "Backup realizado com sucesso!","Sucesso!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
