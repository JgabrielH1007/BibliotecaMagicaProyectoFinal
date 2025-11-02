/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Cola;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielh
 */
public class Cola {
    private Nodo frente;
    private Nodo finalCola;
    private int tamanio;
    
    public void encolar(Libro libro){
        Nodo nuevo = new Nodo(libro);
        if(estaVacia()){
            frente =  finalCola = nuevo;
        }else{
            finalCola.setSiguiente(nuevo);
            finalCola = nuevo;
        }
        tamanio++;
    }
    
    public Libro desencolar(){
        if(estaVacia()){
            return null;
        }
        Libro libro = frente.getLibro();
        frente = frente.getSiguiente();
        if(frente == null){
            finalCola = null;            
        }
        tamanio--;
        return libro;
    }
    
    public boolean estaVacia(){
        return frente == null;
    }
    
    public int tamanio(){
        return tamanio;
    }
    
    public List<Libro> toList() {
        List<Libro> lista = new ArrayList<>();
        Nodo actual = frente; // frente de la cola
        while (actual != null) {
            lista.add(actual.getLibro());
            actual = actual.getSiguiente();
        }
        return lista;
    }
}
