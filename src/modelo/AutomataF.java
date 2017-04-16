/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author ACER
 */
public class AutomataF {
     
    private String[] simbolos;
    private String[] estados;
    private String[] estadosAceptacion;
    private ArrayList<ArrayList> Transiciones;
    private ArrayList<ArrayList> automataSinExtraños;

    public String[] getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(String[] simbolos) {
        this.simbolos = simbolos;
    }

    public String[] getEstados() {
        return estados;
    }

    public void setEstados(String[] estados) {
        this.estados = estados;
    }

    public String[] getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(String[] estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public ArrayList<ArrayList> getTransiciones() {
        return Transiciones;
    }

    public void setTransiciones(ArrayList<ArrayList> Transiciones) {
        this.Transiciones = Transiciones;
    }

    public ArrayList<ArrayList> getAutomataSinExtraños() {
        return automataSinExtraños;
    }

    public void setAutomataSinExtraños(ArrayList<ArrayList> automataSinExtraños) {
        this.automataSinExtraños = automataSinExtraños;
    }

}
