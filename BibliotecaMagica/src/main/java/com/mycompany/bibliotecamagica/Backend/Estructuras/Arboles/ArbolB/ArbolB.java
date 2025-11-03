/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolB;

import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
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
    
    public void buscarRango(int anioInicial, int anioFinal, List<Libro> resultados){
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
    
    public void generarGraphviz(String nombreArchivoDot, String nombreArchivoImagen) {
        if (raiz == null) {
            JOptionPane.showMessageDialog(null,
                    "⚠️ Árbol vacío: no se genera archivo DOT.",
                    "Árbol B", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Nodo> nodos = new ArrayList<>();
        Queue<Nodo> cola = new LinkedList<>();
        Set<Nodo> visitados = new HashSet<>();

        cola.add(raiz);
        visitados.add(raiz);

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            if (actual == null) continue;
            nodos.add(actual);
            for (Nodo hijo : actual.getHijos()) {
                if (hijo != null && !visitados.contains(hijo)) {
                    visitados.add(hijo);
                    cola.add(hijo);
                }
            }
        }

        Map<Nodo, String> idMap = new HashMap<>();
        for (int i = 0; i < nodos.size(); i++) {
            idMap.put(nodos.get(i), "n" + (i + 1));
        }

        try (FileWriter archivo = new FileWriter(nombreArchivoDot)) {
            archivo.write("digraph ArbolB {\n");
            archivo.write("    node [shape=record, fontsize=10, style=filled, fillcolor=\"lightblue\"];\n");
            archivo.write("    rankdir=TB;\n");

            for (Nodo n : nodos) {
                StringBuilder label = new StringBuilder("{");
                for (int i = 0; i < n.getLlaves().size(); i++) {
                    Libro libro = n.getLlaves().get(i);
                    label.append(libro != null ? libro.getAnio() : "");
                    if (i + 1 < n.getLlaves().size()) label.append(" | ");
                }
                label.append("}");
                archivo.write("    " + idMap.get(n) + " [label=\"" + label + "\"];\n");
            }

            for (Nodo n : nodos) {
                for (Nodo hijo : n.getHijos()) {
                    if (hijo != null) {
                        archivo.write("    " + idMap.get(n) + " -> " + idMap.get(hijo) + ";\n");
                    }
                }
            }

            archivo.write("}\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al generar el archivo DOT: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String engine = nodos.size() > 1000 ? "sfdp" : "dot";
        String cmd = engine + " -Tsvg \"" + nombreArchivoDot + "\" -o \"" + nombreArchivoImagen + "\"";

        try {
            Process proceso = Runtime.getRuntime().exec(cmd);
            proceso.waitFor();

            if (proceso.exitValue() == 0) {
                JOptionPane.showMessageDialog(null,
                        "✅ Imagen generada correctamente: " + nombreArchivoImagen,
                        "Graphviz", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "⚠️ Graphviz retornó código " + proceso.exitValue() +
                                ". Asegúrate que '" + engine + "' esté instalado y en PATH.",
                        "Error Graphviz", JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(null,
                    "⚠️ Error al ejecutar Graphviz: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
        
    private String escapeLabel(String s) {
        if (s == null) return "";
        return s.replace("\"", "'")
                .replace("\n", " ")
                .replace("\r", " ")
                .trim();
    }

    
}
