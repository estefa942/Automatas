/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControladorAutomata;
//import controlador.ControladorEntradaDatos;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;

/**
 *
 * @author alejandro.castanor
 */
public class PantallaIngreso extends javax.swing.JFrame {

    JFileChooser abrirArchivo = new JFileChooser();
    File archivo;
    ControladorAutomata ca;
//    ControladorEntradaDatos ced;
    AutomataF af = new AutomataF();
    Vector vEstados = new Vector();
    private String[] estadosAceptacion;
    DefaultTableModel dtm;

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
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEstados = new javax.swing.JTable();
        txtSimbolos = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtEstados = new javax.swing.JTextField();
        btnIngreso = new javax.swing.JButton();
        btnConversor = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnOperar = new javax.swing.JButton();
        btnArchivo = new javax.swing.JButton();
        panel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaNuevoAutomata = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(826, 504));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Ingrese los símbolos de entrada, separados por coma.");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, -1, -1));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 380, -1));

        txtSimbolos.setText("0,1");
        txtSimbolos.setToolTipText("");
        getContentPane().add(txtSimbolos, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 240, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ingrese los estados, seguidos por coma:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        txtEstados.setText("a,b,c");
        txtEstados.setToolTipText("");
        getContentPane().add(txtEstados, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 240, -1));

        btnIngreso.setText("Ingresar");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });
        getContentPane().add(btnIngreso, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 100, -1));

        btnConversor.setText("AFND a AF");
        btnConversor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConversorActionPerformed(evt);
            }
        });
        getContentPane().add(btnConversor, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 240, 30));

        jButton1.setText("extraño");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 460, 80, -1));

        btnOperar.setText("Operar");
        btnOperar.setActionCommand("Operar ");
        btnOperar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOperarActionPerformed(evt);
            }
        });
        getContentPane().add(btnOperar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 460, 130, -1));

        btnArchivo.setText("Abrir archivo");
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });
        getContentPane().add(btnArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, 110, -1));
        getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 360, 400));

        tablaNuevoAutomata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaNuevoAutomata);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 50, 370, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        String[] simbolosEntrando = txtSimbolos.getText().split(",");
        String[] simbolosArr = new String[simbolosEntrando.length + 2];
        simbolosArr[0] = "Estados";
        for (int sym = 1; sym < simbolosArr.length - 1; sym++) {
            simbolosArr[sym] = simbolosEntrando[sym - 1];
        }
        simbolosArr[simbolosArr.length - 1] = "E.A.";
        String[] estados = txtEstados.getText().split(",");
        dtm = new DefaultTableModel(simbolosArr, 0);

        for (int machete = 0; machete < estados.length; machete++) {
            String[] charles = new String[1];
            charles[0] = estados[machete];
            dtm.addRow(charles);
            vEstados.add(estados[machete]);
        }
        af.setEstados(estados);
        af.setSimbolos(simbolosEntrando);
        tablaEstados.setModel(dtm);
        ca = new ControladorAutomata(af, dtm);
        JOptionPane.showMessageDialog(rootPane, "Señor usuario, si desea operar con el automata finito (AF) \ntiene que llenar la tabla con las respectivas transiciones\ny luego hacer clic en el boton 'Operar'");
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnConversorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConversorActionPerformed
        // TODO add your handling code here:
        if (ca.esDeterministico() == false) {
            ca.imprimir(ca.convertirEnDeterministico());
        }
        String[] simbolosEntrando = af.getSimbolos();
        String[] estados = af.getEstados();
        ArrayList<ArrayList> automata = af.getTransiciones();
        String[] simbolosArr = new String[simbolosEntrando.length + 2];
        simbolosArr[0] = "Estados";
        for (int sym = 1; sym < simbolosArr.length - 1; sym++) {
            simbolosArr[sym] = simbolosEntrando[sym - 1];
        }
        simbolosArr[simbolosArr.length - 1] = "E.A.";
        dtm = new DefaultTableModel(simbolosArr, 0);
        
        for (int i = 0; i < estados.length; i++) {
            String[] fila= new String [simbolosArr.length];
            fila[0] = estados[i];
            ArrayList<String> transiciones= automata.get(i);
            for (int j = 0; j < transiciones.size(); j++) {
                fila[j+1]=transiciones.get(j);
            }
            if(ca.esEstadoDeAceptacion(estados[i])){
            fila[estados.length-1]="1";
                    }else{
                fila[estados.length-1]="0";
            }
            dtm.addRow(fila);
        }
        tablaNuevoAutomata.setModel(dtm);
    }//GEN-LAST:event_btnConversorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Probando
//        ca.llenarVisitados();
//        ca.estadosExtraños(0);
//        ca.imprimir(af.getAutomataSinExtraños());
     String a ="1000011*";
     if(ca.verificarHilera(a)){
         System.out.println("acepta");
     }else{
         System.out.println("rechaza");
     }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnOperarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperarActionPerformed
        af.setTransiciones(ca.guardarAutomata());
        ca.imprimir(ca.guardarAutomata());
        if (ca.esDeterministico()) {
            JOptionPane.showMessageDialog(rootPane, "El autómata es deterministico");
        } else {
            JOptionPane.showMessageDialog(rootPane, "El autómata es no deterministico");
        }
        ca.EstadosAceptacion();

    }//GEN-LAST:event_btnOperarActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        String[] estadotes;
        String[] ouch = null;
        if (abrirArchivo.showDialog(null, "ABRIR ARCHIVO") == JFileChooser.APPROVE_OPTION) {
            archivo = abrirArchivo.getSelectedFile();
            if (archivo.canRead()) {
                if (archivo.getName().endsWith("txt")) {
                    try {
                        File fichero_entrada;
                        fichero_entrada = new File(archivo.getAbsolutePath());
                        Scanner scaneoPapu = new Scanner(fichero_entrada);
                        Vector<String> datosDeEntrada = new Vector<String>();
                        String[] slapChop;
                        String symbols = "";
                        int contador = 0;
                        while (scaneoPapu.hasNext()) {
                            String lineaExtraida = scaneoPapu.nextLine();
                            switch (contador) {
                                case 0:
                                    slapChop = lineaExtraida.split(":");
                                    lineaExtraida = slapChop[1];
                                    symbols = lineaExtraida;
                                    txtSimbolos.setText(lineaExtraida);
                                    break;
                                case 1:
                                    slapChop = lineaExtraida.split(":");
                                    lineaExtraida = slapChop[1];
                                    txtEstados.setText(lineaExtraida);
                                    ouch = symbols.split(",");
                                    slapChop = new String[ouch.length + 2];
                                    slapChop[0] = "Estados";
                                    for (int i = 0; i < ouch.length; i++) {
                                        slapChop[i+1] = ouch[i];
                                    }
                                    slapChop[slapChop.length - 1] = "E.A.";
                                    dtm = new DefaultTableModel(slapChop,0);
                                    break;
                                default:
                                    slapChop = lineaExtraida.split(":");
                                    lineaExtraida = slapChop[0] + "," + slapChop[1];
                                    slapChop = lineaExtraida.split(",");
                                    dtm.addRow(slapChop);
                                    tablaEstados.setModel(dtm);
                                    break;
                            }
                            contador++;
                            datosDeEntrada.add(lineaExtraida);
                        }
                        estadotes =  txtEstados.getText().split(",");
                        af.setEstados(estadotes);
                        af.setSimbolos(ouch);
                        ca = new ControladorAutomata(af, dtm);
                        JOptionPane.showMessageDialog(rootPane, "Señor usuario, si desea operar con el automata finito (AF) \ntiene que llenar la tabla con las respectivas transiciones\ny luego hacer clic en el boton 'Operar'");
                    } catch (Exception e) {
                        System.out.println("Se ha producido un error " + e + ". Revise argumentos y datos");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un archivo de texto.");
            }
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaIngreso.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaIngreso.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnConversor;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton btnOperar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panel2;
    private javax.swing.JTable tablaEstados;
    private javax.swing.JTable tablaNuevoAutomata;
    private javax.swing.JTextField txtEstados;
    private javax.swing.JTextField txtSimbolos;
    // End of variables declaration//GEN-END:variables
}
