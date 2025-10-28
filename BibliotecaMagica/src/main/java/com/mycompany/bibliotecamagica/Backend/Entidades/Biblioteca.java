/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Entidades;

import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.ArbolAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolB.ArbolB;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolBPlus.ArbolBPlus;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Cola.Cola;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;

/**
 *
 * @author gabrielh
 */
public class Biblioteca {
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
    private Libro[][] inventario;
    
    public void agregarLibro(Libro libro){
        
    }
    
    public void inicializarInventario(){
        
    }
    
    
    
}
