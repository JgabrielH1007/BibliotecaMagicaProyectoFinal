/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package com.mycompany.bibliotecamagica.InterfazGrafica;

import com.mycompany.bibliotecamagica.Backend.Control;
import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class CreacionDeLibros extends javax.swing.JInternalFrame {
    private Control control;
    /**
     * Creates new form CreacionDeLibros
     */
    public CreacionDeLibros(Control control) {
        this.control = control;
        initComponents();
        llenarComboBox();
    }

    private void llenarComboBox() {
        jcbDestino.removeAllItems(); 
        jcbOrigen.removeAllItems();
        for (Biblioteca b : control.obtenerBibliotecas()) {
            jcbDestino.addItem(b.getId()); 
            jcbOrigen.addItem(b.getId());
        }
    }
    
    public boolean verificarDatos() {
        String titulo = jtfTitulo.getText().trim();
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String autor = jtfAutor.getText().trim();
        if (autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El autor no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        String anioStr = jtfAnio.getText().trim();
        int anio;
        try {
            anio = Integer.parseInt(anioStr);
            if (anio <= 0) {
                JOptionPane.showMessageDialog(this, "El año debe ser un número positivo.", "Error", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El año debe ser un número entero.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String generosStr = jtfGeneros.getText().trim();
        if (generosStr.isEmpty() || !generosStr.contains("/")) {
            JOptionPane.showMessageDialog(this, "Los géneros deben estar separados por '/'.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        List<String> generos = Arrays.asList(generosStr.split("/"));
        if (generos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar al menos un género.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        String isbn = jtfIsbn.getText().trim();
        String isbnSoloDigitos = isbn.replaceAll("-", "");
        if (!isbnSoloDigitos.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(this, "El ISBN debe tener exactamente 13 dígitos.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (jcbOrigen.getSelectedItem() == null || jcbDestino.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar las bibliotecas de origen y destino.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    public void crearLibro() {
        if (verificarDatos()) {
            Libro libro = new Libro();
            libro.setTitulo(jtfTitulo.getText().trim());
            libro.setAutor(jtfAutor.getText().trim());
            libro.setAnio(Integer.parseInt(jtfAnio.getText().trim()));
            libro.setIsnb(jtfIsbn.getText().trim());

            String generosStr = jtfGeneros.getText().trim();
            List<String> generos = Arrays.asList(generosStr.split("/"));
            libro.getGeneros().clear();
            libro.getGeneros().addAll(generos);

            libro.setBibliotecaActual(jcbOrigen.getSelectedItem().toString());
            libro.setBibliotecaDestino(jcbDestino.getSelectedItem().toString());

            libro.setPrioridad("Normal"); 

            control.cargarLibros(libro, control.obtenerBibliotecas()); 

            JOptionPane.showMessageDialog(this, "Libro creado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            jtfTitulo.setText("");
            jtfAutor.setText("");
            jtfAnio.setText("");
            jtfIsbn.setText("");
            jtfGeneros.setText("");
            jcbOrigen.setSelectedIndex(-1);
            jcbDestino.setSelectedIndex(-1);
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbtCargar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcbOrigen = new javax.swing.JComboBox<>();
        jcbDestino = new javax.swing.JComboBox<>();
        jtfAnio = new javax.swing.JTextField();
        jtfAutor = new javax.swing.JTextField();
        jtfGeneros = new javax.swing.JTextField();
        jtfIsbn = new javax.swing.JTextField();
        jtfTitulo = new javax.swing.JTextField();
        jbtGuardar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jbtCargar.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jbtCargar.setText("Cargar Libros");
        jbtCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCargarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel1.setText("Titulo:");

        jLabel2.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel2.setText("ISBN:");

        jlabel3.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jlabel3.setText("Generos:");

        jLabel4.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel4.setText("Autor:");

        jLabel5.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel5.setText("Año:");

        jLabel3.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel3.setText("Biblioteca origen:");

        jLabel6.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel6.setText("Biblioteca Destino");

        jcbOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jcbDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtfAnio.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jtfAutor.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jtfGeneros.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jtfIsbn.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jtfTitulo.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jbtGuardar.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jbtGuardar.setText("GUARDAR");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel7.setText("Crea un libro:");

        jLabel8.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel8.setText("O carga un archivo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(42, 42, 42)
                            .addComponent(jtfAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addGap(31, 31, 31)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jtfIsbn)
                                .addComponent(jtfTitulo))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jcbOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jlabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtfGeneros))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(33, 33, 33)
                                        .addComponent(jtfAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(jbtGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jbtCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlabel3)
                    .addComponent(jtfGeneros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtfIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jtfAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtfAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jcbOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jcbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(19, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbtGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCargarActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo: ");
        javax.swing.filechooser.FileNameExtensionFilter filtro = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filtro);
        int resultado = fileChooser.showOpenDialog(this);
        if(resultado == JFileChooser.APPROVE_OPTION){
            File seleccionado = fileChooser.getSelectedFile();
            String ruta = seleccionado.getAbsolutePath();
            control.leerArchivo(ruta,control.obtenerBibliotecas());
        }else{
            JOptionPane.showMessageDialog(this, 
            "Carga cancelada por el usuario", 
            "Operación cancelada", 
            JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jbtCargarActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        // TODO add your handling code here:
        crearLibro();
    }//GEN-LAST:event_jbtGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton jbtCargar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JComboBox<String> jcbDestino;
    private javax.swing.JComboBox<String> jcbOrigen;
    private javax.swing.JLabel jlabel3;
    private javax.swing.JTextField jtfAnio;
    private javax.swing.JTextField jtfAutor;
    private javax.swing.JTextField jtfGeneros;
    private javax.swing.JTextField jtfIsbn;
    private javax.swing.JTextField jtfTitulo;
    // End of variables declaration//GEN-END:variables
}
