/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.StringTokenizer;
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
    ArrayList<ArrayList> particiones = new ArrayList<>();
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
                return valor;
            }
        }
        return -1;
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
     * realidad separa estados de aceptación de los estados de rechazo generando
     * así 2 particiones; una partición P0 con los estados de rechazo y una
     * particion P1 con los estados de aceptación.
     */
    public void EstadosAceptacion() {

        ArrayList<String> estAcp = new ArrayList<>();
        ArrayList<String> estRec = new ArrayList<>();
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 1);
            if (indicador.compareTo("1") == 0) {
                estAcp.add((String) dtm.getValueAt(i, 0));
            } else {
                estRec.add((String) dtm.getValueAt(i, 0));
            }
        }
        
        particiones.add(estRec);
        particiones.add(estAcp);
    }

    /**
     * Permite determinar si el estado ingresado es de aceptación.
     *
     * @param estado
     * @return un booleano con la confirmación de si es un estado de acpetación
     * o no.
     */
    public boolean esEstadoDeAceptacion(String estado) {
        boolean b = false;
        ArrayList<String> aceptacion = particiones.get(1);
        for (int i = 0; i < aceptacion.size(); i++) {
            if (aceptacion.get(i).equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * Permite verificar si el autómata ingresado es deterministico o no
     * deterministico, mediante la revision de sus transiciones
     *
     * @return un booleano con la confirmación de si es deterministico o no.
     */
    public boolean esDeterministico() {
        int count = 0;
        boolean b = true;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            for (int j = 0; j < af.getSimbolos().length; j++) {
                String estado = (String) dtm.getValueAt(i, j + 1);
                if (estado != null) {
                    if (estado.indexOf("-") != -1) {
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

    public String quitarEstadosRepetidos(String a, String b) {
        String estado = a;
        if (a == "\u0020") {
            estado = "";
        }
        if (b != "\u0020") {
            for (int i = 0; i < b.length(); i++) {
                char c = b.charAt(i);
                String caracter = (new StringBuffer().append(c)).toString();
                if (estado.contains(caracter) == false) {
                    estado = estado + caracter;
                }
            }
        }
        return estado;
    }

    /**
     * Este método permite hacer la unión de las transiciones de varios estados
     *
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
                    transicionesUnidas = transiciones;
                } else if (transicionesUnidas.get(j).contains(estado) == false) {
                    String nEstado = quitarEstadosRepetidos(transicionesUnidas.get(j), estado);
                    transicionesUnidas.set(j, nEstado);
                }
            }
            
        }
        return transicionesUnidas;
    }

    /**
     * Este método permite verificar si ya existe un estado, para evitar las
     * repeticiones en el momento de agregar.
     *
     * @param estados
     * @param estado
     * @return
     */
    public boolean existeEstado(ArrayList<String> estados, String estado) {
        boolean b = false;
        for (int i = 0; i < estados.size(); i++) {
            String a = estados.get(i);
            int count = 0;
            for (int j = 0; j < estado.length(); j++) {
                char c = estado.charAt(j);
                String caracter = (new StringBuffer().append(c)).toString();
                if (a.contains(caracter)) {
                    count++;
                }
            }

            if (count == estado.length()&& estado.length()==a.length()) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * Este método permite verificar si el simbolo es correcto, para evitar
     * evaluar simbolos que no pertenecen al autómata.
     *
     * @param simbolo
     * @return
     */
    public boolean existeSimbolo(String simbolo) {
        boolean b = false;
        String[] simbolos = af.getSimbolos();
        for (int i = 0; i < simbolos.length; i++) {
            String a = simbolos[i];
            if (a.equals(simbolo)) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * Permite verificar decidir que estado de aceptación va a tener la unión de
     * avrios estados
     *
     * @param estados
     * @return
     */
    public boolean definirEstadoDeAceptacion(String[] estados) {
        boolean b = false;
        for (int i = 0; i < estados.length; i++) {
            if (esEstadoDeAceptacion(estados[i])) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * Este método permite revisar las transiciones de un estado, y en caso de
     * que un transición vaya a dos estados, los concatena.
     *
     * @param transiciones
     * @return
     */
    public ArrayList<String> revisarTransiciones(ArrayList<String> transiciones) {
        ArrayList<String> transicionesNuevas = new ArrayList<>();
        for (int i = 0; i < transiciones.size(); i++) {
            String a = transiciones.get(i);
            if (a != "\u0020") {
                if (a.contains("-")) {
                    String[] concatenado = a.split("-");
                    String nuevoEstado = String.join("", concatenado);
                    transicionesNuevas.add(nuevoEstado);
                } else {
                    transicionesNuevas.add(a);
                }
            } else {
                transicionesNuevas.add(a);
            }
            
        }
        return transicionesNuevas;
    }

    /**
     * Este método permite convertir un autómata no deterministico a
     * deterministico, tomando el primer estado con sus transiciones del
     * automata inicial, y a partir de este mediante la concatenación de estado
     * y transiciones, contruir el autómata final.
     *
     * @return un ArrayList<ArrayList> con el nuevo autómata.
     */
     public ArrayList<ArrayList> convertirEnDeterministico() {
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        ArrayList<ArrayList> automataD = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();
        estados.add(af.getEstados()[0]);
        if (esEstadoDeAceptacion(af.getEstados()[0])) {
            estadosAceptacion.add(af.getEstados()[0]);
        }
        ArrayList<String> transiciones = revisarTransiciones(af.getTransiciones().get(0));
        automataD.add(transiciones);
        for (int i = 0; i < transiciones.size(); i++) {
            String estado = transiciones.get(i);
            if (existeEstado(estados, estado) == false) {
                if(estado!="\u0020"){
                estados.add(estado);
                }
                String[] concatenado = convertirString(estado);
                if (definirEstadoDeAceptacion(concatenado)) {
                   
                    estadosAceptacion.add(estado);
                }

            }
        }

        int k = 1;
        while (k != estados.size()) {
            String estado1 = estados.get(k);
            String[] estadoConca = convertirString(estado1);
            ArrayList<String> a = unirTransiciones(estadoConca);
            if (definirEstadoDeAceptacion(estadoConca)) {
                if(estado1!="\u0020"){
                estadosAceptacion.add(estado1);
                }
            }
            automataD.add(a);
            for (int j = 0; j < a.size(); j++) {
                if (existeEstado(estados, a.get(j)) == false) {
                    if(a.get(j)!="\u0020"){
                    estados.add(a.get(j));
                    }

                }
            }
            k++;
        }
        af.setTransiciones(automataD);
        String[] nEstados = new String[estados.size()];
        String[] nAceptacion = new String[estadosAceptacion.size()];
        for (int i = 0; i < estados.size(); i++) {

            nEstados[i] = estados.get(i);

        }
        for (int i = 0; i < estadosAceptacion.size(); i++) {
            String a = estadosAceptacion.get(i);

            nAceptacion[i] = estadosAceptacion.get(i);

        }
        af.setEstados(nEstados);
        af.setEstadosAceptacion(nAceptacion);
        af.setTransiciones(organizarAutomata(estados, automataD));
        return automataD;
    }

    public String[] convertirString(String a) {
        String[] estadoConca = new String[a.length()];
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            String caracter = (new StringBuffer().append(c)).toString();
            estadoConca[i] = caracter;
        }
        return estadoConca;
    }

   
    
    public String intercambiarEstados(ArrayList<String> estados,String estado){
       String estadoN="";
       String estadoA="";
        for (int i = 0; i < estados.size(); i++) {
            estadoA = estados.get(i);
            int count=0;
            for (int j = 0; j < estado.length(); j++) {
                char c = estado.charAt(j);
                String caracter = (new StringBuffer().append(c)).toString();
                if (estadoA.contains(caracter)) {
                    count++;
                }
            }
            if(count==estado.length()){
                estadoN = estadoA;
                break;
            }
        }
        return estadoN;
    }
    
    public ArrayList<ArrayList> organizarAutomata(ArrayList<String> estados,ArrayList<ArrayList> automata ){
        ArrayList<ArrayList> automataNuevo = new ArrayList<>();
        for (int i = 0; i < automata.size(); i++) {
            ArrayList<String> transiciones = automata.get(i);
            ArrayList<String> transicionesN = new ArrayList<>();
            for (int j = 0; j < transiciones.size(); j++) {
                String estado=transiciones.get(j);
                if(estado.length()!=1){
                    transicionesN.add(intercambiarEstados(estados, estado));
                }else{
                    transicionesN.add(estado);
                }
            }
            automataNuevo.add(transicionesN);
        }
        return automataNuevo;
    }
    public boolean verificarHilera(String hilera) {
        boolean b = true;
        ArrayList<ArrayList> automata = af.getTransiciones();
        ArrayList<String> transicionesEstado = new ArrayList<>();
        String siguienteEstado = "";
        if (hilera == null) {
            b = false;
            return b;
        }
        for (int i = 0; i < hilera.length(); i++) {
            char c = hilera.charAt(i);
            String caracter = (new StringBuffer().append(c)).toString();
            if (caracter.equals("*")) {
                if (esEstadoDeAceptacion(siguienteEstado) == false) {
                    b = false;
                }
            }
            if (existeSimbolo(caracter)) {
                if (i == 0) {
                    transicionesEstado = automata.get(0);
                    siguienteEstado = transicionesEstado.get(convertirSimbolos(caracter));
                    if (siguienteEstado.equals("\u0020")) {
                        b = false;
                        break;
                    } else {

                    }
                } else {
                    int a = convertirEstados(siguienteEstado);
                    transicionesEstado = automata.get(a);
                    siguienteEstado = transicionesEstado.get(convertirSimbolos(caracter));
                    if (siguienteEstado.equals("\u0020")) {
                        b = false;
                        break;
                    }
                }
            }
        }
        return b;
    }

    /**
     * Método iniciado a las 1854 horas del jueves 6 de abril del 2017 La
     * funcionalidad del método fue terminada el viernes 14 de abril del 2017 :v
     *
     * @return
     */
    public ArrayList<ArrayList> Simplificar() {
        ArrayList<String> enEvaluacion;
        ArrayList<String> transEstado;
        ArrayList<String> excluido;
        String estEnPart;
        int posEstado;
        for (int contPart = 0; contPart < particiones.size(); contPart++) {
            enEvaluacion = particiones.get(contPart);
            for (int cpee = 0; cpee < enEvaluacion.size(); cpee++) {
                estEnPart = enEvaluacion.get(cpee);
                posEstado = convertirEstados(estEnPart);
                transEstado = af.getTransiciones().get(posEstado);
                if (!enEvaluacion.containsAll(transEstado)) {
                    //Aquí ya estoy ordenando qué hacer si la particion no contiene los estados de transición posibles.
                    excluido = new ArrayList<>();
                    excluido.add(estEnPart);
                    enEvaluacion.remove(enEvaluacion.indexOf(estEnPart));
                    if (enEvaluacion.size() == 1) {
                        particiones.remove(particiones.indexOf(enEvaluacion));
                    }
                    particiones.add(excluido);
                    contPart = -1;
                    cpee = -1;
                }
            }
        }
        //Aquí ya solo es hacer la carpintería de llevar las particiones que ya tengo a la tabla :v
        //gg izi
        return null;
    }
    
    public DefaultTableModel ParticionesEnTabla() {
        String[] estados = af.getEstados();
        String[] sTabla = new String[af.getSimbolos().length + 2];
        sTabla[0] = "Estados";
        sTabla[sTabla.length - 1] = "E.A.";
        DefaultTableModel dtm;
        Random rnd = new Random();
        ArrayList<String> enEvaluacion;
        ArrayList<String> transEstado;
        String representante;
        int posEstado;
        
        for (int csym = 0; csym < af.getSimbolos().length; csym++) {
            sTabla[csym + 1] = af.getSimbolos()[csym];
        }
        dtm = new DefaultTableModel(sTabla, 0);
        //Aquí ya voy a llevarme las particiones a la tabla
        for (int cep = 0; cep < particiones.size(); cep++) {
            enEvaluacion = particiones.get(cep);
            if(enEvaluacion.size() > 1){
                representante = enEvaluacion.get(rnd.nextInt()%enEvaluacion.size());
            }
        }
        return null;
    }
}
