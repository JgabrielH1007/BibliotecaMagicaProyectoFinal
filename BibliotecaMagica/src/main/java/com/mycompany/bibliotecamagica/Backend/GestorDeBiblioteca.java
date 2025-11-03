/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend;



import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Cola.Cola;
import com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.Grafo.Grafo;
import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import javax.swing.JOptionPane;

import javax.swing.SwingUtilities;

import javax.swing.SwingUtilities;

public class GestorDeBiblioteca implements Runnable {
    private volatile boolean running = false;
    private final Grafo grafo;
    private final List<Biblioteca> bibliotecas;
    private final ExecutorService executor; 

    public GestorDeBiblioteca(Grafo grafo, List<Biblioteca> bibliotecas) {
        this.grafo = grafo;
        this.bibliotecas = bibliotecas;
        this.executor = Executors.newFixedThreadPool(
                Math.max(1, Runtime.getRuntime().availableProcessors() / 2)
        );
    }

    @Override
    public void run() {
        running = true;
        System.out.println("🚀 Gestor de bibliotecas iniciado...");

        while (running) {
            boolean huboActividad = false;

            for (Biblioteca b : bibliotecas) {
                executor.submit(() -> {
                    boolean ingreso = procesarColaIngreso(b);
                    boolean salida = procesarColaSalida(b);
                    boolean traspaso = procesarColaTraspaso(b);

                    if (ingreso || salida || traspaso) {
                        synchronized (this) {
                            notifyAll(); 
                        }
                    }
                });
            }

            try {
                synchronized (this) {
                    wait(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdownNow();
        System.out.println("🛑 Gestor de bibliotecas detenido.");
    }

    private boolean procesarColaIngreso(Biblioteca b) {
        Cola cola = b.getColaIngreso();
        if (cola == null || cola.estaVacia()) return false;

        Libro libro;
        synchronized (cola) {
            libro = (Libro) cola.desencolar();
        }

        System.out.println("📘 [" + b.getNombre() + "] Procesando ingreso de: " + libro.getTitulo());

        dormirSeguro(b.getTiempoIngreso());

        synchronized (b) {
            b.getCatalogo().agregarLibro(libro);
            b.getArbolTitulo().insertar(libro.getTitulo(), libro);
            b.getArbolAnio().insertar(libro);
            b.getArbolGenero().insertar(libro);
            b.getTabla().insertar(libro);
        }

        System.out.println("✅ [" + b.getNombre() + "] Libro agregado al catálogo: " + libro.getTitulo());
        return true;
    }

    private boolean procesarColaSalida(Biblioteca b) {
        Cola cola = b.getColaSalida();
        if (cola == null || cola.estaVacia()) return false;

        Libro libro;
        synchronized (cola) {
            libro = (Libro) cola.desencolar();
        }

        System.out.println("🚚 [" + b.getNombre() + "] Despachando libro: " + libro.getTitulo());

        dormirSeguro(b.getIntervaloDespacho());

        List<Biblioteca> camino = libro.getCamino();
        if (camino == null || camino.isEmpty()) {
            System.out.println("❌ [" + b.getNombre() + "] No hay camino definido para " + libro.getTitulo());
            return false;
        }

        Biblioteca siguiente = obtenerSiguienteBiblioteca(b, camino);
        if (siguiente == null) return false;

        if (siguiente.getId().equals(libro.getBibliotecaDestino())) {
            synchronized (siguiente) {
                if (siguiente.getColaIngreso() == null) siguiente.setColaIngreso(new Cola());
                siguiente.getColaIngreso().encolar(libro);
            }
            System.out.println("📥 [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() + "' llegó a destino (ingreso).");
        } else {
            synchronized (siguiente) {
                if (siguiente.getColaTraspaso() == null) siguiente.setColaTraspaso(new Cola());
                siguiente.getColaTraspaso().encolar(libro);
            }
            System.out.println("➡️ [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() + "' en tránsito (traspaso).");
        }
        return true;
    }

    private boolean procesarColaTraspaso(Biblioteca b) {
        Cola cola = b.getColaTraspaso();
        if (cola == null || cola.estaVacia()) return false;

        Libro libro;
        synchronized (cola) {
            libro = (Libro) cola.desencolar();
        }

        System.out.println("🔁 [" + b.getNombre() + "] Procesando traspaso de: " + libro.getTitulo());

        dormirSeguro(b.getTiempoTraspaso());

        List<Biblioteca> camino = libro.getCamino();
        if (camino == null) return false;

        Biblioteca siguiente = obtenerSiguienteBiblioteca(b, camino);
        if (siguiente == null) {
            System.out.println("⚠️ [" + b.getNombre() + "] No se encontró siguiente biblioteca para " + libro.getTitulo());
            return false;
        }

        if (siguiente.getId().equals(libro.getBibliotecaDestino())) {
            synchronized (siguiente) {
                if (siguiente.getColaIngreso() == null) siguiente.setColaIngreso(new Cola());
                siguiente.getColaIngreso().encolar(libro);
            }
            System.out.println("📥 [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() + "' llegó a destino final (ingreso).");
        } else {
            synchronized (siguiente) {
                if (siguiente.getColaTraspaso() == null) siguiente.setColaTraspaso(new Cola());
                siguiente.getColaTraspaso().encolar(libro);
            }
            System.out.println("➡️ [" + siguiente.getNombre() + "] Libro '" + libro.getTitulo() + "' sigue en tránsito (traspaso).");
        }
        return true;
    }


    private Biblioteca obtenerSiguienteBiblioteca(Biblioteca actual, List<Biblioteca> camino) {
        for (int i = 0; i < camino.size(); i++) {
            if (camino.get(i).getId().equals(actual.getId()) && i + 1 < camino.size()) {
                return camino.get(i + 1);
            }
        }
        return null;
    }

    private void dormirSeguro(int segundos) {
        try {
            Thread.sleep(Math.max(100, segundos * 1000L));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void detener() {
        running = false;
        executor.shutdownNow();
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(null, "🛑 Gestor de bibliotecas detenido.", "Gestor detenido", JOptionPane.INFORMATION_MESSAGE)
        );
    }

    public boolean isRunning() {
        return running;
    }
}
