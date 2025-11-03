package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL;


import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.NodoAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gabrielh
 */
public class ArbolAVL {
    private NodoAVL raiz;
    
    //Misma implementacion del proyecto pasado
    private int obtenerAltura(NodoAVL nodo){
        if(nodo == null){
            return 0;
        }
        return nodo.getAltura();
    }
    
    private int obtenerFe(NodoAVL nodo){
        if(nodo == null){
            return 0;
        }
        return obtenerAltura(nodo.getIzq()) - obtenerAltura(nodo.getDer());
    }
    
    private void actualizarAlturaYFe(NodoAVL nodo){
        if(nodo != null){
            nodo.setAltura(1 + Math.max(obtenerAltura(nodo.getIzq()), obtenerAltura(nodo.getDer())));
            nodo.setFe(obtenerFe(nodo));
        }
    }
    
    private NodoAVL rotacionDD(NodoAVL ref){ 
        NodoAVL nodo = ref;
        NodoAVL nodo1 = ref.getDer();
        nodo.setDer(nodo1.getIzq());
        nodo1.setIzq(nodo);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        return nodo1;
    }
    
    private NodoAVL rotacionII(NodoAVL ref){
        NodoAVL nodo = ref;
        NodoAVL nodo1 = ref.getIzq();
        nodo.setIzq(nodo1.getDer());
        nodo1.setDer(nodo);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        return nodo1;
    }
    
    private NodoAVL rotacionID(NodoAVL ref){
        NodoAVL nodo = ref;
        NodoAVL nodo1 = ref.getIzq();
        NodoAVL nodo2 = nodo1.getDer();
        nodo.setIzq(nodo2.getDer());
        nodo1.setDer(nodo2.getIzq());
        nodo2.setDer(nodo);
        nodo2.setIzq(nodo1);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        actualizarAlturaYFe(nodo2);
        return nodo2;
    }
    
    private NodoAVL rotacionDI(NodoAVL ref){
        NodoAVL nodo = ref;
        NodoAVL nodo1 = ref.getDer();
        NodoAVL nodo2 = nodo1.getIzq();
        nodo.setDer(nodo2.getIzq());
        nodo1.setIzq(nodo2.getDer());
        nodo2.setIzq(nodo);
        nodo2.setDer(nodo1);
        actualizarAlturaYFe(nodo);
        actualizarAlturaYFe(nodo1);
        actualizarAlturaYFe(nodo2);
        return nodo2;
    }
    
    private NodoAVL insertarRecursivo(NodoAVL nodo, String titulo, Libro libro){
        if(nodo==null){
            NodoAVL nuevoNodo = new NodoAVL();
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
    
    private NodoAVL buscarRecursivo(NodoAVL nodo, String titulo){
        if(nodo == null || nodo.getLibro().getTitulo().equals(titulo)){
            return nodo;
        }
        if(titulo.compareTo(nodo.getLibro().getTitulo()) <0){
            return buscarRecursivo(nodo.getIzq(), titulo);
        }
        
        return buscarRecursivo(nodo.getDer(), titulo);
    }
    
    private NodoAVL eliminarRecursivoTitulo(NodoAVL nodo, String titulo) {
        if (nodo == null) {
            return nodo;
        }

        if (titulo.compareTo(nodo.getLibro().getTitulo()) < 0) {
            nodo.setIzq(eliminarRecursivoTitulo(nodo.getIzq(), titulo));
        } else if (titulo.compareTo(nodo.getLibro().getTitulo()) > 0) {
            nodo.setDer(eliminarRecursivoTitulo(nodo.getDer(), titulo));
        } else {
            if (nodo.getIzq() == null || nodo.getDer() == null) {
                NodoAVL temp = (nodo.getIzq() != null) ? nodo.getIzq() : nodo.getDer();
                if (temp == null) {
                    nodo = null;
                } else {
                    nodo = temp;
                }
            } else {
                NodoAVL temp = nodo.getDer();
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
    
    private void inOrdenRecursivo(NodoAVL nodo, ListaEnlazada inOrden){
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
    
    public NodoAVL buscar(String titulo){
        return buscarRecursivo(raiz, titulo);
    }
    
    public void insertar(String titulo, Libro libro){
        raiz = insertarRecursivo(raiz, titulo, libro);
    }
    
    public NodoAVL getRaiz() {
        return raiz;
    }
    
    public void generarGraphviz(String nombreArchivoDOT, String nombreImagen) {
        try (FileWriter archivo = new FileWriter(nombreArchivoDOT)) {
            archivo.write("digraph G {\n");
            archivo.write("    node [shape=box, style=filled, color=lightblue];\n");
            generarGraphvizRecursivo(raiz, archivo);
            archivo.write("}\n");
        } catch (IOException e) {
            System.out.println("⚠️ No se pudo escribir el archivo DOT: " + e.getMessage());
            return;
        }

        try {
            String comando = "dot -Tsvg " + nombreArchivoDOT + " -o " + nombreImagen;
            Process proceso = Runtime.getRuntime().exec(comando);
            proceso.waitFor();

            if (proceso.exitValue() == 0) {
                System.out.println("✅ Imagen generada correctamente: " + nombreImagen);
            } else {
                System.out.println("❌ Error al generar la imagen con Graphviz.");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("⚠️ Error al ejecutar Graphviz: " + e.getMessage());
        }
    }

    private void generarGraphvizRecursivo(NodoAVL nodo, FileWriter archivo) throws IOException {
        if (nodo != null) {
            String titulo = limpiarTexto(nodo.getLibro().getTitulo());

            archivo.write("    \"" + titulo + "\" [label=\"" + titulo + "\"];\n");

            if (nodo.getIzq() != null) {
                String izq = limpiarTexto(nodo.getIzq().getLibro().getTitulo());
                archivo.write("    \"" + titulo + "\" -> \"" + izq + "\";\n");
                generarGraphvizRecursivo(nodo.getIzq(), archivo);
            }

            if (nodo.getDer() != null) {
                String der = limpiarTexto(nodo.getDer().getLibro().getTitulo());
                archivo.write("    \"" + titulo + "\" -> \"" + der + "\";\n");
                generarGraphvizRecursivo(nodo.getDer(), archivo);
            }
        }
    }

    private String limpiarTexto(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "'")
                    .replace("\\", "")
                    .replace("\n", " ")
                    .replace("{", "(")
                    .replace("}", ")")
                    .trim();
    }
    
}
