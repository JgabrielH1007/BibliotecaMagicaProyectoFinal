/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bibliotecamagica.Backend.Entidades;

/**
 *
 * @author gabrielh
 */
public class Libro {
    private String titulo;
    private String isnb;
    private String genero;
    private String autor;
    private int anio;
    private int numCopias;
    private long isnbNum;
    private boolean estado;
    private String bibliotecaActual;
    private String bibliotecaDestino;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsnb() {
        return isnb;
    }

    public void setIsnb(String isnb) {
        this.isnb = isnb;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getNumCopias() {
        return numCopias;
    }

    public void setNumCopias(int numCopias) {
        this.numCopias = numCopias;
    }

    public long getIsnbNum() {
        return isnbNum;
    }

    public void setIsnbNum(long isnbNum) {
        this.isnbNum = isnbNum;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getBibliotecaActual() {
        return bibliotecaActual;
    }

    public void setBibliotecaActual(String bibliotecaActual) {
        this.bibliotecaActual = bibliotecaActual;
    }

    public String getBibliotecaDestino() {
        return bibliotecaDestino;
    }

    public void setBibliotecaDestino(String bibliotecaDestino) {
        this.bibliotecaDestino = bibliotecaDestino;
    }
    
}
