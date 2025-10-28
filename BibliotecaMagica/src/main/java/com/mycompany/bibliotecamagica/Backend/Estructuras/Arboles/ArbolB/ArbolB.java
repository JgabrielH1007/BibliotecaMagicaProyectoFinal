/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolB;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class ArbolB {
    private Nodo raiz;
    private final int GRADO = 3;
    
    public ArbolB(){
        this.raiz=null;
    }
    
    public void buscarRango(int anioInicial, int anioFinal, ListaEnlazada resultados){
        if(raiz!=null){
            raiz.buscarPorRango(anioInicial, anioFinal, resultados);
        }
    }
    
    public void insertar(Libro libro){
        if(raiz==null){
            raiz = new Nodo(GRADO,true);
            raiz.getLlaves().add(libro);
            return;
        }
        if(raiz.getLlaves().size()==2*GRADO-1){
            Nodo nuevo = new Nodo(GRADO, false);
            nuevo.getHijos().add(raiz);
            nuevo.dividir(0, raiz);
            int i = 0;
            if(nuevo.getLlaves().get(0).getAnio()<libro.getAnio()){
                i++;
            }
            nuevo.getHijos().get(i).insertarNoLleno(libro);
            raiz = nuevo;
        }else{
            raiz.insertarNoLleno(libro);
        }
    }
    
    public void eliminar(int anio, Libro libEspecifico){
        if(raiz == null){
            JOptionPane.showMessageDialog(null, "Arbol Vacio", "ARBOL VACIO", JOptionPane.WARNING_MESSAGE);
            return;
        }
        raiz.eliminar(anio, libEspecifico);
        if(raiz.getLlaves().isEmpty()){
            Nodo temp = raiz;
            if(raiz.isEsHoja()){
                raiz = null;
            }else{
                raiz = raiz.getHijos().get(0);
            }
            temp = null;
        }
    }
    
}
