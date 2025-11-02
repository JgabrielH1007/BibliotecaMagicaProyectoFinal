/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolBPlus;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gabrielh
 */
public class ArbolBPlus {
    private Nodo raiz;
    private int orden;
    private int maxClaves;
    private int minClaves;
    
    public ArbolBPlus(int orden){
        this.orden = orden;
        this.maxClaves= orden -1;
        this.minClaves = (int) Math.ceil(orden/2.0)-1;
        this.raiz = new Nodo(true);
    }
    
    public void insertar(Libro libro) {
        String key = libro.getGenero();
        String[] promoKey = new String[1];
        Nodo[] promoNodo = new Nodo[1];
        boolean[] splitHappened = new boolean[1];

        insertarRecursivo(raiz, key, libro, promoKey, promoNodo, splitHappened);

        if (splitHappened[0] && promoNodo[0] != null) {
            Nodo nuevaRaiz = new Nodo(false);
            nuevaRaiz.getClaves().add(promoKey[0]);
            nuevaRaiz.getHijos().add(raiz);
            nuevaRaiz.getHijos().add(promoNodo[0]);
            raiz = nuevaRaiz;
        }
    }

    private void insertarRecursivo(Nodo nodo, String key, Libro libro,
                                   String[] promoKey, Nodo[] promoNodo, boolean[] splitHappened) {

        if (nodo.isEsHoja()) {
            insertarEnHoja(nodo, libro);

            if (nodo.getClaves().size() > maxClaves) {
                Map.Entry<Nodo, String> div = dividirHoja(nodo);
                promoKey[0] = div.getValue();
                promoNodo[0] = div.getKey();
                splitHappened[0] = true;
            } else {
                splitHappened[0] = false;
            }
            return;
        }

        int i = 0;
        while (i < nodo.getClaves().size() && key.compareTo(nodo.getClaves().get(i)) >= 0) {
            i++;
        }

        String[] childPromoKey = new String[1];
        Nodo[] childPromoNodo = new Nodo[1];
        boolean[] childSplit = new boolean[1];

        insertarRecursivo(nodo.getHijos().get(i), key, libro, childPromoKey, childPromoNodo, childSplit);

        if (childSplit[0]) {
            nodo.getClaves().add(i, childPromoKey[0]);
            nodo.getHijos().add(i + 1, childPromoNodo[0]);

            if (nodo.getClaves().size() > maxClaves) {
                Map.Entry<Nodo, String> div = dividirInterno(nodo);
                promoKey[0] = div.getValue();
                promoNodo[0] = div.getKey();
                splitHappened[0] = true;
                return;
            }
        }

        splitHappened[0] = false;
    }
    
    private void insertarEnHoja(Nodo hoja, Libro libro) {
        String key = libro.getGenero();
        List<String> claves = hoja.getClaves();
        List<List<Libro>> registros = hoja.getReg();

        int i = 0;
        while (i < claves.size() && key.compareTo(claves.get(i)) > 0) i++;

        if (i < claves.size() && claves.get(i).equals(key)) {
            registros.get(i).add(libro);
        } else {
            claves.add(i, key);
            List<Libro> lista = new ArrayList<>();
            lista.add(libro);
            registros.add(i, lista);
        }
    }
    
    private Map.Entry<Nodo, String> dividirHoja(Nodo hoja) {
        Nodo nuevo = new Nodo(true);
        int mitad = hoja.getClaves().size() / 2;

        nuevo.getClaves().addAll(hoja.getClaves().subList(mitad, hoja.getClaves().size()));
        nuevo.getReg().addAll(hoja.getReg().subList(mitad, hoja.getReg().size()));

        hoja.getClaves().subList(mitad, hoja.getClaves().size()).clear();
        hoja.getReg().subList(mitad, hoja.getReg().size()).clear();

        nuevo.setSiguiente(hoja.getSiguiente());
        hoja.setSiguiente(nuevo);

        String promoKey = nuevo.getClaves().get(0);
        return new AbstractMap.SimpleEntry<>(nuevo, promoKey);
    }
    
    private Map.Entry<Nodo, String> dividirInterno(Nodo nodo) {
        Nodo nuevo = new Nodo(false);
        int mitad = nodo.getClaves().size() / 2;
        String promoKey = nodo.getClaves().get(mitad);

        nuevo.getClaves().addAll(nodo.getClaves().subList(mitad + 1, nodo.getClaves().size()));
        nuevo.getHijos().addAll(nodo.getHijos().subList(mitad + 1, nodo.getHijos().size()));

        nodo.getClaves().subList(mitad, nodo.getClaves().size()).clear();
        nodo.getHijos().subList(mitad + 1, nodo.getHijos().size()).clear();

        return new AbstractMap.SimpleEntry<>(nuevo, promoKey);
    }

    public void buscarPorGenero(String genero, List<Libro> resultados) {
        Nodo hoja = encontrarHoja(raiz, genero);

        while (hoja != null) {
            for (int i = 0; i < hoja.getClaves().size(); i++) {
                if (hoja.getClaves().get(i).equalsIgnoreCase(genero)) {
                    for (Libro libro : hoja.getReg().get(i)) {
                        resultados.add(libro);
                    }
                    return;
                }
            }
            hoja = hoja.getSiguiente();
        }
    }
    
    private Nodo encontrarHoja(Nodo nodo, String key) {
        if (nodo.isEsHoja()) return nodo;

        int i = 0;
        while (i < nodo.getClaves().size() && key.compareTo(nodo.getClaves().get(i)) >= 0) i++;
        return encontrarHoja(nodo.getHijos().get(i), key);
    }
    
    public boolean eliminarPorTitulo(String titulo) {
        return eliminarEnHojasPorTitulo(raiz, titulo);
    }

    private boolean eliminarEnHojasPorTitulo(Nodo nodo, String titulo) {
        if (nodo.isEsHoja()) {
            for (int i = 0; i < nodo.getReg().size(); i++) {
                List<Libro> lista = nodo.getReg().get(i);
                lista.removeIf(l -> l.getTitulo().equalsIgnoreCase(titulo));
            }
            return true;
        }

        for (Nodo hijo : nodo.getHijos()) {
            if (eliminarEnHojasPorTitulo(hijo, titulo)) return true;
        }
        return false;
    }
    
}
