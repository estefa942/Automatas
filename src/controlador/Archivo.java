/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;

/**
 *
 * @author Alejo Castaño Rojas
 */
public class Archivo {

    /**
     * Constructor de la clase Archivo
     */
    public Archivo() {
    }
    
    /**
     * Este método recolecta toda la información pertinente para el usuario y
     * para el sistema, la guarda en la ubicación seleccionada por el usuario
     * de tal modo que pueda ser ingresado desde allí posteriormente.
     * @param automata
     * @param dtm 
     */
    public void GuardarEnTXT(AutomataF automata, DefaultTableModel dtm){
        String ruta = "";
        JFileChooser jfc = new JFileChooser();
        try {
            if (jfc.showSaveDialog(null) == jfc.APPROVE_OPTION) {
                ruta = jfc.getSelectedFile().getAbsolutePath();
                File archivo = new File(ruta);
                FileWriter fw = new FileWriter(archivo);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter pw = new PrintWriter(bw);
                if (archivo.exists()) {
                    archivo.delete();
                }
                pw.print("simbolos:");
                for (int i = 0; i < automata.getSimbolos().length; i++) {
                    if (i != automata.getSimbolos().length - 1) {
                        pw.print(automata.getSimbolos()[i] + ",");
                    } else {
                        pw.print(automata.getSimbolos()[i]);
                        pw.println();
                    }
                }
                pw.print("estados:");
                for (int i = 0; i < automata.getEstados().length; i++) {
                    if (i != automata.getEstados().length - 1) {
                        pw.print(automata.getEstados()[i] + ",");
                    } else {
                        pw.print(automata.getEstados()[i]);
                        pw.println();
                    }
                }
                for (int i = 0; i < dtm.getRowCount(); i++) {
                    for (int j = 0; j < dtm.getColumnCount(); j++) {
                        if (j == 0) {
                            pw.print(dtm.getValueAt(i, j).toString() + ":");
                        } else if (j == dtm.getColumnCount() - 1) {
                            if (dtm.getValueAt(i, j) != null) {
                                pw.print("#");
                                pw.println();
                            } else {
                                pw.print("\u0020");
                                pw.println();
                            }
                        } else {
                            pw.print(dtm.getValueAt(i, j).toString() + ",");
                        }
                    }
                }
                pw.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Este método recolecta toda la información pertinente para el usuario y
     * para el sistema, la guarda en formato de impresión PDF en la ubicación
     * seleccionada por el usuario.
     * @param automata
     * @param dtm 
     */
    public void GuardarEnPDF(AutomataF automata, DefaultTableModel dtm){
        String ruta = "";
        JFileChooser jfc = new JFileChooser();
        if (jfc.showSaveDialog(jfc) == jfc.APPROVE_OPTION) {
            ruta = jfc.getSelectedFile().getAbsolutePath();
            File archivo = new File(ruta);
            String contenido = "";
            //Aquí debe ir entonces lo que es escritura de archivo
            if (archivo.exists()) {
                archivo.delete();
            }
            contenido += "simbolos:";
            for (int i = 0; i < automata.getSimbolos().length; i++) {
                if (i != automata.getSimbolos().length - 1) {
                    contenido += automata.getSimbolos()[i] + ",";
                } else {
                    contenido += automata.getSimbolos()[i] + "\n";
                }
            }
            contenido += "estados:";
            for (int i = 0; i < automata.getEstados().length; i++) {
                if (i != automata.getEstados().length - 1) {
                    contenido += automata.getEstados()[i] + ",";
                } else {
                    contenido += automata.getEstados()[i] + "\n";
                }
            }
            for (int i = 0; i < dtm.getRowCount(); i++) {
                for (int j = 0; j < dtm.getColumnCount(); j++) {
                    if (j == 0) {
                        contenido += dtm.getValueAt(i, j).toString() + ":";
                    } else if (j == dtm.getColumnCount() - 1) {
                        if (dtm.getValueAt(i, j) != null) {
                            contenido += "#\n";
                        } else {
                            contenido += "\u0020" + "\n";
                        }
                    } else {
                        contenido += dtm.getValueAt(i, j).toString() + ",";
                    }
                }
            }
            try {
                FileOutputStream out = new FileOutputStream(ruta + ".pdf");
                Document doc = new Document();
                PdfWriter.getInstance(doc, out);
                doc.open();
                doc.add(new Paragraph(contenido));
                doc.close();
                JOptionPane msg = new JOptionPane("¡PDF correctamente creado!");
            } catch (Exception e) {
                JOptionPane msg = new JOptionPane("No se pudo crear el PDF");
            }
        }
    }
    
}
