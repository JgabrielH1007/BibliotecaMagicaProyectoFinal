/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolB;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class Nodo {
    private int grado;
    private boolean esHoja;
    private List<Libro> llaves;
    private List<Nodo> hijos;

    public Nodo(int grado, boolean esHoja) {
        this.grado = grado;
        this.esHoja = esHoja;
        this.llaves = new ArrayList<>();
        this.hijos = new ArrayList<>();
    }
    
    public void buscarPorRango(int anioInicial, int anioFinal, ListaEnlazada resultados){
        int i;
        for(i = 0; i<llaves.size();i++){
            if((!esHoja)){
                hijos.get(i).buscarPorRango(anioInicial, anioFinal, resultados);
            }
            if (llaves.get(i).getAnio() >= anioInicial && llaves.get(i).getAnio() <= anioFinal) {
                resultados.agregarLibro(llaves.get(i));
            }        
        }
        if(!esHoja){
            hijos.get(i).buscarPorRango(anioInicial, anioFinal, resultados);
        }
    }
    
    public void insertarNoLleno(Libro libro){
        int llave = libro.getAnio();
        int i = llaves.size()-1;
        
        if(esHoja){
            while(i>=0 && llaves.get(i).getAnio()>llave){
                i--;
            }
            llaves.add(i+1,libro);
        }else{
            while(i>=0 && llaves.get(i).getAnio() > llave){
                i--;
            }
            i++;
            if(hijos.get(i).llaves.size() == 2*grado-1){
                dividir(i, hijos.get(i));
                if(llaves.get(i).getAnio()<llave){
                    i++;
                }
            }
            hijos.get(i).insertarNoLleno(libro);
        }
    }
    
    public void dividir(int indice, Nodo hijo){
        Libro midObj = hijo.llaves.get(grado-1);
        Nodo nuevo = new Nodo(hijo.grado, hijo.esHoja);
        
        for(int j =0; j <grado-1;j++){
            nuevo.llaves.add(hijo.llaves.get(j+grado));
        }
        if(!hijo.esHoja){
                for(int j=0;j<grado;j++){
                    nuevo.hijos.add(hijo.hijos.get(j+grado));
                }
        }
        
        hijo.llaves = new ArrayList<>(hijo.llaves.subList(0, grado-1));
        if(!hijo.esHoja){
            hijo.hijos = new ArrayList<>(hijo.hijos.subList(0, grado));
        }
        hijos.add(indice+1, nuevo);
        llaves.add(indice, midObj);
    }

    public int encontrarLLave(int anio){
        for (int i = 0; i<llaves.size();i++){
            if(llaves.get(i).getAnio() == anio){
                return i;
            }
        }
        return -1;
    }
    
    public void fusionar(int indice){
        Nodo hijo = hijos.get(indice);
        Nodo hermano = hijos.get(indice+1);
        
        hijo.llaves.add(llaves.get(indice));
        
        for (int i = 0; i < hermano.llaves.size(); i++) {
            hijo.llaves.add(hermano.llaves.get(i));
        }
        if (!hijo.esHoja) {
            for (int i = 0; i < hermano.hijos.size(); i++) {
                hijo.hijos.add(hermano.hijos.get(i));
            }
        }
        
        llaves.remove(indice);
        hijos.remove(indice+1);
        
        hermano = null;
    }
    
    public boolean prestarDeAnterior(int idx) {
        Nodo hijo = hijos.get(idx);
        Nodo hermano = hijos.get(idx - 1);

        hijo.llaves.add(0, llaves.get(idx - 1));

        if (!hijo.esHoja) {
            hijo.hijos.add(0, hermano.hijos.get(hermano.hijos.size() - 1));
        }

        llaves.set(idx - 1, hermano.llaves.get(hermano.llaves.size() - 1));

        hermano.llaves.remove(hermano.llaves.size() - 1);
        if (!hermano.esHoja) {
            hermano.hijos.remove(hermano.hijos.size() - 1);
        }

        return true;
    }
    
    public boolean prestarDeSiguiente(int indice) {
        Nodo hijo = hijos.get(indice);
        Nodo hermano = hijos.get(indice + 1);

        hijo.llaves.add(llaves.get(indice));

        if (!hijo.esHoja) {
            hijo.hijos.add(hermano.hijos.get(0));
        }

        llaves.set(indice, hermano.llaves.get(0));

        hermano.llaves.remove(0);

        if (!hermano.esHoja) {
            hermano.hijos.remove(0);
        }

        return true;
    }
    
   public void llenar(int indice){
       if (indice != 0 && hijos.get(indice-1).llaves.size()>=grado) {
           prestarDeAnterior(indice);
       }else if(indice!= llaves.size() && hijos.get(indice+1).llaves.size()>=grado){
           prestarDeSiguiente(indice);
       }else{
           if(indice != llaves.size()){
               fusionar(indice);
           }else{
               fusionar(indice-1);
           }
       }
   }
   
    public void eliminarDeHoja(int indice){
        llaves.remove(indice);
    }
   
    public Libro obtenerPredecesor(int idx) {
        Nodo cur = hijos.get(idx);
        while (!cur.esHoja) {
            cur = cur.hijos.get(cur.hijos.size() - 1); 
        }
        return cur.llaves.get(cur.llaves.size() - 1); 
    }

    public Libro obtenerSucesor(int idx) {
        Nodo cur = hijos.get(idx + 1);
        while (!cur.esHoja) {
            cur = cur.hijos.get(0);
        }
        return cur.llaves.get(0); 
    }

    public void eliminarDeNoHoja(int indice, Libro libEspecifico){
        Libro obj = obtenerPredecesor(indice);
        int llave = obj.getAnio();
        if (hijos.get(indice).llaves.size() >= grado) {
            Libro preObj = obtenerPredecesor(indice);
            llaves.set(indice, preObj);
            hijos.get(indice).eliminar(preObj.getAnio(),libEspecifico);
        }else if(hijos.get(indice+1).llaves.size()>=grado){
            Libro succObj = obtenerSucesor(indice);
            llaves.set(indice, succObj);
            hijos.get(indice+1).eliminar(indice, libEspecifico);
        }else{
            fusionar(indice);
            hijos.get(indice).eliminar(llave,libEspecifico);
        }
    }
    
    public void eliminar(int anio, Libro libEspecifico){
        int indice = encontrarLLave(anio);
        if(indice!=-1){
            while (indice < llaves.size()&& llaves.get(indice).getAnio()==anio){
                if(libEspecifico == null || llaves.get(indice).getIsnb().equals(libEspecifico.getIsnb())){
                    if(esHoja){
                        eliminarDeHoja(indice);
                    }else{
                        eliminarDeNoHoja(indice, libEspecifico);
                    }
                    return;
                }
                ++indice;
            }
        }else{
            if (esHoja) {
                JOptionPane.showMessageDialog(null,"Libro: "+libEspecifico.getTitulo()+"\n"+"No existe "
                        + "en arbol b", "LIBRO NO EXISTE", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceHijo = 0;
            while(indiceHijo<llaves.size()&&llaves.get(indiceHijo).getAnio()<anio){
                ++indiceHijo;
            }
            if(hijos.get(indiceHijo).llaves.size()<grado){
                llenar(indiceHijo);
            }
            hijos.get(indiceHijo).eliminar(anio, libEspecifico);
        }
    }

    public List<Libro> getLlaves() {
        return llaves;
    }

    public List<Nodo> getHijos() {
        return hijos;
    }

    public boolean isEsHoja() {
        return esHoja;
    }

    public void setEsHoja(boolean esHoja) {
        this.esHoja = esHoja;
    }
    
    
    
}
