/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minsait.data.technologies.sopadeletras;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author w10
 */
public class InsertadorDePalabras {

    //private final String direccionDiccionario = "C:\\Users\\w10\\Documents\\NetBeansProjects\\sopaDeLetras\\src\\resourse\\DiccionarioEsp.txt";
    private final String direccionDiccionario = "src\\main\\java\\com\\minsait\\data\\technologies\\sopadeletras\\diccionario\\DiccionarioEsp.txt";
    private Diccionario nuevoDicc;

    public InsertadorDePalabras() throws IOException {
        this.nuevoDicc = new Diccionario(this.direccionDiccionario);
    }

    public Cuadricula insertarNivelFacil(Cuadricula cuadricula, String modo) throws IOException {
        //mientras haya espacio, insertar palabras horizontalmente
        //mientras haya espacio, insertar verticalmente
        Cuadricula cuadriculaConNivelFacil = null;
        int tamCuadricula = cuadricula.getTamanio();
        int numeroDePalabras = (int) Math.floor(Math.random() * (tamCuadricula * 2 - 5) + 5);

        for (int i = 0; i < numeroDePalabras; i++) {
            int columnaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int filaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int horizontOVerti = (int) Math.floor(Math.random() * 10);
            int tamanioPalabra = (int) Math.floor(Math.random() * (12 - 3) + 3);
            char direccion = horizontOVerti % 2 == 0 ? 'h' : 'v';

            ArrayList<String> palabras = nuevoDicc.readDictionary(tamanioPalabra);
            int indiceAleatorio = (int) Math.floor(Math.random() * (palabras.size() - 1) + 1);
            String palabraAInsertar = palabras.get(indiceAleatorio);//cambiar por un numero aleatorio
            if (modo.equals("normal")) {
                cuadriculaConNivelFacil = insertarPalabra(cuadricula, palabraAInsertar, columnaAleatoria, filaAleatoria, direccion);
            } else {
                cuadriculaConNivelFacil = insertarPalabraInversa(cuadricula, palabraAInsertar, columnaAleatoria, filaAleatoria, direccion);
            }
        }

        return cuadriculaConNivelFacil;
    }

    public Cuadricula insertarNivelMedio(Cuadricula cuadricula) throws IOException {
        Cuadricula sopaNivelMedio;
        int tamCuadricula = cuadricula.getTamanio();
        int numeroDePalabras = (int) Math.floor(Math.random() * (tamCuadricula * 2 - 5) + 5);
        char direccion = 'd';

        //hacer lo del nivel facil
        sopaNivelMedio = insertarNivelFacil(cuadricula, "normal");
        for (int i = 0; i < numeroDePalabras; i++) {
            int columnaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int filaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int horizontOVerti = (int) Math.floor(Math.random() * 10);
            int tamanioPalabra = tamCuadricula - columnaAleatoria;

            ArrayList<String> palabras = nuevoDicc.readDictionary(tamanioPalabra);
            int indiceAleatorio = (int) Math.floor(Math.random() * (palabras.size() - 1) + 1);
            String palabraAInsertar = palabras.get(indiceAleatorio);//cambiar por un numero aleatorio
            sopaNivelMedio = insertarPalabra(sopaNivelMedio, palabraAInsertar, columnaAleatoria, filaAleatoria, direccion);
        }
        //y si hay posibilidad, meter palabras diagonalmente
        return sopaNivelMedio;
    }

    public Cuadricula insertarNivelDificil(Cuadricula cuadricula) throws IOException {
        Cuadricula sopaNivelDificil;
        int tamCuadricula = cuadricula.getTamanio();
        int numeroDePalabras = (int) Math.floor(Math.random() * (tamCuadricula * 2 - 5) + 5);
        char direccion = 'd';

        //hacer lo del nivel facil
        sopaNivelDificil = insertarNivelFacil(cuadricula, "inverso");
        //y si hay posibilidad, meter palabras diagonalmente
        for (int i = 0; i < numeroDePalabras; i++) {
            int columnaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int filaAleatoria = (int) Math.floor(Math.random() * tamCuadricula);
            int horizontOVerti = (int) Math.floor(Math.random() * 10);
            int tamanioPalabra = tamCuadricula - columnaAleatoria;

            ArrayList<String> palabras = nuevoDicc.readDictionary(tamanioPalabra);
            int indiceAleatorio = (int) Math.floor(Math.random() * (palabras.size() - 1) + 1);
            String palabraAInsertar = palabras.get(indiceAleatorio);//cambiar por un numero aleatorio
            sopaNivelDificil = insertarPalabraInversa(sopaNivelDificil, palabraAInsertar, columnaAleatoria, filaAleatoria, direccion);
        }
        return sopaNivelDificil;
    }

    private Cuadricula insertarPalabra(Cuadricula cuadricula, String palabra, int columna, int fila, char direccion) {
        int tamPalabra = palabra.length();
        char[] letrasEnPalabra = obtenerLetrasDePalabra(palabra);
        boolean caminoLibre = true;
        int columnaCasillaOcupada;
        int filaCasillaOcupada;
        char letraEnPalabraExistente = 0;
        int indiceCompatible = -1;
        Enum statusCasilla;

        switch (direccion) {
            case 'h':
                for (int i = 0; i < tamPalabra; i++) {
                    statusCasilla = cuadricula.statusCasilla(columna + i, fila);
                    if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {//checar el estado cuando la casilla consultada este fuera del rango
                        caminoLibre = false;
                        columnaCasillaOcupada = columna + i;
                        filaCasillaOcupada = fila;
                        letraEnPalabraExistente = cuadricula.getLetra(columnaCasillaOcupada, filaCasillaOcupada);
                        break;
                    }
                }

                if (caminoLibre) {
                    //poner la palabra  
                    for (int i = 0; i < tamPalabra; i++) {
                        cuadricula.insertarChar(letrasEnPalabra[i], columna + i, fila);
                    }
                    System.out.println("==============");
                    System.out.println(letrasEnPalabra);
                    System.out.println("==============");
                } else {
                    //checar si alguna letra de la palabra es igual a una que ya este ocupada
                    for (int i = 0; i < tamPalabra; i++) {
                        if (letrasEnPalabra[i] == letraEnPalabraExistente) {
                            indiceCompatible = i;
                            break;
                        }
                    }
                    //si es asi, checar si es posible meterla(palabras cruzadas)
                    if (indiceCompatible > -1) {
                        int parteDerechaPalabra = tamPalabra - indiceCompatible;
                        int parteIzqPalara = indiceCompatible;
                        for (int i = 0; i < parteIzqPalara; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna + i, fila);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        for (int i = indiceCompatible; i < tamPalabra; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna + i, fila);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        if (caminoLibre) {
                            //poner la palabra  
                            for (int i = 0; i < tamPalabra; i++) {
                                cuadricula.insertarChar(letrasEnPalabra[i], columna + i, fila);
                            }
                            System.out.println("==============");
                            System.out.println(letrasEnPalabra);
                            System.out.println("==============");
                        }
                    }
                }
                break;
            case 'v':
                for (int i = 0; i < tamPalabra; i++) {
                    statusCasilla = cuadricula.statusCasilla(columna, fila + i);
                    if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {//checar el estado cuando la casilla consultada este fuera del rango
                        caminoLibre = false;
                        columnaCasillaOcupada = columna;
                        filaCasillaOcupada = fila + i;
                        letraEnPalabraExistente = cuadricula.getLetra(columnaCasillaOcupada, filaCasillaOcupada);
                        break;
                    }
                }

                if (caminoLibre) {
                    //poner la palabra  
                    for (int i = 0; i < tamPalabra; i++) {
                        cuadricula.insertarChar(letrasEnPalabra[i], columna, fila + i);
                    }
                    System.out.println("==============");
                    System.out.println(letrasEnPalabra);
                    System.out.println("==============");
                } else {
                    //checar si alguna letra de la palabra es igual a una que ya este ocupada
                    for (int i = 0; i < tamPalabra; i++) {
                        if (letrasEnPalabra[i] == letraEnPalabraExistente) {
                            indiceCompatible = i;
                            break;
                        }
                    }
                    //si es asi, checar si es posible meterla(palabras cruzadas)
                    if (indiceCompatible > -1) {
                        int parteDerechaPalabra = tamPalabra - indiceCompatible;
                        int parteIzqPalara = indiceCompatible;
                        for (int i = 0; i < parteIzqPalara; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna, fila + i);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        for (int i = indiceCompatible; i < tamPalabra; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna, fila + i);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        if (caminoLibre) {
                            //poner la palabra  
                            for (int i = 0; i < tamPalabra; i++) {
                                cuadricula.insertarChar(letrasEnPalabra[i], columna, fila + i);
                            }
                            System.out.println("==============");
                            System.out.println(letrasEnPalabra);
                            System.out.println("==============");
                        }
                    }
                }
                break;
            case 'd':
                for (int i = 0; i < tamPalabra; i++) {
                    statusCasilla = cuadricula.statusCasilla(columna + i, fila + i);
                    if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {//checar el estado cuando la casilla consultada este fuera del rango
                        caminoLibre = false;
                        columnaCasillaOcupada = columna + i;
                        filaCasillaOcupada = fila + i;
                        letraEnPalabraExistente = cuadricula.getLetra(columnaCasillaOcupada, filaCasillaOcupada);
                        break;
                    }
                }

                if (caminoLibre) {
                    //poner la palabra  
                    for (int i = 0; i < tamPalabra; i++) {
                        cuadricula.insertarChar(letrasEnPalabra[i], columna + i, fila + i);
                    }
                    System.out.println("==============");
                    System.out.println(letrasEnPalabra);
                    System.out.println("==============");
                } else {
                    //checar si alguna letra de la palabra es igual a una que ya este ocupada
                    for (int i = 0; i < tamPalabra; i++) {
                        if (letrasEnPalabra[i] == letraEnPalabraExistente) {
                            indiceCompatible = i;
                            break;
                        }
                    }
                    //si es asi, checar si es posible meterla(palabras cruzadas)
                    if (indiceCompatible > -1) {
                        int parteDerechaPalabra = tamPalabra - indiceCompatible;
                        int parteIzqPalara = indiceCompatible;
                        for (int i = 0; i < parteIzqPalara; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna + i, fila + i);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        for (int i = indiceCompatible; i < tamPalabra; i++) {
                            statusCasilla = cuadricula.statusCasilla(columna + i, fila + i);
                            if (statusCasilla.equals(EstatusCasilla.ocupado) || statusCasilla.equals(EstatusCasilla.fueraDeRango)) {
                                caminoLibre = false;
                                break;
                            }
                        }
                        if (caminoLibre) {
                            //poner la palabra  
                            for (int i = 0; i < tamPalabra; i++) {
                                cuadricula.insertarChar(letrasEnPalabra[i], columna + i, fila + i);
                            }
                            System.out.println("==============");
                            System.out.println(letrasEnPalabra);
                            System.out.println("==============");
                        }
                    }
                }
                break;
            default:
                break;
        }

        /*
        obtener el tamaño de la palabra
        verificar el estado de cada una de las casillas iniciando por la casilla especificada en (columna, fila)
        si todas las casillas no pertenecen a una palabra se inserta la palabra
        sino
        verificar si alguna letra que ya pertenezca a una palabra vertical coincide con una letra de la palabra a insertar
        si es asi
         verificar las casillas al rededor  y si no se sale dela cuadricula y no tienen estado se escriben los caracteres de la palabra, 
        sino
        pues no se inserta la palabra, y se regresa la misma cuadricula
         */
        return cuadricula;
    }

    private Cuadricula insertarPalabraInversa(Cuadricula cuadricula, String palabra, int columna, int fila, char direccion) {
        Cuadricula cuadriculaModificada;
//        invertir palabra
        String palabraInvertida = obtenerPalabraInversa(palabra);
//        usar metodo de insertar palabra
        cuadriculaModificada = insertarPalabra(cuadricula, palabraInvertida, columna, fila, direccion);
        return cuadriculaModificada;
    }

    private char[] obtenerLetrasDePalabra(String palabra) {
        int tamPalabra = palabra.length();
        char[] letrasDePalabra = new char[tamPalabra];
        for (int i = 0; i < tamPalabra; i++) {
            letrasDePalabra[i] = palabra.charAt(i);
        }
        return letrasDePalabra;
    }

    private String obtenerPalabraInversa(String palabra) {
        int tamPalabra = palabra.length();
        String palabraInvertida;
        char[] letrasDePalabraInversa= new char[tamPalabra];
        char[] letrasDePalabra = new char[tamPalabra];
        
        letrasDePalabra = obtenerLetrasDePalabra(palabra);
        int indiceInver=0;
        for (int i = tamPalabra - 1; i >= 0; i--) {
            letrasDePalabraInversa[indiceInver++] = letrasDePalabra[i];
        }
        palabraInvertida = new String(letrasDePalabraInversa);
        return palabraInvertida;
    }

    public static void main(String[] args) throws IOException {
        Cuadricula sopaDeLetrasConNivel;
        Cuadricula sopaDeLetrasVacia = new Cuadricula(15);
        InsertadorDePalabras nuevoInsertador = new InsertadorDePalabras();
        sopaDeLetrasConNivel = nuevoInsertador.insertarNivelDificil(sopaDeLetrasVacia);
        sopaDeLetrasConNivel.imprimirCuadricula();
    }
//    private ArrayList obtenerPalabrasDeDicc(String direcc, int tamañoPalabra) throws IOException {
//        ArrayList dato;
//        dato = Diccionario.readDictionary(direcc, tamañoPalabra);
//        for (int x = 0; x < dato.size(); x++) {
//            System.out.println(dato.get(x));
//        }
//        return dato;
//    }
}
