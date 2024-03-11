/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paintbasico;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author torres
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VentanaPrincipal
     */
    public VentanaPrincipal() {
        initComponents();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoFormas = new javax.swing.ButtonGroup();
        grupoColores = new javax.swing.ButtonGroup();
        paintBasico1 = new paintbasico.PaintBasico();
        barraHerramientas = new javax.swing.JToolBar();
        botonLinea = new javax.swing.JToggleButton();
        botonRectangulo = new javax.swing.JToggleButton();
        botonOvalo = new javax.swing.JToggleButton();
        panelInferior = new javax.swing.JPanel();
        BarraEstado = new javax.swing.JLabel();
        panelOpcionesInferior = new javax.swing.JPanel();
        panelColores = new javax.swing.JPanel();
        botonColor1 = new javax.swing.JToggleButton();
        botonColor2 = new javax.swing.JToggleButton();
        botonColor3 = new javax.swing.JToggleButton();
        botonColor4 = new javax.swing.JToggleButton();
        botonColor5 = new javax.swing.JToggleButton();
        botonColor6 = new javax.swing.JToggleButton();
        botonRelleno = new javax.swing.JCheckBox();
        lienzo = new paintbasico.Lienzo();
        menuBarra = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        Separador1 = new javax.swing.JPopupMenu.Separator();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barraHerramientas.setRollover(true);

        grupoFormas.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Linea.gif"))); // NOI18N
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLinea.addMouseListener(formListener);
        botonLinea.addActionListener(formListener);
        barraHerramientas.add(botonLinea);

        grupoFormas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Rectangulo.gif"))); // NOI18N
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addMouseListener(formListener);
        botonRectangulo.addActionListener(formListener);
        barraHerramientas.add(botonRectangulo);

        grupoFormas.add(botonOvalo);
        botonOvalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Ovalo.gif"))); // NOI18N
        botonOvalo.setFocusable(false);
        botonOvalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOvalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOvalo.addMouseListener(formListener);
        botonOvalo.addActionListener(formListener);
        barraHerramientas.add(botonOvalo);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        panelInferior.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panelInferior.setMinimumSize(new java.awt.Dimension(426, 46));
        panelInferior.setLayout(new java.awt.BorderLayout());

        BarraEstado.setText("Barra de estado");
        BarraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInferior.add(BarraEstado, java.awt.BorderLayout.CENTER);

        panelOpcionesInferior.setLayout(new java.awt.BorderLayout());

        panelColores.setPreferredSize(new java.awt.Dimension(80, 50));
        panelColores.setLayout(new java.awt.GridLayout(2, 3));

        botonColor1.setBackground(new java.awt.Color(0, 0, 0));
        grupoColores.add(botonColor1);
        botonColor1.setSelected(true);
        botonColor1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor1.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor1.addActionListener(formListener);
        panelColores.add(botonColor1);

        botonColor2.setBackground(java.awt.Color.green);
        grupoColores.add(botonColor2);
        botonColor2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor2.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor2.addActionListener(formListener);
        panelColores.add(botonColor2);

        botonColor3.setBackground(java.awt.Color.blue);
        grupoColores.add(botonColor3);
        botonColor3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor3.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor3.addActionListener(formListener);
        panelColores.add(botonColor3);

        botonColor4.setBackground(java.awt.Color.white);
        grupoColores.add(botonColor4);
        botonColor4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor4.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor4.addActionListener(formListener);
        panelColores.add(botonColor4);

        botonColor5.setBackground(java.awt.Color.yellow);
        grupoColores.add(botonColor5);
        botonColor5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor5.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor5.addActionListener(formListener);
        panelColores.add(botonColor5);

        botonColor6.setBackground(java.awt.Color.red);
        grupoColores.add(botonColor6);
        botonColor6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor6.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor6.addActionListener(formListener);
        panelColores.add(botonColor6);

        panelOpcionesInferior.add(panelColores, java.awt.BorderLayout.WEST);

        botonRelleno.setText("Relleno");
        botonRelleno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        botonRelleno.addActionListener(formListener);
        panelOpcionesInferior.add(botonRelleno, java.awt.BorderLayout.EAST);

        panelInferior.add(panelOpcionesInferior, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        lienzoLayout.setVerticalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 397, Short.MAX_VALUE)
        );

        getContentPane().add(lienzo, java.awt.BorderLayout.CENTER);

        menuArchivo.setText("Archivo");

        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(formListener);
        menuArchivo.add(menuNuevo);

        menuAbrir.setText("Abrir");
        menuAbrir.addMouseListener(formListener);
        menuAbrir.addActionListener(formListener);
        menuArchivo.add(menuAbrir);

        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(formListener);
        menuArchivo.add(menuGuardar);
        menuArchivo.add(Separador1);

        menuBarra.add(menuArchivo);

        setJMenuBar(menuBarra);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.MouseListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == botonLinea) {
                VentanaPrincipal.this.botonLineaActionPerformed(evt);
            }
            else if (evt.getSource() == botonRectangulo) {
                VentanaPrincipal.this.botonRectanguloActionPerformed(evt);
            }
            else if (evt.getSource() == botonOvalo) {
                VentanaPrincipal.this.botonOvaloActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor1) {
                VentanaPrincipal.this.botonColor1ActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor2) {
                VentanaPrincipal.this.botonColor2ActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor3) {
                VentanaPrincipal.this.botonColor3ActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor4) {
                VentanaPrincipal.this.botonColor4ActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor5) {
                VentanaPrincipal.this.botonColor5ActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor6) {
                VentanaPrincipal.this.botonColor6ActionPerformed(evt);
            }
            else if (evt.getSource() == menuNuevo) {
                VentanaPrincipal.this.menuNuevoActionPerformed(evt);
            }
            else if (evt.getSource() == menuAbrir) {
                VentanaPrincipal.this.menuAbrirActionPerformed(evt);
            }
            else if (evt.getSource() == botonRelleno) {
                VentanaPrincipal.this.botonRellenoActionPerformed(evt);
            }
            else if (evt.getSource() == menuGuardar) {
                VentanaPrincipal.this.menuGuardarActionPerformed(evt);
            }
        }

        public void mouseClicked(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == botonLinea) {
                VentanaPrincipal.this.botonLineaMouseClicked(evt);
            }
            else if (evt.getSource() == botonRectangulo) {
                VentanaPrincipal.this.botonRectanguloMouseClicked(evt);
            }
            else if (evt.getSource() == botonOvalo) {
                VentanaPrincipal.this.botonOvaloMouseClicked(evt);
            }
            else if (evt.getSource() == menuAbrir) {
                VentanaPrincipal.this.menuAbrirMouseClicked(evt);
            }
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
        }
    }// </editor-fold>//GEN-END:initComponents

    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        // TODO add your handling code here:
        System.out.println("Nuevo");
    }//GEN-LAST:event_menuNuevoActionPerformed

    private void botonColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor1ActionPerformed
        System.out.println("Color Negro");
        this.lienzo.setColor(Color.BLACK);
    }//GEN-LAST:event_botonColor1ActionPerformed

    private void botonColor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor2ActionPerformed
        System.out.println("Color Verde");
        this.lienzo.setColor(Color.GREEN);
    }//GEN-LAST:event_botonColor2ActionPerformed

    private void botonColor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor3ActionPerformed
        System.out.println("Color Azul");
        this.lienzo.setColor(Color.BLUE);
    }//GEN-LAST:event_botonColor3ActionPerformed

    private void botonColor4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor4ActionPerformed
        System.out.println("Color Blanco");
        this.lienzo.setColor(Color.WHITE);
    }//GEN-LAST:event_botonColor4ActionPerformed

    private void botonColor5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor5ActionPerformed
        System.out.println("Color Amarillo");
        this.lienzo.setColor(Color.YELLOW);
    }//GEN-LAST:event_botonColor5ActionPerformed

    private void botonColor6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor6ActionPerformed
        System.out.println("Color Rojo");
        this.lienzo.setColor(Color.RED);
    }//GEN-LAST:event_botonColor6ActionPerformed

    private void botonLineaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonLineaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonLineaMouseClicked

    private void botonRectanguloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonRectanguloMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRectanguloMouseClicked

    private void botonOvaloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonOvaloMouseClicked
        // TODO add your handling code here:        
    }//GEN-LAST:event_botonOvaloMouseClicked

    private void menuAbrirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuAbrirMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuAbrirMouseClicked

    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        // TODO add your handling code here:
        System.out.println("Abrir");
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
                File f = dlg.getSelectedFile();
                //Código
        }
    }//GEN-LAST:event_menuAbrirActionPerformed

    private void botonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaActionPerformed
        // TODO add your handling code here:
        System.out.println("Dibujo lineas");
        this.lienzo.setForma(Forma.LINEA);
    }//GEN-LAST:event_botonLineaActionPerformed

    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
        // TODO add your handling code here:
        System.out.println("Dibujo Rectangulos");
        this.lienzo.setForma(Forma.RECTANGULO);
    }//GEN-LAST:event_botonRectanguloActionPerformed

    private void botonOvaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOvaloActionPerformed
        // TODO add your handling code here:
        System.out.println("Dibujo Elipse");
        this.lienzo.setForma(Forma.ELIPSE);
    }//GEN-LAST:event_botonOvaloActionPerformed

    private void botonRellenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenoActionPerformed
        // TODO add your handling code here:
        this.lienzo.setRelleno(!this.lienzo.getRelleno());
    }//GEN-LAST:event_botonRellenoActionPerformed

    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        // TODO add your handling code here:
        System.out.println("Guardar");
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showSaveDialog(this);
    }//GEN-LAST:event_menuGuardarActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BarraEstado;
    private javax.swing.JPopupMenu.Separator Separador1;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JToggleButton botonColor1;
    private javax.swing.JToggleButton botonColor2;
    private javax.swing.JToggleButton botonColor3;
    private javax.swing.JToggleButton botonColor4;
    private javax.swing.JToggleButton botonColor5;
    private javax.swing.JToggleButton botonColor6;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JToggleButton botonOvalo;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JCheckBox botonRelleno;
    private javax.swing.ButtonGroup grupoColores;
    private javax.swing.ButtonGroup grupoFormas;
    private paintbasico.Lienzo lienzo;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBarra;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuNuevo;
    private paintbasico.PaintBasico paintBasico1;
    private javax.swing.JPanel panelColores;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOpcionesInferior;
    // End of variables declaration//GEN-END:variables
}
