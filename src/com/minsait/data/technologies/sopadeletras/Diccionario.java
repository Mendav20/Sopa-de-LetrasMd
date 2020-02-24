/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.minsait.data.technologies.sopadeletras;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author w10
 */
public class Diccionario {

    private static ArrayList<String> listaGeneral = new ArrayList<String>();
    private String localizacionArchivo;
    public static ArrayList<String> lista = new ArrayList<String>();
    public static String cadena;

    public Diccionario(String archivo) throws IOException {
        this.localizacionArchivo = archivo;
        readFileDictionary(this.localizacionArchivo);
    }

    private ArrayList<String> selectWords(ArrayList array, int tam) {
        Random random = new Random();
        int i = 0;
        while (listaGeneral.size() > 1) {
            i++;
            int randomIndex = random.nextInt(listaGeneral.size());
            if ((listaGeneral.get(randomIndex).length()) < tam && (listaGeneral.get(randomIndex).length()) > 2) {
                lista.add(listaGeneral.get(randomIndex));
            }
            if (i > listaGeneral.size()) {
                break;
            }
        }
        return lista;
    }

    private void readFileDictionary(String archivo) throws FileNotFoundException, IOException {

        BufferedReader b = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "utf-8"));
        while ((cadena = b.readLine()) != null) {
            cadena=stripAccents(cadena);
            cadena = cadena.toUpperCase();
            this.listaGeneral.add(cadena);
        }
        b.close();
    }

    public ArrayList<String> readDictionary(int maxTamPalabra) throws FileNotFoundException, IOException {
        ArrayList listaPreparada;
        listaPreparada = selectWords(listaGeneral, maxTamPalabra);
        return listaPreparada;
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
