package com.minsait.data.technologies.sopadeletras;

import java.util.ArrayList;
import java.util.List;

class Cuadricula {

    //para pintar de un color diferente en consola
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    private int tamanio = 10;
    Casilla cuadricula[][];

    public Cuadricula() {
        iniciaCuadricula();
    }

    public Cuadricula(int tamanio) {
        this.tamanio = tamanio;
        iniciaCuadricula();
    }

    public int getTamanio() {
        return this.tamanio;
    }

//    public List<List<Casilla>> getCasillas() {
//        return casilla;
//    }
//    public List<Casilla> getCasillasFila() {
//        return casillasFila;
//    }
    public Enum statusCasilla(int columna, int fila) {
        if (columna >= this.tamanio || fila >= this.tamanio || columna < 0 || fila < 0) {
            return EstatusCasilla.fueraDeRango;
        } else {
            return this.cuadricula[columna][fila].getEstado();
        }
    }

    public void insertarChar(char letra, int columna, int fila) {
        this.cuadricula[columna][fila].setLetra(letra);
        this.cuadricula[columna][fila].setEstado(EstatusCasilla.ocupado);
    }

    public char getLetra(int columna, int fila) {
        if (columna >= this.tamanio || fila >= this.tamanio || columna < 0 || fila < 0) {
            return Character.MIN_VALUE;
        } else {
            return this.cuadricula[columna][fila].getLetra();
        }
        
        
    }

    private void iniciaCuadricula() {
        this.cuadricula = new Casilla[this.tamanio][this.tamanio];
        for (int fila = 0; fila < tamanio; fila++) {
            for (int columna = 0; columna < tamanio; columna++) {
                cuadricula[fila][columna] = new Casilla(columna, fila);
            }
        }
    }

    public void imprimirCuadricula() {
        for (int fila = 0; fila < tamanio; fila++) {
            for (int columna = 0; columna < tamanio; columna++) {
                if (cuadricula[columna][fila].getEstado().equals(EstatusCasilla.ocupado)) {
                    System.out.print(ANSI_RED + cuadricula[columna][fila].getLetra() + "\t" + ANSI_RESET);
                } else {
                    System.out.print(cuadricula[columna][fila].getLetra() + "\t");
                }
            }
            System.out.println("\n");
        }
    }
}
