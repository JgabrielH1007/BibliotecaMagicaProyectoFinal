/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Cola;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;

/**
 *
 * @author gabrielh
 */
public class Nodo {
    private Libro libro;
    private Nodo siguiente;
    
    public Nodo(Libro libro){
        this.libro = libro; 
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
}
