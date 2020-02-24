package com.minsait.data.technologies.sopadeletras;

public class Casilla {

    private boolean color;
    private Integer posicionColumna;
    private Integer posicionFila;
    private EstatusCasilla estado;
    private char letra;
    int aEnAscii = 65;
    int zEnAscii = 90;

    public Casilla( Integer posicionColumna, Integer posicionFila) {
        this.posicionColumna = posicionColumna;
        this.posicionFila = posicionFila;
        this.estado = EstatusCasilla.vacio;
        this.letra = generarLetraAleatoria();
        this.color=false;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public char getLetra() {
        return this.letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public Integer getPosicionColumna() {
        return posicionColumna;
    }

    public Integer getPosicionFila() {
        return posicionFila;
    }

    public EstatusCasilla getEstado() {
        return this.estado;
    }

    public void setEstado(EstatusCasilla estado) {
        this.estado = estado;
    }

    private char generarLetraAleatoria() {
        int generaNumeroAleatorio = (int) Math.floor(Math.random() * (zEnAscii - aEnAscii) + aEnAscii);
        return letra = (char) generaNumeroAleatorio;
    }

}
