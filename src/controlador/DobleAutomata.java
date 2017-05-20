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

  private AutomataF a1;
    private AutomataF a2;
    private AutomataF af;
    
    public DobleAutomata(AutomataF a1, AutomataF a2){
        this.a1 = a1;
        this.a2 = a2;
    }
    
    public void contruirAutomata(){
        af = new AutomataF();
        ArrayList<ArrayList> total = new ArrayList<>();
        
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
            if (i < a1.getEstadosAceptacion().length) {
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
     }

    public AutomataF getAf() {
        return af;
    }

    public void setAf(AutomataF af) {
        this.af = af;
    }
  
    
}
