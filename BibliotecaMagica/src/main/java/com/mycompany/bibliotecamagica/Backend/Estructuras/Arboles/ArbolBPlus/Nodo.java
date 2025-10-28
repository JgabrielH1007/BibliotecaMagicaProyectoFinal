/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolBPlus;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabrielh
 */
public class Nodo {
    private boolean esHoja;
    private List<String> claves;
    private List<List<Libro>> reg;
    private List<Nodo> hijos;
    private Nodo siguiente;

    public Nodo(boolean esHoja) {
        this.esHoja = esHoja;
        this.claves = new ArrayList<>();
        this.reg = new ArrayList<>();
        this.hijos = new ArrayList<>();
        this.siguiente = null;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }

    public List<String> getClaves() {
        return claves;
    }

    public void setClaves(List<String> claves) {
        this.claves = claves;
    }

    public List<List<Libro>> getReg() {
        return reg;
    }

    public void setReg(List<List<Libro>> reg) {
        this.reg = reg;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public void setHijos(List<Nodo> hijos) {
        this.hijos = hijos;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
}
