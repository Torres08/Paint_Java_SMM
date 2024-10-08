/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paintbasico;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.event.InternalFrameAdapter;
import sm.jltr.graficos.Forma;
import static sm.jltr.graficos.Forma.ELIPSE;
import static sm.jltr.graficos.Forma.FANTASMA;
import static sm.jltr.graficos.Forma.LINEA;
import static sm.jltr.graficos.Forma.RECTANGULO;
import sm.jltr.graficos.MiForma;
import sm.jltr.iu.Lienzo;

/**
 * La clase VentanaPrincipal representa la ventana principal de la aplicación.
 * Extiende la clase javax.swing.JFrame para proporcionar una ventana de interfaz gráfica de usuario.
 * @author torres
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
    * Constructor de la clase VentanaPrincipal.
    * Inicializa y configura los componentes de la ventana.
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
        jProgressBar1 = new javax.swing.JProgressBar();
        barraHerramientas = new javax.swing.JToolBar();
        botonNuevaImagen = new javax.swing.JButton();
        botonAbrirImagen = new javax.swing.JButton();
        botonGuardarImagen = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        botonLinea = new javax.swing.JToggleButton();
        botonRectangulo = new javax.swing.JToggleButton();
        botonOvalo = new javax.swing.JToggleButton();
        botonFantasma = new javax.swing.JToggleButton();
        botonMover = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        panelColor = new javax.swing.JPanel();
        botonColor = new javax.swing.JToggleButton();
        botonRellenar = new javax.swing.JToggleButton();
        botonTransparencia = new javax.swing.JToggleButton();
        botonAlisar = new javax.swing.JToggleButton();
        botonGrosor = new javax.swing.JSlider();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        botonVolcarImagen = new javax.swing.JButton();
        panelInferior = new javax.swing.JPanel();
        BarraEstado = new javax.swing.JLabel();
        escritorio = new javax.swing.JDesktopPane();
        menuBarra = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        Separador1 = new javax.swing.JPopupMenu.Separator();
        menuImagen = new javax.swing.JMenu();
        menuVolcado = new javax.swing.JMenuItem();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barraHerramientas.setRollover(true);

        botonNuevaImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        botonNuevaImagen.setFocusable(false);
        botonNuevaImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevaImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevaImagen.addActionListener(formListener);
        barraHerramientas.add(botonNuevaImagen);

        botonAbrirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        botonAbrirImagen.setFocusable(false);
        botonAbrirImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAbrirImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAbrirImagen.addActionListener(formListener);
        barraHerramientas.add(botonAbrirImagen);

        botonGuardarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        botonGuardarImagen.setFocusable(false);
        botonGuardarImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardarImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardarImagen.addActionListener(formListener);
        barraHerramientas.add(botonGuardarImagen);
        barraHerramientas.add(jSeparator2);

        grupoFormas.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        botonLinea.setSelected(true);
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLinea.addActionListener(formListener);
        barraHerramientas.add(botonLinea);

        grupoFormas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addActionListener(formListener);
        barraHerramientas.add(botonRectangulo);

        grupoFormas.add(botonOvalo);
        botonOvalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        botonOvalo.setFocusable(false);
        botonOvalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOvalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOvalo.addActionListener(formListener);
        barraHerramientas.add(botonOvalo);

        grupoFormas.add(botonFantasma);
        botonFantasma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/fantasma.png"))); // NOI18N
        botonFantasma.setFocusable(false);
        botonFantasma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonFantasma.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonFantasma.addActionListener(formListener);
        barraHerramientas.add(botonFantasma);

        grupoFormas.add(botonMover);
        botonMover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/seleccion.png"))); // NOI18N
        botonMover.setFocusable(false);
        botonMover.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonMover.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonMover.addActionListener(formListener);
        barraHerramientas.add(botonMover);
        barraHerramientas.add(jSeparator1);

        panelColor.setMaximumSize(new java.awt.Dimension(30, 30));
        panelColor.setMinimumSize(new java.awt.Dimension(30, 30));
        panelColor.setPreferredSize(new java.awt.Dimension(30, 30));

        botonColor.setBackground(new java.awt.Color(0, 0, 0));
        grupoColores.add(botonColor);
        botonColor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonColor.setMaximumSize(new java.awt.Dimension(30, 30));
        botonColor.setMinimumSize(new java.awt.Dimension(30, 30));
        botonColor.setPreferredSize(new java.awt.Dimension(30, 30));
        botonColor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonColor.addActionListener(formListener);

        javax.swing.GroupLayout panelColorLayout = new javax.swing.GroupLayout(panelColor);
        panelColor.setLayout(panelColorLayout);
        panelColorLayout.setHorizontalGroup(
            panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelColorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(botonColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        panelColorLayout.setVerticalGroup(
            panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelColorLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(botonColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        barraHerramientas.add(panelColor);

        botonRellenar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rellenar.png"))); // NOI18N
        botonRellenar.setFocusable(false);
        botonRellenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRellenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRellenar.addActionListener(formListener);
        barraHerramientas.add(botonRellenar);

        botonTransparencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/transparencia.png"))); // NOI18N
        botonTransparencia.setFocusable(false);
        botonTransparencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTransparencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTransparencia.addActionListener(formListener);
        barraHerramientas.add(botonTransparencia);

        botonAlisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        botonAlisar.setFocusable(false);
        botonAlisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAlisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAlisar.addActionListener(formListener);
        barraHerramientas.add(botonAlisar);

        botonGrosor.setMaximumSize(new java.awt.Dimension(35, 30));
        botonGrosor.setMinimumSize(new java.awt.Dimension(35, 30));
        botonGrosor.setPreferredSize(new java.awt.Dimension(35, 30));
        botonGrosor.addChangeListener(formListener);
        barraHerramientas.add(botonGrosor);
        barraHerramientas.add(jSeparator3);

        botonVolcarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/volcado.png"))); // NOI18N
        botonVolcarImagen.setFocusable(false);
        botonVolcarImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonVolcarImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonVolcarImagen.addActionListener(formListener);
        barraHerramientas.add(botonVolcarImagen);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        panelInferior.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panelInferior.setMinimumSize(new java.awt.Dimension(426, 46));
        panelInferior.setLayout(new java.awt.BorderLayout());

        BarraEstado.setText("Barra de estado");
        BarraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInferior.add(BarraEstado, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 746, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );

        getContentPane().add(escritorio, java.awt.BorderLayout.CENTER);

        menuArchivo.setText("Archivo");

        menuNuevo.setText("Nuevo");
        menuNuevo.addActionListener(formListener);
        menuArchivo.add(menuNuevo);

        menuAbrir.setText("Abrir");
        menuAbrir.addActionListener(formListener);
        menuArchivo.add(menuAbrir);

        menuGuardar.setText("Guardar");
        menuGuardar.addActionListener(formListener);
        menuArchivo.add(menuGuardar);
        menuArchivo.add(Separador1);

        menuBarra.add(menuArchivo);

        menuImagen.setText("Imagen");

        menuVolcado.setText("Volcado");
        menuVolcado.addActionListener(formListener);
        menuImagen.add(menuVolcado);

        menuBarra.add(menuImagen);

        setJMenuBar(menuBarra);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, javax.swing.event.ChangeListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == botonNuevaImagen) {
                VentanaPrincipal.this.botonNuevaImagenActionPerformed(evt);
            }
            else if (evt.getSource() == botonAbrirImagen) {
                VentanaPrincipal.this.botonAbrirImagenActionPerformed(evt);
            }
            else if (evt.getSource() == botonGuardarImagen) {
                VentanaPrincipal.this.botonGuardarImagenActionPerformed(evt);
            }
            else if (evt.getSource() == botonLinea) {
                VentanaPrincipal.this.botonLineaActionPerformed(evt);
            }
            else if (evt.getSource() == botonRectangulo) {
                VentanaPrincipal.this.botonRectanguloActionPerformed(evt);
            }
            else if (evt.getSource() == botonOvalo) {
                VentanaPrincipal.this.botonOvaloActionPerformed(evt);
            }
            else if (evt.getSource() == botonFantasma) {
                VentanaPrincipal.this.botonFantasmaActionPerformed(evt);
            }
            else if (evt.getSource() == botonMover) {
                VentanaPrincipal.this.botonMoverActionPerformed(evt);
            }
            else if (evt.getSource() == botonColor) {
                VentanaPrincipal.this.botonColorActionPerformed(evt);
            }
            else if (evt.getSource() == botonRellenar) {
                VentanaPrincipal.this.botonRellenarActionPerformed(evt);
            }
            else if (evt.getSource() == botonTransparencia) {
                VentanaPrincipal.this.botonTransparenciaActionPerformed(evt);
            }
            else if (evt.getSource() == botonAlisar) {
                VentanaPrincipal.this.botonAlisarActionPerformed(evt);
            }
            else if (evt.getSource() == menuNuevo) {
                VentanaPrincipal.this.menuNuevoActionPerformed(evt);
            }
            else if (evt.getSource() == menuAbrir) {
                VentanaPrincipal.this.menuAbrirActionPerformed(evt);
            }
            else if (evt.getSource() == menuGuardar) {
                VentanaPrincipal.this.menuGuardarActionPerformed(evt);
            }
            else if (evt.getSource() == menuVolcado) {
                VentanaPrincipal.this.menuVolcadoActionPerformed(evt);
            }
            else if (evt.getSource() == botonVolcarImagen) {
                VentanaPrincipal.this.botonVolcarImagenActionPerformed(evt);
            }
        }

        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            if (evt.getSource() == botonGrosor) {
                VentanaPrincipal.this.botonGrosorStateChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    /**
    * Obtiene el lienzo seleccionado en la ventana interna actual del escritorio.
    *
    * @return El objeto Lienzo seleccionado, o null si no hay ninguna ventana interna seleccionada o no tiene un lienzo.
    */
    private Lienzo getSelectedLienzo() {
        VentanaInterna vi;
        vi = (VentanaInterna)escritorio.getSelectedFrame();
        return vi!=null ? vi.getLienzo() : null;
    }
    
    /**
    * Deselecciona todas las figuras en el lienzo seleccionado, si está en modo seleccion.
    */
    private void deseleccionar(){
         Lienzo lienzo = getSelectedLienzo();
        
         System.out.println("Deseleccionar, Estado Lienzo Mover:" + lienzo.isMover());
         for(MiForma s: lienzo.getListaFiguras()) {
            if (lienzo.isMover()){
                if (s != null){
                    //System.out.println("Hola");
                    s.getAtributos().setSeleccionado(false);
                    lienzo.repaint(); // lienzo repaint
                }       
            }     
        }
    }
    
    
    /** 
     * Se realiza un volcado de las imagenes seleccionadas
    */
    private void menuVolcadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVolcadoActionPerformed
        /*
        VentanaInterna vi = new VentanaInterna();
        escritorio.add(vi);
        vi.setVisible(true);
        */
        this.botonVolcarImagenActionPerformed(evt);
    }//GEN-LAST:event_menuVolcadoActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona la opción de menú "Abrir".
    * Abre un diálogo de selección de archivos para elegir una imagen, y muestra la imagen seleccionada en una nueva ventana interna.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        JFileChooser dlg = new JFileChooser();
        int resp = dlg.showOpenDialog(this);
        if( resp == JFileChooser.APPROVE_OPTION) {
                
            try{
                File f = dlg.getSelectedFile();
                BufferedImage img = ImageIO.read(f);
                VentanaInterna vi = new VentanaInterna();
                vi.getLienzo().setImagen(img);
                this.escritorio.add(vi);
                vi.setTitle(f.getName());
                vi.setVisible(true);
            } catch (Exception ex){
                System.err.println("Error al leer la imagen");
            }
           
        }
        System.out.println("Abrir Imagen");

    }//GEN-LAST:event_menuAbrirActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para dibujar líneas.
    * Desselecciona todas las figuras en el lienzo seleccionado y configura la forma de dibujo en el lienzo como una línea.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaActionPerformed

        Lienzo lienzo = getSelectedLienzo();
        deseleccionar();
        
        if (!botonMover.isSelected())
              lienzo.setMover(false);
        
        if(lienzo != null)
            lienzo.setForma(sm.jltr.graficos.Forma.LINEA);
        
        System.out.println("Forma Linea");
    }//GEN-LAST:event_botonLineaActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para dibujar rectángulos.
    * Deselecciona todas las figuras en el lienzo seleccionado y configura la forma de dibujo en el lienzo como un rectángulo.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
       Lienzo lienzo = getSelectedLienzo();
       deseleccionar();

       if (!botonMover.isSelected())
              lienzo.setMover(false);
       
       if(lienzo != null)
          lienzo.setForma(sm.jltr.graficos.Forma.RECTANGULO);
        
       System.out.println("Forma Rectangulo" );
    }//GEN-LAST:event_botonRectanguloActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para dibujar óvalos.
    * Deselecciona todas las figuras en el lienzo seleccionado y configura la forma de dibujo en el lienzo como una elipse.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonOvaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOvaloActionPerformed
       Lienzo lienzo = getSelectedLienzo();
       deseleccionar();

       if (!botonMover.isSelected())
              lienzo.setMover(false);
              
       if(lienzo != null)
        lienzo.setForma(sm.jltr.graficos.Forma.ELIPSE);
       
       System.out.println("Forma Elipse");
    }//GEN-LAST:event_botonOvaloActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona la opción de menú "Guardar".
    * Permite al usuario guardar la imagen mostrada en la ventana interna seleccionada como un archivo JPG.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        
        VentanaInterna vi=(VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getPaintedImage();
            if (img != null) {
                JFileChooser dlg = new JFileChooser();
                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = dlg.getSelectedFile();
                        ImageIO.write(img, "jpg", f);
                        vi.setTitle(f.getName());
                    } catch (Exception ex) {
                        System.err.println("Error al guardar la imagen");
                    }
                }
            }
        }
        System.out.println("Guardar Imagen");
    }//GEN-LAST:event_menuGuardarActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para dibujar formas fantasma.
    * Deselecciona todas las figuras en el lienzo seleccionado y configura la forma de dibujo en el lienzo como una forma fantasma.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonFantasmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFantasmaActionPerformed
        Lienzo lienzo = getSelectedLienzo();
        deseleccionar();

        if (!botonMover.isSelected())
            lienzo.setMover(false);
            
         
        if(lienzo != null)
            lienzo.setForma(sm.jltr.graficos.Forma.FANTASMA);
        
       System.out.println("Forma Fantasma");

    }//GEN-LAST:event_botonFantasmaActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para activar o desactivar el modo de mover figuras.
    * Cambia el estado del modo mover en el lienzo seleccionado y lo refleja en el botón de selección.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMoverActionPerformed
     
      Lienzo lienzo = getSelectedLienzo();
      if(lienzo != null){ 
                  
           lienzo.setMover(!lienzo.isMover());
           if (botonMover.isSelected()){
              lienzo.setMover(true); 
           } 
          
      }
      System.out.println("Mover");     
    }//GEN-LAST:event_botonMoverActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para elegir un color.
    * Abre un selector de color para que el usuario elija un color, y lo aplica a la forma seleccionada en el lienzo.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColorActionPerformed
         Color color = JColorChooser.showDialog(this, "Elije un color", Color.RED);
  
         Lienzo lienzo = getSelectedLienzo();
         if(lienzo != null){
            lienzo.getAtributosActual().setColor(color);
        
            for (MiForma s: lienzo.getListaFiguras()){
                if (s != null && s.getAtributos().isSeleccionado()){
                    // actualizar las variables 
                    s.getAtributos().setColor(color);
                    this.repaint();
                }   
            }
            
         }
            
         System.out.println("Color " + color);
         botonColor.setBackground(color);
        
    }//GEN-LAST:event_botonColorActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para activar o desactivar el relleno de las figuras.
    * Cambia el estado del relleno en la forma seleccionada en el lienzo y lo refleja en todas las figuras seleccionadas.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonRellenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenarActionPerformed
       
       Lienzo lienzo = getSelectedLienzo();
       
       if(lienzo != null){ 
            boolean RellenarActual = lienzo.getAtributosActual().getRelleno();
            lienzo.getAtributosActual().setRelleno(!RellenarActual);
            //System.out.println("Rellenar: " + lienzo.getAtributosActual().getRelleno() );
            
                for (MiForma s: lienzo.getListaFiguras()){
                     if (s != null && s.getAtributos().isSeleccionado()){
                         // actualizar las variables 
                         s.getAtributos().setRelleno(lienzo.getAtributosActual().getRelleno());
                         this.repaint();
                    }   
                }
       }   
       System.out.println("Rellenar ");
    }//GEN-LAST:event_botonRellenarActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para activar o desactivar la transparencia de las figuras.
    * Cambia el estado de la transparencia en la forma seleccionada en el lienzo y lo refleja en todas las figuras seleccionadas.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonTransparenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTransparenciaActionPerformed
       
       Lienzo lienzo = getSelectedLienzo();
       
       if(lienzo != null){
           
           lienzo.getAtributosActual().setTransparencia(!lienzo.getAtributosActual().isTransparencia());
           System.out.println("Transparencia: " + lienzo.getAtributosActual().isTransparencia() );
       
           for (MiForma s: lienzo.getListaFiguras()){
               if (s != null && s.getAtributos().isSeleccionado()){
                         // actualizar las variables 
                         s.getAtributos().setTransparencia(lienzo.getAtributosActual().isTransparencia());
                         this.repaint();
               } 
           }  
           
       }
        System.out.println("Transparencia");

    }//GEN-LAST:event_botonTransparenciaActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para activar o desactivar el alisado de las líneas.
    * Cambia el estado del alisado en la forma seleccionada en el lienzo y lo refleja en todas las figuras seleccionadas.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonAlisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlisarActionPerformed

       Lienzo lienzo = getSelectedLienzo();
       if(lienzo != null){
           lienzo.getAtributosActual().setAlisado(!lienzo.getAtributosActual().isAlisado());
            System.out.println("Alisado: " + lienzo.getAtributosActual().isAlisado() );
       
            for (MiForma s: lienzo.getListaFiguras()){
               if (s != null && s.getAtributos().isSeleccionado()){
                         // actualizar las variables 
                         s.getAtributos().setAlisado(lienzo.getAtributosActual().isAlisado());
                         this.repaint();
               } 
           } 
       
       }
        System.out.println("Alisar");
 
    }//GEN-LAST:event_botonAlisarActionPerformed

    /**
    * Maneja el evento de cambio de estado cuando se ajusta el valor del grosor de la línea.
    * Actualiza el grosor de la línea en la forma seleccionada en el lienzo y lo refleja en todas las figuras seleccionadas.
    * Además, imprime un mensaje de depuración.
    *
    * @param evt El evento de cambio de estado que desencadenó este método.
    */
    private void botonGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonGrosorStateChanged
        Stroke st = new BasicStroke((Integer) this.botonGrosor.getValue());
       
       Lienzo lienzo = getSelectedLienzo();
       
       if(lienzo != null){
           lienzo.getAtributosActual().setStroke(st);
           
           for (MiForma s: lienzo.getListaFiguras()){
               if (s != null && s.getAtributos().isSeleccionado()){
                         // actualizar las variables 
                         s.getAtributos().setStroke(st);
                         this.repaint();
               } 
           } 
       } 
       System.out.println("Grosor");
    }//GEN-LAST:event_botonGrosorStateChanged

    /**
    * Maneja el evento de acción cuando se selecciona la opción de menú "Nuevo".
    * Crea una nueva ventana interna y la agrega al escritorio, luego inicializa una imagen en blanco con un borde negro discontinuo y la establece como imagen de fondo en el lienzo de la ventana interna.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void menuNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuNuevoActionPerformed
        // Obtener la ventana interna seleccionada
        // El recortar no lo aplico bien, ver la practica 9 para tenerlo bien
        VentanaInterna vi = new VentanaInterna();
        
        vi.addInternalFrameListener(new ManejadorVentanaInterna());
        
        escritorio.add(vi);
        vi.setVisible(true);
        
        BufferedImage imagen;        
        imagen = new BufferedImage(vi.getWidth(), vi.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = imagen.createGraphics();

        // Rellenar la imagen con color blanco
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imagen.getWidth(), imagen.getHeight());

        // Dibujar el borde negro discontinuo
        Stroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, imagen.getHeight() - 1, imagen.getWidth() - 1, imagen.getHeight() - 1); // Línea inferior
        g2d.drawLine(imagen.getWidth() - 1, 0, imagen.getWidth() - 1, imagen.getHeight() - 1); // Línea derecha // Línea izquierda
        
        //Establecer el área de recorte del lienzo para que coincida con el tamaño de la imagen
        //Graphics2D g2dLienzo = vi.getLienzo().getGraphics2D();
        //Graphics2D g2dLienzo = (Graphics2D) vi.getLienzo().getGraphics2D();
        //Rectangle clip = new Rectangle(0, 0, imagen.getWidth(), imagen.getHeight());        g2d.setClip(clip);
        //g2d.setClip(clip);

        vi.getLienzo().setImagen(imagen);
        System.out.println("Nueva Imagen");
    }//GEN-LAST:event_menuNuevoActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para crear una nueva imagen.
    * Llama al método de manejo de eventos correspondiente para la opción de menú "Nuevo".
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonNuevaImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaImagenActionPerformed
        this.menuNuevoActionPerformed(evt);
    }//GEN-LAST:event_botonNuevaImagenActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para abrir una imagen.
    * Llama al método de manejo de eventos correspondiente para la opción de menú "Abrir".
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonAbrirImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirImagenActionPerformed
        this.menuAbrirActionPerformed(evt);
    }//GEN-LAST:event_botonAbrirImagenActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para guardar una imagen.
    * Llama al método de manejo de eventos correspondiente para la opción de menú "Guardar".
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonGuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarImagenActionPerformed
        this.menuGuardarActionPerformed(evt);
    }//GEN-LAST:event_botonGuardarImagenActionPerformed

    /**
    * Maneja el evento de acción cuando se selecciona el botón para volcar una imagen sobre una forma seleccionada.
    * Obtiene la ventana interna seleccionada y busca formas seleccionadas en su lienzo. 
    * Luego, crea una nueva imagen del tamaño de la forma seleccionada, dibuja la forma sobre la imagen y la remueve del lienzo.
    * Finalmente, repinta la ventana interna para reflejar los cambios.
    *
    * @param evt El evento de acción que desencadenó este método.
    */
    private void botonVolcarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolcarImagenActionPerformed
    // Obtener la ventana interna seleccionada
    // solo borra, ver la practica 9 para tenerlo bien
    
    VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
    
    if (vi != null) {
        Iterator<MiForma> iterator = vi.getLienzo().getListaFiguras().iterator();
        
        while (iterator.hasNext()) {
            MiForma forma = iterator.next();
            
            if (forma.getAtributos().isSeleccionado()) {
                BufferedImage imgout = vi.getLienzo().getPaintedImage();
                
                //Graphics2D g2dImagen = imgout.createGraphics();
                
                //g2dImagen.paint(forma);
                forma.setImage(imgout);
                
                //forma.drawImage();    
                //forma.paint(g2dImagen);
                                   
                iterator.remove();
               
            }
        }
        
        vi.repaint();
        
        System.out.println("Volcado Imagen");
        } 
        
    }//GEN-LAST:event_botonVolcarImagenActionPerformed

    /**
    * Clase interna que maneja eventos relacionados con la activación de ventanas internas.
    */
    private class ManejadorVentanaInterna extends InternalFrameAdapter{
        // 1.Definir la clase manejadora y sobrecargar los metodos que sean necesarios
        // 2.crear el objeto manejador (hacer el new de la clase anterior)
        // 3.Enlazar el generador con el manejador  
        
        /**
        * Maneja el evento de activación de una ventana interna.
        * Actualiza los estados de los botones según los atributos y la forma actual del lienzo de la ventana interna activada.
        * 
        * @param evt El evento de activación de la ventana interna.
        */
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt){
             VentanaInterna vi = (VentanaInterna)evt.getInternalFrame();
             botonRellenar.setSelected(vi.getLienzo().getAtributosActual().getRelleno());
             botonAlisar.setSelected(vi.getLienzo().getAtributosActual().isAlisado());
             botonColor.setBackground(vi.getLienzo().getAtributosActual().getColor());
             
             Forma formaActual = vi.getLienzo().getForma();
             
             botonMover.setSelected(vi.getLienzo().isMover());
             
             if (! vi.getLienzo().isMover()) {
                switch(formaActual) {
                    case LINEA -> botonLinea.setSelected(true);
                    case RECTANGULO -> botonRectangulo.setSelected(true);
                    case ELIPSE -> botonOvalo.setSelected(true);
                    case FANTASMA -> botonFantasma.setSelected(true);
                    default -> {
                        botonLinea.setSelected(false);
                        botonRectangulo.setSelected(false);
                        botonOvalo.setSelected(false);
                        botonFantasma.setSelected(false);
                    }
                }
             }
             
                 
         }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BarraEstado;
    private javax.swing.JPopupMenu.Separator Separador1;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JButton botonAbrirImagen;
    private javax.swing.JToggleButton botonAlisar;
    private javax.swing.JToggleButton botonColor;
    private javax.swing.JToggleButton botonFantasma;
    private javax.swing.JSlider botonGrosor;
    private javax.swing.JButton botonGuardarImagen;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JToggleButton botonMover;
    private javax.swing.JButton botonNuevaImagen;
    private javax.swing.JToggleButton botonOvalo;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JToggleButton botonRellenar;
    private javax.swing.JToggleButton botonTransparencia;
    private javax.swing.JButton botonVolcarImagen;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.ButtonGroup grupoColores;
    private javax.swing.ButtonGroup grupoFormas;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuBarra;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenu menuImagen;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuVolcado;
    private paintbasico.PaintBasico paintBasico1;
    private javax.swing.JPanel panelColor;
    private javax.swing.JPanel panelInferior;
    // End of variables declaration//GEN-END:variables
}
