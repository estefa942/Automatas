/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;
/**
 *
 * @author ACER
 */
public class ControladorAutomata {
    AutomataF af;
    String[] estados;
    String[] simbolos;
    //Para probar
    ArrayList<ArrayList> automataNuevo = new ArrayList<>();
    int[] visitados ;

    private String[] estadosAceptacion;
    DefaultTableModel dtm;

    public ControladorAutomata(AutomataF af, DefaultTableModel dtm) {
        this.af = af;
        this.dtm = dtm;
        estados = af.getEstados();
        simbolos = af.getSimbolos();
    }
      
    
    public int convertirSimbolos(String a){
        int valor=0;
        for(int i=0;i<simbolos.length;i++){
            if(simbolos[i].equals(a)){
                valor=i;
            }
        }
        return valor;
    }
    
    public int convertirEstados(String a) {
        int valor = 0;
        for (int i = 0; i < af.getEstados().length; i++) {
            if (af.getEstados()[i].equals(a)) {
                valor = i;

            }
        }
        return valor;
    }
    //probar
    public void llenarVisitados(){
        visitados = new int[af.getTransiciones().size()];
        for (int i = 0; i < visitados.length; i++) {
            visitados[i]=0;
        }
    }
    
    public ArrayList<ArrayList> guardarAutomata() {

        ArrayList<ArrayList> automata = new ArrayList<>();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            ArrayList<String> transiciones = new ArrayList<>();
            for (int j = 0; j < af.getSimbolos().length; j++) {
                String estado = (String) dtm.getValueAt(i, j + 1);

                if (estado != null) {
                    transiciones.add(estado);
                } else {
                    transiciones.add("Error");
                }
            }
             automata.add(transiciones);
        }
        return automata;
    }
    
    public void imprimir(ArrayList<ArrayList>a){
        for (int i = 0; i < a.size(); i++) {
            ArrayList b= a.get(i);
            for (int j = 0; j < b.size(); j++) {
                System.out.print("|"+b.get(j)+"|");
            }
            System.out.println("");
        }
    }
    
    public String[] EstadosAceptacion() {
        String[] estadosAceptacion = new String[af.getEstados().length];
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 1);
            estadosAceptacion[i] = indicador;
        }
        return estadosAceptacion;
    }

    public boolean esDeterministico() {
        int count = 0;
        boolean b = true;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            for (int j = 0; j < af.getSimbolos().length; j++) {
                String estado = (String) dtm.getValueAt(i, j + 1);
                if (estado != null) {
                    if (estado.indexOf(",") != -1) {
                        b = false;
                        break;
                    }
                }
            }
        }
        if (b) {
            return b;
        } else {
            return b;
        }
    }

    public void estadosExtraños(int p){
        visitados[p]=1;
        automataNuevo.add(af.getTransiciones().get(p));
        for (int i = 0; i < af.getSimbolos().length; i++) {
            String b = (String) af.getTransiciones().get(p).get(i);
             if(visitados[convertirEstados(b)]==0){
                estadosExtraños(convertirEstados(b));
            }
        }
    }
    
//    
//    public int [][] matrizAutomata( DefaultTableModel dtm){
//        
//        int[][] ma = new int[estados.length][simbolos.length];
//        for (int i = 0; i < dtm.getRowCount() ; i++) {
//            for (int j = 0; j < simbolos.length; j++) {
////                System.out.println(dtm.getColumnCount());
////                System.out.println(j);
//                String estado = (String) dtm.getValueAt(i,j+1);
//                if(estado!=null){
//                    int numEstado = convertirEstados(estado);
//                    ma[i][j]=numEstado;
//                }else{
//                    ma[i][j]=-1;
//                }
//                System.out.println(ma[i][j]);
//            }
//        }
//        return ma;
//    }
//    
//    public boolean esDeterministico(DefaultTableModel dtm){
//        int count=0;
//        boolean b=true;
//        for (int i = 0; i < dtm.getRowCount() ; i++) {
//            for (int j = 0; j < simbolos.length; j++) {
//              String estado = (String) dtm.getValueAt(i,j+1);
//              if(estado!=null){
//                  if(estado.indexOf("-")!=-1){
//                      b=false;
//                      break;
//                  }
//              }
//            }
//        }
//       
//        return b;
//    }
}
