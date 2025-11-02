/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bibliotecamagica.InterfazGrafica;

import com.mycompany.bibliotecamagica.Backend.Control;
import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author gabrielh
 */
public class CreacionDeConexiones extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CreacionDeConexiones.class.getName());
    private Control control;
    private VentanaPrincipal pr;
    /**
     * Creates new form CreacionDeConexiones
     */
    public CreacionDeConexiones(Control control, VentanaPrincipal pr) {
        this.control = control;
        this.pr = pr;
        initComponents();
        llenarConConexiones();
        llenarComboBox();
    }
    
    private void llenarComboBox() {
        jbcBiblioteca2.removeAllItems(); 
        jcbBiblioteca1.removeAllItems(); 
        for (Biblioteca b : control.obtenerBibliotecas()) {
            jbcBiblioteca2.addItem(b.getId());
            jcbBiblioteca1.addItem(b.getId());
        }
    }
    
    private void limpiar(){
        jtfCosto.setText("");
        jtfTiempo.setText("");
    }
    
    
    
    private void agregarConexion() {
        String id1 = (String) jcbBiblioteca1.getSelectedItem();
        String id2 = (String) jbcBiblioteca2.getSelectedItem();
        String tiempoTexto = jtfTiempo.getText().trim();
        String costoTexto = jtfCosto.getText().trim();

        if (tiempoTexto.isEmpty() || costoTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Debes llenar todos los campos.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (id1.equalsIgnoreCase(id2)) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ No puedes conectar una biblioteca consigo misma.",
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tiempo;
        double costo;

        try {
            tiempo = Integer.parseInt(tiempoTexto);
            if (tiempo <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ El tiempo debe ser un número entero positivo.",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            costo = Double.parseDouble(costoTexto);
            if (costo < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ El costo debe ser un número válido (positivo).",
                    "Error de entrada",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (verificarConexion()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ La conexión ya está establecida entre " + id1 + " y " + id2,
                    "Conexión duplicada",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        control.getGr().conectar(id1, id2, tiempo, costo);
        JOptionPane.showMessageDialog(this,
                "✅ Conexión agregada entre " + id1 + " y " + id2,
                "Conexión exitosa",
                JOptionPane.INFORMATION_MESSAGE);

        llenarConConexiones();
        limpiar();
    }
    
    private boolean verificarConexion(){
        String conexion = (String) jcbBiblioteca1.getSelectedItem() + " a " +(String)jbcBiblioteca2.getSelectedItem();
        for (String cone : control.obtenerConexiones()) {
            if(conexion.equals(cone)){
                return true;
            }
        }
        return false;
    }
    
    private void llenarConConexiones(){
        jTextArea1.setEditable(false);

         jTextArea1.setText("");

         // Obtener las conexiones del grafo
         List<String> conexiones = control.obtenerConexiones();

         if (conexiones.isEmpty()) {
             jTextArea1.append("⚠️ No hay conexiones registradas.\n");
             return;
         }

         // Mostrar cada conexión en una línea
         for (String conexion : conexiones) {
             jTextArea1.append(conexion + "\n");
         }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jcbBiblioteca1 = new javax.swing.JComboBox<>();
        jbcBiblioteca2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jbtGuardar = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtfTiempo = new javax.swing.JTextField();
        jtfCosto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jcbBiblioteca1.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jcbBiblioteca1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jbcBiblioteca2.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jbcBiblioteca2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel1.setText("CREACIÓN DE CONEXIONES:");

        jLabel2.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel2.setText("A:");

        jbtGuardar.setFont(new java.awt.Font("URW Bookman", 0, 11)); // NOI18N
        jbtGuardar.setText("GUARDAR");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        btnAtras.setFont(new java.awt.Font("URW Bookman", 0, 11)); // NOI18N
        btnAtras.setText("ATRAS <<---");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtrasActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel3.setText("Ingrese tiempo:");

        jLabel4.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel4.setText("Ingrese costo:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAtras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtGuardar, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcbBiblioteca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbcBiblioteca2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jtfTiempo)
                                            .addComponent(jtfCosto, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))))
                                .addGap(0, 13, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbBiblioteca1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jbcBiblioteca2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jtfTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jtfCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtGuardar)
                        .addGap(34, 34, 34)
                        .addComponent(btnAtras))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        pr.setVisible(true);
    }//GEN-LAST:event_btnAtrasActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        // TODO add your handling code here:
        agregarConexion();
       
    }//GEN-LAST:event_jbtGuardarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> jbcBiblioteca2;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JComboBox<String> jcbBiblioteca1;
    private javax.swing.JTextField jtfCosto;
    private javax.swing.JTextField jtfTiempo;
    // End of variables declaration//GEN-END:variables
}
