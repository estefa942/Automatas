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
import sun.misc.Queue;

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
    int[] visitados;

    private String[] estadosAceptacion;
    DefaultTableModel dtm;

    public ControladorAutomata(AutomataF af, DefaultTableModel dtm) {
        this.af = af;
        this.dtm = dtm;
        estados = af.getEstados();
        simbolos = af.getSimbolos();
    }

    public int convertirSimbolos(String a) {
        int valor = 0;
        for (int i = 0; i < simbolos.length; i++) {
            if (simbolos[i].equals(a)) {
                valor = i;
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
    public void llenarVisitados() {
        visitados = new int[af.getTransiciones().size()];
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = 0;
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

    public void imprimir(ArrayList<ArrayList> a) {
        for (int i = 0; i < a.size(); i++) {
            ArrayList b = a.get(i);
            for (int j = 0; j < b.size(); j++) {
                System.out.print("|" + b.get(j) + "|");
            }
            System.out.println("");
        }
    }

    public String[] EstadosAceptacion() {
        String[] estadosAceptacion;
        Object[] arrAuxiliar;
        ArrayList<String> estAcp = new ArrayList<>();
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 1);
            if(indicador.compareTo("1") == 0){
                estAcp.add((String) dtm.getValueAt(i, 0));
            }
        }
        arrAuxiliar = estAcp.toArray();
        estadosAceptacion = new String[arrAuxiliar.length];
        for(int c = 0; c < arrAuxiliar.length; c++){
            estadosAceptacion[c] = arrAuxiliar[c].toString();
        }
        af.setEstadosAceptacion(estadosAceptacion);
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

    public void estadosExtraños(int p) {
        visitados[p] = 1;
        automataNuevo.add(af.getTransiciones().get(p));
        for (int i = 0; i < af.getSimbolos().length; i++) {
            String b = (String) af.getTransiciones().get(p).get(i);
            if (visitados[convertirEstados(b)] == 0) {
                estadosExtraños(convertirEstados(b));
            }
        }
        af.setAutomataSinExtraños(automataNuevo);
    }

    public ArrayList<String> unirTransiciones(String[] estados) {
        ArrayList<String> transicionesUnidas = new ArrayList<>();
        for (int i = 0; i < estados.length; i++) {
            int a = convertirEstados(estados[i]);
            ArrayList<String> transiciones = af.getTransiciones().get(a);
            for (int j = 0; j < transiciones.size(); j++) {
                String estado = transiciones.get(j);
                if (transicionesUnidas.size() == 0) {
                    transicionesUnidas.add(estado);
                } else {
                    if (transicionesUnidas.get(j).indexOf(estado) != -1) {
                        transicionesUnidas.set(j, transicionesUnidas.get(j) + estado);
                    }

                }
            }

        }
        return transicionesUnidas;
    }

    public boolean existeEstado(ArrayList<String> estados, String estado){
        boolean b = false;
        for (int i = 0; i < estados.size(); i++) {
            String a= estados.get(i);
            if(a.equals(estado)){
                b=true;
                break;
            }
        }
        return b;
    }
    public void convertirEnDeterministico() {
        ArrayList<ArrayList> automata = af.getTransiciones();
        ArrayList<ArrayList> automataD= new ArrayList<>();
        
        Queue estadosConcatenados = new Queue();
        ArrayList<String> estados = new ArrayList<>();
         ArrayList<String> transi = new ArrayList<>();
        estados.add(af.getEstados()[0]);
        estados= af.getTransiciones().get(0);
       //Apartescxzito
        for (int i = 0; i <estados.size(); i++) {
            String a= estados.get(i);
            if(a.indexOf(",")==-1){
                 String[] concatenado = a.split(",");
                 String nuevoEstado = String.join("", concatenado);
                 transi.add(nuevoEstado);
            }else{
                transi.add(a);
            }
            
        }
        automataD.add(transi);

        for (int i = 0; i < automata.size(); i++) {
            ArrayList<String> transicionesD = new ArrayList<>();
            ArrayList<String> transiciones = automata.get(i);
            for (int j = 0; j < 10; j++) {
                String estado = transiciones.get(j);
                if (estado != "error") {
                    if (estado.indexOf(",") == -1) {
                        
                        String[] concatenado = estado.split(",");
//                        estadosConcatenados.enqueue(String.join("", concatenado));
                        String nuevoEstado = String.join("", concatenado);
                         if(existeEstado(estados,nuevoEstado )==false){
                             estados.add(nuevoEstado);
                             ArrayList<String>a= unirTransiciones(concatenado);
                             automataD.add(a);
                         }
                        
                    }else{
                        if(existeEstado(estados,estado )==false){
                             estados.add(estado);
                             //Buscar las transiciones de este estado en  el automata inicial :8
                            }
                             
                         }
                    }
                }                    

            }
            
        }
    }



