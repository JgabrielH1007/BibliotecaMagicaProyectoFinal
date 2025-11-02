/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Lista;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;

/**
 *
 * @author gabrielh
 */
public class NodoL {
    private Libro libro;
    private NodoL siguiente;

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public NodoL getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoL siguiente) {
        this.siguiente = siguiente;
    }
 
}
