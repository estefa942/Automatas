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
 * @author Estefany Muriel Cano
 * @author Alejandro Castaño Rojas
 */
public class ControladorAutomata {

    AutomataF af;
    String[] estados;
    String[] simbolos;
    ArrayList<ArrayList> particiones = new ArrayList<>();
    ArrayList<ArrayList> transDePart = new ArrayList<>();
    int[] visitados;
    ArrayList<ArrayList> automataNuevo = new ArrayList<>();
    DefaultTableModel dtm;

    /**
     * Constructor de la clase ControladorAutomata
     * @param af El autómata a trabajar
     * @param dtm La tabla perteneciente.
     */
    public ControladorAutomata(AutomataF af, DefaultTableModel dtm) {
        this.af = af;
        this.dtm = dtm;
        estados = af.getEstados();
        simbolos = af.getSimbolos();
    }

    public AutomataF getAf() {
        return af;
    }

    public void setAf(AutomataF af) {
        this.af = af;
    }

    public String[] getEstados() {
        return estados;
    }

    public void setEstados(String[] estados) {
        this.estados = estados;
    }

    public String[] getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(String[] simbolos) {
        this.simbolos = simbolos;
    }

    public ArrayList<ArrayList> getParticiones() {
        return particiones;
    }

    public void setParticiones(ArrayList<ArrayList> particiones) {
        this.particiones = particiones;
    }

    public ArrayList<ArrayList> getTransDePart() {
        return transDePart;
    }

    public void setTransDePart(ArrayList<ArrayList> transDePart) {
        this.transDePart = transDePart;
    }

    public DefaultTableModel getDtm() {
        return dtm;
    }

    public void setDtm(DefaultTableModel dtm) {
        this.dtm = dtm;
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
     * Este método llena con ceros, el vector de visitados, el cual se utiliza
     * para sacar los estados extraños
     */
    public void llenarVisitados() {
        visitados = new int[af.getTransiciones().size()];
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = 0;
        }
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

    /**
     * Permite verificar si el estado que se va a guardar es válido.
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

        ArrayList<String> estAcpt = new ArrayList<>();
        ArrayList<String> estRec = new ArrayList<>();
        String[] estadosAcep = af.getEstadosAceptacion();
        String[] estados = af.getEstados();
        for (int i = 0; i < estados.length; i++) {
            String estado = estados[i];
            if (esEstadoDeAceptacion(estado)) {
                estAcpt.add(estado);
            } else {
                estRec.add(estado);
            }
        }

        particiones.add(estRec);
        particiones.add(estAcpt);

    }

    /**
     * Este método guarda los estado iniciales que fueron ingrresados en la tabla
     * del autómata, toma solo los estados que en el campo "E.I" tienen el
     * siguiente simbolo "#".
     */
    public void estadosIniciales() {

        ArrayList<String> estIniciales = new ArrayList<>();

        String indicador;
        for (int i = 0; i < dtm.getRowCount(); i++) {
            indicador = (String) dtm.getValueAt(i, af.getSimbolos().length + 2);
            if(i==0 && indicador==null){
                estIniciales.add((String) dtm.getValueAt(i, 0));
            }
            if (indicador != null && indicador.equals("#")) {
                estIniciales.add((String) dtm.getValueAt(i, 0));

            }
            String[] estadoIni = new String[estIniciales.size()];
            convertirArray(estIniciales, estadoIni);
            af.setEstadosIniciales(estadoIni);

        }

    }

    /**
     * Permite determinar si el estado ingresado es de aceptación.
     *
     * @param estado String con el estado que se desea conocer si es de
     * aceptación
     * @return un booleano con la confirmación de si es un estado de aceptación
     * o no.
     */
    public boolean esEstadoDeAceptacion(String estado) {
        boolean b = false;
        String[] aceptacion = af.getEstadosAceptacion();
        for (int i = 0; i < aceptacion.length; i++) {
            if (aceptacion[i].equals(estado)) {
                b = true;
                break;
            }
        }
        return b;

    }

    /**
     * Permite verificar si el autómata ingresado es deterministico o no
     * deterministico, mediante la revisión de sus transiciones.
     *
     * @return un booleano con la confirmación de si es deterministico o no.
     */
    public boolean esDeterministico() {
        int count = 0;
        boolean b = true;
        if (af.getEstadosIniciales().length == 1) {
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
        } else {
            b = false;
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
     * @return Un Array de Strings con las transiciones previamente concatenadas
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
     * Permite decidir que estado de aceptación va a tener la unión o intersección de varios
     * estados     *
     * @param estados Arreglo con los estados a verificar su estado de
     * aceptación
     * @param tipo Ingresa un booleano que define el tipo de operación
     * @return un booleano en true si al menos uno de los estados es de
     * aceptación, o false de lo contrario.
     */
    public boolean definirEstadoDeAceptacion(String[] estados, boolean tipo) {
        boolean b = false;
        if (tipo) {
            for (int i = 0; i < estados.length; i++) {
                if (esEstadoDeAceptacion(estados[i])) {
                    b = true;
                    break;
                }
            }

        } else {
            int count = 0;
            for (int i = 0; i < estados.length; i++) {
                if (esEstadoDeAceptacion(estados[i])) {
                    count++;

                }
                if (count == estados.length) {
                    b = true;
                }
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
     * autómata inicial, y a partir de este mediante la concatenación de estados
     * y transiciones, contruir el autómata final.
     *
     * @return un ArrayList de ArrayList de tipo String con el nuevo autómata.
     */
    public ArrayList<ArrayList> convertirEnDeterministico() {
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        ArrayList<ArrayList> automataD = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();
        boolean c = true;
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
                if (definirEstadoDeAceptacion(concatenado, c)) {

                    estadosAceptacion.add(estado);
                }

            }
        }

        int k = 1;
        while (k != estados.size()) {
            String estado1 = estados.get(k);
            String[] estadoConca = convertirString(estado1);
            ArrayList<String> a = unirTransiciones(estadoConca);
            if (definirEstadoDeAceptacion(estadoConca, c)) {
                if (estado1 != "\u0020") {
                    if (!existeEstadoAceptacion(estadosAceptacion, estado1)) {
                        estadosAceptacion.add(estado1);
                    }
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
        af.setTransiciones(organizarAutomata(estados, automataD));
        actualizarParticiones(estadosAceptacion, estados);
        return automataD;
    }

   /**
    * Este método permite realizar la unión e intersección de uno o dos autómatas, para ello se recibe un booleano que indica
    * el tipo de proceso a seguir, si es true se realiza la unión y si es false, se realiza la intersección, para esto se toman
    * los estados iniciales y se juntan con sus respectivas transiciones, y a partir de esos nuevos estados se empieza a contruir el nuevo
    * autómata.
    * @param tipo booleano que indica si se va a hacer la unión o la intersección.
    * @return Array con el nuevo autómata. 
    */
    
    public ArrayList<ArrayList> unionAutomata(boolean tipo) {
        ArrayList<ArrayList> automataFinal = new ArrayList<>();
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        ArrayList<String> estados = new ArrayList<>();
        ArrayList<String> transiciones = new ArrayList<>();
        boolean c = true;
        String[] estadosIniciales = af.getEstadosIniciales();
        if (definirEstadoDeAceptacion(estadosIniciales, c) == true) {
            String nEstado = String.join("", estadosIniciales);
            estados.add(nEstado);
            if (definirEstadoDeAceptacion(estadosIniciales, tipo)) {
                estadosAceptacion.add(nEstado);
            }
            transiciones = unirTransiciones(estadosIniciales);
            automataFinal.add(transiciones);
            for (int i = 0; i < transiciones.size(); i++) {
                String estado = transiciones.get(i);
                if (existeEstado(estados, estado) == false) {
                    if (estado != "\u0020") {
                        estados.add(estado);
                    }
                    String[] concatenado = convertirString(estado);
                    if (definirEstadoDeAceptacion(concatenado, tipo)) {

                        estadosAceptacion.add(estado);
                    }

                }
            }
            int k = 1;
            while (k != estados.size()) {
                String estado1 = estados.get(k);
                String[] estadoConca = convertirString(estado1);
                transiciones = unirTransiciones(estadoConca);
                if (definirEstadoDeAceptacion(estadoConca, tipo)) {
                    if (estado1 != "\u0020") {
                        if (!existeEstadoAceptacion(estadosAceptacion, estado1)) {
                            estadosAceptacion.add(estado1);
                        }
                    }
                }
                automataFinal.add(transiciones);
                for (int j = 0; j < transiciones.size(); j++) {
                    if (existeEstado(estados, transiciones.get(j)) == false) {
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
            convertirArray(estados, nEstados);
            convertirArray(estadosAceptacion, nAceptacion);
            af.setEstados(nEstados);
            af.setEstadosAceptacion(nAceptacion);
            af.setTransiciones(organizarAutomata(estados, automataFinal));
            actualizarParticiones(estadosAceptacion, estados);

        }
        return automataFinal;
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
     * Este método permite convertir un ArrayList de Strings en un arreglo de
     * String
     *
     * @param a entra el Array que se va a convertir
     * @param b entra el arreglo donde se van a pasar los datos del Array
     */
    public void convertirArray(ArrayList<String> a, String[] b) {
        for (int i = 0; i < a.size(); i++) {
            String x = a.get(i);

            b[i] = a.get(i);

        }

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
     * Este método determina si un estado es de aceptación o no
     *
     * @param estado el estado que se quiere verificar.
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

  
    /**
     * Este método evalua la partición que se le ingresa por parámetro, y trae
     * del autómata inicial las transiciones pertenecientes a cada elemento del
     * Array.
     *
     * @param particion La partición que se quiere analizar
     * @return Retorna todas las transciones de la particion que se entren por
     * parámetro
     */
    public ArrayList<ArrayList> transicionesParticion(ArrayList<String> particion) {
        ArrayList<ArrayList> transiciones = new ArrayList<>();
        for (int i = 0; i < particion.size(); i++) {
            String a = particion.get(i);
            int numEstado = convertirEstados(a);
            transiciones.add(af.getTransiciones().get(numEstado));
        }
        return transiciones;
    }

    /**
     * Entra el array con la partición a tratar y otro Array con los símbolos a
     * buscar dentro del primer Array, este arrojará el Array con los símbolos
     * que no estan en esa partición.
     *
     * @param particion partición que se quiere analizar.
     * @param transicionASym los simbolos que se van a buscar
     * @return ArrayList con  los estados no contenidos en la partición.
     */
    public ArrayList<String> estadosNoContenidos(ArrayList<String> particion, ArrayList<String> transicionASym) {
        ArrayList<String> noContenidos = new ArrayList<>();

        for (int i = 0; i < transicionASym.size(); i++) {
            String a = transicionASym.get(i);
            int count = 0;
            for (int j = 0; j < particion.size(); j++) {
                String b = particion.get(j);
                if (a.equals(b)) {
                    count++;
                }
            }
            if (count == 0) {
                noContenidos.add(a);
            }
        }
        return noContenidos;
    }

    /**
     * Este método permite crear una nueva partición para ello se mira la partición que se va a modificar, y la transición 
     * que hace que esta partición se tenga que modificar, luego miramos los estados de la transición correspondiente y si estos estados
     * no estan contenidos en la partición se crea un nuevo array con la nueva partición
     * @param particion partición a analizar
     * @param noContenidos los estados no contenidos en la determinada partición
     * @param posSym la posición en el array que pertenece a el símbolo que se está manejando
     * @return Un Array con la nueva partición creada.
     */
    public ArrayList<String> creaNuevaParticion(ArrayList<String> particion, ArrayList<String> noContenidos, int posSym) {
        ArrayList<ArrayList> transicionesParticion = transicionesParticion(particion);
        ArrayList<String> nuevaParticion = new ArrayList<>();
        for (int i = 0; i < noContenidos.size(); i++) {
            int a = particion.size();
            for (int j = 0; j < a; j++) {
                String x = noContenidos.get(i);
                if (transicionesParticion.get(j).get(posSym).equals(x)) {
                    nuevaParticion.add(particion.get(j));
                    particion.remove(j);
                    transicionesParticion.remove(j);
                    if (particion.size() != 1) {
                        j--;
                    }
                    a = particion.size();
                }
            }

        }
        return nuevaParticion;
    }

    /**
     * Este método permite tomar las transiciones actuales del autómata, y mira
     * cuales estados estan contenidos en las transiciones para así ir llenando
     * el vector de visitados e ir identificando que estados son extraños,
     * finalmente actulizamos las transiciones sin estados extraños.
     *
     * @param p ingresa la posición del estado desde el cual se va a empezar a
     * analizar.
     */
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
     * Este método luego de haber sacado los estados extraños, permite
     * reestablecer todos los valores del autómata, actualiza los estados sin
     * los estados que fueron extraidos, y los estados de aceptación y las
     * particiones también los actualiza.
     */
    public void actualizarAutomata() {
        String[] estados = af.getEstados();
        ArrayList<String> estadosNuevos = new ArrayList<>();
        ArrayList<String> estadosAceptacion = new ArrayList<>();

        for (int i = 0; i < visitados.length; i++) {
            if (visitados[i] == 1) {
                estadosNuevos.add(estados[i]);
            }
        }
        for (int i = 0; i < estadosNuevos.size(); i++) {
            if (esEstadoDeAceptacion(estadosNuevos.get(i))) {
                estadosAceptacion.add(estadosNuevos.get(i));
            }
        }
        String[] estadosN = new String[estadosNuevos.size()];
        String[] estadosA = new String[estadosAceptacion.size()];
        convertirArray(estadosNuevos, estadosN);
        convertirArray(estadosAceptacion, estadosA);
        af.setTransiciones(af.getAutomataSinExtraños());
        af.setEstadosAceptacion(estadosA);
        af.setEstados(estadosN);
        actualizarParticiones(estadosAceptacion, estadosNuevos);
    }
/**
 * Este método permite mirar que estados son equivalente y eliminar los estados extraños, para esto tomamos las particiones
 * con los estados de aceptación y de rechazo, y vamos mirando sus transiciones cuando entra determinado simbolo, si sus transiciones van a una partición diferente
 * se crea otra nueva partición con el estado que difiere y se agrega a un array de particiones, se sigue analizando cada partición hasta que terminemos
 * todas las particiones con los diferentes símbolos, finalmente actualiza los valores del autómata: transiciones, estados,estados de Aceptación.
 */
    public void simplificar() {
        ArrayList<String> particionesR = copiaArray(particiones.get(0));
        ArrayList<String> particionesA = copiaArray(particiones.get(1));
        ArrayList<ArrayList> transicionesA;
        ArrayList<ArrayList> particionesT = new ArrayList<>();
        particionesT.add(particionesR);
        particionesT.add(particionesA);
        int f = 0;
        for (int m = 0; m < 2; m++) {
            for (int k = 0; k < particionesT.size(); k++) {
                for (int i = 0; i < af.getSimbolos().length; i++) {
                    boolean d = false;
                    while (d == false) {
                        ArrayList<String> transicionesASym = new ArrayList<>();
                        for (int j = 0; j < particionesT.get(k).size(); j++) {

                            transicionesA = transicionesParticion(particionesT.get(k));
                            ArrayList<String> actualTransicion = transicionesA.get(j);
                            String b = actualTransicion.get(i);
                            if (!existeEstado(transicionesASym, b)) {
                                transicionesASym.add(actualTransicion.get(i));
                            }

                        }

                        for (int j = 0; j < particionesT.size(); j++) {
                            ArrayList<String> particion = particionesT.get(j);
                            if (particion.containsAll(transicionesASym)) {
                                d = true;
                            }
                        }
                        if (d == false) {
                            ArrayList<String> particionPrincipal = particionesT.get(k);
                            for (int j = 0; j < particionesT.size(); j++) {
                                ArrayList<String> particion = particionesT.get(j);
                                ArrayList<String> noContenidos = estadosNoContenidos(particion, transicionesASym);
                                if (noContenidos.size() != transicionesASym.size()) {
                                    particionesT.add(creaNuevaParticion(particionPrincipal, noContenidos, i));
                                    break;
                                }
                            }

                        }

                    }

                }
            }
        }
        construirAutomata(particionesT);
        llenarVisitados();
        estadosExtraños(0);
        actualizarAutomata();

    }

    /**
     * De acuerdo a las particiones que se tomaron del método simplificar, este método une las transiciones basandose en 
     * los estados que estan en cada partición, además actualiza las transiciones y finalemte actualiza el autómata, es decir,
     * sus nuevos estados, estados de aceptación y transiciones.
     * @param particiones  ingre el Array con todas las particiones obtenidas de los estados equivalentes.
     */
    public void construirAutomata(ArrayList<ArrayList> particiones) {
        String[] estados = new String[particiones.size()];
        ArrayList<String> estados1 = new ArrayList<>();
        ArrayList<String> estadosAceptacion1 = new ArrayList<>();

        String[] estadoInicial = new String[1];
        ArrayList<ArrayList> automata = new ArrayList<>();
        for (int i = 0; i < particiones.size(); i++) {
            String nEstado = String.join("", particiones.get(i));

            if (nEstado.contains(af.getEstados()[0])) {

                estados1.add(nEstado);
                if (definirEstadoDeAceptacion(convertirString(nEstado), true)) {
                    estadosAceptacion1.add(nEstado);
                }
                break;
            }

        }
        for (int i = 0; i < particiones.size(); i++) {
            String nEstado = String.join("", particiones.get(i));
            if (!existeEstado(estados1, nEstado)) {
                estados1.add(nEstado);
                if (definirEstadoDeAceptacion(convertirString(nEstado), true)) {
                    estadosAceptacion1.add(nEstado);
                }
            }
        }
        convertirArray(estados1, estados);
        String[] estadosAceptacion = new String[estadosAceptacion1.size()];
        convertirArray(estadosAceptacion1, estadosAceptacion);
        for (int i = 0; i < particiones.size(); i++) {//estdos de aceptación haer
            ArrayList<String> transicion = unirTransiciones(convertirString(estados[i]));
            automata.add(completarTransicion(transicion, estados, estados1));
        }
        af.setEstados(estados);
        af.setEstadosAceptacion(estadosAceptacion);
        af.setEstadosIniciales(estadoInicial);
        af.setTransiciones(automata);
    }
/**
 * Este método mira el estado en cada transición, y si almenos contiene un caracter que pereteneza a 
 * a cualquiera de los nuevos estados, se reemplaza con el estado correspondiente 
 * @param transicion Ingresa la transición que se va a analizar
 * @param estados  Ingresa un arreglo los estados actuales del autómata
 * @param estados1 ingresa un array con los estados del autómata
 * @return Un array con la nueva transición.
 */
    public ArrayList<String> completarTransicion(ArrayList<String> transicion, String[] estados, ArrayList<String> estados1) {

        ArrayList<String> nuevaTransicion = new ArrayList<>();
        for (int i = 0; i < transicion.size(); i++) {
            String estado = intercambiarEstados(estados1, transicion.get(i));
            for (int j = 0; j < estados.length; j++) {
                if (estados[j].contains(estado)) {
                    estado = estados[j];
                    nuevaTransicion.add(estado);
                    break;
                }
            }
        }

        return nuevaTransicion;
    }

    /**
     * Este método crea una copia con todos los elementos de un Arraylist.
     * @param array elemento que se quiere clonar.
     * @return un array con la copia del elemento.
     */
    public ArrayList<String> copiaArray(ArrayList<String> array) {
        ArrayList<String> copiaArray = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            copiaArray.add(array.get(i));
        }
        return copiaArray;
    }

}
