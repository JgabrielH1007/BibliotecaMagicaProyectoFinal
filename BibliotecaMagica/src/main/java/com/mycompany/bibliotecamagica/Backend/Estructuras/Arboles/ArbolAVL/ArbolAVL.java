package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL;


import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.Nodo;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gabrielh
 */
public class ArbolAVL {
    private Nodo raiz;
    
    //Misma implementacion del proyecto pasado
    private int obtenerAltura(Nodo nodo){
        if(nodo == null){
            return 0;
        }
        return nodo.getAltura();
    }
    
    private int obtenerFe(Nodo nodo){
        if(nodo == null){
            return 0;
        }
        return obtenerAltura(nodo.getIzq()) - obtenerAltura(nodo.getDer());
    }
    
    private void actualizarAlturaYFe(Nodo nodo){
        if(nodo != null){
            nodo.setAltura(1 + Math.max(obtenerAltura(nodo.getIzq()), obtenerAltura(nodo.getDer())));
            nodo.setFe(obtenerFe(nodo));
        }
    }
    
    private Nodo rotacionDD(Nodo ref){ 
        Nodo nodo = ref;
        Nodo nodo1 = ref.getDer();
        nodo.setDer(nodo1.getIzq());
        nodo1.setIzq(nodo);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        return nodo1;
    }
    
    private Nodo rotacionII(Nodo ref){
        Nodo nodo = ref;
        Nodo nodo1 = ref.getIzq();
        nodo.setIzq(nodo1.getDer());
        nodo1.setDer(nodo);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        return nodo1;
    }
    
    private Nodo rotacionID(Nodo ref){
        Nodo nodo = ref;
        Nodo nodo1 = ref.getIzq();
        Nodo nodo2 = nodo1.getDer();
        nodo.setIzq(nodo2.getDer());
        nodo1.setDer(nodo2.getIzq());
        nodo2.setDer(nodo);
        nodo2.setIzq(nodo1);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        actualizarAlturaYFe(nodo2);
        return nodo2;
    }
    
    private Nodo rotacionDI(Nodo ref){
        Nodo nodo = ref;
        Nodo nodo1 = ref.getDer();
        Nodo nodo2 = nodo1.getIzq();
        nodo.setDer(nodo2.getIzq());
        nodo1.setIzq(nodo2.getDer());
        nodo2.setIzq(nodo);
        nodo2.setDer(nodo1);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        actualizarAlturaYFe(nodo2);
        return nodo2;
    }
    
    private Nodo insertarRecursivo(Nodo nodo, String titulo, Libro libro){
        if(nodo==null){
            Nodo nuevoNodo = new Nodo();
            nuevoNodo.setLibro(libro);
            return nuevoNodo;
        }
        if (titulo.compareTo(nodo.getLibro().getTitulo()) < 0) {
            nodo.setIzq(insertarRecursivo(nodo.getIzq(), titulo, libro));
        } else if (titulo.compareTo(nodo.getLibro().getTitulo()) > 0) {
            nodo.setDer(insertarRecursivo(nodo.getDer(), titulo, libro));
        } else {
            return nodo;
        }
        
        actualizarAlturaYFe(nodo);
        
        if(nodo.getFe() > 1 && nodo.getDer().getFe() > 0){
            return rotacionDD(nodo);
        }else if (nodo.getFe() < -1 && nodo.getIzq().getFe() <= 0) {
            return rotacionII(nodo);
        }else if (nodo.getFe() < -1 && nodo.getIzq().getFe() > 0) {
            return rotacionID(nodo);
        }else if (nodo.getFe() > 1 && nodo.getDer().getFe() < 0) {
            return rotacionDI(nodo);
        }
        return nodo;
    }
    
    private Nodo buscarRecursivo(Nodo nodo, String titulo){
        if(nodo == null || nodo.getLibro().getTitulo().equals(titulo)){
            return nodo;
        }
        if(titulo.compareTo(nodo.getLibro().getTitulo()) <0){
            return buscarRecursivo(nodo.getIzq(), titulo);
        }
        
        return buscarRecursivo(nodo.getDer(), titulo);
    }
    
    private Nodo eliminarRecursivoTitulo(Nodo nodo, String titulo) {
        if (nodo == null) {
            return nodo;
        }

        if (titulo.compareTo(nodo.getLibro().getTitulo()) < 0) {
            nodo.setIzq(eliminarRecursivoTitulo(nodo.getIzq(), titulo));
        } else if (titulo.compareTo(nodo.getLibro().getTitulo()) > 0) {
            nodo.setDer(eliminarRecursivoTitulo(nodo.getDer(), titulo));
        } else {
            if (nodo.getIzq() == null || nodo.getDer() == null) {
                Nodo temp = (nodo.getIzq() != null) ? nodo.getIzq() : nodo.getDer();
                if (temp == null) {
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                Nodo temp = nodo.getDer();
                while (temp.getIzq() != null) {
                    temp = temp.getIzq();
                }
                nodo.setLibro(temp.getLibro());
                nodo.setDer(eliminarRecursivoTitulo(nodo.getDer(), temp.getLibro().getTitulo()));
            }
        }

        if (nodo == null) {
            return nodo;
        }

        actualizarAlturaYFe(nodo);

        if (nodo.getFe() > 1 && obtenerFe(nodo.getIzq()) >= 0) {
            return rotacionII(nodo);
        }
        if (nodo.getFe() < -1 && obtenerFe(nodo.getDer()) <= 0) {
            return rotacionDD(nodo);
        }
        if (nodo.getFe() > 1 && obtenerFe(nodo.getIzq()) < 0) {
            return rotacionID(nodo);
        }
        if (nodo.getFe() < -1 && obtenerFe(nodo.getDer()) > 0) {
            return rotacionDI(nodo);
        }
        return nodo;
    }
    
    private void inOrdenRecursivo(Nodo nodo, ListaEnlazada inOrden){
        if(nodo != null){
            inOrdenRecursivo(nodo.getIzq(), inOrden);
            inOrden.agregarLibro(nodo.getLibro());
            inOrdenRecursivo(nodo.getDer(), inOrden);
        }
    }
    
    public ListaEnlazada obtenerInOrden(){
        ListaEnlazada inOrden = new ListaEnlazada();
        inOrdenRecursivo(raiz, inOrden);
        return inOrden;
    }
    
    public void eliminar(String titulo){
        raiz = eliminarRecursivoTitulo(raiz, titulo);
    }
    
    public Nodo buscar(String titulo){
        return buscarRecursivo(raiz, titulo);
    }
    
    public void insertar(String titulo, Libro libro){
        raiz = insertarRecursivo(raiz, titulo, libro);
    }
    
    //Aqui generare la grafica.
    
}
