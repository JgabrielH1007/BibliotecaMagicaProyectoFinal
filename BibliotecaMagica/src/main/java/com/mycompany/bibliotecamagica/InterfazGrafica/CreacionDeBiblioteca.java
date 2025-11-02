/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bibliotecamagica.InterfazGrafica;

import com.mycompany.bibliotecamagica.Backend.Control;
import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author gabrielh
 */
public class CreacionDeBiblioteca extends javax.swing.JFrame {
    private List<Biblioteca> lista;
    private Control control;
    private VentanaPrincipal pr;
    private Biblioteca biblioEditada;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CreacionDeBiblioteca.class.getName());


    public CreacionDeBiblioteca(Control control, VentanaPrincipal pr) {
        this.pr = pr;
        this.control = control;
        this.lista = control.obtenerBibliotecas();
        initComponents();
        configurarListenerID();
    }

    private void configurarListenerID() {
        txfID.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                SwingUtilities.invokeLater(() -> llenarCampos());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                SwingUtilities.invokeLater(() -> llenarCampos());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                SwingUtilities.invokeLater(() -> llenarCampos());
            }
        });
    }

    
    public void llenarCampos() {
        String id = txfID.getText().trim();

        for (Biblioteca b : lista) {
            if (b.getId().equals(id)) {
                txfNombre.setText(b.getNombre());
                txfUbi.setText(b.getUbicacion());
                txfTingreso.setText(String.valueOf(b.getTiempoIngreso()));
                txfTtraspaso.setText(String.valueOf(b.getTiempoTraspaso()));
                txfIntervaloDespacho.setText(String.valueOf(b.getIntervaloDespacho()));
                return; 
            }
        }
        limpiarCampos();
    }
    
    private void limpiarCampos(){
        txfNombre.setText("");
        txfUbi.setText("");
        txfTingreso.setText("");
        txfTtraspaso.setText("");
        txfIntervaloDespacho.setText("");
    }
    
    private boolean verificarExistencia(){
        String id = txfID.getText().trim();
        for(Biblioteca b : lista){
            if(b.getId().equals(id)){
                biblioEditada = b;
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txfID = new javax.swing.JTextField();
        txfNombre = new javax.swing.JTextField();
        txfUbi = new javax.swing.JTextField();
        txfTingreso = new javax.swing.JTextField();
        txfTtraspaso = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jbtGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txfIntervaloDespacho = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jbtAtras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txfID.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        txfNombre.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        txfUbi.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        txfUbi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfUbiActionPerformed(evt);
            }
        });

        txfTingreso.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        txfTingreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfTingresoActionPerformed(evt);
            }
        });

        txfTtraspaso.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel1.setText("Ingrese ID:");

        jLabel2.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel2.setText("Ingrese Nombre:");

        jLabel3.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel3.setText("Ingrese Ubicación:");

        jLabel4.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel4.setText("Ingrese tiempo ingreso:");

        jLabel5.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel5.setText("Ingrese tiempo de traspaso:");

        jbtGuardar.setFont(new java.awt.Font("URW Bookman", 1, 12)); // NOI18N
        jbtGuardar.setText("GUARDAR");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("URW Bookman", 1, 14)); // NOI18N
        jLabel6.setText("CREACION O EDICION DE BIBLIOTECAS:");

        txfIntervaloDespacho.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        txfIntervaloDespacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfIntervaloDespachoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("URW Bookman", 0, 12)); // NOI18N
        jLabel7.setText("Ingreses intervalo de despacho:");

        jbtAtras.setFont(new java.awt.Font("URW Bookman", 1, 12)); // NOI18N
        jbtAtras.setText("ATRAS <<--");
        jbtAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfIntervaloDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txfNombre)
                                .addComponent(txfID)
                                .addComponent(txfUbi)
                                .addComponent(txfTingreso)
                                .addComponent(txfTtraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbtGuardar))))
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(364, Short.MAX_VALUE)
                    .addComponent(jbtAtras)
                    .addGap(15, 15, 15)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfUbi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfTingreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfTtraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfIntervaloDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jbtGuardar)
                .addGap(26, 26, 26))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(265, Short.MAX_VALUE)
                    .addComponent(jbtAtras)
                    .addGap(26, 26, 26)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txfUbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfUbiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfUbiActionPerformed

    private void txfTingresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfTingresoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfTingresoActionPerformed

    private void txfIntervaloDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfIntervaloDespachoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfIntervaloDespachoActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        String Id = txfID.getText().trim();
        String nombre = txfNombre.getText().trim();
        String ubicacion = txfUbi.getText().trim();
        String ingresoStr = txfTingreso.getText().trim();
        String traspasoStr = txfTtraspaso.getText().trim();
        String despachoStr = txfIntervaloDespacho.getText().trim();

        if (nombre.isEmpty() || ubicacion.isEmpty() || ingresoStr.isEmpty() || traspasoStr.isEmpty() || despachoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Todos los campos deben estar completos.");
            return;
        }

        int tiempoIngreso, tiempoTraspaso, intervaloDespacho;

        try {
            tiempoIngreso = Integer.parseInt(ingresoStr);
            tiempoTraspaso = Integer.parseInt(traspasoStr);
            intervaloDespacho = Integer.parseInt(despachoStr);

            if (tiempoIngreso <= 0 || tiempoTraspaso <= 0 || intervaloDespacho <= 0) {
                JOptionPane.showMessageDialog(this, "⚠️ Los tiempos deben ser enteros positivos.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Los tiempos deben ser números enteros válidos.");
            return;
        }

        if (verificarExistencia()) {
            biblioEditada.setNombre(nombre);
            biblioEditada.setUbicacion(ubicacion);
            biblioEditada.setTiempoIngreso(tiempoIngreso);
            biblioEditada.setTiempoTraspaso(tiempoTraspaso);
            biblioEditada.setIntervaloDespacho(intervaloDespacho);
            control.getGr().actualizarVertice(biblioEditada);

            JOptionPane.showMessageDialog(this, "✅ Biblioteca actualizada correctamente.");
        } else {
            Biblioteca nuevaBiblioteca = new Biblioteca();
            nuevaBiblioteca.setId(Id);
            nuevaBiblioteca.setNombre(nombre);
            nuevaBiblioteca.setUbicacion(ubicacion);
            nuevaBiblioteca.setTiempoIngreso(tiempoIngreso);
            nuevaBiblioteca.setTiempoTraspaso(tiempoTraspaso);
            nuevaBiblioteca.setIntervaloDespacho(intervaloDespacho);
            control.getGr().actualizarVertice(nuevaBiblioteca);
            JOptionPane.showMessageDialog(this, "Biblioteca creada correctamente.");
        }
        limpiarCampos();
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jbtAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAtrasActionPerformed
        // TODO add your handling code here:
        pr.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtAtrasActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton jbtAtras;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JTextField txfID;
    private javax.swing.JTextField txfIntervaloDespacho;
    private javax.swing.JTextField txfNombre;
    private javax.swing.JTextField txfTingreso;
    private javax.swing.JTextField txfTtraspaso;
    private javax.swing.JTextField txfUbi;
    // End of variables declaration//GEN-END:variables
}
