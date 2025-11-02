/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.TablaHash;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielh
 */
public class TablaHash {
    private Nodo[] tabla;
    private int capacidad;
    private int tamaño;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new Nodo[capacidad];
        this.tamaño = 0;
    }

    private int hash(long clave) {
        clave ^= (clave >>> 33);
        clave *= 0xff51afd7ed558ccdL;
        clave ^= (clave >>> 33);
        clave *= 0xc4ceb9fe1a85ec53L;
        clave ^= (clave >>> 33);
        return (int) (Math.abs(clave) & (capacidad - 1));
    }

    public void insertar(Libro libro) {
        long clave = libro.getIsnbNum();
        int indice = hash(clave);
        Nodo nuevo = new Nodo(clave, libro);

        if (tabla[indice] == null) {
            tabla[indice] = nuevo;
        } else {
            Nodo actual = tabla[indice];
            while (actual != null) {
                if (actual.clave == clave) {
                    actual.valor = libro; 
                    return;
                }
                if (actual.siguiente == null) break;
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo; 
        }
        tamaño++;
    }

    public Libro buscar(long clave) {
        int indice = hash(clave);
        Nodo actual = tabla[indice];
        while (actual != null) {
            if (actual.clave == clave) return actual.valor;
            actual = actual.siguiente;
        }
        return null; 
    }

    public boolean eliminar(long clave) {
        int indice = hash(clave);
        Nodo actual = tabla[indice];
        Nodo anterior = null;

        while (actual != null) {
            if (actual.clave == clave) {
                if (anterior == null) {
                    tabla[indice] = actual.siguiente;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamaño--;
                return true;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        return false;
    }

    public void mostrar() {
        for (int i = 0; i < capacidad; i++) {
            System.out.print(i + ": ");
            Nodo actual = tabla[i];
            while (actual != null) {
                System.out.print(" -> " + actual.valor);
                actual = actual.siguiente;
            }
            System.out.println();
        }
    }

    public int getTamaño() {
        return tamaño;
    }
    
    public List<Libro> getAllLibros() {
        List<Libro> lista = new ArrayList<>();

        for (int i = 0; i < capacidad; i++) {
            Nodo nodo = tabla[i]; 
            while (nodo != null) {
                lista.add(nodo.valor);   
                nodo = nodo.siguiente; 
            }
        }

        return lista;
    }
}
