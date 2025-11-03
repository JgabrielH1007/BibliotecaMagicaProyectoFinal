/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Entidades;

import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.ArbolAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolB.ArbolB;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolBPlus.ArbolBPlus;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Cola.Cola;
import com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.TablaHash.TablaHash;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;

/**
 *
 * @author gabrielh
 */
public class Biblioteca {
    private String id;
    private String nombre;
    private String ubicacion;
    private int tiempoIngreso;
    private int tiempoTraspaso;
    private int intervaloDespacho;
    private Cola colaIngreso;
    private Cola colaTraspaso;
    private Cola colaSalida;
    private ListaEnlazada catalogo;
    private ArbolAVL arbolTitulo;
    private ArbolB arbolAnio;
    private ArbolBPlus arbolGenero;
    private TablaHash tabla;
    private Libro[][] inventario;
    
    public Biblioteca(){
        colaIngreso = new Cola();
        colaTraspaso = new Cola();
        colaSalida = new Cola();
        catalogo = new ListaEnlazada();
        arbolTitulo = new ArbolAVL();
        arbolAnio = new ArbolB();
        arbolGenero = new ArbolBPlus(3);
        tabla = new TablaHash(1250);
    }
    
    
    public void iniciarTabla(int cantidadLibros){
        
    }
    
    public void inicializarInventario(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getTiempoIngreso() {
        return tiempoIngreso;
    }

    public void setTiempoIngreso(int tiempoIngreso) {
        this.tiempoIngreso = tiempoIngreso;
    }

    public int getTiempoTraspaso() {
        return tiempoTraspaso;
    }

    public void setTiempoTraspaso(int tiempoTraspaso) {
        this.tiempoTraspaso = tiempoTraspaso;
    }

    public int getIntervaloDespacho() {
        return intervaloDespacho;
    }

    public void setIntervaloDespacho(int intervaloDespacho) {
        this.intervaloDespacho = intervaloDespacho;
    }

    public Cola getColaIngreso() {
        return colaIngreso;
    }

    public void setColaIngreso(Cola colaIngreso) {
        this.colaIngreso = colaIngreso;
    }

    public Cola getColaTraspaso() {
        return colaTraspaso;
    }

    public void setColaTraspaso(Cola colaTraspaso) {
        this.colaTraspaso = colaTraspaso;
    }

    public Cola getColaSalida() {
        return colaSalida;
    }

    public void setColaSalida(Cola colaSalida) {
        this.colaSalida = colaSalida;
    }

    public ListaEnlazada getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(ListaEnlazada catalogo) {
        this.catalogo = catalogo;
    }

    public ArbolAVL getArbolTitulo() {
        return arbolTitulo;
    }

    public void setArbolTitulo(ArbolAVL arbolTitulo) {
        this.arbolTitulo = arbolTitulo;
    }

    public ArbolB getArbolAnio() {
        return arbolAnio;
    }

    public void setArbolAnio(ArbolB arbolAnio) {
        this.arbolAnio = arbolAnio;
    }

    public ArbolBPlus getArbolGenero() {
        return arbolGenero;
    }

    public void setArbolGenero(ArbolBPlus arbolGenero) {
        this.arbolGenero = arbolGenero;
    }

    public Libro[][] getInventario() {
        return inventario;
    }

    public void setInventario(Libro[][] inventario) {
        this.inventario = inventario;
    }

    public TablaHash getTabla() {
        return tabla;
    }

    public void setTabla(TablaHash tabla) {
        this.tabla = tabla;
    }
    
    public void agregarLibro(Libro libro) {
    if (libro == null) return;

    if (libro.getBibliotecaDestino().equalsIgnoreCase(this.nombre)) {
        if (colaIngreso == null) colaIngreso = new Cola();
        colaIngreso.encolar(libro);
        System.out.println("📘 Libro " + libro.getTitulo() + " agregado a la cola de ingreso de " + nombre);
    } else {
        if (colaSalida == null) colaSalida = new Cola();
        colaSalida.encolar(libro);
        System.out.println("🚚 Libro " + libro.getTitulo() + " agregado a la cola de salida de " + nombre);
    }
}
    
    
    
}
