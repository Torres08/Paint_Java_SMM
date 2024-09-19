/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package paintbasico;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BandCombineOp;
import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;
import sm.image.EqualizationOp;
import sm.image.KernelProducer;
import sm.image.LookupTableProducer;
import sm.image.SepiaOp;
import sm.image.TintOp;
import sm.image.color.YCbCrColorSpace;
import sm.jltr.eventos.LienzoAdapter;
import sm.jltr.eventos.LienzoEvent;
import sm.jltr.graficos.Forma;
import static sm.jltr.graficos.Forma.ELIPSE;
import static sm.jltr.graficos.Forma.FANTASMA;
import static sm.jltr.graficos.Forma.LINEA;
import static sm.jltr.graficos.Forma.RECTANGULO;
import sm.jltr.graficos.MiForma;
import sm.jltr.images.CambiarTonoOp;
import sm.jltr.images.PosterizarOp;
import sm.jltr.images.RojoOp;
import sm.jltr.iu.Lienzo;
import sm.sound.SMClipPlayer;
import sm.sound.SMSoundRecorder;

/**
 * La clase VentanaPrincipal representa la ventana principal de la aplicación.
 * Extiende la clase javax.swing.JFrame para proporcionar una ventana de
 * interfaz gráfica de usuario.
 *
 * @author torres
 */
public class VentanaPrincipal extends javax.swing.JFrame {

    /**
     * Clase que implementa un manejador de eventos para un lienzo.
     *
     *
     * manejador de eventos para rgb y lienzo, manejador en la ventana principal
     * manejador de mouse en la VP new para crearla asociarla al lienzo cuando
     * haces un nuevo o abris hago el lienzo addlistener cada vez que creas
     * ventana interna, new abrir y nuevo
     *
     */
    class MiManejadorLienzo extends LienzoAdapter {

        /**
         * Método que se llama cuando se añade una figura al lienzo. Imprime un
         * mensaje en la consola indicando la figura que se añadió.
         *
         * @param evt El evento de añadir figura que contiene información sobre
         * la figura añadida.
         */
        @Override
        public void shapeAdded(LienzoEvent evt) {
            System.out.println("Figura " + evt.getForma() + " añadida");
        }

        @Override
        public void posicion(LienzoEvent evt) {
            int x = evt.getX();
            int y = evt.getY();
            actualizarPosicionRatonEnLabel(x, y);
        }

        @Override
        public void color(LienzoEvent evt) {
            int x = evt.getX();
            int y = evt.getY();
            Color color = obtenerColorPosicion(x, y);
            //System.out.println("Color: " + color);
            actualizarRBGEnlabel(color);
        }

    }

    class ManejadorAudio implements LineListener {

        private JButton botonPlay;
        private Thread contadorThread;
        private boolean empezar;
        private int tiempo;

        public ManejadorAudio() {
            this.tiempo = 0;
        }

        @Override
        public void update(LineEvent event) {
            if (event.getType() == LineEvent.Type.START) {
                startCounter();
                botonPlay.setEnabled(false);
                System.out.println("disable play");
            }

            if (event.getType() == LineEvent.Type.STOP) {
                stopCounter();
                botonPlay.setEnabled(true);
                System.out.println("enable play");
            }

            if (event.getType() == LineEvent.Type.CLOSE) {
            }
        }

        // ponerlo a 0 botonTiempo
        // 
        private void startCounter() {
            empezar = true;
            contadorThread = new Thread(() -> {
                try {
                    while (empezar) {
                        Thread.sleep(1000); // Sleep for 1 second
                        tiempo++;
                        int minutos = tiempo / 60;
                        int segundos = tiempo % 60;
                        String timeFormatted = String.format("%02d:%02d", minutos, segundos);
                        System.out.println("Elapsed time: " + timeFormatted);
                        SwingUtilities.invokeLater(() -> botonTiempo.setText(timeFormatted));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            contadorThread.start();
        }

        private void stopCounter() {
            empezar = false;
            if (contadorThread != null) {
                try {
                    contadorThread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public int getElapsedTime() {
            return tiempo;
        }

        public void resetCounter() {
            tiempo = 0;
        }
    }

    private SMClipPlayer player = null;
    private SMSoundRecorder recorder = null;

    BufferedImage imgFuente;

    MiManejadorLienzo manejador = new MiManejadorLienzo();

    Color color = Color.BLACK;

    Color c1 = Color.RED;
    Color c2 = Color.BLUE;

    //lienzo viene de vi.getLienzo2D()
    //lienzo. addLienzoListener(manejador);
    /**
     * Constructor de la clase VentanaPrincipal. Inicializa y configura los
     * componentes de la ventana.
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
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        barraHerramientas = new javax.swing.JToolBar();
        botonNuevaImagen = new javax.swing.JButton();
        botonAbrirImagen = new javax.swing.JButton();
        botonGuardarImagen = new javax.swing.JButton();
        botonDuplicar = new javax.swing.JButton();
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
        jSeparator12 = new javax.swing.JToolBar.Separator();
        botonPlay = new javax.swing.JButton();
        botonPausa = new javax.swing.JButton();
        botonListaReproduccion = new javax.swing.JComboBox<>();
        botonRecord = new javax.swing.JButton();
        botonTiempo = new javax.swing.JLabel();
        panelInferior = new javax.swing.JPanel();
        BarraEstado = new javax.swing.JLabel();
        barraHerramientasInferior = new javax.swing.JToolBar();
        Separator5 = new javax.swing.JPanel();
        imagenBrillo = new javax.swing.JLabel();
        botonBrillo = new javax.swing.JSlider();
        Separator1 = new javax.swing.JPanel();
        imagenContraste = new javax.swing.JLabel();
        botonContraste = new javax.swing.JSlider();
        Separator3 = new javax.swing.JPanel();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        Separator4 = new javax.swing.JPanel();
        ComboBoxFiltros = new javax.swing.JComboBox<>();
        Separator2 = new javax.swing.JPanel();
        imagenCometa = new javax.swing.JLabel();
        botonCometa = new javax.swing.JSlider();
        Separator6 = new javax.swing.JPanel();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        Separator8 = new javax.swing.JPanel();
        botonContrasteNormal = new javax.swing.JButton();
        botonContrasteIluminado = new javax.swing.JButton();
        botonContrasteOscurecido = new javax.swing.JButton();
        botonOscurecerZonasClaras = new javax.swing.JButton();
        botonNegativo = new javax.swing.JButton();
        Separator7 = new javax.swing.JPanel();
        imagenRotar = new javax.swing.JLabel();
        botonRotar = new javax.swing.JSlider();
        Separator9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonReto = new javax.swing.JSlider();
        Separator14 = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JToolBar.Separator();
        Separator10 = new javax.swing.JPanel();
        botonRotacion180 = new javax.swing.JButton();
        Separator11 = new javax.swing.JPanel();
        botonIncrementarEscalado = new javax.swing.JButton();
        botonReducirEscalado = new javax.swing.JButton();
        Separator13 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        Separator12 = new javax.swing.JPanel();
        botonBandas = new javax.swing.JButton();
        comboBoxEspacioColor = new javax.swing.JComboBox<>();
        Separator15 = new javax.swing.JPanel();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        Separator16 = new javax.swing.JPanel();
        botonCombinar = new javax.swing.JButton();
        botonTintado = new javax.swing.JButton();
        botonTintadoSlider = new javax.swing.JSlider();
        botonSepia = new javax.swing.JButton();
        botonEcualizacion = new javax.swing.JButton();
        botonFiltroRojo = new javax.swing.JButton();
        botonFiltroRojoSlider = new javax.swing.JSlider();
        Separator17 = new javax.swing.JPanel();
        imagenPosterizacion = new javax.swing.JLabel();
        botonPosterizacion = new javax.swing.JSlider();
        Separator18 = new javax.swing.JPanel();
        panelC1 = new javax.swing.JPanel();
        botonC1 = new javax.swing.JToggleButton();
        botonCambioColor = new javax.swing.JSlider();
        panelC2 = new javax.swing.JPanel();
        botonC2 = new javax.swing.JToggleButton();
        menuPosicionRGB = new javax.swing.JPanel();
        labelPosicionRGB = new javax.swing.JLabel();
        labelPosicionRaton = new javax.swing.JLabel();
        escritorio = new javax.swing.JDesktopPane();
        menuBarra = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menuNuevo = new javax.swing.JMenuItem();
        menuAbrir = new javax.swing.JMenuItem();
        menuGuardar = new javax.swing.JMenuItem();
        menuImagen = new javax.swing.JMenu();
        menuVolcado = new javax.swing.JMenuItem();
        menuSeparadorImagen1 = new javax.swing.JPopupMenu.Separator();
        menuRescaleOp = new javax.swing.JMenuItem();
        menuConvolveOp = new javax.swing.JMenuItem();
        menuCometa = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        menuAffineTransformOp = new javax.swing.JMenuItem();
        menuLookupOp = new javax.swing.JMenuItem();
        menuLookupOpReto = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        menuBandCombineOp = new javax.swing.JMenuItem();
        menuColorConvertOp = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        menuOperadorPosterizarOp = new javax.swing.JMenuItem();
        menuOperadorCambioColor = new javax.swing.JMenuItem();

        FormListener formListener = new FormListener();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barraHerramientas.setRollover(true);

        botonNuevaImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/nuevo.png"))); // NOI18N
        botonNuevaImagen.setToolTipText("Nuevo");
        botonNuevaImagen.setFocusable(false);
        botonNuevaImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNuevaImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNuevaImagen.addActionListener(formListener);
        barraHerramientas.add(botonNuevaImagen);

        botonAbrirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/abrir.png"))); // NOI18N
        botonAbrirImagen.setToolTipText("Abrir");
        botonAbrirImagen.setFocusable(false);
        botonAbrirImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAbrirImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAbrirImagen.addActionListener(formListener);
        barraHerramientas.add(botonAbrirImagen);

        botonGuardarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/guardar.png"))); // NOI18N
        botonGuardarImagen.setToolTipText("Guardar");
        botonGuardarImagen.setFocusable(false);
        botonGuardarImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonGuardarImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonGuardarImagen.addActionListener(formListener);
        barraHerramientas.add(botonGuardarImagen);

        botonDuplicar.setText(".2x");
        botonDuplicar.setToolTipText("Duplicar");
        botonDuplicar.setFocusable(false);
        botonDuplicar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonDuplicar.setMaximumSize(new java.awt.Dimension(32, 30));
        botonDuplicar.setMinimumSize(new java.awt.Dimension(32, 30));
        botonDuplicar.setPreferredSize(new java.awt.Dimension(32, 30));
        botonDuplicar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonDuplicar.addActionListener(formListener);
        barraHerramientas.add(botonDuplicar);
        barraHerramientas.add(jSeparator2);

        grupoFormas.add(botonLinea);
        botonLinea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/linea.png"))); // NOI18N
        botonLinea.setToolTipText("Linea");
        botonLinea.setFocusable(false);
        botonLinea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonLinea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonLinea.addActionListener(formListener);
        barraHerramientas.add(botonLinea);

        grupoFormas.add(botonRectangulo);
        botonRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rectangulo.png"))); // NOI18N
        botonRectangulo.setToolTipText("Rectangulo");
        botonRectangulo.setFocusable(false);
        botonRectangulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRectangulo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRectangulo.addActionListener(formListener);
        barraHerramientas.add(botonRectangulo);

        grupoFormas.add(botonOvalo);
        botonOvalo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/elipse.png"))); // NOI18N
        botonOvalo.setToolTipText("Ovalo");
        botonOvalo.setFocusable(false);
        botonOvalo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOvalo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOvalo.addActionListener(formListener);
        barraHerramientas.add(botonOvalo);

        grupoFormas.add(botonFantasma);
        botonFantasma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/fantasma.png"))); // NOI18N
        botonFantasma.setToolTipText("Fantasma");
        botonFantasma.setFocusable(false);
        botonFantasma.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonFantasma.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonFantasma.addActionListener(formListener);
        barraHerramientas.add(botonFantasma);

        grupoFormas.add(botonMover);
        botonMover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/seleccion.png"))); // NOI18N
        botonMover.setToolTipText("Mover y Seleccionar");
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
        botonColor.setToolTipText("Color");
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
        botonRellenar.setToolTipText("Rellenar");
        botonRellenar.setFocusable(false);
        botonRellenar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRellenar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRellenar.addActionListener(formListener);
        barraHerramientas.add(botonRellenar);

        botonTransparencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/transparencia.png"))); // NOI18N
        botonTransparencia.setToolTipText("Transparencia");
        botonTransparencia.setFocusable(false);
        botonTransparencia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTransparencia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTransparencia.addActionListener(formListener);
        barraHerramientas.add(botonTransparencia);

        botonAlisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/alisar.png"))); // NOI18N
        botonAlisar.setToolTipText("Alisar");
        botonAlisar.setFocusable(false);
        botonAlisar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonAlisar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonAlisar.addActionListener(formListener);
        barraHerramientas.add(botonAlisar);

        botonGrosor.setToolTipText("Grosor");
        botonGrosor.setValue(1);
        botonGrosor.setMaximumSize(new java.awt.Dimension(50, 25));
        botonGrosor.setMinimumSize(new java.awt.Dimension(50, 25));
        botonGrosor.setPreferredSize(new java.awt.Dimension(50, 25));
        botonGrosor.addChangeListener(formListener);
        barraHerramientas.add(botonGrosor);
        barraHerramientas.add(jSeparator3);

        botonVolcarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/volcado.png"))); // NOI18N
        botonVolcarImagen.setToolTipText("Volcar Imagen");
        botonVolcarImagen.setFocusable(false);
        botonVolcarImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonVolcarImagen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonVolcarImagen.addActionListener(formListener);
        barraHerramientas.add(botonVolcarImagen);
        barraHerramientas.add(jSeparator12);

        botonPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/play.png"))); // NOI18N
        botonPlay.setFocusable(false);
        botonPlay.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPlay.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonPlay.addActionListener(formListener);
        barraHerramientas.add(botonPlay);

        botonPausa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/pausa.png"))); // NOI18N
        botonPausa.setFocusable(false);
        botonPausa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonPausa.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonPausa.addActionListener(formListener);
        barraHerramientas.add(botonPausa);

        botonListaReproduccion.setMaximumSize(new java.awt.Dimension(160, 39));
        botonListaReproduccion.setMinimumSize(new java.awt.Dimension(160, 39));
        botonListaReproduccion.setPreferredSize(new java.awt.Dimension(160, 39));
        barraHerramientas.add(botonListaReproduccion);

        botonRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/record.png"))); // NOI18N
        botonRecord.setFocusable(false);
        botonRecord.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRecord.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRecord.addActionListener(formListener);
        barraHerramientas.add(botonRecord);

        botonTiempo.setText("00:00");
        barraHerramientas.add(botonTiempo);

        getContentPane().add(barraHerramientas, java.awt.BorderLayout.PAGE_START);

        panelInferior.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panelInferior.setMinimumSize(new java.awt.Dimension(426, 46));
        panelInferior.setLayout(new java.awt.BorderLayout());

        BarraEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BarraEstado.setText("Linea");
        BarraEstado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInferior.add(BarraEstado, java.awt.BorderLayout.CENTER);

        barraHerramientasInferior.setRollover(true);
        barraHerramientasInferior.setPreferredSize(new java.awt.Dimension(379, 39));

        Separator5.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator5.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator5Layout = new javax.swing.GroupLayout(Separator5);
        Separator5.setLayout(Separator5Layout);
        Separator5Layout.setHorizontalGroup(
            Separator5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator5Layout.setVerticalGroup(
            Separator5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator5);

        imagenBrillo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagenBrillo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/brillo.png"))); // NOI18N
        imagenBrillo.setAlignmentX(10.0F);
        imagenBrillo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        barraHerramientasInferior.add(imagenBrillo);

        botonBrillo.setMaximum(255);
        botonBrillo.setMinimum(-255);
        botonBrillo.setToolTipText("Brillo");
        botonBrillo.setValue(0);
        botonBrillo.setMaximumSize(new java.awt.Dimension(50, 30));
        botonBrillo.setMinimumSize(new java.awt.Dimension(50, 30));
        botonBrillo.setPreferredSize(new java.awt.Dimension(50, 30));
        botonBrillo.addChangeListener(formListener);
        botonBrillo.addFocusListener(formListener);
        barraHerramientasInferior.add(botonBrillo);

        Separator1.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator1.setMinimumSize(new java.awt.Dimension(10, 0));
        Separator1.setPreferredSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator1Layout = new javax.swing.GroupLayout(Separator1);
        Separator1.setLayout(Separator1Layout);
        Separator1Layout.setHorizontalGroup(
            Separator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator1Layout.setVerticalGroup(
            Separator1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator1);

        imagenContraste.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagenContraste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste.png"))); // NOI18N
        imagenContraste.setAlignmentX(10.0F);
        imagenContraste.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        barraHerramientasInferior.add(imagenContraste);

        botonContraste.setMajorTickSpacing(1);
        botonContraste.setMaximum(20);
        botonContraste.setMinimum(1);
        botonContraste.setMinorTickSpacing(1);
        botonContraste.setToolTipText("Contraste");
        botonContraste.setValue(10);
        botonContraste.setMaximumSize(new java.awt.Dimension(50, 30));
        botonContraste.setMinimumSize(new java.awt.Dimension(50, 30));
        botonContraste.setPreferredSize(new java.awt.Dimension(50, 30));
        botonContraste.addChangeListener(formListener);
        botonContraste.addFocusListener(formListener);
        barraHerramientasInferior.add(botonContraste);

        Separator3.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator3.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator3Layout = new javax.swing.GroupLayout(Separator3);
        Separator3.setLayout(Separator3Layout);
        Separator3Layout.setHorizontalGroup(
            Separator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator3Layout.setVerticalGroup(
            Separator3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator3);

        jSeparator5.setMaximumSize(new java.awt.Dimension(30, 32767));
        jSeparator5.setMinimumSize(new java.awt.Dimension(15, 5));
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(7, 5));
        barraHerramientasInferior.add(jSeparator5);

        Separator4.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator4.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator4Layout = new javax.swing.GroupLayout(Separator4);
        Separator4.setLayout(Separator4Layout);
        Separator4Layout.setHorizontalGroup(
            Separator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator4Layout.setVerticalGroup(
            Separator4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator4);

        ComboBoxFiltros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Media", "Binomial", "Enfoque", "Relieve", "Laplaciano", "Iluminado 3x3", "Iluminado 5x5" }));
        ComboBoxFiltros.setToolTipText("Filtros");
        ComboBoxFiltros.setMaximumSize(new java.awt.Dimension(140, 39));
        ComboBoxFiltros.addActionListener(formListener);
        barraHerramientasInferior.add(ComboBoxFiltros);

        Separator2.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator2.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator2Layout = new javax.swing.GroupLayout(Separator2);
        Separator2.setLayout(Separator2Layout);
        Separator2Layout.setHorizontalGroup(
            Separator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator2Layout.setVerticalGroup(
            Separator2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator2);

        imagenCometa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagenCometa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/cometa.png"))); // NOI18N
        imagenCometa.setAlignmentX(10.0F);
        imagenCometa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        barraHerramientasInferior.add(imagenCometa);

        botonCometa.setMajorTickSpacing(1);
        botonCometa.setMaximum(20);
        botonCometa.setMinimum(1);
        botonCometa.setToolTipText("Cometa");
        botonCometa.setValue(1);
        botonCometa.setMaximumSize(new java.awt.Dimension(50, 30));
        botonCometa.setMinimumSize(new java.awt.Dimension(50, 30));
        botonCometa.setPreferredSize(new java.awt.Dimension(50, 30));
        botonCometa.addChangeListener(formListener);
        botonCometa.addFocusListener(formListener);
        barraHerramientasInferior.add(botonCometa);

        Separator6.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator6.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator6Layout = new javax.swing.GroupLayout(Separator6);
        Separator6.setLayout(Separator6Layout);
        Separator6Layout.setHorizontalGroup(
            Separator6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator6Layout.setVerticalGroup(
            Separator6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator6);

        jSeparator6.setMaximumSize(new java.awt.Dimension(30, 32767));
        jSeparator6.setMinimumSize(new java.awt.Dimension(15, 5));
        jSeparator6.setOpaque(true);
        jSeparator6.setPreferredSize(new java.awt.Dimension(7, 5));
        barraHerramientasInferior.add(jSeparator6);

        Separator8.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator8.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator8Layout = new javax.swing.GroupLayout(Separator8);
        Separator8.setLayout(Separator8Layout);
        Separator8Layout.setHorizontalGroup(
            Separator8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator8Layout.setVerticalGroup(
            Separator8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator8);

        botonContrasteNormal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/contraste2.png"))); // NOI18N
        botonContrasteNormal.setToolTipText("Contraste Normal");
        botonContrasteNormal.setFocusable(false);
        botonContrasteNormal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonContrasteNormal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonContrasteNormal.addActionListener(formListener);
        barraHerramientasInferior.add(botonContrasteNormal);

        botonContrasteIluminado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/iluminar.png"))); // NOI18N
        botonContrasteIluminado.setToolTipText("Contraste Iluminado");
        botonContrasteIluminado.setFocusable(false);
        botonContrasteIluminado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonContrasteIluminado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonContrasteIluminado.addActionListener(formListener);
        barraHerramientasInferior.add(botonContrasteIluminado);

        botonContrasteOscurecido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ocurecer.png"))); // NOI18N
        botonContrasteOscurecido.setToolTipText("Contraste Oscurecido");
        botonContrasteOscurecido.setFocusable(false);
        botonContrasteOscurecido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonContrasteOscurecido.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonContrasteOscurecido.addActionListener(formListener);
        barraHerramientasInferior.add(botonContrasteOscurecido);

        botonOscurecerZonasClaras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/operador1.png"))); // NOI18N
        botonOscurecerZonasClaras.setToolTipText("Oscurecer Zonas Claras");
        botonOscurecerZonasClaras.setFocusable(false);
        botonOscurecerZonasClaras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonOscurecerZonasClaras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonOscurecerZonasClaras.addActionListener(formListener);
        barraHerramientasInferior.add(botonOscurecerZonasClaras);

        botonNegativo.setText("NEG");
        botonNegativo.setToolTipText("Negativo");
        botonNegativo.setFocusable(false);
        botonNegativo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonNegativo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonNegativo.addActionListener(formListener);
        barraHerramientasInferior.add(botonNegativo);

        Separator7.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator7.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator7Layout = new javax.swing.GroupLayout(Separator7);
        Separator7.setLayout(Separator7Layout);
        Separator7Layout.setHorizontalGroup(
            Separator7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator7Layout.setVerticalGroup(
            Separator7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator7);

        imagenRotar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagenRotar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/operador2.png"))); // NOI18N
        imagenRotar.setAlignmentX(10.0F);
        imagenRotar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        barraHerramientasInferior.add(imagenRotar);

        botonRotar.setMajorTickSpacing(1);
        botonRotar.setMaximum(255);
        botonRotar.setToolTipText("Rotar");
        botonRotar.setValue(10);
        botonRotar.setMaximumSize(new java.awt.Dimension(50, 30));
        botonRotar.setMinimumSize(new java.awt.Dimension(50, 30));
        botonRotar.setPreferredSize(new java.awt.Dimension(50, 30));
        botonRotar.addChangeListener(formListener);
        botonRotar.addFocusListener(formListener);
        barraHerramientasInferior.add(botonRotar);

        Separator9.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator9.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator9Layout = new javax.swing.GroupLayout(Separator9);
        Separator9.setLayout(Separator9Layout);
        Separator9Layout.setHorizontalGroup(
            Separator9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator9Layout.setVerticalGroup(
            Separator9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator9);

        jLabel1.setText("RETO ");
        barraHerramientasInferior.add(jLabel1);

        botonReto.setMajorTickSpacing(1);
        botonReto.setMaximum(255);
        botonReto.setToolTipText("Transformación lineal con un punto de control");
        botonReto.setValue(128);
        botonReto.setMaximumSize(new java.awt.Dimension(50, 30));
        botonReto.setMinimumSize(new java.awt.Dimension(50, 30));
        botonReto.setPreferredSize(new java.awt.Dimension(50, 30));
        botonReto.addChangeListener(formListener);
        botonReto.addFocusListener(formListener);
        barraHerramientasInferior.add(botonReto);

        Separator14.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator14.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator14Layout = new javax.swing.GroupLayout(Separator14);
        Separator14.setLayout(Separator14Layout);
        Separator14Layout.setHorizontalGroup(
            Separator14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator14Layout.setVerticalGroup(
            Separator14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator14);

        jSeparator7.setMaximumSize(new java.awt.Dimension(30, 32767));
        jSeparator7.setMinimumSize(new java.awt.Dimension(15, 5));
        jSeparator7.setOpaque(true);
        jSeparator7.setPreferredSize(new java.awt.Dimension(7, 5));
        barraHerramientasInferior.add(jSeparator7);

        Separator10.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator10.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator10Layout = new javax.swing.GroupLayout(Separator10);
        Separator10.setLayout(Separator10Layout);
        Separator10Layout.setHorizontalGroup(
            Separator10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator10Layout.setVerticalGroup(
            Separator10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator10);

        botonRotacion180.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rotar180.png"))); // NOI18N
        botonRotacion180.setToolTipText("Rotar 180");
        botonRotacion180.setFocusable(false);
        botonRotacion180.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonRotacion180.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonRotacion180.addActionListener(formListener);
        barraHerramientasInferior.add(botonRotacion180);

        Separator11.setMaximumSize(new java.awt.Dimension(10, 0));
        Separator11.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator11Layout = new javax.swing.GroupLayout(Separator11);
        Separator11.setLayout(Separator11Layout);
        Separator11Layout.setHorizontalGroup(
            Separator11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator11Layout.setVerticalGroup(
            Separator11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator11);

        botonIncrementarEscalado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/mas.png"))); // NOI18N
        botonIncrementarEscalado.setToolTipText("Incrementar Escalado");
        botonIncrementarEscalado.setFocusable(false);
        botonIncrementarEscalado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonIncrementarEscalado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonIncrementarEscalado.addActionListener(formListener);
        barraHerramientasInferior.add(botonIncrementarEscalado);

        botonReducirEscalado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/menos.png"))); // NOI18N
        botonReducirEscalado.setToolTipText("Reducir Escalado");
        botonReducirEscalado.setFocusable(false);
        botonReducirEscalado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonReducirEscalado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonReducirEscalado.addActionListener(formListener);
        barraHerramientasInferior.add(botonReducirEscalado);

        Separator13.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator13.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator13Layout = new javax.swing.GroupLayout(Separator13);
        Separator13.setLayout(Separator13Layout);
        Separator13Layout.setHorizontalGroup(
            Separator13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator13Layout.setVerticalGroup(
            Separator13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator13);

        jSeparator8.setMaximumSize(new java.awt.Dimension(30, 32767));
        jSeparator8.setMinimumSize(new java.awt.Dimension(15, 5));
        jSeparator8.setOpaque(true);
        jSeparator8.setPreferredSize(new java.awt.Dimension(7, 5));
        barraHerramientasInferior.add(jSeparator8);

        Separator12.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator12.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator12Layout = new javax.swing.GroupLayout(Separator12);
        Separator12.setLayout(Separator12Layout);
        Separator12Layout.setHorizontalGroup(
            Separator12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator12Layout.setVerticalGroup(
            Separator12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator12);

        botonBandas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/bandas.png"))); // NOI18N
        botonBandas.setToolTipText("Bandas");
        botonBandas.setFocusable(false);
        botonBandas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonBandas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonBandas.addActionListener(formListener);
        barraHerramientasInferior.add(botonBandas);

        comboBoxEspacioColor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "sRGB", "YCC", "GREY" }));
        comboBoxEspacioColor.setToolTipText("Espacio Color");
        comboBoxEspacioColor.addActionListener(formListener);
        barraHerramientasInferior.add(comboBoxEspacioColor);

        Separator15.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator15.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator15Layout = new javax.swing.GroupLayout(Separator15);
        Separator15.setLayout(Separator15Layout);
        Separator15Layout.setHorizontalGroup(
            Separator15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator15Layout.setVerticalGroup(
            Separator15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator15);

        jSeparator9.setMaximumSize(new java.awt.Dimension(30, 32767));
        jSeparator9.setMinimumSize(new java.awt.Dimension(15, 5));
        jSeparator9.setOpaque(true);
        jSeparator9.setPreferredSize(new java.awt.Dimension(7, 5));
        barraHerramientasInferior.add(jSeparator9);

        Separator16.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator16.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator16Layout = new javax.swing.GroupLayout(Separator16);
        Separator16.setLayout(Separator16Layout);
        Separator16Layout.setHorizontalGroup(
            Separator16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator16Layout.setVerticalGroup(
            Separator16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator16);

        botonCombinar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/combinar.png"))); // NOI18N
        botonCombinar.setToolTipText("Combinar");
        botonCombinar.setFocusable(false);
        botonCombinar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonCombinar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonCombinar.addActionListener(formListener);
        barraHerramientasInferior.add(botonCombinar);

        botonTintado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/tintar.png"))); // NOI18N
        botonTintado.setToolTipText("Tintado");
        botonTintado.setFocusable(false);
        botonTintado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonTintado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonTintado.addActionListener(formListener);
        barraHerramientasInferior.add(botonTintado);

        botonTintadoSlider.setMaximum(10);
        botonTintadoSlider.setMinimum(1);
        botonTintadoSlider.setToolTipText("Tintado Slider");
        botonTintadoSlider.setValue(2);
        botonTintadoSlider.setMaximumSize(new java.awt.Dimension(50, 30));
        botonTintadoSlider.setMinimumSize(new java.awt.Dimension(50, 30));
        botonTintadoSlider.setPreferredSize(new java.awt.Dimension(50, 30));
        botonTintadoSlider.addChangeListener(formListener);
        botonTintadoSlider.addFocusListener(formListener);
        barraHerramientasInferior.add(botonTintadoSlider);

        botonSepia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/sepia.png"))); // NOI18N
        botonSepia.setToolTipText("Sepia");
        botonSepia.setFocusable(false);
        botonSepia.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonSepia.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonSepia.addActionListener(formListener);
        barraHerramientasInferior.add(botonSepia);

        botonEcualizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/ecualizar.png"))); // NOI18N
        botonEcualizacion.setToolTipText("Ecualizacion");
        botonEcualizacion.setFocusable(false);
        botonEcualizacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonEcualizacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonEcualizacion.addActionListener(formListener);
        barraHerramientasInferior.add(botonEcualizacion);

        botonFiltroRojo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/rojo.png"))); // NOI18N
        botonFiltroRojo.setToolTipText("Filtro Rojo");
        botonFiltroRojo.setFocusable(false);
        botonFiltroRojo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonFiltroRojo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonFiltroRojo.addActionListener(formListener);
        barraHerramientasInferior.add(botonFiltroRojo);

        botonFiltroRojoSlider.setMaximum(20);
        botonFiltroRojoSlider.setMinimum(2);
        botonFiltroRojoSlider.setToolTipText("Filtro Rojo Slider");
        botonFiltroRojoSlider.setValue(2);
        botonFiltroRojoSlider.setMaximumSize(new java.awt.Dimension(50, 30));
        botonFiltroRojoSlider.setMinimumSize(new java.awt.Dimension(50, 30));
        botonFiltroRojoSlider.setPreferredSize(new java.awt.Dimension(50, 30));
        botonFiltroRojoSlider.addChangeListener(formListener);
        botonFiltroRojoSlider.addFocusListener(formListener);
        barraHerramientasInferior.add(botonFiltroRojoSlider);

        Separator17.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator17.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator17Layout = new javax.swing.GroupLayout(Separator17);
        Separator17.setLayout(Separator17Layout);
        Separator17Layout.setHorizontalGroup(
            Separator17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator17Layout.setVerticalGroup(
            Separator17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator17);

        imagenPosterizacion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        imagenPosterizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/posterizar.png"))); // NOI18N
        imagenPosterizacion.setAlignmentX(10.0F);
        imagenPosterizacion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        barraHerramientasInferior.add(imagenPosterizacion);

        botonPosterizacion.setMaximum(20);
        botonPosterizacion.setMinimum(2);
        botonPosterizacion.setToolTipText("Posterizacion");
        botonPosterizacion.setValue(2);
        botonPosterizacion.setMaximumSize(new java.awt.Dimension(50, 30));
        botonPosterizacion.setMinimumSize(new java.awt.Dimension(50, 30));
        botonPosterizacion.setPreferredSize(new java.awt.Dimension(50, 30));
        botonPosterizacion.addChangeListener(formListener);
        botonPosterizacion.addFocusListener(formListener);
        barraHerramientasInferior.add(botonPosterizacion);

        Separator18.setMaximumSize(new java.awt.Dimension(10, 1));
        Separator18.setMinimumSize(new java.awt.Dimension(10, 0));

        javax.swing.GroupLayout Separator18Layout = new javax.swing.GroupLayout(Separator18);
        Separator18.setLayout(Separator18Layout);
        Separator18Layout.setHorizontalGroup(
            Separator18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        Separator18Layout.setVerticalGroup(
            Separator18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        barraHerramientasInferior.add(Separator18);

        panelC1.setMaximumSize(new java.awt.Dimension(30, 30));
        panelC1.setMinimumSize(new java.awt.Dimension(30, 30));

        botonC1.setBackground(java.awt.Color.red);
        botonC1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonC1.setMaximumSize(new java.awt.Dimension(20, 20));
        botonC1.setMinimumSize(new java.awt.Dimension(20, 20));
        botonC1.setPreferredSize(new java.awt.Dimension(20, 20));
        botonC1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonC1.addActionListener(formListener);

        javax.swing.GroupLayout panelC1Layout = new javax.swing.GroupLayout(panelC1);
        panelC1.setLayout(panelC1Layout);
        panelC1Layout.setHorizontalGroup(
            panelC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelC1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(botonC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 10, Short.MAX_VALUE)))
        );
        panelC1Layout.setVerticalGroup(
            panelC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelC1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelC1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(botonC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        barraHerramientasInferior.add(panelC1);

        botonCambioColor.setMaximum(20);
        botonCambioColor.setMinimum(2);
        botonCambioColor.setToolTipText("Cambio Color");
        botonCambioColor.setValue(2);
        botonCambioColor.setMaximumSize(new java.awt.Dimension(50, 30));
        botonCambioColor.setMinimumSize(new java.awt.Dimension(50, 30));
        botonCambioColor.setPreferredSize(new java.awt.Dimension(50, 30));
        botonCambioColor.addChangeListener(formListener);
        botonCambioColor.addFocusListener(formListener);
        barraHerramientasInferior.add(botonCambioColor);

        panelC2.setMaximumSize(new java.awt.Dimension(30, 30));
        panelC2.setMinimumSize(new java.awt.Dimension(30, 30));

        botonC2.setBackground(java.awt.Color.blue);
        botonC2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        botonC2.setMaximumSize(new java.awt.Dimension(20, 20));
        botonC2.setMinimumSize(new java.awt.Dimension(20, 20));
        botonC2.setPreferredSize(new java.awt.Dimension(20, 20));
        botonC2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        botonC2.addActionListener(formListener);

        javax.swing.GroupLayout panelC2Layout = new javax.swing.GroupLayout(panelC2);
        panelC2.setLayout(panelC2Layout);
        panelC2Layout.setHorizontalGroup(
            panelC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelC2Layout.createSequentialGroup()
                    .addGap(0, 5, Short.MAX_VALUE)
                    .addComponent(botonC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 5, Short.MAX_VALUE)))
        );
        panelC2Layout.setVerticalGroup(
            panelC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(panelC2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelC2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(botonC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        barraHerramientasInferior.add(panelC2);

        panelInferior.add(barraHerramientasInferior, java.awt.BorderLayout.PAGE_START);

        menuPosicionRGB.setLayout(new java.awt.GridLayout(1, 0));

        labelPosicionRGB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPosicionRGB.setText("RGB");
        labelPosicionRGB.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelPosicionRGB.addMouseMotionListener(formListener);
        menuPosicionRGB.add(labelPosicionRGB);

        labelPosicionRaton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelPosicionRaton.setText("(x,y)");
        labelPosicionRaton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelPosicionRaton.addMouseMotionListener(formListener);
        menuPosicionRGB.add(labelPosicionRaton);

        panelInferior.add(menuPosicionRGB, java.awt.BorderLayout.LINE_START);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        escritorio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1610, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 374, Short.MAX_VALUE)
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

        menuBarra.add(menuArchivo);

        menuImagen.setText("Imagen");

        menuVolcado.setText("Volcado");
        menuVolcado.addActionListener(formListener);
        menuImagen.add(menuVolcado);
        menuImagen.add(menuSeparadorImagen1);

        menuRescaleOp.setText("RescaleOp");
        menuRescaleOp.addActionListener(formListener);
        menuImagen.add(menuRescaleOp);

        menuConvolveOp.setText("ConvolveOp");
        menuConvolveOp.addActionListener(formListener);
        menuImagen.add(menuConvolveOp);

        menuCometa.setText("Cometa");
        menuCometa.addActionListener(formListener);
        menuImagen.add(menuCometa);
        menuImagen.add(jSeparator4);

        menuAffineTransformOp.setText("AffineTransformOp");
        menuAffineTransformOp.addActionListener(formListener);
        menuImagen.add(menuAffineTransformOp);

        menuLookupOp.setText("LookupOp");
        menuLookupOp.addActionListener(formListener);
        menuImagen.add(menuLookupOp);

        menuLookupOpReto.setText("LookupUpReto");
        menuLookupOpReto.addActionListener(formListener);
        menuImagen.add(menuLookupOpReto);
        menuImagen.add(jSeparator10);

        menuBandCombineOp.setText("bandCombineOp");
        menuBandCombineOp.addActionListener(formListener);
        menuImagen.add(menuBandCombineOp);

        menuColorConvertOp.setText("ColorConvertOp");
        menuColorConvertOp.addActionListener(formListener);
        menuImagen.add(menuColorConvertOp);
        menuImagen.add(jSeparator11);

        menuOperadorPosterizarOp.setText("OperadorPosterizarOp");
        menuOperadorPosterizarOp.addActionListener(formListener);
        menuImagen.add(menuOperadorPosterizarOp);

        menuOperadorCambioColor.setText("OperadorCambioColor");
        menuOperadorCambioColor.addActionListener(formListener);
        menuImagen.add(menuOperadorCambioColor);

        menuBarra.add(menuImagen);

        setJMenuBar(menuBarra);

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener, java.awt.event.FocusListener, java.awt.event.MouseMotionListener, javax.swing.event.ChangeListener {
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
            else if (evt.getSource() == botonDuplicar) {
                VentanaPrincipal.this.botonDuplicarActionPerformed(evt);
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
            else if (evt.getSource() == botonVolcarImagen) {
                VentanaPrincipal.this.botonVolcarImagenActionPerformed(evt);
            }
            else if (evt.getSource() == botonPlay) {
                VentanaPrincipal.this.botonPlayActionPerformed(evt);
            }
            else if (evt.getSource() == botonPausa) {
                VentanaPrincipal.this.botonPausaActionPerformed(evt);
            }
            else if (evt.getSource() == botonRecord) {
                VentanaPrincipal.this.botonRecordActionPerformed(evt);
            }
            else if (evt.getSource() == ComboBoxFiltros) {
                VentanaPrincipal.this.ComboBoxFiltrosActionPerformed(evt);
            }
            else if (evt.getSource() == botonContrasteNormal) {
                VentanaPrincipal.this.botonContrasteNormalActionPerformed(evt);
            }
            else if (evt.getSource() == botonContrasteIluminado) {
                VentanaPrincipal.this.botonContrasteIluminadoActionPerformed(evt);
            }
            else if (evt.getSource() == botonContrasteOscurecido) {
                VentanaPrincipal.this.botonContrasteOscurecidoActionPerformed(evt);
            }
            else if (evt.getSource() == botonOscurecerZonasClaras) {
                VentanaPrincipal.this.botonOscurecerZonasClarasActionPerformed(evt);
            }
            else if (evt.getSource() == botonNegativo) {
                VentanaPrincipal.this.botonNegativoActionPerformed(evt);
            }
            else if (evt.getSource() == botonRotacion180) {
                VentanaPrincipal.this.botonRotacion180ActionPerformed(evt);
            }
            else if (evt.getSource() == botonIncrementarEscalado) {
                VentanaPrincipal.this.botonIncrementarEscaladoActionPerformed(evt);
            }
            else if (evt.getSource() == botonReducirEscalado) {
                VentanaPrincipal.this.botonReducirEscaladoActionPerformed(evt);
            }
            else if (evt.getSource() == botonBandas) {
                VentanaPrincipal.this.botonBandasActionPerformed(evt);
            }
            else if (evt.getSource() == comboBoxEspacioColor) {
                VentanaPrincipal.this.comboBoxEspacioColorActionPerformed(evt);
            }
            else if (evt.getSource() == botonCombinar) {
                VentanaPrincipal.this.botonCombinarActionPerformed(evt);
            }
            else if (evt.getSource() == botonTintado) {
                VentanaPrincipal.this.botonTintadoActionPerformed(evt);
            }
            else if (evt.getSource() == botonSepia) {
                VentanaPrincipal.this.botonSepiaActionPerformed(evt);
            }
            else if (evt.getSource() == botonEcualizacion) {
                VentanaPrincipal.this.botonEcualizacionActionPerformed(evt);
            }
            else if (evt.getSource() == botonFiltroRojo) {
                VentanaPrincipal.this.botonFiltroRojoActionPerformed(evt);
            }
            else if (evt.getSource() == botonC1) {
                VentanaPrincipal.this.botonC1ActionPerformed(evt);
            }
            else if (evt.getSource() == botonC2) {
                VentanaPrincipal.this.botonC2ActionPerformed(evt);
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
            else if (evt.getSource() == menuRescaleOp) {
                VentanaPrincipal.this.menuRescaleOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuConvolveOp) {
                VentanaPrincipal.this.menuConvolveOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuCometa) {
                VentanaPrincipal.this.menuCometaActionPerformed(evt);
            }
            else if (evt.getSource() == menuAffineTransformOp) {
                VentanaPrincipal.this.menuAffineTransformOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuLookupOp) {
                VentanaPrincipal.this.menuLookupOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuLookupOpReto) {
                VentanaPrincipal.this.menuLookupOpRetoActionPerformed(evt);
            }
            else if (evt.getSource() == menuBandCombineOp) {
                VentanaPrincipal.this.menuBandCombineOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuColorConvertOp) {
                VentanaPrincipal.this.menuColorConvertOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuOperadorPosterizarOp) {
                VentanaPrincipal.this.menuOperadorPosterizarOpActionPerformed(evt);
            }
            else if (evt.getSource() == menuOperadorCambioColor) {
                VentanaPrincipal.this.menuOperadorCambioColorActionPerformed(evt);
            }
        }

        public void focusGained(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == botonBrillo) {
                VentanaPrincipal.this.botonBrilloFocusGained(evt);
            }
            else if (evt.getSource() == botonContraste) {
                VentanaPrincipal.this.botonContrasteFocusGained(evt);
            }
            else if (evt.getSource() == botonCometa) {
                VentanaPrincipal.this.botonCometaFocusGained(evt);
            }
            else if (evt.getSource() == botonRotar) {
                VentanaPrincipal.this.botonRotarFocusGained(evt);
            }
            else if (evt.getSource() == botonReto) {
                VentanaPrincipal.this.botonRetoFocusGained(evt);
            }
            else if (evt.getSource() == botonTintadoSlider) {
                VentanaPrincipal.this.botonTintadoSliderFocusGained(evt);
            }
            else if (evt.getSource() == botonFiltroRojoSlider) {
                VentanaPrincipal.this.botonFiltroRojoSliderFocusGained(evt);
            }
            else if (evt.getSource() == botonPosterizacion) {
                VentanaPrincipal.this.botonPosterizacionFocusGained(evt);
            }
            else if (evt.getSource() == botonCambioColor) {
                VentanaPrincipal.this.botonCambioColorFocusGained(evt);
            }
        }

        public void focusLost(java.awt.event.FocusEvent evt) {
            if (evt.getSource() == botonBrillo) {
                VentanaPrincipal.this.botonBrilloFocusLost(evt);
            }
            else if (evt.getSource() == botonContraste) {
                VentanaPrincipal.this.botonContrasteFocusLost(evt);
            }
            else if (evt.getSource() == botonCometa) {
                VentanaPrincipal.this.botonCometaFocusLost(evt);
            }
            else if (evt.getSource() == botonRotar) {
                VentanaPrincipal.this.botonRotarFocusLost(evt);
            }
            else if (evt.getSource() == botonReto) {
                VentanaPrincipal.this.botonRetoFocusLost(evt);
            }
            else if (evt.getSource() == botonTintadoSlider) {
                VentanaPrincipal.this.botonTintadoSliderFocusLost(evt);
            }
            else if (evt.getSource() == botonFiltroRojoSlider) {
                VentanaPrincipal.this.botonFiltroRojoSliderFocusLost(evt);
            }
            else if (evt.getSource() == botonPosterizacion) {
                VentanaPrincipal.this.botonPosterizacionFocusLost(evt);
            }
            else if (evt.getSource() == botonCambioColor) {
                VentanaPrincipal.this.botonCambioColorFocusLost(evt);
            }
        }

        public void mouseDragged(java.awt.event.MouseEvent evt) {
        }

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == labelPosicionRGB) {
                VentanaPrincipal.this.labelPosicionRGBMouseMoved(evt);
            }
            else if (evt.getSource() == labelPosicionRaton) {
                VentanaPrincipal.this.labelPosicionRatonMouseMoved(evt);
            }
        }

        public void stateChanged(javax.swing.event.ChangeEvent evt) {
            if (evt.getSource() == botonGrosor) {
                VentanaPrincipal.this.botonGrosorStateChanged(evt);
            }
            else if (evt.getSource() == botonBrillo) {
                VentanaPrincipal.this.botonBrilloStateChanged(evt);
            }
            else if (evt.getSource() == botonContraste) {
                VentanaPrincipal.this.botonContrasteStateChanged(evt);
            }
            else if (evt.getSource() == botonCometa) {
                VentanaPrincipal.this.botonCometaStateChanged(evt);
            }
            else if (evt.getSource() == botonRotar) {
                VentanaPrincipal.this.botonRotarStateChanged(evt);
            }
            else if (evt.getSource() == botonReto) {
                VentanaPrincipal.this.botonRetoStateChanged(evt);
            }
            else if (evt.getSource() == botonTintadoSlider) {
                VentanaPrincipal.this.botonTintadoSliderStateChanged(evt);
            }
            else if (evt.getSource() == botonFiltroRojoSlider) {
                VentanaPrincipal.this.botonFiltroRojoSliderStateChanged(evt);
            }
            else if (evt.getSource() == botonPosterizacion) {
                VentanaPrincipal.this.botonPosterizacionStateChanged(evt);
            }
            else if (evt.getSource() == botonCambioColor) {
                VentanaPrincipal.this.botonCambioColorStateChanged(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Obtiene el lienzo seleccionado en la ventana interna actual del
     * escritorio.
     *
     * @return El objeto Lienzo seleccionado, o null si no hay ninguna ventana
     * interna seleccionada o no tiene un lienzo.
     */
    private Lienzo getSelectedLienzo() {
        VentanaInterna vi;
        vi = (VentanaInterna) escritorio.getSelectedFrame();
        return vi != null ? vi.getLienzo() : null;
    }

    /**
     * Deselecciona todas las figuras en el lienzo actual. Si el lienzo está en
     * estado de movimiento, deselecciona todas las figuras. Este método
     * actualiza la representación visual del lienzo después de la deselección.
     */
    private void deseleccionar() {
        Lienzo lienzo = getSelectedLienzo();
        //lienzo.addLienzoListener(manejador);

        if (lienzo != null) {
            System.out.println("Deseleccionar, Estado Lienzo Mover:" + lienzo.isMover());
            for (MiForma s : lienzo.getListaFiguras()) {
                if (lienzo.isMover()) {
                    if (s != null) {
                        //System.out.println("Hola");
                        s.getAtributos().setSeleccionado(false);
                        lienzo.repaint(); // lienzo repaint
                    }
                }
            }
        }
    }

    /**
     * Se realiza un volcado de las imagenes seleccionadas
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void menuVolcadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuVolcadoActionPerformed

        this.botonVolcarImagenActionPerformed(evt);
    }//GEN-LAST:event_menuVolcadoActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona la opción de menú
     * "Abrir". Abre un diálogo de selección de archivos para elegir una imagen,
     * y muestra la imagen seleccionada en una nueva ventana interna.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void menuAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAbrirActionPerformed
        String proyectoRuta = System.getProperty("user.dir");
        File proyectoDirectorio = new File(proyectoRuta);
        String directorioPadre = proyectoDirectorio.getParent();
        JFileChooser dlg = new JFileChooser(directorioPadre);

        int resp = dlg.showOpenDialog(this);
        if (resp == JFileChooser.APPROVE_OPTION) {

            try {
                //File f = dlg.getSelectedFile();

                File f = new File(dlg.getSelectedFile().getAbsolutePath()) {
                    @Override
                    public String toString() {
                        return this.getName();
                    }
                };

                String extension = getFileExtension(f);

                // parte del audio trabajo con wav o au, si no supongo que es una imagen
                if (extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("au")) {
                    this.botonListaReproduccion.addItem(f);
                    this.botonListaReproduccion.setSelectedItem(f);
                } else {
                    // parte de la imagen
                    BufferedImage img = ImageIO.read(f);
                    VentanaInterna vi = new VentanaInterna();
                    Graphics2D g2d = img.createGraphics();

                    //stroke
                    Stroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0);
                    g2d.setStroke(stroke);
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(0, img.getHeight() - 1, img.getWidth() - 1, img.getHeight() - 1); // Línea inferior
                    g2d.drawLine(img.getWidth() - 1, 0, img.getWidth() - 1, img.getHeight() - 1); // Línea derecha // Línea izquierda

                    vi.getLienzo().setImagen(img);
                    this.escritorio.add(vi);
                    vi.setTitle(f.getName());
                    vi.setVisible(true);
                    vi.addInternalFrameListener(new ManejadorVentanaInterna());
                }
            } catch (Exception ex) {
                System.err.println("Error al leer la imagen");
            }

        }

        System.out.println("Abrir Imagen");

    }//GEN-LAST:event_menuAbrirActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para dibujar
     * líneas. Desselecciona todas las figuras en el lienzo seleccionado y
     * configura la forma de dibujo en el lienzo como una línea. Además, imprime
     * un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLineaActionPerformed

        Lienzo lienzo = getSelectedLienzo();

        if (lienzo != null) {
            deseleccionar();

            if (!botonMover.isSelected()) {
                lienzo.setMover(false);
            }

            lienzo.setForma(sm.jltr.graficos.Forma.LINEA);

            System.out.println("Forma Linea");
            this.BarraEstado.setText("Linea");
        }

    }//GEN-LAST:event_botonLineaActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para dibujar
     * rectángulos. Deselecciona todas las figuras en el lienzo seleccionado y
     * configura la forma de dibujo en el lienzo como un rectángulo. Además,
     * imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonRectanguloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRectanguloActionPerformed
        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {
            deseleccionar();

            if (!botonMover.isSelected()) {
                lienzo.setMover(false);
            }

            // if(lienzo != null)
            lienzo.setForma(sm.jltr.graficos.Forma.RECTANGULO);

            System.out.println("Forma Rectangulo");
            this.BarraEstado.setText("Rectangulo");
        }

    }//GEN-LAST:event_botonRectanguloActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para dibujar
     * óvalos. Deselecciona todas las figuras en el lienzo seleccionado y
     * configura la forma de dibujo en el lienzo como una elipse. Además,
     * imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonOvaloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOvaloActionPerformed
        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {
            deseleccionar();

            if (!botonMover.isSelected()) {
                lienzo.setMover(false);
            }

            lienzo.setForma(sm.jltr.graficos.Forma.ELIPSE);

            System.out.println("Forma Elipse");
            this.BarraEstado.setText("Elipse");
        }

    }//GEN-LAST:event_botonOvaloActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona la opción de menú
     * "Guardar". Permite al usuario guardar la imagen mostrada en la ventana
     * interna seleccionada como un archivo JPG.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void menuGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuGuardarActionPerformed
        String proyectoRuta = System.getProperty("user.dir");
        File proyectoDirectorio = new File(proyectoRuta);
        String directorioPadre = proyectoDirectorio.getParent();

        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getPaintedImage();
            if (img != null) {
                JFileChooser dlg = new JFileChooser(directorioPadre);

                dlg.setFileFilter(new FileNameExtensionFilter("Archivos PNG", "png"));
                dlg.setFileFilter(new FileNameExtensionFilter("Archivos JPEG", "jpg", "jpeg"));
                dlg.setFileFilter(new FileNameExtensionFilter("Archivos BMP", "bmp"));

                int resp = dlg.showSaveDialog(this);
                if (resp == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = dlg.getSelectedFile();

                        String extension = getFileExtension(f);

                        if (!extension.isEmpty()) {
                            ImageIO.write(img, extension, f);
                            vi.setTitle(f.getName());
                        } else {
                            // PNG por defecto
                            ImageIO.write(img, "png", f);
                            vi.setTitle(f.getName() + ".png");
                        }
                    } catch (Exception ex) {
                        System.err.println("Error al guardar la imagen");
                    }
                }
            }
        }
        System.out.println("Guardar Imagen");
    }//GEN-LAST:event_menuGuardarActionPerformed

    /**
     * Obtiene la extensión del archivo proporcionado.
     *
     * @param file El archivo del cual se desea obtener la extensión.
     * @return La extensión del archivo en minúsculas, o una cadena vacía si no
     * tiene extensión.
     */
    private String getFileExtension(File file) {
        String extension = "";
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            extension = fileName.substring(dotIndex + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * Maneja el evento de acción cuando se selecciona el botón para dibujar
     * formas fantasma. Deselecciona todas las figuras en el lienzo seleccionado
     * y configura la forma de dibujo en el lienzo como una forma fantasma.
     * Además, imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonFantasmaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFantasmaActionPerformed
        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {
            deseleccionar();

            if (!botonMover.isSelected()) {
                lienzo.setMover(false);
            }

            lienzo.setForma(sm.jltr.graficos.Forma.FANTASMA);

            System.out.println("Forma Fantasma");
            this.BarraEstado.setText("Fantasma");
        }


    }//GEN-LAST:event_botonFantasmaActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para activar o
     * desactivar el modo de mover figuras. Cambia el estado del modo mover en
     * el lienzo seleccionado y lo refleja en el botón de selección.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonMoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonMoverActionPerformed

        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {

            lienzo.setMover(!lienzo.isMover());
            if (botonMover.isSelected()) {
                lienzo.setMover(true);
            }

        }
        System.out.println("Mover");
        this.BarraEstado.setText("Mover");
    }//GEN-LAST:event_botonMoverActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para elegir un
     * color. Abre un selector de color para que el usuario elija un color, y lo
     * aplica a la forma seleccionada en el lienzo. Además, imprime un mensaje
     * de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColorActionPerformed
        color = JColorChooser.showDialog(this, "Elije un color", Color.RED);

        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {
            lienzo.getAtributosActual().setColor(color);

            for (MiForma s : lienzo.getListaFiguras()) {
                if (s != null && s.getAtributos().isSeleccionado()) {
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
     * Maneja el evento de acción cuando se selecciona el botón para activar o
     * desactivar el relleno de las figuras. Cambia el estado del relleno en la
     * forma seleccionada en el lienzo y lo refleja en todas las figuras
     * seleccionadas. Además, imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonRellenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRellenarActionPerformed

        Lienzo lienzo = getSelectedLienzo();

        if (lienzo != null) {
            boolean RellenarActual = lienzo.getAtributosActual().getRelleno();
            lienzo.getAtributosActual().setRelleno(!RellenarActual);
            //System.out.println("Rellenar: " + lienzo.getAtributosActual().getRelleno() );

            for (MiForma s : lienzo.getListaFiguras()) {
                if (s != null && s.getAtributos().isSeleccionado()) {
                    // actualizar las variables 
                    s.getAtributos().setRelleno(lienzo.getAtributosActual().getRelleno());
                    this.repaint();
                }
            }
        }
        System.out.println("Rellenar ");
    }//GEN-LAST:event_botonRellenarActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para activar o
     * desactivar la transparencia de las figuras. Cambia el estado de la
     * transparencia en la forma seleccionada en el lienzo y lo refleja en todas
     * las figuras seleccionadas. Además, imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonTransparenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTransparenciaActionPerformed

        Lienzo lienzo = getSelectedLienzo();

        if (lienzo != null) {

            lienzo.getAtributosActual().setTransparencia(!lienzo.getAtributosActual().isTransparencia());
            System.out.println("Transparencia: " + lienzo.getAtributosActual().isTransparencia());

            for (MiForma s : lienzo.getListaFiguras()) {
                if (s != null && s.getAtributos().isSeleccionado()) {
                    // actualizar las variables 
                    s.getAtributos().setTransparencia(lienzo.getAtributosActual().isTransparencia());
                    this.repaint();
                }
            }

        }
        System.out.println("Transparencia");

    }//GEN-LAST:event_botonTransparenciaActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para activar o
     * desactivar el alisado de las líneas. Cambia el estado del alisado en la
     * forma seleccionada en el lienzo y lo refleja en todas las figuras
     * seleccionadas. Además, imprime un mensaje de depuración.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonAlisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAlisarActionPerformed

        Lienzo lienzo = getSelectedLienzo();
        if (lienzo != null) {
            lienzo.getAtributosActual().setAlisado(!lienzo.getAtributosActual().isAlisado());
            System.out.println("Alisado: " + lienzo.getAtributosActual().isAlisado());

            for (MiForma s : lienzo.getListaFiguras()) {
                if (s != null && s.getAtributos().isSeleccionado()) {
                    // actualizar las variables 
                    s.getAtributos().setAlisado(lienzo.getAtributosActual().isAlisado());
                    this.repaint();
                }
            }

        }
        System.out.println("Alisar");

    }//GEN-LAST:event_botonAlisarActionPerformed

    /**
     * Maneja el evento de cambio de estado cuando se ajusta el valor del grosor
     * de la línea. Actualiza el grosor de la línea en la forma seleccionada en
     * el lienzo y lo refleja en todas las figuras seleccionadas. Además,
     * imprime un mensaje de depuración.
     *
     * @param evt El evento de cambio de estado que desencadenó este método.
     */
    private void botonGrosorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonGrosorStateChanged
        Stroke st = new BasicStroke((Integer) this.botonGrosor.getValue());

        Lienzo lienzo = getSelectedLienzo();

        if (lienzo != null) {
            lienzo.getAtributosActual().setStroke(st);

            for (MiForma s : lienzo.getListaFiguras()) {
                if (s != null && s.getAtributos().isSeleccionado()) {
                    // actualizar las variables 
                    s.getAtributos().setStroke(st);
                    this.repaint();
                }
            }
        }
        System.out.println("Grosor");
    }//GEN-LAST:event_botonGrosorStateChanged

    /**
     * Maneja el evento de acción cuando se selecciona la opción de menú
     * "Nuevo". Crea una nueva ventana interna y la agrega al escritorio, luego
     * inicializa una imagen en blanco con un borde negro discontinuo y la
     * establece como imagen de fondo en el lienzo de la ventana interna.
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

        // Cambiar RBG a TYPE_INT_ARGB  
        //imagen = vi.getLienzo().getPaintedImage();
        imagen = new BufferedImage(vi.getWidth(), vi.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2d = imagen.createGraphics();

        // Dibujar el borde negro discontinuo
        Stroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0);
        g2d.setStroke(stroke);
        g2d.setColor(Color.BLACK);
        g2d.drawLine(0, imagen.getHeight() - 1, imagen.getWidth() - 1, imagen.getHeight() - 1); // Línea inferior
        g2d.drawLine(imagen.getWidth() - 1, 0, imagen.getWidth() - 1, imagen.getHeight() - 1); // Línea derecha // Línea izquierda

        //Establecer el área de recorte del lienzo para que coincida con el tamaño de la imagen
        vi.getLienzo().setImagen(imagen);

        // lsitener
        vi.getLienzo().addLienzoListener(manejador);

        System.out.println("Nueva Imagen");
    }//GEN-LAST:event_menuNuevoActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para crear una
     * nueva imagen. Llama al método de manejo de eventos correspondiente para
     * la opción de menú "Nuevo".
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonNuevaImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaImagenActionPerformed
        this.menuNuevoActionPerformed(evt);
    }//GEN-LAST:event_botonNuevaImagenActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para abrir una
     * imagen. Llama al método de manejo de eventos correspondiente para la
     * opción de menú "Abrir".
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonAbrirImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAbrirImagenActionPerformed
        this.menuAbrirActionPerformed(evt);
    }//GEN-LAST:event_botonAbrirImagenActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para guardar una
     * imagen. Llama al método de manejo de eventos correspondiente para la
     * opción de menú "Guardar".
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonGuardarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarImagenActionPerformed
        this.menuGuardarActionPerformed(evt);
    }//GEN-LAST:event_botonGuardarImagenActionPerformed

    /**
     * Maneja el evento de acción cuando se selecciona el botón para volcar una
     * imagen sobre una forma seleccionada. Obtiene la ventana interna
     * seleccionada y busca formas seleccionadas en su lienzo. Luego, crea una
     * nueva imagen del tamaño de la forma seleccionada, dibuja la forma sobre
     * la imagen y la remueve del lienzo. Finalmente, repinta la ventana interna
     * para reflejar los cambios.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonVolcarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonVolcarImagenActionPerformed

        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();

        if (vi != null) {
            BufferedImage imgout = vi.getLienzo().getPaintedImageSeleccionada();
            vi.getLienzo().setImagen(imgout);
            vi.repaint();
            System.out.println("Volcado Imagen");
        }


    }//GEN-LAST:event_botonVolcarImagenActionPerformed

    /**
     * Maneja el cambio de estado del botón Cometa. Este método se activa cuando
     * cambia el valor del botón Cometa y aplica un filtro de emborronamiento
     * horizontal a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de cambio que activó este método.
     */
    private void botonCometaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonCometaStateChanged
        System.out.println("Valor Cometa (0.1:2):" + botonCometa.getValue());
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null && imgFuente != null) {
            try {

                // Definir un filtro de emborronamiento horizontal
                // falta aplicar bandas a los lados + slider que deshaga el cambio comprobar
                int intensidad = botonCometa.getValue();

                Kernel k = cometaKernel(intensidad);

                ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null); // edge op
                BufferedImage imgdest = cop.filter(imgFuente, vi.getLienzo().getImagen()); // aqui problema
                vi.getLienzo().setImagen(imgdest);
                vi.getLienzo().repaint();

            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }


    }//GEN-LAST:event_botonCometaStateChanged

    /**
     * Crea un kernel para aplicar un filtro de emborronamiento horizontal tipo
     * cometa.
     *
     * @param valor2 El valor utilizado para determinar el tamaño del kernel y
     * los factores de emborronamiento.
     * @return Un kernel para aplicar un filtro de emborronamiento horizontal
     * tipo cometa.
     */
    private Kernel cometaKernel(int valor2) {
        int tamaño = valor2;
        float[] filtro = new float[tamaño];
        int centro = tamaño / 2;

        // Valor central
        float valorCentral = 1.0f; // Set the central value to 1.0f for normalization purposes
        filtro[centro] = valorCentral;

        // Distribuir valores a la derecha e izquierda
        float suma = valorCentral;
        float decremento = 0.1f; // Adjust this value to control how quickly values decrease

        // Right values
        for (int i = centro + 1; i < tamaño; i++) {
            float valor = filtro[i - 1] - decremento;
            if (valor < 0) {
                valor = 0;
            }
            filtro[i] = valor;
            suma += valor;
        }

        // Left values
        for (int i = centro - 1; i >= 0; i--) {
            float valor = filtro[i + 1] - decremento;
            if (valor < 0) {
                valor = 0;
            }
            filtro[i] = valor;
            suma += valor;
        }

        // Normalizar
        for (int i = 0; i < tamaño; i++) {
            filtro[i] /= suma;
        }

        // Imprimir los valores del filtro para depuración
        System.out.println("Tamaño filtro: " + tamaño);
        for (int i = 0; i < filtro.length; i++) {
            System.out.print(filtro[i] + " ");
        }
        System.out.println(); // Añadir salto de línea después de imprimir los valores

        // Crear y devolver el Kernel
        return new Kernel(tamaño, 1, filtro);
    }

    /**
     * Maneja el cambio de estado del botón Brillo. Este método se activa cuando
     * cambia el valor del botón Brillo y aplica un ajuste de brillo a la imagen
     * en la ventana interna seleccionada.
     *
     * @param evt El evento de cambio que activó este método.
     */
    private void botonBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonBrilloStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        System.out.println("Valor Brillo (-255:255):" + botonBrillo.getValue());

        if (vi != null && imgFuente != null) {
            // get value del slider te da el valor del slider
            try {
                RescaleOp rop = new RescaleOp(1.0F, this.botonBrillo.getValue(), null);
                rop.filter(imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }
    }//GEN-LAST:event_botonBrilloStateChanged

    /**
     * Maneja el cambio de estado del botón Contraste. Este método se activa
     * cuando cambia el valor del botón Contraste y aplica un ajuste de
     * contraste a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de cambio que activó este método.
     */
    private void botonContrasteStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonContrasteStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        System.out.println("Valor Contraste: (0.1:2): " + (float) botonContraste.getValue() / 10.0);

        if (vi != null && imgFuente != null) {
            // get value del slider te da el valor del slider
            try {
                RescaleOp rop = new RescaleOp((float) (this.botonContraste.getValue() / 10.0), 0, null);
                rop.filter(imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }
    }//GEN-LAST:event_botonContrasteStateChanged

    /**
     * Maneja la acción del menú RescaleOp. Este método se activa cuando se
     * selecciona la opción del menú RescaleOp y aplica un ajuste de brillo a la
     * imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuRescaleOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRescaleOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {
                    // para brillo y contraste jugamos con a y b
                    // a = 1.0F
                    // b = 100.0F
                    // variando B vario la oscuridad
                    RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    rop.filter(img, img);
                    vi.getLienzo().repaint();

                    // para convolve op  img != img, funete destino distinto por lo que hay que hacer el segundo metodo 
                    // segundo metodo 
                    //RescaleOp rop = new RescaleOp(1.0F, 100.0F, null);
                    //BufferedImage imgdest = rop.filter(img, null);
                    //vi.getLienzo().setImagen(imgdest);
                    //vi.getLienzo().repaint();
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuRescaleOpActionPerformed

    /**
     * Maneja la acción del menú ConvolveOp. Este método se activa cuando se
     * selecciona la opción del menú ConvolveOp y aplica un filtro de
     * convolución a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuConvolveOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuConvolveOpActionPerformed
        // convolveOP puedo hacer muchas cosas
        // aplicar mascara suavizado(binomial), realce , fronteras(laplaciano) tienes que crear un filtro antes, una matriz

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // filtro suavizado, 1/9, matrix 3x3 , 1/9
                    float filtro[] = {0.1f, 0.1f, 0.1f, 0.1f, 0.2f, 0.1f, 0.1f, 0.1f, 0.1f};

                    // filtro es una matriz 3x3
                    Kernel k = new Kernel(3, 3, filtro);
                    ConvolveOp cop = new ConvolveOp(k);
                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuConvolveOpActionPerformed

    /**
     * Maneja el evento de enfoque ganado del botón Brillo. Este método se
     * activa cuando el botón Brillo obtiene el foco y realiza una
     * inicialización de la imagen fuente.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonBrilloFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonBrilloFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }

    }//GEN-LAST:event_botonBrilloFocusGained

    /**
     * Maneja el evento de enfoque perdido del botón Brillo. Este método se
     * activa cuando el botón Brillo pierde el foco y realiza una limpieza de la
     * imagen fuente y ajusta el valor del botón Brillo a cero.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonBrilloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonBrilloFocusLost
        imgFuente = null;
        this.botonBrillo.setValue(0);
        repaint();
    }//GEN-LAST:event_botonBrilloFocusLost

    /**
     * Maneja el evento de enfoque ganado del botón Contraste. Este método se
     * activa cuando el botón Contraste obtiene el foco y realiza una
     * inicialización de la imagen fuente.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonContrasteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonContrasteFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonContrasteFocusGained

    /**
     * Maneja el evento de enfoque perdido del botón Contraste. Este método se
     * activa cuando el botón Contraste pierde el foco y realiza una limpieza de
     * la imagen fuente y ajusta el valor del botón Contraste a 10.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonContrasteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonContrasteFocusLost
        imgFuente = null;
        this.botonContraste.setValue(10);
        repaint();
    }//GEN-LAST:event_botonContrasteFocusLost

    /**
     * Maneja la acción del ComboBox de filtros. Este método se activa cuando se
     * selecciona un filtro en el ComboBox y aplica el filtro seleccionado a la
     * imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void ComboBoxFiltrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxFiltrosActionPerformed

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();

            int seleccion = this.ComboBoxFiltros.getSelectedIndex();
            Kernel k = this.getKernel(seleccion);

            if (img != null && k != null) {
                try {

                    ConvolveOp cop = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
                    BufferedImage imgdest = cop.filter(img, null);
                    vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_ComboBoxFiltrosActionPerformed

    /**
     * Maneja el evento de enfoque perdido del botón Cometa. Este método se
     * activa cuando el botón Cometa pierde el foco y realiza una limpieza de la
     * imagen fuente y ajusta el valor del botón Cometa a 5.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonCometaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonCometaFocusLost
        imgFuente = null;
        this.botonCometa.setValue(1);
        repaint();
    }//GEN-LAST:event_botonCometaFocusLost

    /**
     * Maneja el evento de enfoque ganado del botón Cometa. Este método se
     * activa cuando el botón Cometa obtiene el foco y realiza una
     * inicialización de la imagen fuente.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonCometaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonCometaFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }

    }//GEN-LAST:event_botonCometaFocusGained

    /**
     * Maneja la acción del menú Cometa. Este método se activa cuando se
     * selecciona la opción del menú Cometa y aplica un filtro de
     * emborronamiento horizontal a la imagen en la ventana interna
     * seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuCometaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCometaActionPerformed

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // Definir un filtro de emborronamiento horizontal
                    // falta aplicar las bandas laterales
                    //float[] filtro = {0.05f, 0.1f, 0.2f, 0.3f, 0.2f, 0.1f, 0.05f}; 
                    //float[] filtro = {0.0f, 0.0f, 0.0f, 0.3f, 0.2f, 0.1f, 0.05f}; 
                    //float[] filtro = {0.0f, 0.0f, 0.0f, 0.05f, 0.15f, 0.3f, 0.5f}; 
                    float[] filtro = {0f, 0f, 0f, 0.5f, 0.3f, 0.15f, 0.05f};

                    Kernel k = new Kernel(filtro.length, 1, filtro);
                    ConvolveOp cop = new ConvolveOp(k);
                    BufferedImage imgdest = cop.filter(img, null);

                    vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuCometaActionPerformed

    /*
        Funcion exponencial decreciente, interactua con los valores de  pixeles altos
        f(x)=T+(x−T)×factor de atenuacion 
    
        x es el valor del píxel.
        T es el umbral que delimita las zonas claras de las zonas oscuras.
        El "factor de atenuación" es un valor entre 0 y 1 que controla la intensidad de la atenuación.
    
        Esta función es lineal para valores de píxeles por debajo del umbral TT, lo que significa que las zonas oscuras se mantienen iguales. Sin embargo, para valores de píxeles por encima de TT, la función aplica una atenuación exponencial decreciente, lo que resulta en un oscurecimiento de las zonas claras.
     */
    
    /**
     * Maneja la acción del botón "Oscurecer Zonas Claras". Este método se
     * activa cuando se presiona el botón "Oscurecer Zonas Claras" y aplica un
     * efecto de oscurecimiento a las zonas claras de la imagen.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonOscurecerZonasClarasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonOscurecerZonasClarasActionPerformed
        int umbral = 128;
        double gamma = 2.0; // potencia 
        double k = 1.2; // Factor de atenuación

        byte funcionT[] = new byte[256];

        for (int i = 0; i < 256; i++) {
            if (i < umbral) {
                funcionT[i] = (byte) i;
            } else {
                // Calcular f(x-128)
                double normalizado = (i - umbral) / (double) (255 - umbral); // divido por el mismo
                double transformacion = Math.pow(normalizado, gamma) * (255 - umbral) ;

                // Aplicar el factor de escala y el desplazamiento
                int nuevoValor = (int) (k * transformacion + umbral);
                if (nuevoValor > 255) {
                    nuevoValor = 255;
                }
                funcionT[i] = (byte) nuevoValor;
            }
        }

        // Crear la tabla de búsqueda
        LookupTable tabla = new ByteLookupTable(0, funcionT);

        // Aplicar la transformación a la imagen
        this.aplicarLookUp(tabla);


    }//GEN-LAST:event_botonOscurecerZonasClarasActionPerformed

    /**
     * Maneja el cambio de estado del botón "Rotar". Este método se activa
     * cuando se modifica el valor del botón "Rotar" y realiza una rotación de
     * la imagen según el valor seleccionado.
     *
     * @param evt El evento de cambio de estado que activó este método.
     */
    private void botonRotarStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonRotarStateChanged
        // TODO add your handling code here:
        int radianes = this.botonRotar.getValue();
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            //BufferedImage img = vi.getLienzo().getImagen();
            if (this.imgFuente != null) {
                this.rotarFuncion(radianes, this.imgFuente, vi);
            }
        }

    }//GEN-LAST:event_botonRotarStateChanged

    /**
     * Maneja el evento de enfoque ganado del botón "Rotar". Este método se
     * activa cuando el botón "Rotar" obtiene el foco y establece la imagen
     * fuente como la imagen actual de la ventana interna seleccionada.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonRotarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonRotarFocusGained
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            this.imgFuente = vi.getLienzo().getImagen();
            // filter se aplica sobre la imagen y genera una nueva, no es necesario hacer una copia, se puede hacer la original, si imgdest = img img si habria que hacer una copia
        }
    }//GEN-LAST:event_botonRotarFocusGained

    /**
     * Maneja el evento de enfoque perdido del botón "Rotar". Este método se
     * activa cuando el botón "Rotar" pierde el foco y limpia la imagen fuente y
     * restablece el valor del botón "Rotar" a cero.
     *
     * @param evt El evento de enfoque que activó este método.
     */
    private void botonRotarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonRotarFocusLost
        // TODO add your handling code here:
        this.imgFuente = null;
        this.botonRotar.setValue(0);
    }//GEN-LAST:event_botonRotarFocusLost

    /**
     * Maneja la acción del menú AffineTransformOp. Este método se activa cuando
     * se selecciona la opción del menú AffineTransformOp y aplica una
     * transformación afín a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuAffineTransformOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAffineTransformOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    //hacemos un escalado
                    //AffineTransform at = AffineTransform.getScaleInstance(1.5,1.5);
                    //AffineTransformOp atop = new AffineTransformOp(at,null);
                    //rotacion
                    AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(45), img.getWidth() / 2, img.getHeight() / 2);

                    //mejora interpolacion
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

                    // al segundo es null = creo una imagen, si no la actualizaria
                    // mejorar resultado con otra interpolacion  AffineTransformOp .TYPE_BILINEAR
                    // normalizar es importante
                    // oscurecer raiz, iluminar = potencia y logaritmo, , en el bucle se una una funcion u otra
                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuAffineTransformOpActionPerformed

    /**
     * Maneja la acción del menú LookupOp. Este método se activa cuando se
     * selecciona la opción del menú LookupOp y aplica una operación de consulta
     * a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuLookupOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLookupOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    byte funcionT[] = new byte[256];
                    // recorro todos los valores y valoro la funcion en cada uno
                    // rango salida esta entre 0 y 255, si no lo esta antes de entrar en el bucle calcular constante normalizacion
                    for (int x = 0; x < 256; x++) {
                        funcionT[x] = (byte) (255 - x); // Negativo
                    }
                    LookupTable tabla = new ByteLookupTable(0, funcionT);
                    LookupOp lop = new LookupOp(tabla, null);

                    //BufferedImage imgdest = lop.filter(img, null);
                    //sustituir la imagen anteorio 
                    lop.filter(img, img);

                    // no es necesario
                    //vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuLookupOpActionPerformed

    /**
     * Maneja la acción del botón "Negativo". Este método se activa cuando se
     * presiona el botón "Negativo" y aplica el efecto de negativo a la imagen
     * en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonNegativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNegativoActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    byte funcionT[] = new byte[256];
                    // recorro todos los valores y valoro la funcion en cada uno
                    // rango salida esta entre 0 y 255, si no lo esta antes de entrar en el bucle calcular constante normalizacion
                    for (int x = 0; x < 256; x++) {
                        funcionT[x] = (byte) (255 - x); // Negativo
                    }
                    LookupTable tabla = new ByteLookupTable(0, funcionT);
                    LookupOp lop = new LookupOp(tabla, null);

                    //BufferedImage imgdest = lop.filter(img, null);
                    //sustituir la imagen anteorio 
                    lop.filter(img, img);

                    // no es necesario
                    //vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonNegativoActionPerformed

    /**
     * Maneja la acción del botón "Contraste Normal". Este método se activa
     * cuando se presiona el botón "Contraste Normal" y aplica una función de
     * contraste normal a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonContrasteNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContrasteNormalActionPerformed

        // lookup con funcion S
        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_SFUNCION);
        this.aplicarLookUp(tabla);

    }//GEN-LAST:event_botonContrasteNormalActionPerformed

    /**
     * Maneja la acción del botón "Contraste Iluminado". Este método se activa
     * cuando se presiona el botón "Contraste Iluminado" y aplica una función de
     * contraste iluminado a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonContrasteIluminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContrasteIluminadoActionPerformed

        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_ROOT);
        this.aplicarLookUp(tabla);


    }//GEN-LAST:event_botonContrasteIluminadoActionPerformed

    /**
     * Aplica una operación de consulta a la imagen en la ventana interna
     * seleccionada.
     *
     * @param table La tabla de consulta que define la transformación a aplicar
     * a los píxeles de la imagen.
     */
    private void aplicarLookUp(LookupTable table) {
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_ROOT);
                    LookupOp lop = new LookupOp(table, null);

                    //BufferedImage imgdest = lop.filter(img, null);
                    //sustituir la imagen anteorio 
                    lop.filter(img, img);

                    // no es necesario
                    //vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Maneja la acción del botón "Contraste Oscurecido". Este método se activa
     * cuando se presiona el botón "Contraste Oscurecido" y aplica una función
     * de contraste oscurecido a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonContrasteOscurecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContrasteOscurecidoActionPerformed
        LookupTable tabla = LookupTableProducer.createLookupTable(LookupTableProducer.TYPE_POWER);
        this.aplicarLookUp(tabla);
    }//GEN-LAST:event_botonContrasteOscurecidoActionPerformed

    /**
     * Maneja la acción del botón "Rotación 180°". Este método se activa cuando
     * se presiona el botón "Rotación 180°" y rota la imagen en la ventana
     * interna seleccionada 180 grados.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonRotacion180ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRotacion180ActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                this.rotarFuncion(180, img, vi);
            }
        }


    }//GEN-LAST:event_botonRotacion180ActionPerformed

    /**
     * Maneja la acción del botón "Incrementar Escalado". Este método se activa
     * cuando se presiona el botón "Incrementar Escalado" y escala la imagen en
     * la ventana interna seleccionada por un factor de 1.5 veces.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonIncrementarEscaladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIncrementarEscaladoActionPerformed
        double valor = 1.5;
        this.escalarFuncion(valor);
    }//GEN-LAST:event_botonIncrementarEscaladoActionPerformed

    /**
     * Maneja la acción del botón "Reducir Escalado". Este método se activa
     * cuando se presiona el botón "Reducir Escalado" y escala la imagen en la
     * ventana interna seleccionada por un factor de 0.5 veces.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonReducirEscaladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReducirEscaladoActionPerformed
        double valor = 0.5;
        this.escalarFuncion(valor);
    }//GEN-LAST:event_botonReducirEscaladoActionPerformed

    /*
        transformacion lineal con un punto de control
        T[0,255] -> T [0,255]
        funcion recta si no me equivoco
    
        T(x,a) { (a*x)/128 si x < 128
                 ((255-a)(x-128))/127 + a si x >= 128
    
        a es pequeño = imagen mas oscura , x < 128 los valores de los pixeles se multiplican por un valor menor que 1, reduce intensidad
        a es grande, cerca 255, imagen más clara , x>=128 pixeles se multiplican por un valor mayor que 1
        a igual a 128, no hay cambios significativos,brillo original.
     */
    /**
     * Maneja la acción del menú "LookupOp Reto". Este método se activa cuando
     * se selecciona la opción "LookupOp Reto" del menú y aplica una
     * transformación a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuLookupOpRetoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLookupOpRetoActionPerformed
        // TODO add your handling code here:

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    int a = 50;

                    byte lt[] = new byte[256];
                    for (int l = 0; l < 128; l++) {
                        lt[l] = (byte) (((float) (a * l)) / 128.0f);
                    }

                    for (int l = 128; l < 256; l++) {
                        lt[l] = (byte) (((255.0f - a) * (l - 128.0f)) / (float) (127.0f) + a);
                    }

                    ByteLookupTable tabla = new ByteLookupTable(0, lt);
                    LookupOp lop = new LookupOp(tabla, null);

                    //BufferedImage imgdest = lop.filter(img, null);
                    //sustituir la imagen anteorio 
                    lop.filter(this.imgFuente, vi.getLienzo().getImagen());

                    // no es necesario
                    //vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }


    }//GEN-LAST:event_menuLookupOpRetoActionPerformed

    /**
     * Maneja el cambio de estado del botón "Reto". Este método se activa cuando
     * se cambia el estado del botón "Reto" y aplica una transformación a la
     * imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de cambio de estado que activó este método.
     */
    private void botonRetoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonRetoStateChanged

        /*
        int radianes = this.botonRotar.getValue();
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            //BufferedImage img = vi.getLienzo().getImagen();
            if (this.imgFuente != null)
                this.rotarFuncion(radianes,this.imgFuente,vi);
        }
         */
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        int a = this.botonReto.getValue();

        if (vi != null) {
            //BufferedImage img = vi.getLienzo().getImagen();
            if (this.imgFuente != null) {
                try {

                    byte lt[] = new byte[256];
                    for (int l = 0; l < 128; l++) {
                        lt[l] = (byte) (((float) (a * l)) / 128.0f);
                    }

                    for (int l = 128; l < 256; l++) {
                        lt[l] = (byte) (((255.0f - a) * (l - 128.0f)) / (float) (127.0f) + a);
                    }

                    ByteLookupTable tabla = new ByteLookupTable(0, lt);
                    LookupOp lop = new LookupOp(tabla, null);

                    //BufferedImage imgdest = lop.filter(img, null);
                    //sustituir la imagen anteorio 
                    lop.filter(this.imgFuente, vi.getLienzo().getImagen());

                    // no es necesario
                    //vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonRetoStateChanged

    /**
     * Maneja el evento de foco ganado del botón "Reto". Este método se activa
     * cuando el botón "Reto" gana el foco y guarda la imagen actual del lienzo
     * de la ventana interna seleccionada para su posterior procesamiento.
     *
     * @param evt El evento de foco ganado que activó este método.
     */
    private void botonRetoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonRetoFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonRetoFocusGained

    /**
     * Maneja el evento de foco perdido del botón "Reto". Este método se activa
     * cuando el botón "Reto" pierde el foco y restablece la variable de imagen
     * fuente a nulo y restablece el valor del botón de rotación a 128.
     *
     * @param evt El evento de foco perdido que activó este método.
     */
    private void botonRetoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonRetoFocusLost
        this.imgFuente = null;
        this.botonRotar.setValue(128);
    }//GEN-LAST:event_botonRetoFocusLost

    /**
     * Maneja la acción del botón "Duplicar". Este método se activa cuando se
     * hace clic en el botón "Duplicar" y crea una nueva ventana interna
     * duplicando la ventana interna seleccionada en el escritorio.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonDuplicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDuplicarActionPerformed

        VentanaInterna vi = (VentanaInterna) escritorio.getSelectedFrame();
        vi.addInternalFrameListener(new ManejadorVentanaInterna());

        if (vi != null) {
            // Duplicar la ventana interna seleccionada
            VentanaInterna viDuplicada = duplicarVentanaInterna(vi);
            // Agregar la ventana interna duplicada al escritorio
            escritorio.add(viDuplicada);
            viDuplicada.setTitle("Copia - " + vi.getTitle());
            viDuplicada.setVisible(true);
        }


    }//GEN-LAST:event_botonDuplicarActionPerformed

    private void labelPosicionRatonMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelPosicionRatonMouseMoved

    }//GEN-LAST:event_labelPosicionRatonMouseMoved

    private Color obtenerColorPosicion(int x, int y) {
        try {
            Robot robot = new Robot();
            Color colorRaton = robot.getPixelColor(x, y);
            return colorRaton;
        } catch (AWTException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * Maneja la acción del menú "BandCombineOp". Este método se activa cuando
     * se selecciona la opción "BandCombineOp" del menú y aplica una operación
     * de combinación de bandas a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuBandCombineOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBandCombineOpActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {
                    float[][] matriz = {{1.0F, 0.0F, 0.0F},
                    {0.0F, 0.0F, 1.0F},
                    {0.0F, 1.0F, 0.0F}};

                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    // se aplica sobre el raster + filter
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_menuBandCombineOpActionPerformed

    /**
     * Maneja la acción del menú "ColorConvertOp". Este método se activa cuando
     * se selecciona la opción "ColorConvertOp" del menú y convierte la imagen
     * en la ventana interna seleccionada a escala de grises.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void menuColorConvertOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuColorConvertOpActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {
                    ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    // al poner la segunda a null hay imagen a salida
                    BufferedImage imgdest = op.filter(img, null);
                    //vi.getLienzo().setImagen(imgdest);

                    // ponerlo en una ventana
                    VentanaInterna viBand = new VentanaInterna();
                    viBand.getLienzo().setImagen(imgdest);
                    escritorio.add(viBand);
                    viBand.setVisible(true);
                    viBand.setTitle(vi.getTitle() + " Gris ");

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_menuColorConvertOpActionPerformed

    /*
        combinación de bandas según la
        siguiente regla: el valor de cada banda se sustituirá por la media de las otras bandas (p.e., en el
        caso de una imagen RGB, la banda roja se sustituirá por la media de las bandas verde y azul, la
        verde por la media de la roja y azul, y la azul por la media de la roja y verde). Para ello, habrá que
        elegir la matriz de combinación adecuada que permita dicha operación, ¿sabes cuál es?

        matriz dde combinacion de componentes de color: para rgb seria la siguiente matriz
        float[][] matriz = {{0.0F, 0.5F, 0.5F},
                            0.5F, 0.0F, 0.5F},
                            {0.5F, 0.5F, 0.0F}};

     */
    /**
     * Maneja la acción del botón "Combinar". Este método se activa cuando se
     * hace clic en el botón "Combinar" y aplica una combinación personalizada
     * de bandas a la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonCombinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCombinarActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {
                    float[][] matriz = {{0.0F, 0.5F, 0.5F},
                    {0.5F, 0.0F, 0.5F},
                    {0.5F, 0.5F, 0.0F}};

                    BandCombineOp bcop = new BandCombineOp(matriz, null);
                    // se aplica sobre el raster + filter
                    bcop.filter(img.getRaster(), img.getRaster());
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_botonCombinarActionPerformed

    /**
     * Maneja la acción del botón "Bandas". Este método se activa cuando se hace
     * clic en el botón "Bandas" y crea ventanas internas para mostrar cada
     * banda de color de la imagen en la ventana interna seleccionada.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void botonBandasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBandasActionPerformed
        // TODO add your handling code here:
        // en vez de usar CS_GRAY usar GreyColorSPace

        // 1. crear una imagen por cada banda
        // 2. crear correspondientes ventanas internas que muestren imagenes 
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {

                // hazlo para un bucle for mejor
                for (int i = 0; i < 3; i++) {
                    BufferedImage imgBand = getImageBand(img, i); // Obtener la banda de color correspondiente

                    VentanaInterna viBand = new VentanaInterna();
                    viBand.getLienzo().setImagen(imgBand);
                    escritorio.add(viBand);
                    viBand.setVisible(true);
                    viBand.setTitle(vi.getTitle() + " Banda " + i);
                }

            }
        }


    }//GEN-LAST:event_botonBandasActionPerformed

    /**
     * Maneja la acción del ComboBox "Espacio de Color". Este método se activa
     * cuando se selecciona un espacio de color en el ComboBox y convierte la
     * imagen en la ventana interna seleccionada al espacio de color
     * correspondiente.
     *
     * @param evt El evento de acción que activó este método.
     */
    private void comboBoxEspacioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxEspacioColorActionPerformed
        // TODO add your handling code here:
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {

                int seleccion = this.comboBoxEspacioColor.getSelectedIndex();
                String nombre = "";

                ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                switch (seleccion) {
                    case 0 -> {
                        cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                        nombre = "sRGB";
                    }
                    case 1 -> {

                        cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                        nombre = "YCC";
                    }
                    case 2 -> {
                        cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                        nombre = "GRAY";
                    }

                }

                // cs tiene que tener un espacio de color a convertir
                try {
                    //ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
                    ColorConvertOp op = new ColorConvertOp(cs, null);
                    BufferedImage imgdest = op.filter(img, null);

                    // crear la ventana interna y añadirle imagen destino
                    VentanaInterna viBand = new VentanaInterna();
                    viBand.getLienzo().setImagen(imgdest);
                    escritorio.add(viBand);
                    viBand.setVisible(true);
                    viBand.setTitle(vi.getTitle() + " " + nombre);

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_comboBoxEspacioColorActionPerformed

    private void botonCambioColorStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonCambioColorStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        System.out.println("Valor CambioColor: (2:20): " + botonCambioColor.getValue());

        if (vi != null && imgFuente != null) {
            try {

                // Usar C1 y C2
                int umbral = botonCambioColor.getValue();
                CambiarTonoOp op = new CambiarTonoOp(c1, c2, umbral);
                op.filter(this.imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();

            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }


    }//GEN-LAST:event_botonCambioColorStateChanged

    private void botonCambioColorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonCambioColorFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonCambioColorFocusGained

    private void botonCambioColorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonCambioColorFocusLost
        this.imgFuente = null;
        this.botonPosterizacion.setValue(2); // a lo minimo
        repaint();
    }//GEN-LAST:event_botonCambioColorFocusLost

    /**
     * Método que se ejecuta cuando se hace clic en el botón "Sepia". Aplica el
     * efecto sepia a la imagen en la ventana interna actual, si está
     * disponible.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonTintadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonTintadoActionPerformed

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // que sea el color del boton 
                    TintOp tintado = new TintOp(color, 0.5f);
                    tintado.filter(img, img);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_botonTintadoActionPerformed

    /**
     * Método que se ejecuta cuando se hace clic en el botón "Sepia". Aplica el
     * efecto sepia a la imagen en la ventana interna actual, si está
     * disponible.
     *
     * @param evt El evento de acción que desencadenó este método.
     */
    private void botonSepiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSepiaActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // que sea el color del boton 
                    SepiaOp sepia = new SepiaOp();
                    sepia.filter(img, img);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_botonSepiaActionPerformed

    /*
        Una vez incorporada esta herramienta a la aplicación, probar a ecualizar una imagen en
        color; ¿notas algún efecto extraño? Una vez hecha la prueba, usar de nuevo la misma imagen y
        convertirla a YCC, extraer sus tres bandas y aplicar la ecualización solo en el canal Y, ¿qué tal
        ahora?
    
            - aumenta el contraste usar la imagenPocoContrastada
     */
    private void botonEcualizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEcualizacionActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // hacia un cambio de espacio de color , luo nuevo es extraer las bandas jugando con el raster
                    // Paso 1
                    // que sea el color del boton 
                    EqualizationOp ecualizacion = new EqualizationOp();
                    ecualizacion.filter(img, img);
                    vi.getLienzo().repaint();
                    // Java por defecto tiende a iluminar en exceso la imagen tras la ecualizacion
                    //ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_PYCC);
                    //ColorConvertOp yccConvert = new ColorConvertOp(cs,null);
                    //BufferedImage yccImage = yccConverter.filter(img,null);


                    /*
                    // 1. Convertir la imagen a YCbCr utilizando YCbCrColorSpace (COnvertir la imagen a YCC)
                    YCbCrColorSpace ycbcrCS = new YCbCrColorSpace();
                    ColorConvertOp yccConverter = new ColorConvertOp(ycbcrCS, null);
                    BufferedImage ycbcrImage = yccConverter.filter(img, null);

                    // 2. Separar las bandas Y, C, Cr
                    BufferedImage[] bandas = extraerTresBandas(ycbcrImage);
                    BufferedImage imgY = bandas[0];
                    BufferedImage imgCb = bandas[1];
                    BufferedImage imgCr = bandas[2];
                    
                    // 3. Aplicar ecualizacion solo para Y
                    EqualizationOp ecualizacionY = new EqualizationOp();
                    ecualizacionY.filter(imgY, imgY);  
                    
                    // 4. volvemos a combinar las bandas
                    BufferedImage imgEqualizedYCC = combinarTresBandas(imgY, imgCb, imgCr);
                    
                    // 5. Convertimos a RGB
                    ColorSpace rgb = ColorSpace.getInstance(ColorSpace.CS_sRGB);                    
                    ColorConvertOp rgbConverter = new ColorConvertOp(ycbcrCS, rgb);
                    BufferedImage imgRGB = rgbConverter.filter(imgEqualizedYCC, null);
                     */
                    // ¿Como paso de ycbcrCS a rgb?
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

        // extraer imagen a YCC, extraer 3 bandas y aplicar la ecualizacion solo en el canal Y
        // 1. convierta a YYC yccImage
        // 2. extraer banda Y

    }//GEN-LAST:event_botonEcualizacionActionPerformed

    // extraer bandas imagen YCC
    public static BufferedImage[] extraerTresBandas(BufferedImage yccImage) {
        // raster es una estructura de datos que representa una cuadricula de pixeles en una imagen
        WritableRaster raster = yccImage.getRaster();
        int width = yccImage.getWidth();
        int height = yccImage.getHeight();

        BufferedImage imgY = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage imgCb = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage imgCr = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] yccValues = new int[3];
                raster.getPixel(x, y, yccValues);

                imgY.getRaster().setPixel(x, y, new int[]{yccValues[0]});
                imgCb.getRaster().setPixel(x, y, new int[]{yccValues[1]});
                imgCr.getRaster().setPixel(x, y, new int[]{yccValues[2]});
            }
        }

        return new BufferedImage[]{imgY, imgCb, imgCr};
    }

    // combinar las tres bandas
    public static BufferedImage combinarTresBandas(BufferedImage imgY, BufferedImage imgCb, BufferedImage imgCr) {
        int width = imgY.getWidth();
        int height = imgY.getHeight();

        BufferedImage imgEqualizedYCC = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        WritableRaster combinedRaster = imgEqualizedYCC.getRaster();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] yccValues = new int[3];
                yccValues[0] = imgY.getRaster().getPixel(x, y, new int[1])[0];
                yccValues[1] = imgCb.getRaster().getPixel(x, y, new int[1])[0];
                yccValues[2] = imgCr.getRaster().getPixel(x, y, new int[1])[0];

                combinedRaster.setPixel(x, y, yccValues);
            }
        }

        return imgEqualizedYCC;
    }

    private void botonPosterizacionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonPosterizacionStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        System.out.println("Valor Posterizar: (2:20): " + botonPosterizacion.getValue());

        if (vi != null && imgFuente != null) {
            try {

                // entre 2 y 20 el valor 
                PosterizarOp op = new PosterizarOp(botonPosterizacion.getValue());
                op.filter(this.imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();

            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }

    }//GEN-LAST:event_botonPosterizacionStateChanged

    private void botonPosterizacionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonPosterizacionFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonPosterizacionFocusGained

    private void botonPosterizacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonPosterizacionFocusLost
        this.imgFuente = null;
        this.botonPosterizacion.setValue(2); // a lo minimo
        repaint();
    }//GEN-LAST:event_botonPosterizacionFocusLost

    private void menuOperadorPosterizarOpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOperadorPosterizarOpActionPerformed

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // que sea el color del boton 
                    // 5-> lo que vas variando
                    PosterizarOp op = new PosterizarOp(5);
                    op.filter(img, img);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }

    }//GEN-LAST:event_menuOperadorPosterizarOpActionPerformed

    private void labelPosicionRGBMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelPosicionRGBMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_labelPosicionRGBMouseMoved

    private void botonFiltroRojoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFiltroRojoActionPerformed

        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // que sea el color del boton 
                    // 5-> lo que vas variando
                    RojoOp op = new RojoOp(20);
                    op.filter(img, img);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }


    }//GEN-LAST:event_botonFiltroRojoActionPerformed

    private void botonC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonC1ActionPerformed
        c1 = JColorChooser.showDialog(this, "Elije un color", Color.BLUE);
        // tengo que enviar c1

        System.out.println("c1 " + c1);
        botonC1.setBackground(c1);


    }//GEN-LAST:event_botonC1ActionPerformed

    private void botonC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonC2ActionPerformed
        c2 = JColorChooser.showDialog(this, "Elije un color", Color.BLUE);

        System.out.println("c2 " + c2);
        botonC2.setBackground(c2);
    }//GEN-LAST:event_botonC2ActionPerformed

    private void menuOperadorCambioColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOperadorCambioColorActionPerformed
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());

        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    Color c1 = Color.RED;
                    Color c2 = Color.BLUE;
                    int umbral = 10;
                    CambiarTonoOp op = new CambiarTonoOp(c1, c2, 10);
                    op.filter(img, img);
                    // que sea el color del boton 
                    // 5-> lo que vas variando
                    //PosterizarOp op = new PosterizarOp(5);
                    //op.filter(img, img);

                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }//GEN-LAST:event_menuOperadorCambioColorActionPerformed

    private void botonTintadoSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonTintadoSliderStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        System.out.println("Valor Contraste: (0.1:2): " + (float) botonTintadoSlider.getValue() / 10.0);

        if (vi != null && imgFuente != null) {

            try {

                // vaya de 0 a 1.0
                TintOp op = new TintOp(color, (float) (this.botonTintadoSlider.getValue() / 10.0));
                op.filter(imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();

            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }
    }//GEN-LAST:event_botonTintadoSliderStateChanged

    private void botonTintadoSliderFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonTintadoSliderFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonTintadoSliderFocusGained

    private void botonTintadoSliderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonTintadoSliderFocusLost
        imgFuente = null;
        this.botonContraste.setValue(1);
        repaint();
    }//GEN-LAST:event_botonTintadoSliderFocusLost

    private void botonFiltroRojoSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_botonFiltroRojoSliderStateChanged
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        System.out.println("Valor FiltroRojo: (2:25): " + (float) botonFiltroRojoSlider.getValue());

        if (vi != null && imgFuente != null) {

            try {

                // vaya de 0 a 1.0
                RojoOp op = new RojoOp(this.botonFiltroRojoSlider.getValue());
                op.filter(imgFuente, vi.getLienzo().getImagen());
                vi.getLienzo().repaint();

            } catch (IllegalArgumentException e) {
                System.err.println(e.getLocalizedMessage());
            }

        }
    }//GEN-LAST:event_botonFiltroRojoSliderStateChanged

    private void botonFiltroRojoSliderFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonFiltroRojoSliderFocusGained
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        // inicializo la bufferedImage
        if (vi != null) {
            ColorModel cm = vi.getLienzo().getImagen().getColorModel();
            WritableRaster raster = vi.getLienzo().getImagen().copyData(null);
            boolean alfaPre = vi.getLienzo().getImagen().isAlphaPremultiplied();
            imgFuente = new BufferedImage(cm, raster, alfaPre, null);
        }
    }//GEN-LAST:event_botonFiltroRojoSliderFocusGained

    private void botonFiltroRojoSliderFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_botonFiltroRojoSliderFocusLost
        imgFuente = null;
        this.botonContraste.setValue(2);
        repaint();
    }//GEN-LAST:event_botonFiltroRojoSliderFocusLost

    private void botonPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPlayActionPerformed
        File f = (File) botonListaReproduccion.getSelectedItem();
        if (f != null) {
            player = new SMClipPlayer(f);
            if (player != null) {
                player.addLineListener(new ManejadorAudio());
                botonPlay.setEnabled(false); //
                player.play();
            }
        }
    }//GEN-LAST:event_botonPlayActionPerformed

    private void botonPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPausaActionPerformed

        System.out.println("Boton Pausa " + recorder);
        if (player != null) {
            player.stop();
            botonPlay.setEnabled(true); //
            player = null;
            System.out.println("Terminado reproduccion");
        }

        if (recorder != null) {
            recorder.stop();
            botonRecord.setEnabled(true);
            recorder = null;
            System.out.println("Terminado grabacion");
        }


    }//GEN-LAST:event_botonPausaActionPerformed

    private void botonRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRecordActionPerformed
        // TODO add your handling code here:
        String proyectoRuta = System.getProperty("user.dir");
        File proyectoDirectorio = new File(proyectoRuta);
        String directorioPadre = proyectoDirectorio.getParent();
        JFileChooser dlg = new JFileChooser(directorioPadre);

        int resp = dlg.showSaveDialog(this);

        if (resp == JFileChooser.APPROVE_OPTION) {

            try {
                File f = dlg.getSelectedFile();
                recorder = new SMSoundRecorder(f);
                recorder.addLineListener(new ManejadorAudio());
                botonRecord.setEnabled(false);
                if (recorder != null) {
                    recorder.record();
                }

            } catch (Exception ex) {
                System.err.println("Error al leer la imagen");
            }

        }

        System.out.println("Grabando");

    }//GEN-LAST:event_botonRecordActionPerformed

    /**
     * Obtiene la banda de color especificada de una imagen y la devuelve como
     * una nueva imagen en escala de grises.
     *
     * @param img La imagen de la cual se obtendrá la banda de color.
     * @param banda El índice de la banda de color que se desea obtener.
     * @return Una nueva imagen que representa la banda de color especificada en
     * escala de grises.
     */
    private BufferedImage getImageBand(BufferedImage img, int banda) {
        //Creamos el modelo de color de la nueva imagen basado en un espcio de color GRAY
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ComponentColorModel cm = new ComponentColorModel(cs, false, false,
                Transparency.OPAQUE,
                DataBuffer.TYPE_BYTE);
        //Creamos el nuevo raster a partir del raster de la imagen original
        int vband[] = {banda};
        WritableRaster bRaster = (WritableRaster) img.getRaster().createWritableChild(0, 0,
                img.getWidth(), img.getHeight(), 0, 0, vband);
        //Creamos una nueva imagen que contiene como raster el correspondiente a la banda
        return new BufferedImage(cm, bRaster, false, null);
    }

    /**
     * Actualiza el texto de un JLabel con las coordenadas del ratón
     * proporcionadas.
     *
     * @param x La coordenada x del ratón.
     * @param y La coordenada y del ratón.
     */
    public void actualizarPosicionRatonEnLabel(int x, int y) {
        labelPosicionRaton.setText("(" + x + ", " + y + ")");

    }

    // lo mismo pero para RGB
    public void actualizarRBGEnlabel(Color color) {
        this.labelPosicionRGB.setText("RGB:" + color.getRed() + "," + color.getGreen() + "," + color.getBlue());
    }

    /**
     * Duplica una ventana interna existente con su contenido.
     *
     * @param ventanaExistente La ventana interna que se desea duplicar.
     * @return La nueva instancia de VentanaInterna duplicada con la imagen de
     * la ventana existente.
     */
    private VentanaInterna duplicarVentanaInterna(VentanaInterna original) {

        // Crear una nueva instancia de VentanaInterna
        VentanaInterna copia = new VentanaInterna();

        // Copiar la imagen del lienzo
        copia.getLienzo().setImagen(original.getLienzo().getImagen());

        // Obtener el listado de MiFormas del lienzo original
        List<MiForma> listaFigurasOriginal = original.getLienzo().getListaFiguras();

        // Crear una nueva lista para las formas en el lienzo de la copia
        List<MiForma> listaFigurasCopia = new ArrayList<>();

        // Realizar una copia profunda de cada MiForma
        for (MiForma forma : listaFigurasOriginal) {
            listaFigurasCopia.add(forma.clone());
        }

        // Establecer la lista copiada en el lienzo de la copia
        copia.getLienzo().setListaFiguras(listaFigurasCopia);

        return copia;
    }

    /**
     * Escala la imagen en la ventana interna seleccionada por un factor dado.
     *
     * @param valor El factor de escala que se aplicará a la imagen.
     */
    private void escalarFuncion(double valor) {
        VentanaInterna vi = (VentanaInterna) (escritorio.getSelectedFrame());
        if (vi != null) {
            BufferedImage img = vi.getLienzo().getImagen();
            if (img != null) {
                try {

                    // hacemos un escalado
                    AffineTransform at = AffineTransform.getScaleInstance(valor, valor);

                    //mejora interpolacion
                    AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

                    BufferedImage imgdest = atop.filter(img, null);
                    vi.getLienzo().setImagen(imgdest);
                    vi.getLienzo().repaint();

                } catch (IllegalArgumentException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    /**
     * Rota la imagen dada un número de radianes alrededor de su centro y
     * actualiza la ventana interna.
     *
     * @param radianes El ángulo de rotación en radianes.
     * @param img La imagen que se va a rotar.
     * @param vi La ventana interna que contiene la imagen.
     */
    private void rotarFuncion(int radianes, BufferedImage img, VentanaInterna vi) {
        try {

            //rotacion
            AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(radianes), img.getWidth() / 2, img.getHeight() / 2);

            //mejora interpolacion
            AffineTransformOp atop = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

            BufferedImage imgdest = atop.filter(img, null);
            vi.getLienzo().setImagen(imgdest);
            vi.getLienzo().repaint();

        } catch (IllegalArgumentException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

    /*
        mascara iluminado que emborrona e ilumina la imagen ¿Por que?
            - matriz de convolucion de valor 0.2
            - cada pixel de la imagen se calcula como una combinacion lineal de los pixeles vecinos, donde los valores más
              cercanos tienen peso mayor
            - valores filtro iguales = valores se promedin con el de los vecinos = suavizado los bordes y detalles de la imagen, lo que emborrona
            - al ser valor positivo, suma de los pixeles vecinos = aumenta el brillo
     */
    /**
     * Devuelve un objeto Kernel según la selección proporcionada.
     *
     * @param seleccion El índice de la selección del filtro.
     * @return El objeto Kernel correspondiente a la selección.
     */
    private Kernel getKernel(int seleccion) {
        Kernel k = null;
        switch (seleccion) {
            case 0 -> {
                k = KernelProducer.createKernel(KernelProducer.TYPE_MEDIA_3x3);
            }
            case 1 -> {
                k = KernelProducer.createKernel(KernelProducer.TYPE_BINOMIAL_3x3);
            }
            case 2 -> {
                k = KernelProducer.createKernel(KernelProducer.TYPE_ENFOQUE_3x3);
            }
            case 3 -> {
                k = KernelProducer.createKernel(KernelProducer.TYPE_RELIEVE_3x3);
            }
            case 4 -> {
                k = KernelProducer.createKernel(KernelProducer.TYPE_LAPLACIANA_3x3);
            }
            case 5 -> {
                float[] filtro = {0.2f, 0.2f, 0.2f,
                    0.2f, 0.2f, 0.2f,
                    0.2f, 0.2f, 0.2f};
                k = new Kernel(3, 3, filtro);
            }
            case 6 -> {
                // suma total tiene que ser igual a la anterior = 1.0
                /*
                    Solo aplica emborronamiento
                        float[] filtro = {
                            0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                            0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                            0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                            0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
                            0.04f, 0.04f, 0.04f, 0.04f, 0.04f
                        };
                 */

                // parecido es este, reduzco lla contribucion de pixeles en el centro
                float[] filtro = {
                    0.02f, 0.04f, 0.06f, 0.04f, 0.02f,
                    0.04f, 0.08f, 0.10f, 0.08f, 0.04f,
                    0.06f, 0.10f, 0.12f, 0.10f, 0.06f,
                    0.04f, 0.08f, 0.10f, 0.08f, 0.04f,
                    0.02f, 0.04f, 0.06f, 0.04f, 0.02f
                };

                k = new Kernel(5, 5, filtro);
            }

        }
        return k;
    }

    /**
     * Clase interna que maneja eventos relacionados con la activación de
     * ventanas internas.
     */
    private class ManejadorVentanaInterna extends InternalFrameAdapter {
        // 1.Definir la clase manejadora y sobrecargar los metodos que sean necesarios
        // 2.crear el objeto manejador (hacer el new de la clase anterior)
        // 3.Enlazar el generador con el manejador  

        /**
         * Maneja el evento de activación de una ventana interna. Actualiza los
         * estados de los botones según los atributos y la forma actual del
         * lienzo de la ventana interna activada.
         *
         * @param evt El evento de activación de la ventana interna.
         */
        public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            VentanaInterna vi = (VentanaInterna) evt.getInternalFrame();
            botonRellenar.setSelected(vi.getLienzo().getAtributosActual().getRelleno());
            botonAlisar.setSelected(vi.getLienzo().getAtributosActual().isAlisado());
            botonColor.setBackground(vi.getLienzo().getAtributosActual().getColor());

            Forma formaActual = vi.getLienzo().getForma();

            botonMover.setSelected(vi.getLienzo().isMover());

            if (!vi.getLienzo().isMover()) {
                switch (formaActual) {
                    case LINEA ->
                        botonLinea.setSelected(true);
                    case RECTANGULO ->
                        botonRectangulo.setSelected(true);
                    case ELIPSE ->
                        botonOvalo.setSelected(true);
                    case FANTASMA ->
                        botonFantasma.setSelected(true);
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
    private javax.swing.JComboBox<String> ComboBoxFiltros;
    private javax.swing.JPanel Separator1;
    private javax.swing.JPanel Separator10;
    private javax.swing.JPanel Separator11;
    private javax.swing.JPanel Separator12;
    private javax.swing.JPanel Separator13;
    private javax.swing.JPanel Separator14;
    private javax.swing.JPanel Separator15;
    private javax.swing.JPanel Separator16;
    private javax.swing.JPanel Separator17;
    private javax.swing.JPanel Separator18;
    private javax.swing.JPanel Separator2;
    private javax.swing.JPanel Separator3;
    private javax.swing.JPanel Separator4;
    private javax.swing.JPanel Separator5;
    private javax.swing.JPanel Separator6;
    private javax.swing.JPanel Separator7;
    private javax.swing.JPanel Separator8;
    private javax.swing.JPanel Separator9;
    private javax.swing.JToolBar barraHerramientas;
    private javax.swing.JToolBar barraHerramientasInferior;
    private javax.swing.JButton botonAbrirImagen;
    private javax.swing.JToggleButton botonAlisar;
    private javax.swing.JButton botonBandas;
    private javax.swing.JSlider botonBrillo;
    private javax.swing.JToggleButton botonC1;
    private javax.swing.JToggleButton botonC2;
    private javax.swing.JSlider botonCambioColor;
    private javax.swing.JToggleButton botonColor;
    private javax.swing.JButton botonCombinar;
    private javax.swing.JSlider botonCometa;
    private javax.swing.JSlider botonContraste;
    private javax.swing.JButton botonContrasteIluminado;
    private javax.swing.JButton botonContrasteNormal;
    private javax.swing.JButton botonContrasteOscurecido;
    private javax.swing.JButton botonDuplicar;
    private javax.swing.JButton botonEcualizacion;
    private javax.swing.JToggleButton botonFantasma;
    private javax.swing.JButton botonFiltroRojo;
    private javax.swing.JSlider botonFiltroRojoSlider;
    private javax.swing.JSlider botonGrosor;
    private javax.swing.JButton botonGuardarImagen;
    private javax.swing.JButton botonIncrementarEscalado;
    private javax.swing.JToggleButton botonLinea;
    private javax.swing.JComboBox<File> botonListaReproduccion;
    private javax.swing.JToggleButton botonMover;
    private javax.swing.JButton botonNegativo;
    private javax.swing.JButton botonNuevaImagen;
    private javax.swing.JButton botonOscurecerZonasClaras;
    private javax.swing.JToggleButton botonOvalo;
    private javax.swing.JButton botonPausa;
    private javax.swing.JButton botonPlay;
    private javax.swing.JSlider botonPosterizacion;
    private javax.swing.JButton botonRecord;
    private javax.swing.JToggleButton botonRectangulo;
    private javax.swing.JButton botonReducirEscalado;
    private javax.swing.JToggleButton botonRellenar;
    private javax.swing.JSlider botonReto;
    private javax.swing.JButton botonRotacion180;
    private javax.swing.JSlider botonRotar;
    private javax.swing.JButton botonSepia;
    private javax.swing.JLabel botonTiempo;
    private javax.swing.JButton botonTintado;
    private javax.swing.JSlider botonTintadoSlider;
    private javax.swing.JToggleButton botonTransparencia;
    private javax.swing.JButton botonVolcarImagen;
    private javax.swing.JComboBox<String> comboBoxEspacioColor;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.ButtonGroup grupoColores;
    private javax.swing.ButtonGroup grupoFormas;
    private javax.swing.JLabel imagenBrillo;
    private javax.swing.JLabel imagenCometa;
    private javax.swing.JLabel imagenContraste;
    private javax.swing.JLabel imagenPosterizacion;
    private javax.swing.JLabel imagenRotar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JToolBar.Separator jSeparator12;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JLabel labelPosicionRGB;
    private javax.swing.JLabel labelPosicionRaton;
    private javax.swing.JMenuItem menuAbrir;
    private javax.swing.JMenuItem menuAffineTransformOp;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuItem menuBandCombineOp;
    private javax.swing.JMenuBar menuBarra;
    private javax.swing.JMenuItem menuColorConvertOp;
    private javax.swing.JMenuItem menuCometa;
    private javax.swing.JMenuItem menuConvolveOp;
    private javax.swing.JMenuItem menuGuardar;
    private javax.swing.JMenu menuImagen;
    private javax.swing.JMenuItem menuLookupOp;
    private javax.swing.JMenuItem menuLookupOpReto;
    private javax.swing.JMenuItem menuNuevo;
    private javax.swing.JMenuItem menuOperadorCambioColor;
    private javax.swing.JMenuItem menuOperadorPosterizarOp;
    private javax.swing.JPanel menuPosicionRGB;
    private javax.swing.JMenuItem menuRescaleOp;
    private javax.swing.JPopupMenu.Separator menuSeparadorImagen1;
    private javax.swing.JMenuItem menuVolcado;
    private javax.swing.JPanel panelC1;
    private javax.swing.JPanel panelC2;
    private javax.swing.JPanel panelColor;
    private javax.swing.JPanel panelInferior;
    // End of variables declaration//GEN-END:variables
}
