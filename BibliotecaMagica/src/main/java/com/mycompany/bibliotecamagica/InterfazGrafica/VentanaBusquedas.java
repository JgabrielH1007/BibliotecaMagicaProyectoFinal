/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bibliotecamagica.InterfazGrafica;

import com.mycompany.bibliotecamagica.Backend.Control;
import com.mycompany.bibliotecamagica.Backend.Entidades.Biblioteca;
import com.mycompany.bibliotecamagica.Backend.Entidades.Libro;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Arboles.ArbolAVL.NodoAVL;
import com.mycompany.bibliotecamagica.Backend.Estructuras.Lista.ListaEnlazada;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gabrielh
 */
public class VentanaBusquedas extends javax.swing.JInternalFrame {
    private Biblioteca biblio;
    private Control control;
    private long tiempoSec = -1;
    private long tiempoAVL = -1;
    private long tiempoHash = -1;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaBusquedas.class.getName());
    
    public VentanaBusquedas(Control control, Biblioteca biblio) {
        this.control = control;
        this.biblio = biblio;
        initComponents();setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        grpBotones.add(btnIsbn);
        grpBotones.add(btnTitulo);
        grpBotones.add(btnGenero);
        grpBotones.add(btnAnios);
        desactivarCampos();
        activarCampos();
        
    }
    
    private void desactivarCampos(){
        tfIsbn.enable(false);
        tfTitulo.enable(false);
        tfGenero.enable(false);
        tfAnioFinal.enable(false);
        tfAnioInicial.enable(false);
    }
    
    private void activarCampos(){
        btnIsbn.addActionListener(e -> {
            desactivarCampos();
            tfIsbn.enable(true);
        });
        
        btnTitulo.addActionListener(e -> {
            desactivarCampos();
            tfTitulo.enable(true);
        });
        
        btnGenero.addActionListener(e -> {
            desactivarCampos();
            tfGenero.enable(true);
        });
        
        btnAnios.addActionListener(e -> {
            desactivarCampos();
            tfAnioFinal.enable(true);
            tfAnioInicial.enable(true);
        });
    }
    
    private Libro realizarBusquedaPorTitulo() {
        String titulo = tfTitulo.getText().trim();

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese un título para buscar.",
                                          "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        long inicioSec = System.nanoTime();
        Libro resultadoSec = biblio.getCatalogo().buscarPorTitulo(titulo);
        long finSec = System.nanoTime();
        tiempoSec = finSec - inicioSec;

        long inicioAVL = System.nanoTime();
        Libro resultadoAVL = null;
        NodoAVL nodo = biblio.getArbolTitulo().buscar(titulo);
        if (nodo != null) resultadoAVL = nodo.getLibro();
        long finAVL = System.nanoTime();
        tiempoAVL = finAVL - inicioAVL;

        if (resultadoAVL == null && resultadoSec == null) {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el libro con título: " + titulo,
                                          "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return (resultadoAVL != null) ? resultadoAVL : resultadoSec;
    }

    
    private Libro realizarBusquedaPorIsbn() {
        String isbn = tfIsbn.getText().trim();

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese un ISBN para buscar.",
                                          "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String isbnSoloDigitos = isbn.replaceAll("-", "");
        if (isbnSoloDigitos.length() != 13 || !isbnSoloDigitos.matches("\\d{13}")) {
            JOptionPane.showMessageDialog(this, "⚠️ ISBN inválido (debe tener 13 dígitos): " + isbn,
                                          "Error ISBN", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        long isbnNum;
        try {
            isbnNum = Long.parseLong(isbnSoloDigitos);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error al convertir ISBN a número.",
                                          "Error de formato", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        long inicioHash = System.nanoTime();
        Libro resultadoHash = biblio.getTabla().buscar(isbnNum);
        long finHash = System.nanoTime();
        tiempoHash = finHash - inicioHash;

        if (resultadoHash == null) {
            JOptionPane.showMessageDialog(this, "❌ No se encontró el libro con ISBN: " + isbn,
                                          "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        return resultadoHash;
    }

    
    private List<Libro> realizarBusquedaPorGenero() {
        String genero = tfGenero.getText().trim();
        List<Libro> resultados = new ArrayList<>();

        if (genero.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Ingrese un género para buscar.", 
                                          "Campo vacío", JOptionPane.WARNING_MESSAGE);
            return resultados;
        }

        biblio.getArbolGenero().buscarPorGenero(genero,resultados);

        return resultados;
    }
    
    private List<Libro> realizarBusquedaPorRango(){
        int anioInicial;
        int anioFinal;
        List<Libro> resultados = new ArrayList<>();
        try {
            anioInicial = Integer.parseInt(tfAnioInicial.getText().trim());
            anioFinal = Integer.parseInt(tfAnioFinal.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Ingrese un año inicial válido (solo números).", 
                "Error de formato", JOptionPane.ERROR_MESSAGE);
            return resultados;
        }
        biblio.getArbolAnio().buscarRango(anioInicial, anioFinal, resultados);
        return resultados;
    }
    
    private void llenarTabla(List<Libro> libros) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Título", "ISBN", "Género", "Año", "Biblioteca Actual"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        jTable1.setModel(modelo);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setResizingAllowed(false);  

        if (libros == null || libros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ No se encontraron resultados.",
                                          "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                libro.getTitulo(),
                libro.getIsnb(),
                libro.getGenero(),
                libro.getAnio(),
                libro.getBibliotecaActual()
            });
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpBotones = new javax.swing.ButtonGroup();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfAnioInicial = new javax.swing.JTextField();
        tfGenero = new javax.swing.JTextField();
        tfIsbn = new javax.swing.JTextField();
        tfTitulo = new javax.swing.JTextField();
        tfAnioFinal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnTitulo = new javax.swing.JRadioButton();
        btnIsbn = new javax.swing.JRadioButton();
        btnGenero = new javax.swing.JRadioButton();
        btnAnios = new javax.swing.JRadioButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnComparacion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("URW Bookman", 1, 14)); // NOI18N
        jLabel3.setText("REALIZA UNA BUSQUEDA...");

        jLabel1.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel1.setText("Ingresa el titulo del libro:");

        jLabel2.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel2.setText("Ingresa el isbn del libro:");

        jLabel4.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel4.setText("Ingresa el genero:");

        jLabel5.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel5.setText("a");

        tfAnioInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAnioInicialActionPerformed(evt);
            }
        });

        tfGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfGeneroActionPerformed(evt);
            }
        });

        tfIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIsbnActionPerformed(evt);
            }
        });

        tfAnioFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAnioFinalActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("URW Bookman", 0, 14)); // NOI18N
        jLabel6.setText("Ingresa rango de años;");

        btnTitulo.setText("Por titulo.");
        btnTitulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTituloActionPerformed(evt);
            }
        });

        btnIsbn.setText("Por ISBN");
        btnIsbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIsbnActionPerformed(evt);
            }
        });

        btnGenero.setText("Por genero");
        btnGenero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneroActionPerformed(evt);
            }
        });

        btnAnios.setText("rango de años");
        btnAnios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAniosActionPerformed(evt);
            }
        });

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Titulo", "ISBN", "Genero", "Año", "Estado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        btnComparacion.setText("VER COMPARACION DE BUSQUEDAS");
        btnComparacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComparacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(btnTitulo)
                        .addGap(18, 18, 18)
                        .addComponent(btnIsbn)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenero)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnios)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnComparacion))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(tfIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(tfGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfAnioInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfAnioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(btnBuscar)))
                        .addGap(21, 21, 21))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(btnTitulo)
                    .addComponent(btnIsbn)
                    .addComponent(btnGenero)
                    .addComponent(btnAnios))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(tfIsbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(tfAnioInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfAnioFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(btnBuscar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnComparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfAnioInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAnioInicialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAnioInicialActionPerformed

    private void tfIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIsbnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfIsbnActionPerformed

    private void tfGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfGeneroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfGeneroActionPerformed

    private void tfAnioFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAnioFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAnioFinalActionPerformed

    private void btnIsbnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIsbnActionPerformed
    }//GEN-LAST:event_btnIsbnActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
      if (btnTitulo.isSelected()) {
            Libro resultado = realizarBusquedaPorTitulo();
            if (resultado != null) {
                llenarTabla(List.of(resultado));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ No se encontró ningún libro con ese título.", 
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        else if (btnIsbn.isSelected()) {
            Libro resultado = realizarBusquedaPorIsbn();
            if (resultado != null) {
                llenarTabla(List.of(resultado));
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ No se encontró ningún libro con ese ISBN.", 
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } 
        else if (btnGenero.isSelected()) {
            List<Libro> resultados = realizarBusquedaPorGenero();
            llenarTabla(resultados);
        } 
        else if (btnAnios.isSelected()) {
            List<Libro> resultados = realizarBusquedaPorRango();
            llenarTabla(resultados);
        } 
        else {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Seleccione un tipo de búsqueda antes de continuar.", 
                "Sin selección", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnComparacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComparacionActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(this, 
                "Tiempo busqueda secuencial por titulo : "+tiempoSec+"\n"+
                "Tiempo busqueda binaria por titulo : "+tiempoAVL+"\n"+
                "Tiempo busqueda hash por ISBN : "+tiempoHash+"\n", 
                "COMPARACION DE TIEMPOS: ", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnComparacionActionPerformed

    private void btnTituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTituloActionPerformed
    }//GEN-LAST:event_btnTituloActionPerformed

    private void btnGeneroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneroActionPerformed
    }//GEN-LAST:event_btnGeneroActionPerformed

    private void btnAniosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAniosActionPerformed
    }//GEN-LAST:event_btnAniosActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnAnios;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnComparacion;
    private javax.swing.JRadioButton btnGenero;
    private javax.swing.JRadioButton btnIsbn;
    private javax.swing.JRadioButton btnTitulo;
    private javax.swing.ButtonGroup grpBotones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField tfAnioFinal;
    private javax.swing.JTextField tfAnioInicial;
    private javax.swing.JTextField tfGenero;
    private javax.swing.JTextField tfIsbn;
    private javax.swing.JTextField tfTitulo;
    // End of variables declaration//GEN-END:variables
}
