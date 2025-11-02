/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend;

import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Cola.Cola;
import com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.Grafo.Grafo;
import java.util.List;

/**
 *
 * @author gabrielh
 */
public class GestorDeBiblioteca implements Runnable {
    private volatile boolean running = false; 
    private final Grafo grafo;
    private final List<Biblioteca> bibliotecas;

    public GestorDeBiblioteca(Grafo grafo, List<Biblioteca> bibliotecas) {
        this.grafo = grafo;
        this.bibliotecas = bibliotecas;
    }

    @Override
    public void run() {
        running = true;
        System.out.println("🚀 Gestor de bibliotecas iniciado...");

        while (running) {
            try {
                for (Biblioteca b : bibliotecas) {
                    procesarColaIngreso(b);
                    procesarColaSalida(b);
                    procesarColaTraspaso(b);
                }
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("⚠️ Gestor interrumpido.");
            }
        }

        System.out.println("🛑 Gestor de bibliotecas detenido.");
    }


    private void procesarColaIngreso(Biblioteca b) {
        Cola cola = b.getColaIngreso();
        if (cola == null || cola.estaVacia()) return;

        Libro libro = (Libro) cola.desencolar();
        System.out.println("📘 [" + b.getNombre() + "] Procesando ingreso de: " + libro.getTitulo());

        try {
            Thread.sleep(b.getTiempoIngreso() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Agregar al catálogo y estructuras
        b.getCatalogo().agregarLibro(libro);
        b.getArbolTitulo().insertar(libro.getTitulo(), libro);
        b.getArbolAnio().insertar(libro);
        b.getArbolGenero().insertar(libro);
        b.getTabla().insertar(libro);

        System.out.println("✅ [" + b.getNombre() + "] Libro agregado al catálogo: " + libro.getTitulo());
    }


    private void procesarColaSalida(Biblioteca b) {
        Cola cola = b.getColaSalida();
        if (cola == null || cola.estaVacia()) return;

        Libro libro = (Libro) cola.desencolar();
        System.out.println("🚚 [" + b.getNombre() + "] Despachando libro: " + libro.getTitulo());

        try {
            Thread.sleep(b.getIntervaloDespacho() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Biblioteca> camino = libro.getCamino();
        if (camino == null || camino.isEmpty()) {
            System.out.println("❌ [" + b.getNombre() + "] No hay camino definido para " + libro.getTitulo());
            return;
        }

        Biblioteca siguiente = obtenerSiguienteBiblioteca(b, camino);
        if (siguiente != null) {
            if (siguiente.getId().equals(libro.getBibliotecaDestino())) {
                if (siguiente.getColaIngreso() == null) siguiente.setColaIngreso(new Cola());
                siguiente.getColaIngreso().encolar(libro);
                System.out.println("📥 [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() +
                                   "' llegó a destino, encolado en ingreso.");
            } else {
                if (siguiente.getColaTraspaso() == null) siguiente.setColaTraspaso(new Cola());
                siguiente.getColaTraspaso().encolar(libro);
                System.out.println("➡️ [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() +
                                   "' en tránsito (cola traspaso).");
            }
        }
    }


    private void procesarColaTraspaso(Biblioteca b) {
        Cola cola = b.getColaTraspaso();
        if (cola == null || cola.estaVacia()) return;

        Libro libro = (Libro) cola.desencolar();
        System.out.println("🔁 [" + b.getNombre() + "] Procesando traspaso de: " + libro.getTitulo());

        try {
            Thread.sleep(b.getTiempoTraspaso() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Biblioteca> camino = libro.getCamino();
        Biblioteca siguiente = obtenerSiguienteBiblioteca(b, camino);

        if (siguiente != null) {
            if (siguiente.getId().equals(libro.getBibliotecaDestino())) {
                if (siguiente.getColaIngreso() == null) siguiente.setColaIngreso(new Cola());
                siguiente.getColaIngreso().encolar(libro);
                System.out.println("📥 [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() +
                                   "' llegó a destino, encolado en ingreso.");
            } else {
                if (siguiente.getColaTraspaso() == null) siguiente.setColaTraspaso(new Cola());
                siguiente.getColaTraspaso().encolar(libro);
                System.out.println("➡️ [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() +
                                   "' sigue en tránsito (cola traspaso).");
            }
        } else {
            System.out.println("⚠️ [" + b.getNombre() + "] No se encontró siguiente biblioteca para " +
                               libro.getTitulo());
        }
    }


    private Biblioteca obtenerSiguienteBiblioteca(Biblioteca actual, List<Biblioteca> camino) {
        for (int i = 0; i < camino.size(); i++) {
            if (camino.get(i).getId().equals(actual.getId()) && i + 1 < camino.size()) {
                return camino.get(i + 1);
            }
        }
        return null;
    }


    public void detener() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }
}
