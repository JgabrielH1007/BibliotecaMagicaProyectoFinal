/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.Grafo;

import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class Grafo {
    private List<Biblioteca> vertices;
    private int[][] matrizTiempo;
    private double[][] matrizCosto;
    private int[][] siguienteTiempo;
    private int[][] siguienteCosto;
    private int[][] distTiempo;
    private double[][] distCosto;

    public Grafo() {
        vertices = new ArrayList<>();
        matrizTiempo = new int[0][0];
        matrizCosto = new double[0][0];
    }

    public void agregarVertice(Biblioteca biblioteca) {
        for (Biblioteca b : vertices) {
            if (b.getId().equalsIgnoreCase(biblioteca.getId())) {
                System.out.println("⚠️ Biblioteca ya registrada: " + biblioteca.getId());
                return;
            }
        }

        vertices.add(biblioteca);
        redimensionarMatrices();
        System.out.println("✅ Biblioteca añadida al grafo: " + biblioteca);
    }
    
    public void actualizarVertice(Biblioteca biblioteca) {
        for (int i = 0; i < vertices.size(); i++) {
            Biblioteca existente = vertices.get(i);
            if (existente.getId() != null && existente.getId().equalsIgnoreCase(biblioteca.getId())) {
                vertices.set(i, biblioteca);
                System.out.println("♻️ Biblioteca actualizada en el grafo: " + biblioteca.getId());
                return;
            }
        }

        agregarVertice(biblioteca);
    }


    private void redimensionarMatrices() {
        int n = vertices.size();
        int[][] nuevaTiempo = new int[n][n];
        double[][] nuevaCosto = new double[n][n];

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                nuevaTiempo[i][j] = matrizTiempo[i][j];
                nuevaCosto[i][j] = matrizCosto[i][j];
            }
        }

        matrizTiempo = nuevaTiempo;
        matrizCosto = nuevaCosto;
    }

    public void conectar(String id1, String id2, int tiempo, double costo) {
        int i = obtenerIndice(id1);
        int j = obtenerIndice(id2);

        if (i == -1 || j == -1) {
            System.out.println("❌ No se pudo conectar: alguna biblioteca no existe");
            return;
        }

        matrizTiempo[i][j] = tiempo;
        matrizTiempo[j][i] = tiempo;
        matrizCosto[i][j] = costo;
        matrizCosto[j][i] = costo;
        floydWarshallCosto();
        floydWarshallTiempo();
        System.out.println("🔗 Conexión creada: " + id1 + " ↔ " + id2 +
                           " | Tiempo: " + tiempo + " | Costo: " + costo);
    }

    private int obtenerIndice(String id) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getId().equalsIgnoreCase(id)) return i;
        }
        return -1;
    }

    public void floydWarshallTiempo() {
        int n = vertices.size();
        distTiempo = new int[n][n];
        siguienteTiempo = new int[n][n];

        final int INF = Integer.MAX_VALUE / 4;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    distTiempo[i][j] = 0;
                else if (matrizTiempo[i][j] != 0)
                    distTiempo[i][j] = matrizTiempo[i][j];
                else
                    distTiempo[i][j] = INF;

                siguienteTiempo[i][j] = (matrizTiempo[i][j] != 0) ? j : -1;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distTiempo[i][k] + distTiempo[k][j] < distTiempo[i][j]) {
                        distTiempo[i][j] = distTiempo[i][k] + distTiempo[k][j];
                        siguienteTiempo[i][j] = siguienteTiempo[i][k];
                    }
                }
            }
        }

        System.out.println("✅ Rutas óptimas por tiempo calculadas con Floyd–Warshall.");
    }

    public void floydWarshallCosto() {
        int n = vertices.size();
        distCosto = new double[n][n];
        siguienteCosto = new int[n][n];

        final double INF = Double.MAX_VALUE / 4;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    distCosto[i][j] = 0;
                else if (matrizCosto[i][j] != 0)
                    distCosto[i][j] = matrizCosto[i][j];
                else
                    distCosto[i][j] = INF;

                siguienteCosto[i][j] = (matrizCosto[i][j] != 0) ? j : -1;
            }
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distCosto[i][k] + distCosto[k][j] < distCosto[i][j]) {
                        distCosto[i][j] = distCosto[i][k] + distCosto[k][j];
                        siguienteCosto[i][j] = siguienteCosto[i][k];
                    }
                }
            }
        }

        System.out.println("✅ Rutas óptimas por costo calculadas con Floyd–Warshall.");
    }

    public List<Biblioteca> obtenerCaminoTiempo(String origen, String destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == -1 || j == -1 || siguienteTiempo[i][j] == -1) return null;

        List<Biblioteca> camino = new ArrayList<>();
        camino.add(vertices.get(i));

        while (i != j) {
            i = siguienteTiempo[i][j];
            camino.add(vertices.get(i));
        }
        return camino;
    }

    public List<Biblioteca> obtenerCaminoCosto(String origen, String destino) {
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == -1 || j == -1 || siguienteCosto[i][j] == -1) return null;

        List<Biblioteca> camino = new ArrayList<>();
        camino.add(vertices.get(i));

        while (i != j) {
            i = siguienteCosto[i][j];
            camino.add(vertices.get(i));
        }
        return camino;
    }

    public void mostrarCaminoTiempo(String origen, String destino) {
        List<Biblioteca> camino = obtenerCaminoTiempo(origen, destino);
        if (camino == null) {
            System.out.println("❌ No hay ruta entre " + origen + " y " + destino);
            return;
        }
        System.out.println("\n🚚 Camino más rápido entre " + origen + " → " + destino);
        for (Biblioteca b : camino)
            System.out.print(b.getId() + " ");
        System.out.println("\n⏱️ Tiempo total: " + distTiempo[obtenerIndice(origen)][obtenerIndice(destino)]);
    }

    public void mostrarCaminoCosto(String origen, String destino) {
        List<Biblioteca> camino = obtenerCaminoCosto(origen, destino);
        if (camino == null) {
            System.out.println("❌ No hay ruta entre " + origen + " y " + destino);
            return;
        }
        System.out.println("\n💰 Camino más barato entre " + origen + " → " + destino);
        for (Biblioteca b : camino)
            System.out.print(b.getId() + " ");
        System.out.println("\nCosto total: " + distCosto[obtenerIndice(origen)][obtenerIndice(destino)]);
    }

    public List<Biblioteca> getVertices() {
        return vertices;
    }
    
    public List<String> obtenerConexiones() {
        List<String> conexiones = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) { 
                if (matrizTiempo[i][j] != 0 || matrizCosto[i][j] != 0) {
                    String conexion = vertices.get(i).getId() + " a " +
                                      vertices.get(j).getId();
                    System.out.println(conexion +" | Tiempo: " + matrizTiempo[i][j] +
                                      " | Costo: " + matrizCosto[i][j]);
                    conexiones.add(conexion);
                }
            }
        }

        return conexiones;
    }
    
    public void generarGraphviz(String nombreArchivoDot, String nombreArchivoImagen) {
        if (vertices.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "⚠️ El grafo está vacío: no se generará archivo DOT.",
                "Grafo vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (FileWriter archivo = new FileWriter(nombreArchivoDot)) {
            archivo.write("graph GrafoBibliotecas {\n");
            archivo.write("  node [shape=circle, style=filled, color=lightblue];\n");
            archivo.write("  edge [color=gray50];\n");
            archivo.write("  layout=neato;\n"); // usa disposición natural

            // 🔹 Escribir nodos
            for (Biblioteca b : vertices) {
                archivo.write("  \"" + escapeLabel(b.getId()) + "\" "
                            + "[label=\"" + escapeLabel(b.getNombre()) + "\"];\n");
            }

            // 🔹 Escribir conexiones (bidireccionales)
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = i + 1; j < vertices.size(); j++) {
                    if (matrizTiempo[i][j] != 0 || matrizCosto[i][j] != 0) {
                        String id1 = escapeLabel(vertices.get(i).getId());
                        String id2 = escapeLabel(vertices.get(j).getId());
                        archivo.write("  \"" + id1 + "\" -- \"" + id2 + "\" "
                                    + "[label=\"T:" + matrizTiempo[i][j] + " | C:" + matrizCosto[i][j] + "\"];\n");
                    }
                }
            }

            archivo.write("}\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                "❌ Error al generar archivo DOT: " + e.getMessage(),
                "Error Graphviz", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ejecutar Graphviz
        try {
            String comando = "dot -Tsvg \"" + nombreArchivoDot + "\" -o \"" + nombreArchivoImagen + "\"";
            Process proceso = Runtime.getRuntime().exec(comando);
            proceso.waitFor();

            if (proceso.exitValue() == 0) {
                JOptionPane.showMessageDialog(null,
                    "✅ Imagen del grafo generada correctamente: " + nombreArchivoImagen,
                    "Graphviz", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                    "⚠️ Error al ejecutar Graphviz. Verifica que 'dot' esté instalado y en el PATH.",
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