/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;

/**
 *
 * @author estef
 */
public class ControladorVistas {

  /**
   * Este método extrae de la tabla todos lo estados de aceptación y los lleva a un arreglo.
   * @param automata Recibe el autómata con el que se está trabajando
   * @param dtm REcibe la tabla en la que se está trabajando
   * @return Un arreglo de String con todos los estados que son de aceptación.
   */
    public String[] extraerEstadosAceptación(AutomataF automata, DefaultTableModel dtm) {
        ArrayList<String> estAcp = new ArrayList<>();
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, automata.getSimbolos().length + 1);
            if (indicador.compareTo("1") == 0) {
                estAcp.add((String) dtm.getValueAt(i, 0));
            }
        }
        String[] estadosAcp = new String[estAcp.size()];
        for (int i = 0; i < estadosAcp.length; i++) {
            estadosAcp[i] = estAcp.get(i);
        }
        return estadosAcp;
    }
    
       public boolean definirEstadoAceptacion(String estado, AutomataF automata) {
        boolean b = false;
        for (int i = 0; i < automata.getEstadosAceptacion().length; i++) {
            if (automata.getEstadosAceptacion()[i].equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }

     public void llenarTabla(JTable tabla, AutomataF automata,DefaultTableModel dtm) {
        String[] simbolosEntrando = automata.getSimbolos();
        String[] estados = automata.getEstados();
        ArrayList<ArrayList> automataN = automata.getTransiciones();
        String[] simbolosArr = new String[simbolosEntrando.length + 3];
        simbolosArr[0] = "Estados";
        for (int sym = 1; sym < simbolosArr.length - 2; sym++) {
            simbolosArr[sym] = simbolosEntrando[sym - 1];
        }
        simbolosArr[simbolosArr.length - 2] = "E.A.";
        simbolosArr[simbolosArr.length - 1] = "E.I.";
        dtm = new DefaultTableModel(simbolosArr, 0);

        for (int i = 0; i < estados.length; i++) {
            String[] fila = new String[simbolosArr.length];
            fila[0] = estados[i];
            ArrayList<String> transiciones = automataN.get(i);
            for (int j = 0; j < transiciones.size(); j++) {
                fila[j + 1] = transiciones.get(j);
            }
            if (definirEstadoAceptacion(estados[i],automata)) {
                fila[simbolosArr.length - 2] = "1";
            } else {
                fila[simbolosArr.length - 2] = "0";
            }
            if(i == 0){
                fila[simbolosArr.length - 1] = "#";
            }
            dtm.addRow(fila);
        }
        tabla.setModel(dtm);
    }
}
