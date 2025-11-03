/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend;

import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.ArbolAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.NodoAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Cola.Cola;
import com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.Grafo.Grafo;
import com.mycompany.bibliotecamagica.Backend.Estructuras.GrafoYHash.TablaHash.TablaHash;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.NodoL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class Control {
    private Grafo gr;
    private GestorDeBiblioteca gs;
    
    public Control(){
        this.gr = new Grafo();
    }
    
    public void leerBibliotecas(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primera = true;
            int lineNumber = 0;

            while ((linea = br.readLine()) != null) {
                lineNumber++;

                if (primera) { 
                    primera = false; 
                    continue; 
                }

                linea = linea.replace("\"", "").trim();
                if (linea.isEmpty()) continue; 

                String[] partes = linea.split(",");
                if (partes.length < 6) {
                    System.out.println("⚠️ Línea " + lineNumber + " ignorada: número incorrecto de columnas.");
                    continue;
                }

                String id = partes[0].trim();
                String nombre = partes[1].trim();
                String ubicacion = partes[2].trim();
                String sIngreso = partes[3].trim();
                String sTraspaso = partes[4].trim();
                String sIntervalo = partes[5].trim();

                if (id.isEmpty() || nombre.isEmpty() || ubicacion.isEmpty()) {
                    System.out.println("⚠️ Línea " + lineNumber + " ignorada: campos de texto vacíos.");
                    continue;
                }

                Integer tIngreso = parseEnteroPositivo(sIngreso);
                Integer tTraspaso = parseEnteroPositivo(sTraspaso);
                Integer intervalo = parseEnteroPositivo(sIntervalo);

                if (tIngreso == null || tTraspaso == null || intervalo == null) {
                    System.out.println("⚠️ Línea " + lineNumber + " ignorada: valores numéricos inválidos.");
                    continue;
                }

                Biblioteca b = new Biblioteca();
                b.setId(id);
                b.setNombre(nombre);
                b.setUbicacion(ubicacion);
                b.setTiempoIngreso(tIngreso);
                b.setTiempoTraspaso(tTraspaso);
                b.setIntervaloDespacho(intervalo);

                gr.agregarVertice(b);
            }

            JOptionPane.showMessageDialog(null,
                    "✅ Lectura realiza",
                    "Lectura completada", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            System.out.println("Error al leer bibliotecas: " + e.getMessage());
        }
    }

    private Integer parseEnteroPositivo(String texto) {
        try {
            int valor = Integer.parseInt(texto);
            return (valor >= 0) ? valor : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    
    public void leerConexiones(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            boolean primera = true;

            while ((linea = br.readLine()) != null) {
                if (primera) { primera = false; continue; }

                linea = linea.replace("\"", "").trim();
                if (linea.isEmpty()) continue;

                String[] partes = linea.split(",");
                if (partes.length < 4) continue;

                String origen = partes[0].trim();
                String destino = partes[1].trim();
                int tiempo = Integer.parseInt(partes[2].trim());
                double costo = Double.parseDouble(partes[3].trim());

                gr.conectar(origen, destino, tiempo, costo);
            }

            JOptionPane.showMessageDialog(null,
                    "✅ Lectura realiza",
                    "Lectura completada", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            System.out.println("❌ Error al leer conexiones: " + e.getMessage());
        }
    }

    public List<Biblioteca> obtenerBibliotecas(){
        return gr.getVertices();
    }
    
    public List<String> obtenerConexiones(){
        return gr.obtenerConexiones();
    }
    
     public void leerArchivo(String ruta, List<Biblioteca> bibliotecas) {
        File archivo = new File(ruta);

        if (!archivo.exists() || !archivo.getName().toLowerCase().endsWith(".csv")) {
            JOptionPane.showMessageDialog(null,
                    "⚠️ Archivo no válido. Debe tener extensión .csv",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int cargados = 0;
        int ignorados = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encabezado = true;

            while ((linea = br.readLine()) != null) {
                if (encabezado) { // Saltar encabezado
                    encabezado = false;
                    continue;
                }

                Libro libro = crearLibro(linea);

                if (libro != null) {
                    cargarLibros(libro, bibliotecas);
                    cargados++;
                } else {
                    ignorados++;
                }
            }

            JOptionPane.showMessageDialog(null,
                    "✅ Archivo cargado exitosamente\n" +
                    "Libros cargados: " + cargados + "\n" +
                    "Líneas ignoradas: " + ignorados,
                    "Carga completada", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "❌ Error al leer el archivo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Libro crearLibro(String linea) {
        if (linea == null || linea.trim().isEmpty()) return null;

        linea = linea.replace("\"", "").trim();
        String[] partes = linea.split(",");

        if (partes.length < 9) {
            System.out.println("Linea ignorada: "+linea);
            return null;
        }

        try {
            String titulo = partes[0].trim();
            String isbn = partes[1].trim();
            String generos = partes[2].trim();
            int anio = Integer.parseInt(partes[3].trim());
            String autor = partes[4].trim();
            String bibOrigen = partes[6].trim();
            String bibDestino = partes[7].trim();
            String prioridad = partes[8].trim();

            if (titulo.isEmpty() || isbn.isEmpty() || autor.isEmpty() || generos.isEmpty()) {
                //JOptionPane.showMessageDialog(null, "⚠️ Datos incompletos o vacíos: " + linea,
                 //                             "Advertencia", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            String isbnSoloDigitos = isbn.replaceAll("-", "");
            if (isbnSoloDigitos.length() != 13 || !isbnSoloDigitos.matches("\\d{13}")) {
                JOptionPane.showMessageDialog(null, "⚠️ ISBN inválido (debe tener 13 dígitos): " + isbn,
                                              "Error ISBN", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            long isbnNum;
            try {
                isbnNum = Long.parseLong(isbnSoloDigitos);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "⚠️ Error al convertir ISBN a número: " + isbn,
                                              "Error ISBN", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setIsnb(isbn);
            libro.setIsnbNum(isbnNum);
            libro.setGenero(generos);
            libro.setAnio(anio);
            libro.setAutor(autor);
            libro.setBibliotecaActual(bibOrigen);
            libro.setBibliotecaDestino(bibDestino);
            libro.setPrioridad(prioridad);

            return libro;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error en formato numérico (año): " + linea,
                                          "Error de formato", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "⚠️ Error al procesar línea: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void cargarLibros(Libro libro, List<Biblioteca> bibliotecas) {
        if (libro == null) return;

        Biblioteca bActual = null;
        for (Biblioteca b : bibliotecas) {
            if (b.getId().equals(libro.getBibliotecaActual())) {
                bActual = b;
                break;
            }
        }

        if (bActual == null) {
            JOptionPane.showMessageDialog(null, 
                "❌ No se encontró la biblioteca actual para el libro: " + libro.getTitulo(),
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NodoAVL nodoExistente = bActual.getArbolTitulo().buscar(libro.getTitulo());
        if (nodoExistente != null && nodoExistente.getLibro() != null) {
            Libro existente = nodoExistente.getLibro();
            existente.setNumCopias(existente.getNumCopias() + 1);
            JOptionPane.showMessageDialog(null, 
                "📚 Se incrementó copia de " + existente.getTitulo() + " en " + bActual.getNombre(),
                "Actualización", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (libro.getPrioridad().equalsIgnoreCase("Costo")) {
            libro.setCamino(gr.obtenerCaminoCosto(libro.getBibliotecaActual(), libro.getBibliotecaDestino()));
        } else if (libro.getPrioridad().equalsIgnoreCase("Tiempo")) {
            libro.setCamino(gr.obtenerCaminoTiempo(libro.getBibliotecaActual(), libro.getBibliotecaDestino()));
        }

        if (bActual.getId().equals(libro.getBibliotecaDestino())) {
            if (bActual.getColaIngreso() == null) bActual.setColaIngreso(new Cola());
            bActual.getColaIngreso().encolar(libro);

        } else {
            if (bActual.getColaSalida() == null) bActual.setColaSalida(new Cola());
            bActual.getColaSalida().encolar(libro);

        }

        if (gs == null || !gs.isRunning()) {
            gs = new GestorDeBiblioteca(gr, gr.getVertices());
            new Thread(gs).start();
        }
    }




    public Grafo getGr() {
        return gr;
    }
    
    public List<Libro> listarIntercambio(ArbolAVL estructura) {
        List<Libro> lista = new ArrayList<>();

        if (estructura == null || estructura.getRaiz() == null) {
            return lista; 
        }

        llenarListaInOrden(estructura.getRaiz(), lista);

        for (int i = 0; i < lista.size() - 1; i++) {
            for (int j = 0; j < lista.size() - i - 1; j++) {
                Libro libro1 = lista.get(j);
                Libro libro2 = lista.get(j + 1);
                if (libro1.getTitulo().compareToIgnoreCase(libro2.getTitulo()) > 0) {
                    // Intercambiar
                    lista.set(j, libro2);
                    lista.set(j + 1, libro1);
                }
            }
        }

        return lista;
    }

    private void llenarListaInOrden(NodoAVL nodo, List<Libro> lista) {
        if (nodo == null) return;

        llenarListaInOrden(nodo.getIzq(), lista);  
        lista.add(nodo.getLibro());               
        llenarListaInOrden(nodo.getDer(), lista); 
    }
    
    public List<Libro> listarPorSelec(TablaHash tabla) {
        List<Libro> lista = tabla.getAllLibros(); 
        int n = lista.size();

        for (int i = 0; i < n - 1; i++) {
            int indiceMin = i;

            for (int j = i + 1; j < n; j++) {
                if (lista.get(j).getIsnbNum()< lista.get(indiceMin).getIsnbNum()) {
                    indiceMin = j;
                }
            }

            if (indiceMin != i) {
                Libro temp = lista.get(i);
                lista.set(i, lista.get(indiceMin));
                lista.set(indiceMin, temp);
            }
        }

        return lista;
    }
    
    public List<Libro> listarPorInser(ListaEnlazada lista) {
        List<Libro> datos = copiarLista(lista); 

        for (int i = 1; i < datos.size(); i++) {
            Libro key = datos.get(i);
            int j = i - 1;
            while (j >= 0 && datos.get(j).getAutor().compareToIgnoreCase(key.getAutor()) > 0) {
                datos.set(j + 1, datos.get(j));
                j--;
            }
            datos.set(j + 1, key);
        }

        return datos;
    }
    
    public List<Libro> listarPorAnio(ListaEnlazada lista) {
        List<Libro> datos = copiarLista(lista);

        int n = datos.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Libro temp = datos.get(i);
                int j = i;
                while (j >= gap && datos.get(j - gap).getAnio() > temp.getAnio()) {
                    datos.set(j, datos.get(j - gap));
                    j -= gap;
                }
                datos.set(j, temp);
            }
        }

        return datos;
    }



    private List<Libro> copiarLista(ListaEnlazada lista) {
        List<Libro> copia = new ArrayList<>();
        NodoL actual = lista.getInicio();
        while (actual != null) {
            copia.add(actual.getLibro());
            actual = actual.getSiguiente();
        }
        return copia;
    }

    public List<Libro> listarPorQuick(ListaEnlazada lista) {
        List<Libro> datos = copiarLista(lista);
        quickSortTitulo(datos, 0, datos.size() - 1);
        return datos;
    }

    private void quickSortTitulo(List<Libro> arr, int low, int high) {
        if (low < high) {
            int p = particion(arr, low, high);
            quickSortTitulo(arr, low, p - 1);
            quickSortTitulo(arr, p + 1, high);
        }
    }

    private int particion(List<Libro> arr, int low, int high) {
        String pivote = arr.get(high).getTitulo();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j).getTitulo().compareToIgnoreCase(pivote) <= 0) {
                i++;
                Libro tmp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, tmp);
            }
        }
        Libro tmp = arr.get(i + 1);
        arr.set(i + 1, arr.get(high));
        arr.set(high, tmp);
        return i + 1;
    }

    
}
