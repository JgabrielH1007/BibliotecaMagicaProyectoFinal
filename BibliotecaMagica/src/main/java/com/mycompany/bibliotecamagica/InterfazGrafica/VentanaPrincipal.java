/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bibliotecamagica.InterfazGrafica;

import com.mycompany.bibliotecamagica.Backend.Control;
import java.awt.Dimension;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());
    private Control control;
    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        control = new Control();
        initComponents();
        actualizarBotones();

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cargaConexiones = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cargaLibrerias = new javax.swing.JButton();
        establecerConexiones = new javax.swing.JButton();
        establecerBibliotecasManual = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cargaConexiones.setText("CARGAR CONEXIONES");
        cargaConexiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargaConexionesActionPerformed(evt);
            }
        });

        jButton2.setText("IR A BIBLIOTECAS...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("URW Bookman", 3, 18)); // NOI18N
        jLabel2.setText("BIENVENIDO A LA");

        jLabel4.setFont(new java.awt.Font("URW Bookman", 3, 18)); // NOI18N
        jLabel4.setText("BILBLIOTECA TRAVES DEL MUNDO");

        cargaLibrerias.setText("CARGAR BIBLIOTECAS");
        cargaLibrerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargaLibreriasActionPerformed(evt);
            }
        });

        establecerConexiones.setText("ESTABLECER CONEXIONES");
        establecerConexiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                establecerConexionesActionPerformed(evt);
            }
        });

        establecerBibliotecasManual.setText("AGREGAR BIBLIOTECAS MANUAL");
        establecerBibliotecasManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                establecerBibliotecasManualActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(183, 183, 183)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(establecerBibliotecasManual, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(cargaLibrerias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(establecerConexiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cargaConexiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(establecerBibliotecasManual, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cargaLibrerias, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(establecerConexiones, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cargaConexiones, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargaConexionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargaConexionesActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo: ");
        javax.swing.filechooser.FileNameExtensionFilter filtro = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filtro);
        int resultado = fileChooser.showOpenDialog(this);
        if(resultado == JFileChooser.APPROVE_OPTION){
            File seleccionado = fileChooser.getSelectedFile();
            String ruta = seleccionado.getAbsolutePath();
            control.leerConexiones(ruta);
        }else{
            JOptionPane.showMessageDialog(this, 
            "Carga cancelada por el usuario", 
            "Operación cancelada", 
            JOptionPane.WARNING_MESSAGE);
        }
        actualizarBotones();

    }//GEN-LAST:event_cargaConexionesActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        VentanaLibreria librerias = new VentanaLibreria(control, this);
        // Hacer visible el JInternalFrame
        librerias.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cargaLibreriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargaLibreriasActionPerformed
        // TODO add your handling code here:
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo: ");
        javax.swing.filechooser.FileNameExtensionFilter filtro = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv");
        fileChooser.setFileFilter(filtro);
        int resultado = fileChooser.showOpenDialog(this);
        if(resultado == JFileChooser.APPROVE_OPTION){
            File seleccionado = fileChooser.getSelectedFile();
            String ruta = seleccionado.getAbsolutePath();
            control.leerBibliotecas(ruta);
        }else{
            JOptionPane.showMessageDialog(this, 
            "Carga cancelada por el usuario", 
            "Operación cancelada", 
            JOptionPane.WARNING_MESSAGE);
        }
        actualizarBotones();
    }//GEN-LAST:event_cargaLibreriasActionPerformed

    private void establecerConexionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_establecerConexionesActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        CreacionDeConexiones crCone = new CreacionDeConexiones(control,this);
        crCone.setVisible(true);
        actualizarBotones();

    }//GEN-LAST:event_establecerConexionesActionPerformed

    private void establecerBibliotecasManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_establecerBibliotecasManualActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        CreacionDeBiblioteca crBiblio = new CreacionDeBiblioteca(control,this);
        crBiblio.setVisible(true);
        actualizarBotones();
    }//GEN-LAST:event_establecerBibliotecasManualActionPerformed
    
    private void actualizarBotones() {
        boolean hayBibliotecas = control.obtenerBibliotecas() != null && !control.obtenerBibliotecas().isEmpty();
        boolean hayConexiones = control.getGr() != null && control.getGr().obtenerConexiones() != null && 
                                !control.getGr().obtenerConexiones().isEmpty();

        cargaConexiones.setEnabled(hayBibliotecas);
        establecerConexiones.setEnabled(hayBibliotecas);
        jButton2.setEnabled(hayConexiones);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cargaConexiones;
    private javax.swing.JButton cargaLibrerias;
    private javax.swing.JButton establecerBibliotecasManual;
    private javax.swing.JButton establecerConexiones;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
