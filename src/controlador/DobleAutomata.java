/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;

/**
 *
 * @author Alejo Castaño Rojas
 */
public class DobleAutomata {

    private ControladorAutomata ca1;
    private DefaultTableModel dtm;
    private AutomataF a1;
    private AutomataF a2;
    private AutomataF af;

    public DobleAutomata(AutomataF a1, AutomataF a2) {
        this.a1 = a1;
        this.a2 = a2;
    }

    public void construirElGranAF() {
        ArrayList<ArrayList> total = new ArrayList<>();
        af = new AutomataF();
        af.setSimbolos(a1.getSimbolos());
        for (int i = 0; i < a1.getTransiciones().size(); i++) {
            total.add(a1.getTransiciones().get(i));
        }
        for (int i = 0; i < a2.getTransiciones().size(); i++) {
            total.add(a2.getTransiciones().get(i));
        }
        af.setTransiciones(total);
        int tamano = a1.getEstadosIniciales().length + a2.getEstadosIniciales().length;
        String[] estadosIniciales = new String[tamano];
        for (int i = 0; i < tamano; i++) {
            if (i < a1.getEstadosIniciales().length) {
                estadosIniciales[i] = a1.getEstadosIniciales()[i];
            } else {
                estadosIniciales[i] = a2.getEstadosIniciales()[i - a1.getEstadosIniciales().length];
            }
        }
        af.setEstadosIniciales(estadosIniciales);
        tamano = a1.getEstadosAceptacion().length + a2.getEstadosAceptacion().length;
        String[] estadosAceptacion = new String[tamano];
        for (int i = 0; i < tamano; i++) {
            if (i < a1.getEstadosIniciales().length) {
                estadosAceptacion[i] = a1.getEstadosAceptacion()[i];
            } else {
                estadosAceptacion[i] = a2.getEstadosAceptacion()[i - a1.getEstadosAceptacion().length];
            }
        }
        af.setEstadosAceptacion(estadosAceptacion);
        tamano = a1.getEstados().length + a2.getEstados().length;
        String[] estados = new String[tamano];
        for (int i = 0; i < tamano; i++) {
            if (i < a1.getEstados().length) {
                estados[i] = a1.getEstados()[i];
            } else {
                estados[i] = a2.getEstados()[i - a1.getEstados().length];
            }
        }
        af.setEstados(estados);
        dtm = llenarTabla();
        af.setTransiciones(ca1.guardarAutomata());
        ca1.estadosAceptacion();
       
    }

    public ArrayList<ArrayList> Union(boolean flag) {
       ArrayList<ArrayList> reto = unionAutomata(flag);
       return reto;
    }

    /**
     * Este método permite saber si un estado es de aceptación para llenar la
     * tabla cuando se convierte de no determinístico a determinístico
     *
     * @param estado String con el estado que se quiere verificar.
     * @return un booleano en true si el estado es de aceptación, o false de lo
     * contrario.
     */
    public boolean definirEstadoAceptacion(String estado) {
        boolean b = false;
        for (int i = 0; i < af.getEstadosAceptacion().length; i++) {
            if (af.getEstadosAceptacion()[i].equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }
     public ArrayList<ArrayList> unionAutomata(boolean tipo) {
        ArrayList<ArrayList> automataFinal = new ArrayList<>();
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();
        ArrayList<String> transiciones = new ArrayList<>();
        boolean c = true;
        String[] estadosIniciales = af.getEstadosIniciales();
        if (ca1.definirEstadoDeAceptacion(estadosIniciales, c) == true) {
            String nEstado = String.join("", estadosIniciales);
            estados.add(nEstado);
            if (ca1.definirEstadoDeAceptacion(estadosIniciales, tipo)) {
                estadosAceptacion.add(nEstado);
            }
            transiciones = ca1.unirTransiciones(estadosIniciales);
            automataFinal.add(transiciones);
            for (int i = 0; i < transiciones.size(); i++) {
                String estado = transiciones.get(i);
                if (ca1.existeEstado(estados, estado) == false) {
                    if (estado != "\u0020") {
                        estados.add(estado);
                    }
                    String[] concatenado = ca1.convertirString(estado);
                    if (ca1.definirEstadoDeAceptacion(concatenado, tipo)) {

                        estadosAceptacion.add(estado);
                    }

                }
            }
            int k = 1;
            while (k != estados.size()) {
                String estado1 = estados.get(k);
                String[] estadoConca = ca1.convertirString(estado1);
                transiciones = ca1.unirTransiciones(estadoConca);
                if (ca1.definirEstadoDeAceptacion(estadoConca, tipo)) {
                    if (estado1 != "\u0020") {
                        if (!ca1.existeEstadoAceptacion(estadosAceptacion, estado1)) {
                            estadosAceptacion.add(estado1);
                        }
                    }
                }
                automataFinal.add(transiciones);
                for (int j = 0; j < transiciones.size(); j++) {
                    if (ca1.existeEstado(estados, transiciones.get(j)) == false) {
                        if (transiciones.get(j) != "\u0020") {
                            estados.add(transiciones.get(j));
                        }

                    }
                }
                k++;
            }
            af.setTransiciones(automataFinal);
            String[] nEstados = new String[estados.size()];
            String[] nAceptacion = new String[estadosAceptacion.size()];
            ca1.convertirArray(estados, nEstados);
            ca1.convertirArray(estadosAceptacion, nAceptacion);
            af.setEstados(nEstados);
            af.setEstadosAceptacion(nAceptacion);
            af.setTransiciones(ca1.organizarAutomata(estados, automataFinal));
            ca1.actualizarParticiones(estadosAceptacion, estados);
        }
        ca1.imprimir(automataFinal);
        return automataFinal;
    }

    public ControladorAutomata getCa1() {
        return ca1;
    }

    public void setCa1(ControladorAutomata ca1) {
        this.ca1 = ca1;
    }

    public AutomataF getAf() {
        return af;
    }

    public void setAf(AutomataF af) {
        this.af = af;
    }
    
    public DefaultTableModel llenarTabla() {
        String[] simbolosEntrando = af.getSimbolos();
        String[] estados = af.getEstados();
        ArrayList<ArrayList> automata = af.getTransiciones();
        String[] simbolosArr = new String[simbolosEntrando.length + 3];
        simbolosArr[0] = "Estados";
        for (int sym = 1; sym < simbolosArr.length - 2; sym++) {
            simbolosArr[sym] = simbolosEntrando[sym - 1];
        }
        simbolosArr[simbolosArr.length - 2] = "E.A.";
        simbolosArr[simbolosArr.length - 1] = "E.I.";
        DefaultTableModel detm = new DefaultTableModel(simbolosArr, 0);

        for (int i = 0; i < estados.length; i++) {
            String[] fila = new String[simbolosArr.length];
            fila[0] = estados[i];
            ArrayList<String> transiciones = automata.get(i);
            for (int j = 0; j < transiciones.size(); j++) {
                fila[j + 1] = transiciones.get(j);
            }
            if (definirEstadoAceptacion(estados[i])) {
                fila[simbolosArr.length - 2] = "1";
            } else {
                fila[simbolosArr.length - 2] = "0";
            }
            if(i == 0){
                fila[simbolosArr.length - 1] = "#";
            }
            detm.addRow(fila);
        }
        ca1 = new ControladorAutomata(af,detm);
        return detm;
    }
    
}
