/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Lista;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class ListaEnlazada {
    private Nodo inicio;
    private int tamanio;
    
    public void agregarLibro(Libro libro){
        if(libro == null){
            return;
        }
        Nodo nuevoNodo = new Nodo();
        nuevoNodo.setLibro(libro);
        nuevoNodo.setSiguiente(inicio);
        
        if (estaVacia()) {
            inicio = nuevoNodo;
        } else {
            Nodo actual = inicio;
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
        Nodo actual = inicio;
        Nodo anterior = null;
        
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
    
    public Libro buscarPorIsbn(String isbn){
        Nodo actual = inicio;
        while (actual != null) {
            if(actual.getLibro().getIsnb().equals(isbn)){
                return actual.getLibro();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
    
    public Libro buscarPorTitulo(String titulo){
        Nodo actual = inicio;
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
    
    
    
}
