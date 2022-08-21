package view;

import util.Rubricas;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Rubrica;
import modelo.RubricaConfiguracaoDAO;
import modelo.RubricaRef;
import modelo.RubricaRefDAO;
import util.Config;

/**
 *
 * @author Gabriel Moraes
 */
public class InternaRefRubrica extends javax.swing.JInternalFrame {

    JComboBox<String> jComboBox1 = new JComboBox<>();
    TableRowSorter trs;
    DefaultTableModel modelo;

    public InternaRefRubrica(ArrayList<Rubrica> lista) {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ignorar", "Referenciar", "Criar"}));
        setFrameIcon(new javax.swing.ImageIcon(new Config().getIcon()));
        initComponents();
        carregar(lista);
    }

    public final void carregar(ArrayList<Rubrica> lista) {
        modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setNumRows(0);
        
        ArrayList<String> filtro = new ArrayList<>();

        filtro.add("1");
        filtro.add("2");
        filtro.add("49");
        filtro.add("50");
        filtro.add("60");
        filtro.add("61");
        filtro.add("62");
        filtro.add("65");
        filtro.add("66");
        filtro.add("67");
        filtro.add("69");
        filtro.add("70");
        filtro.add("71");
        filtro.add("73");
        filtro.add("74");
        filtro.add("81");
        filtro.add("85");
        filtro.add("87");
        filtro.add("93");
        filtro.add("94");
        //filtro.add("97"); PA
        filtro.add("112");
        filtro.add("142");
        filtro.add("150");
        
        for (Rubrica r : lista) {
            String cnpj = r.getCnpj();

            if (cnpj == null) {
                cnpj = "-";
            }
            
            //Aplicar Filtro
            if(!filtro.contains(r.getNatureza())) {
                modelo.addRow(
                        new Object[]{
                            cnpj,
                            r.getCodigo(),
                            r.getDescricao(),
                            "Ignorar",
                            ""
                        }
                );
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Seleção de Rubrica");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opções"));

        jLabel1.setText("Opção: Ignorar - Lançamentos com essa rubrica serão ignorados.");

        jLabel2.setText("Opção: Referenciar - Lançamentos com essa rubrica serão referenciados para rubrica informada no campo CodReferencia.");

        jLabel3.setText("Opção: Criar - Será criado uma nova rubrica para ser importados os lançamentos.");

        jButton1.setText("Salvar");
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CNPJ", "CodRubrica", "Descrição", "Opção", "CodReferencia"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setRowHeight(23);
        jTable1.setRowSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(75);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(325);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(jComboBox1));
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 331, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ArrayList<RubricaRef> lista = new ArrayList<>();
        int codDominio = 300;
        
        for(int i = 0; i < jTable1.getModel().getRowCount(); i++) {
            RubricaRef rr = new RubricaRef();
            for(int j = 0; j < jTable1.getModel().getColumnCount(); j++) {
                switch(j) {
                    case 0:
                        if(!"-".equals((String) jTable1.getModel().getValueAt(i, j))) {
                            rr.setCnpj((String) jTable1.getModel().getValueAt(i, j));
                        }
                        break;
                    case 1:
                        rr.setCodConcorrente((String) jTable1.getModel().getValueAt(i, j));
                        break;
                    case 3:
                        rr.setTipo((String) jTable1.getModel().getValueAt(i, j));
                        break;
                    case 4:
                        if("Criar".equals(rr.getTipo())) {
                            rr.setCodDominio(String.valueOf(codDominio));
                            codDominio++;
                        } else {
                            rr.setCodDominio((String) jTable1.getModel().getValueAt(i, j));
                        }
                        break;
                }
            }
            
            if("Referenciar".equals(rr.getTipo()) || "Criar".equals(rr.getTipo())) {
                lista.add(rr);
            }
        }
        
        RubricaRefDAO rrDAO = new RubricaRefDAO();
        rrDAO.inserir(lista, Config.conversao.getId());
        
        //Gravas as Rubricas
        Rubricas rubricas = new Rubricas();
        RubricaConfiguracaoDAO rcDAO = new RubricaConfiguracaoDAO();
        
        ArrayList<String> listaRubCriar = new ArrayList<>();
        
        for(RubricaRef rr : rrDAO.carregarPorConversao(Config.conversao.getId())) {
            if("Criar".equals(rr.getTipo())) {
                listaRubCriar.add(rr.getCodConcorrente());
            }
        }
        
        if(!listaRubCriar.isEmpty()) {
            rcDAO.inserir(rubricas.gerar(Config.conversao, rubricas.returno(Config.conversao.getId(), listaRubCriar)), Config.conversao.getId());
        }
        
        JOptionPane.showMessageDialog(InternaRefRubrica.this, "Gravado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
