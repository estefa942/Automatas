/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;
/**
 *
 * @author ACER
 */
public class ControladorAutomata {
    AutomataF af = new AutomataF();
    String[] estados = af.getEstados();
    String[] simbolos = af.getSimbolos();
    
    
  
    
    public int convertirSimbolos(String a){
        int valor=0;
        for(int i=0;i<simbolos.length;i++){
            if(simbolos[i].equals(a)){
                valor=i;
            }
        }
        return valor;
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
