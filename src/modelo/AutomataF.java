/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Vector;

/**
 *
 * @author ACER
 */
public class AutomataF {
     
    public String[] simbolos;
    public String[] estados;
    public String[] estadosAceptacion;
    public int [][] matrizTransiciones;
//
//    public AutomataF(String[] simbolos, String[] estados, String[] estadosAceptacion, int[][] matrizTransiciones) {
//        this.simbolos = simbolos;
//        this.estados = estados;
//        this.estadosAceptacion = estadosAceptacion;
//        this.matrizTransiciones = matrizTransiciones;
//    }

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

    public int[][] getMatrizTransiciones() {
        return matrizTransiciones;
    }

    public void setMatrizTransiciones(int[][] matrizTransiciones) {
        this.matrizTransiciones = matrizTransiciones;
    }
    
     
}
