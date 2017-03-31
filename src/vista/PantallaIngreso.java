/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ACER
 */
public class PantallaIngreso extends javax.swing.JFrame {

    /**
     * Creates new form PantallaIngreso
     */
    public PantallaIngreso() {
        initComponents();
        setLocationRelativeTo(null);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        txtSimbolos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEstados = new javax.swing.JTextField();
        panelMostrador = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEstados = new javax.swing.JTable();
        btn_aceptar = new javax.swing.JButton();
        Fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(810, 530));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ingrese los simbolos de entrada, seguidos por coma:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 130, 260, 17);
        getContentPane().add(txtSimbolos);
        txtSimbolos.setBounds(30, 160, 210, 27);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ingrese los estados, seguidos por coma:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 230, 260, 17);
        getContentPane().add(txtEstados);
        txtEstados.setBounds(30, 260, 210, 27);

        panelMostrador.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaEstados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaEstados);

        panelMostrador.add(jScrollPane2);

        getContentPane().add(panelMostrador);
        panelMostrador.setBounds(330, 60, 450, 380);

        btn_aceptar.setText("Aceptar");
        btn_aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aceptarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_aceptar);
        btn_aceptar.setBounds(96, 350, 110, 31);

        Fondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/fondo2.jpg"))); // NOI18N
        getContentPane().add(Fondo);
        Fondo.setBounds(0, 0, 810, 529);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_aceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aceptarActionPerformed
        // TODO add your handling code here:
        String[] simbolosEntrando = txtSimbolos.getText().split(",");
        String[] simbolos = new String[simbolosEntrando.length + 2];
        simbolos[0] = "Estados";
        for (int sym = 1; sym < simbolos.length - 1; sym++) {
            simbolos[sym] = simbolosEntrando[sym - 1];
        }
        simbolos[simbolos.length - 1] = "E.A.";
        String[] estados = txtEstados.getText().split(",");
        
        DefaultTableModel dtm = new DefaultTableModel(simbolos, 0);
        tablaEstados.setModel(dtm);
        for (int machete = 0; machete < estados.length; machete++) {
            String[] charles = new String[1];
            charles[0] = estados[machete];
            dtm.addRow(charles);
        }
    }//GEN-LAST:event_btn_aceptarActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaIngreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Fondo;
    private javax.swing.JButton btn_aceptar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelMostrador;
    private javax.swing.JTable tablaEstados;
    private javax.swing.JTextField txtEstados;
    private javax.swing.JTextField txtSimbolos;
    // End of variables declaration//GEN-END:variables
}
