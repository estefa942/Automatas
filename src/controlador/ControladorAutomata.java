/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.AutomataF;
import sun.misc.Queue;

/**
 * @author Estefany Muriel Cano
 * @author Alejandro Castaño Rojas
 */
public class ControladorAutomata {

    AutomataF af;
    String[] estados;
    String[] simbolos;
    ArrayList<ArrayList> particiones = new ArrayList<>();

    DefaultTableModel dtm;

    public ControladorAutomata(AutomataF af, DefaultTableModel dtm) {
        this.af = af;
        this.dtm = dtm;
        estados = af.getEstados();
        simbolos = af.getSimbolos();
    }

    /**
     * Este método permite retornar la posición del simbolo que se le entra por
     * parámetro para saber su ubicación dentro del arreglo.
     *
     * @param a String con el simbolo que se quiere buscar
     * @return entero con la posición del símbolo del estado en el arreglo
     */
    public int convertirSimbolos(String a) {
        int valor = 0;
        for (int i = 0; i < simbolos.length; i++) {
            if (simbolos[i].equals(a)) {
                valor = i;
            }
        }
        return valor;
    }

    /**
     * Este método permite retornar la posición del estado que se le entra por
     * parámetro para saber su ubicación dentro del arreglo.
     *
     * @param a String con el simbolo que se quiere buscar
     * @return entero con la posición del estado en el arreglo
     */
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

    /**
     * Este método trae de la tabla todas las transiciones y las guarda en un
     * ArrayList de ArrayList con datos de tipo String
     *
     * @return un ArrayLsit con todas las transiciones del autómata
     */
    public ArrayList<ArrayList> guardarAutomata() {

        ArrayList<ArrayList> automata = new ArrayList<>();
        for (int i = 0; i < dtm.getRowCount(); i++) {
            ArrayList<String> transiciones = new ArrayList<>();
            for (int j = 0; j < af.getSimbolos().length; j++) {
                String estado = (String) dtm.getValueAt(i, j + 1);

                if (estado != null && estadoValido(estados, estado)) {

                    transiciones.add(estado);
                } else {
                    transiciones.add("\u0020");
                }
            }
            automata.add(transiciones);
        }
        return automata;
    }

    /**
     * Permite verificar si el estado que se va a guardar es válido
     *
     * @param estados Arreglo con los estados del autómata
     * @param estado String con el estado a verificar
     * @return boolen en true si existe estado, false de lo contrario.
     */
    public boolean estadoValido(String[] estados, String estado) {
        boolean b = false;
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }

  

    /**
     * Aunque su nombre indique que selecciona los estados de aceptación, en
     * realidad separa estados de aceptación de los estados de rechazo generando
     * así 2 particiones; una partición P0 con los estados de rechazo y una
     * particion P1 con los estados de aceptación.
     */
    public void estadosAceptacion() {

        ArrayList<String> estAcp = new ArrayList<>();
        ArrayList<String> estRec = new ArrayList<>();
        String[] estadosAcp;
        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 1);
            if (indicador.compareTo("1") == 0) {
                estAcp.add((String) dtm.getValueAt(i, 0));
            } else {
                estRec.add((String) dtm.getValueAt(i, 0));
            }
        }
        estadosAcp = new String[estAcp.size()];
        for (int i = 0; i < estadosAcp.length; i++) {
            estadosAcp[i] = estAcp.get(i);
        }
        af.setEstadosAceptacion(estadosAcp);
        particiones.add(estRec);
        particiones.add(estAcp);
    }

    /**
     * Permite determinar si el estado ingresado es de aceptación.
     *
     * @param estado String con el estado que se desea conocer si es de
     * aceptación
     * @return un booleano con la confirmación de si es un estado de acepetación
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

    /**
     * Este método compara dos estados y evita que hayan repeticiones de estados
     * cuando se realice la unión de los mismos.
     *
     * @param a String con el primer estado a comparar
     * @param b String con el segundo estado a comparar
     * @return Un String con el nuevo estado sin repeticiones de estados en el
     * mismo.
     */
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
     * Este método permite hacer la unión de las transiciones de varios estados,
     * tomando las transiciones de cada estado que se va unir y luego compara
     * las transiciones que se van a unir para evitar la repetición de estados
     * en las transiciones finales.
     *
     * @param estados Arreglo de Strings con los estados a los cuales se les va
     * a hacer la unión de transiciones.
     * @return Un Array de Strings con las transciones previamente concatenadas
     * y sin repeticiones en ellas.
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
     * repeticiones en el momento de agregar un nuevo estado.
     *
     * @param estados Lista con los estados existentes hasta el momento.
     * @param estado String con el estado a comparar.
     * @return un booleano en true si el estado ya existe, o false de lo
     * contrario.
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

            if (count == estado.length() && estado.length() == a.length()) {
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
     * @param simbolo String con el simbolo a verificar.
     * @return un booleano en true si el simbolo es correcto, o false de lo
     * contrario.
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
     * Permite decidir que estado de aceptación va a tener la unión de varios
     * estados
     *
     * @param estados Arreglo con los estados a verificar su estado de
     * aceptación
     * @return un booleano en true si al menos uno de los estados es de
     * aceptación, o false de lo contrario.
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
     * @param transiciones Array de String con las transciones a revisar.
     * @return Un ArrayList de Strings con las transiciones concatenadas
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
     * Este método analiza los estados de aceptación que se tienen en el momento
     * y verifica que el estado que va a entrar no se encuentre repetido entre
     * los estados de aceptación que ya están.
     *
     * @param estadosAceptacion Lista que contiene los estados de aceptación que
     * se tienen hasta el momento
     * @param estado String con el estado a verificar.
     * @return un booleano en true si el estado ya existe en la lista o false si
     * no existe
     */
    public boolean existeEstadoAceptacion(ArrayList<String> estadosAceptacion, String estado) {
        boolean b = false;
        for (int i = 0; i < estadosAceptacion.size(); i++) {
            String a = estadosAceptacion.get(i);

            if (a.equals(estado)) {
                b = true;
                break;
            }
        }
        return b;
    }

    /**
     * Este método actualiza las particiones de aceptación o de rechazo cuando
     * el autómata es convertido a determinístico, dichas particiones son
     * utilizadas en simplificar y en verificar la hilera. Para actualizar se
     * toman los estados que no pertenecen a la lista de estado de aceptación y
     * se asigan en la partición de estados de rechazo.
     *
     * @param estadosAceptacion Lista con los estados de aceptación del
     * autómata.
     * @param estados lista con los estados del autómata.
     */
    public void actualizarParticiones(ArrayList<String> estadosAceptacion, ArrayList<String> estados) {
        particiones.clear();
        ArrayList<String> estadosRechazo = new ArrayList<>();
        for (int i = 0; i < estados.size(); i++) {
            String estado = estados.get(i);
            if (estadosAceptacion.contains(estado) == false) {
                estadosRechazo.add(estado);
            }
        }
        particiones.add(estadosRechazo);
        particiones.add(estadosAceptacion);
    }

    /**
     * Este método permite convertir un autómata no deterministico a
     * deterministico, tomando el primer estado con sus transiciones del
     * automata inicial, y a partir de este mediante la concatenación de estado
     * y transiciones, contruir el autómata final.
     *
     * @return un ArrayList de ArrayList de tipo String con el nuevo autómata.
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
                if (estado != "\u0020") {
                    estados.add(estado);
                }
                String[] concatenado = convertirString(estado);
                if (definirEstadoDeAceptacion(concatenado)) {
                    if (existeEstadoAceptacion(estadosAceptacion, estado) == false) {
                        estadosAceptacion.add(estado);
                    }
                }

            }
        }

        int k = 1;
        while (k != estados.size()) {
            String estado1 = estados.get(k);
            String[] estadoConca = convertirString(estado1);
            ArrayList<String> a = unirTransiciones(estadoConca);
            if (definirEstadoDeAceptacion(estadoConca)) {
                if (estado1 != "\u0020" && existeEstadoAceptacion(estadosAceptacion, estado1) == false) {
                    estadosAceptacion.add(estado1);
                }
            }
            automataD.add(a);
            for (int j = 0; j < a.size(); j++) {
                if (existeEstado(estados, a.get(j)) == false) {
                    if (a.get(j) != "\u0020") {
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
        actualizarParticiones(estadosAceptacion, estados);
        af.setTransiciones(organizarAutomata(estados, automataD));
        return automataD;
    }

    /**
     * Este método permite tomar un String y llevarlo a un arreglo con un
     * caracter del String en cada posición.
     *
     * @param a String que se quiere almacenar en un arreglo.
     * @return Un arreglo con el String inicial.
     */
    public String[] convertirString(String a) {
        String[] estadoConca = new String[a.length()];
        for (int i = 0; i < a.length(); i++) {
            char c = a.charAt(i);
            String caracter = (new StringBuffer().append(c)).toString();
            estadoConca[i] = caracter;
        }
        return estadoConca;
    }

    /**
     * Este método permite tomar un estado que se encuentra desordenado, y
     * ordenarlo de la forma en que se define previamente, es decir, si existe
     * un estado compuesto de más estados con un orden específico, los otros
     * estados que contenga sus mismos estados, tambien van a estar con el mismo
     * orden del estado previamente definido.
     *
     * @param estados Lista con los estados del autómata.
     * @param estado String con el estado que se quiere reordenar.
     * @return String con el estado ordenado.
     */
    public String intercambiarEstados(ArrayList<String> estados, String estado) {
        String estadoN = "";
        String estadoA = "";
        for (int i = 0; i < estados.size(); i++) {
            estadoA = estados.get(i);
            int count = 0;
            for (int j = 0; j < estado.length(); j++) {
                char c = estado.charAt(j);
                String caracter = (new StringBuffer().append(c)).toString();
                if (estadoA.contains(caracter)) {
                    count++;
                }
            }
            if (count == estado.length()) {
                estadoN = estadoA;
                break;
            }
        }
        return estadoN;
    }

    /**
     * Toma el autómata que se construyó y reordena los estados de las
     * transiciones,para que coincidan con los estados ya definidos, para evitar
     * confusiones en el usuario,y sea más ordenado el proceso de
     * reconocimiento.
     *
     * @param estados Lista con los estdos del autómata.
     * @param automata ArrayList con todas las transiciones del autómata.
     * @return Un ArrayList con todas las transiciones ordenadas del autómata.
     */
    public ArrayList<ArrayList> organizarAutomata(ArrayList<String> estados, ArrayList<ArrayList> automata) {
        ArrayList<ArrayList> automataNuevo = new ArrayList<>();
        for (int i = 0; i < automata.size(); i++) {
            ArrayList<String> transiciones = automata.get(i);
            ArrayList<String> transicionesN = new ArrayList<>();
            for (int j = 0; j < transiciones.size(); j++) {
                String estado = transiciones.get(j);
                if (estado.length() != 1) {
                    transicionesN.add(intercambiarEstados(estados, estado));
                } else {
                    transicionesN.add(estado);
                }
            }
            automataNuevo.add(transicionesN);
        }
        return automataNuevo;
    }

    /**
     * Este método permite que al tener un autómata ya definido, podamos
     * relaizar un reconocimiento de secuencias mediante el autómata, para saber
     * si la secuencia es válida o no, nos ubicamos en el estado inicial, luego
     * miramos que simbolo entra y avanzamos al estado que indique la
     * transición, hasta llegar al final de la hilera que es denotada por el
     * símbolo '*', cuando se llega al final se mira en que tipo de estado se
     * finalizó y si es de aceptación se acepta la secuencia, de lo contrario se
     * rechaza.
     *
     * @param hilera String con la secuencia que se quiere verificar
     * @return un booleano en true si la secuencia se acepta, o false de lo
     * contrario.
     */
    public boolean verificarHilera(String hilera) {
        boolean b = true;
        hilera = hilera + "*";
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
     * Este método usa las variables globales de particion para determinar si el
     * automata en cuestión contiene estados equivalentes y reducirlo a su forma
     * mínima.
     *
     * @return DefaultTableModel Un modelo de tabla para poder visualizarlo en
     * pantalla
     */
    public DefaultTableModel Simplificar() {
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
                if (/*!enEvaluacion.containsAll(transEstado)*/!enLaMismaParticion(transEstado) && enEvaluacion.size() > 1) {
                    excluido = new ArrayList<>();
                    excluido.add(estEnPart);
                    enEvaluacion.remove(enEvaluacion.indexOf(estEnPart));
                    particiones.add(excluido);
                    contPart = -1;
                    /*cpee = -1;*/
                }
            }
        }
        DefaultTableModel simple = ParticionesEnTabla();
        return simple;
    }

    public DefaultTableModel ParticionesEnTabla() {
        String[] estadosPET = af.getEstados();
        String[] sTabla = new String[af.getSimbolos().length + 2];
        sTabla[0] = "Estados";
        sTabla[sTabla.length - 1] = "E.A.";
        DefaultTableModel dtm;
        ArrayList<String> enEvaluacion;
        ArrayList<String> transEstado;
        String representante;
        int posEstado;
        for (int csym = 0; csym < af.getSimbolos().length; csym++) {
            sTabla[csym + 1] = af.getSimbolos()[csym];
        }
        dtm = new DefaultTableModel(sTabla, 0);
        for (int cep = 0; cep < particiones.size(); cep++) {
            enEvaluacion = particiones.get(cep);
            if (enEvaluacion.size() > 1) {
                representante = enEvaluacion.get(0);
                for (int contTrans = 0; contTrans < af.getTransiciones().size(); contTrans++) {
                    transEstado = af.getTransiciones().get(contTrans);
                    if (enEvaluacion.contains(estadosPET[contTrans])) {
                        estadosPET[contTrans] = representante;
                    }
                    for (int ceev = 0; ceev < enEvaluacion.size(); ceev++) {
                        if (transEstado.contains(enEvaluacion.get(ceev)) && enEvaluacion.get(ceev).compareTo(representante) != 0) {
                            posEstado = transEstado.indexOf(enEvaluacion.get(ceev));
                            transEstado.set(posEstado, representante);
                            ceev = -1;
                        }
                    }
                }
            }
        }
        ArrayList<String> nuevosEstados = new ArrayList<>();
        int posRelativa = 0;
        for (int i = 0; i < estadosPET.length; i++) {
            if (!nuevosEstados.contains(estadosPET[i])) {
                nuevosEstados.add(estadosPET[i]);
            } else {
                af.getTransiciones().remove(i - posRelativa);
                posRelativa++;
            }
        }
        String[] estadosPulidos = new String[nuevosEstados.size()];
        for (int n = 0; n < estadosPulidos.length; n++) {
            estadosPulidos[n] = nuevosEstados.get(n);
        }
        af.setEstados(estadosPulidos);
        String[] fila = new String[dtm.getColumnCount()];
        for (int i = 0; i < af.getEstados().length; i++) {
            fila[0] = estadosPET[i];
            for (int j = 0; j < af.getTransiciones().get(i).size(); j++) {
                fila[j + 1] = af.getTransiciones().get(i).get(j).toString();
            }
            if (estaEnAceptacion(estadosPET[i])) {
                fila[dtm.getColumnCount() - 1] = "1";
            } else {
                fila[dtm.getColumnCount() - 1] = "0";
            }
            dtm.addRow(fila);
        }
        return dtm;
    }

    /**
     * Este método determina si un estado es de aceptación o no
     *
     * @param estado
     * @version 1.5
     * @return boolean
     */
    public boolean estaEnAceptacion(String estado) {
        boolean b = false;
        for (int i = 0; i < af.getEstadosAceptacion().length; i++) {
            if (af.getEstadosAceptacion()[i].compareTo(estado) == 0) {
                b = true;
                return b;
            }
        }
        return b;
    }
    
    public boolean enLaMismaParticion(ArrayList<String> transiciones){
        for (int i = 0; i < particiones.size(); i++) {
            if(particiones.get(i).containsAll(transiciones)){
                return true;
            }
        }
        return false;
    }
}
