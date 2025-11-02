/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Lista;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class ListaEnlazada {
    private NodoL inicio;
    private int tamanio;
    
    public void agregarLibro(Libro libro){
        if(libro == null){
            return;
        }
        NodoL nuevoNodo = new NodoL();
        nuevoNodo.setLibro(libro);
        nuevoNodo.setSiguiente(inicio);
        
        if (estaVacia()) {
            inicio = nuevoNodo;
        } else {
            NodoL actual = inicio;
            while(actual.getSiguiente()!= null){
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamanio++;
    }
    
    public void eliminarLibro(String titulo){
        if(estaVacia()){
            JOptionPane.showMessageDialog(null,"Lista esta vacia", "LISTA VACIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        NodoL actual = inicio;
        NodoL anterior = null;
        
        while(actual != null && !actual.getLibro().getTitulo().equals(titulo)){
            anterior = actual;
            actual = actual.getSiguiente();
        }
        
        if(actual == null){
            JOptionPane.showMessageDialog(null,"Libro: "+titulo+", no encontrado", "LIBRO NO ENCONTRADO", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(anterior == null){
            inicio = actual.getSiguiente();
        }else{
            anterior.setSiguiente(actual.getSiguiente());
        }
        
        actual.setLibro(null);
        actual.setSiguiente(null);
        tamanio--;
    }
    
    public Libro buscarPorTitulo(String titulo){
        NodoL actual = inicio;
        while (actual != null) {
            if(actual.getLibro().getTitulo().equals(titulo)){
                return actual.getLibro();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    public boolean estaVacia(){
        return tamanio == 0;
    }
    
    public int getTamanio(){
        return tamanio;
    }
    
    public ListaEnlazada listarPorInserPorAutor() {
        if (estaVacia() || inicio.getSiguiente() == null) {
            return this; 
        }

        NodoL ordenado = null; 
        NodoL actual = inicio;

        while (actual != null) {
            NodoL siguiente = actual.getSiguiente(); 
            actual.setSiguiente(null);

            if (ordenado == null || 
                actual.getLibro().getAutor().compareToIgnoreCase(ordenado.getLibro().getAutor()) < 0) {
                actual.setSiguiente(ordenado);
                ordenado = actual;
            } else {
                NodoL temp = ordenado;
                while (temp.getSiguiente() != null &&
                       temp.getSiguiente().getLibro().getAutor().compareToIgnoreCase(actual.getLibro().getAutor()) <= 0) {
                    temp = temp.getSiguiente();
                }
                actual.setSiguiente(temp.getSiguiente());
                temp.setSiguiente(actual);
            }

            actual = siguiente;
        }

        inicio = ordenado;
        return this;
    }
    
    public NodoL getInicio() {
        return inicio;
    }
    
    public ListaEnlazada listarPorAnioShell() {
        int n = getTamanio();
        if (n <= 1) return this; 

        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                NodoL actual = getNodo(i);       
                Libro temp = actual.getLibro();
                int j = i;

                NodoL anterior = getNodo(j - gap);
                while (j >= gap && anterior.getLibro().getAnio() > temp.getAnio()) {
                    actual.setLibro(anterior.getLibro());
                    j -= gap;
                    actual = anterior;
                    anterior = getNodo(j - gap);
                }
                actual.setLibro(temp);
            }
        }

        return this;
    }

    private NodoL getNodo(int index) {
        if (index < 0 || index >= tamanio) return null;
        NodoL actual = inicio;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual;
    }
    
    public ListaEnlazada listarPorQuickSortPorTitulo() {
        inicio = quickSort(inicio);
        return this;
    }

    private NodoL quickSort(NodoL head) {
        if (head == null || head.getSiguiente() == null) {
            return head; 
        }

        NodoL pivote = head;
        NodoL menores = null, iguales = null, mayores = null;

        NodoL actual = head;
        while (actual != null) {
            NodoL siguiente = actual.getSiguiente();
            actual.setSiguiente(null);
            int cmp = actual.getLibro().getTitulo().compareToIgnoreCase(pivote.getLibro().getTitulo());
            if (cmp < 0) {
                menores = agregarNodo(menores, actual);
            } else if (cmp == 0) {
                iguales = agregarNodo(iguales, actual);
            } else {
                mayores = agregarNodo(mayores, actual);
            }
            actual = siguiente;
        }

        menores = quickSort(menores);
        mayores = quickSort(mayores);

        return concatenar(concatenar(menores, iguales), mayores);
    }

    private NodoL agregarNodo(NodoL head, NodoL nodo) {
        if (head == null) return nodo;
        NodoL temp = head;
        while (temp.getSiguiente() != null) {
            temp = temp.getSiguiente();
        }
        temp.setSiguiente(nodo);
        return head;
    }

    private NodoL concatenar(NodoL l1, NodoL l2) {
        if (l1 == null) return l2;
        NodoL temp = l1;
        while (temp.getSiguiente() != null) {
            temp = temp.getSiguiente();
        }
        temp.setSiguiente(l2);
        return l1;
    }




   
}
