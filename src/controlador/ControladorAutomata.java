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
    ArrayList<String[]> particiones = new ArrayList<>();
    int[] visitados;

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
                    transiciones.add("\u0020");
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

    /**
     * Aunque su nombre indique que selecciona los estados de aceptación, en
     * realidad separa estados de aceptación de los estados de rechazo
     * generando así 2 particiones; una partición P0 con los estados de rechazo
     * y una particion P1 con los estados de aceptación.
     */
    public void EstadosAceptacion() {
        String[] estadosAcp;
        String[] estadosRec;
        Object[] arrAuxiliar;
        ArrayList<String> estAcp = new ArrayList<>();
        ArrayList<String> estRec = new ArrayList<>();
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 1);
            if (indicador.compareTo("1") == 0) {
                estAcp.add((String) dtm.getValueAt(i, 0));
            }else{
                estRec.add((String) dtm.getValueAt(i, 0));
            }
        }
        arrAuxiliar = estAcp.toArray();
        estadosAcp = new String[arrAuxiliar.length];
        for (int c = 0; c < arrAuxiliar.length; c++) {
            estadosAcp[c] = arrAuxiliar[c].toString();
        }
        arrAuxiliar = estRec.toArray();
        estadosRec = new String[arrAuxiliar.length];
        for(int c = 0; c < arrAuxiliar.length; c++){
            estadosRec[c] = arrAuxiliar[c].toString();
        }
        af.setEstadosAceptacion(estadosAcp);
        particiones.add(estadosRec);
        particiones.add(estadosAcp);
    }
    
    /**
     * Permite determinar si el estado ingresado es de aceptación.
     * @param estado
     * @return un booleano con la confirmación de si es un estado de acpetación o no.
     */
    public boolean esEstadoDeAceptacion(String estado){
        boolean b = false;
        String[] aceptacion =particiones.get(1);
        for (int i = 0; i < aceptacion.length; i++) {
            if(aceptacion[i].equals(estado)){
                b=true;
                break;
            }
        }
        return b;
    }
/**
 * Permite verificar si el autómata ingresado es deterministico o no deterministico,
 * mediante la revision de sus transiciones
 * @return un booleano con la confirmación de si es deterministico o no.
 */
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
/**
 * Este método permite hacer la unión de las transiciones de varios estados
 * @param estados
 * @return 
 */
    public ArrayList<String> unirTransiciones(String[] estados) {
        ArrayList<String> transicionesUnidas = new ArrayList<>();
        for (int i = 0; i < estados.length; i++) {
            int a = convertirEstados(estados[i]);
            ArrayList<String> transiciones = revisarTransiciones(af.getTransiciones().get(a));
            for (int j = 0; j < transiciones.size(); j++) {
                String estado = transiciones.get(j);
                if (transicionesUnidas.size() == 0) {
                    transicionesUnidas=transiciones;
                } else if (transicionesUnidas.get(j).contains(estado)==false) {
                    
                    transicionesUnidas.set(j, transicionesUnidas.get(j) + estado);
                }
            }

        }
        return transicionesUnidas;
    }
/**
 * Este método permite verificar si ya existe un estado, para evitar las repeticiones en el momento de agregar.
 * @param estados
 * @param estado
 * @return 
 */
    public boolean existeEstado(ArrayList<String> estados, String estado) {
        boolean b = false;
        for (int i = 0; i < estados.size(); i++) {
            String a = estados.get(i);
            if (a.equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }

    public boolean definirEstadoDeAceptacion(String[] estados){
        boolean b=false;
        for (int i = 0; i < estados.length; i++) {
            if(esEstadoDeAceptacion(estados[i])){
                b=true;
                break;
            }
        }
        return b;
    }
    /**
     * Este método permite revisar las transiciones de un estado, y en caso de
     * que un transición vaya a dos estados, los concatena.
     * @param transiciones
     * @return 
     */
    public ArrayList<String> revisarTransiciones(ArrayList<String> transiciones) {
      ArrayList<String> transicionesNuevas = new ArrayList<>();
        for (int i = 0; i < transiciones.size(); i++) {
            String a = transiciones.get(i);
            if (a != "\u0020") {
                if (a.contains(",")) {
                    String[] concatenado = a.split(",");
                    String nuevoEstado = String.join("", concatenado);
                    transicionesNuevas.add(nuevoEstado);
                } else {
                    transicionesNuevas.add(a);
                }
            }else{
                transicionesNuevas.add(a);
            }

        }
        return transicionesNuevas;
    }
    
    /**
     * Este método permite convertir un autómata no deterministico a deterministico, tomando el primer estado
     * con sus transiciones del automata inicial, y a partir de este mediante la concatenación de estado y transiciones, 
     * contruir el autómata final.
     * @return un ArrayList<ArrayList> con el nuevo autómata.
     */
    public ArrayList<ArrayList> convertirEnDeterministico() {
        ArrayList<String> estadosAceptacion= new ArrayList<>();
        ArrayList<ArrayList> automata = af.getTransiciones();
        ArrayList<ArrayList> automataD = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();
        estados.add(af.getEstados()[0]);
        if(esEstadoDeAceptacion(af.getEstados()[0])){
        estadosAceptacion.add(af.getEstados()[0]);
        }
        automataD.add(revisarTransiciones(af.getTransiciones().get(0)));
        
        for (int i = 0; i < automata.size(); i++) {
            ArrayList<String> transicionesD = new ArrayList<>();
            ArrayList<String> transiciones = automata.get(i);
            for (int j = 0; j < transiciones.size(); j++) {
                String estado = transiciones.get(j);
                if (estado != "\u0020") {
                    if (estado.contains(",")) {

                        String[] concatenado = estado.split(","); //Acá
                        String nuevoEstado = String.join("", concatenado);
                        if (existeEstado(estados, nuevoEstado) == false) {
                             estados.add(nuevoEstado);
                             if(definirEstadoDeAceptacion(concatenado)){
                             estadosAceptacion.add(nuevoEstado);
                             }
                            ArrayList<String> a = unirTransiciones(concatenado);
                            automataD.add(a);
                        }

                    } else if (existeEstado(estados, estado) == false && estado!="\u0020") {
                        estados.add(estado);
                        if(esEstadoDeAceptacion(estado)){
                        estadosAceptacion.add(estado);
                        }
                        int b = convertirEstados(estado);                        
                        automataD.add(revisarTransiciones(automata.get(b)));
                        
                    }
                }
            }

        }
        af.setTransiciones(automataD);
        String[] nEstados= new String[estados.size()];
        String[] nAceptacion = new String[estadosAceptacion.size()];
        for (int i = 0; i < estados.size(); i++) {
            nEstados[i]=estados.get(i);
           
        }
         for (int i = 0; i < estadosAceptacion.size(); i++) {
           
             nAceptacion[i]=estadosAceptacion.get(i);
        }
        af.setEstados(nEstados);
        af.setEstadosAceptacion(nAceptacion);
        af.setTransiciones(automataD);
        return automataD;
    }
}
