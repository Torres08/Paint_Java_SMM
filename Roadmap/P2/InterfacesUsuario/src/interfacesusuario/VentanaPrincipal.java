/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package interfacesusuario;

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
        lienzo = new javax.swing.JPanel();
        barraHerramientas = new javax.swing.JToolBar();
        botonPunto = new javax.swing.JToggleButton();
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
        menuBarra = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        Separador1 = new javax.swing.JPopupMenu.Separator();
        menuImprimir = new javax.swing.JMenu();
        menuImpresora = new javax.swing.JMenuItem();
        menuFichero = new javax.swing.JMenuItem();
        menuEditar = new javax.swing.JMenu();
        menuCopiar = new javax.swing.JMenuItem();
        menuCortar = new javax.swing.JMenuItem();
        menuPegar = new javax.swing.JMenuItem();
        menuVer = new javax.swing.JMenu();
        botonBarraEstado = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lienzo.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout lienzoLayout = new javax.swing.GroupLayout(lienzo);
        lienzo.setLayout(lienzoLayout);
        lienzoLayout.setHorizontalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        lienzoLayout.setVerticalGroup(
            lienzoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 412, Short.MAX_VALUE)
        );

        getContentPane().add(lienzo, java.awt.BorderLayout.CENTER);

        barraHerramientas.setRollover(true);

        grupoFormas.add(botonPunto);
        botonPunto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Lapiz.gif"))); // NOI18N
        botonPunto.setFocusable(false);
        botonPunto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPunto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(botonPunto);

        grupoFormas.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Linea.gif"))); // NOI18N
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(botonLinea);

        grupoFormas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Rectangulo.gif"))); // NOI18N
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(botonRectangulo);

        grupoFormas.add(botonOvalo);
        botonOvalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/Ovalo.gif"))); // NOI18N
        botonOvalo.setFocusable(false);
        botonOvalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOvalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        barraHerramientas.add(botonOvalo);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        panelInferior.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panelInferior.setMinimumSize(new java.awt.Dimension(426, 46));
        panelInferior.setLayout(new java.awt.BorderLayout());

        BarraEstado.setText("Barra de estado");
        BarraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInferior.add(BarraEstado, java.awt.BorderLayout.CENTER);

        panelOpcionesInferior.setLayout(new java.awt.GridLayout());

        panelColores.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        botonColor1.setBackground(new java.awt.Color(0, 0, 0));
        grupoColores.add(botonColor1);
        botonColor1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor1.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor1ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor1);

        botonColor2.setBackground(java.awt.Color.green);
        grupoColores.add(botonColor2);
        botonColor2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor2.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor2ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor2);

        botonColor3.setBackground(java.awt.Color.blue);
        grupoColores.add(botonColor3);
        botonColor3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor3.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor3ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor3);

        botonColor4.setBackground(java.awt.Color.white);
        grupoColores.add(botonColor4);
        botonColor4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor4.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor4ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor4);

        botonColor5.setBackground(java.awt.Color.yellow);
        grupoColores.add(botonColor5);
        botonColor5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor5.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor5ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor5);

        botonColor6.setBackground(java.awt.Color.red);
        grupoColores.add(botonColor6);
        botonColor6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonColor6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        botonColor6.setPreferredSize(new java.awt.Dimension(25, 25));
        botonColor6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColor6ActionPerformed(evt);
            }
        });
        panelColores.add(botonColor6);

        panelOpcionesInferior.add(panelColores);

        botonRelleno.setText("Relleno");
        botonRelleno.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        panelOpcionesInferior.add(botonRelleno);

        panelInferior.add(panelOpcionesInferior, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        menuArchivo.setText("Archivo");

        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuNuevoActionPerformed(evt);
            }
        });
        menuArchivo.add(menuNuevo);

        menuAbrir.setText("Abrir");
        menuArchivo.add(menuAbrir);

        menuGuardar.setText("Guardar");
        menuArchivo.add(menuGuardar);
        menuArchivo.add(Separador1);

        menuImprimir.setText("Imprimir");

        menuImpresora.setText("Impresora");
        menuImpresora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuImpresoraActionPerformed(evt);
            }
        });
        menuImprimir.add(menuImpresora);

        menuFichero.setText("Fichero");
        menuImprimir.add(menuFichero);

        menuArchivo.add(menuImprimir);

        menuBarra.add(menuArchivo);

        menuEditar.setText("Editar");

        menuCopiar.setText("Copiar");
        menuEditar.add(menuCopiar);

        menuCortar.setText("Cortar");
        menuEditar.add(menuCortar);

        menuPegar.setText("Pegar");
        menuPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPegarActionPerformed(evt);
            }
        });
        menuEditar.add(menuPegar);

        menuBarra.add(menuEditar);

        menuVer.setText("Ver");

        botonBarraEstado.setSelected(true);
        botonBarraEstado.setText("Barra de estado");
        botonBarraEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBarraEstadoActionPerformed(evt);
            }
        });
        menuVer.add(botonBarraEstado);

        menuBarra.add(menuVer);

        setJMenuBar(menuBarra);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuNuevoActionPerformed

    private void menuImpresoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuImpresoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuImpresoraActionPerformed

    private void menuPegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPegarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuPegarActionPerformed

    private void botonBarraEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBarraEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonBarraEstadoActionPerformed

    private void botonColor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor1ActionPerformed
        //this.lienzo.setColor(this.botonColor1.getBackground());
    }//GEN-LAST:event_botonColor1ActionPerformed

    private void botonColor2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonColor2ActionPerformed

    private void botonColor3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonColor3ActionPerformed

    private void botonColor4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonColor4ActionPerformed

    private void botonColor5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonColor5ActionPerformed

    private void botonColor6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColor6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonColor6ActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BarraEstado;
    private javax.swing.JPopupMenu.Separator Separador1;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JCheckBoxMenuItem botonBarraEstado;
    private javax.swing.JToggleButton botonColor1;
    private javax.swing.JToggleButton botonColor2;
    private javax.swing.JToggleButton botonColor3;
    private javax.swing.JToggleButton botonColor4;
    private javax.swing.JToggleButton botonColor5;
    private javax.swing.JToggleButton botonColor6;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JToggleButton botonOvalo;
    private javax.swing.JToggleButton botonPunto;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JCheckBox botonRelleno;
    private javax.swing.ButtonGroup grupoColores;
    private javax.swing.ButtonGroup grupoFormas;
    private javax.swing.JPanel lienzo;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBarra;
    private javax.swing.JMenuItem menuCopiar;
    private javax.swing.JMenuItem menuCortar;
    private javax.swing.JMenu menuEditar;
    private javax.swing.JMenuItem menuFichero;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenuItem menuImpresora;
    private javax.swing.JMenu menuImprimir;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuPegar;
    private javax.swing.JMenu menuVer;
    private javax.swing.JPanel panelColores;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelOpcionesInferior;
    // End of variables declaration//GEN-END:variables
}
