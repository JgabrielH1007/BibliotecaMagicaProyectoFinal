/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.TablaHash;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;

/**
 *
 * @author gabrielh
 */
public class Nodo {
    long clave;
    Libro valor;
    Nodo siguiente;

    public Nodo(long clave, Libro valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}
