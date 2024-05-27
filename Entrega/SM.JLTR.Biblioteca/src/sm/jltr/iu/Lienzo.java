/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package sm.jltr.iu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sm.jltr.eventos.LienzoEvent;
import sm.jltr.eventos.LienzoListener;
import sm.jltr.graficos.Atributos;
import sm.jltr.graficos.Forma;
import sm.jltr.graficos.MiElipse2D;
import sm.jltr.graficos.MiFantasma2D;
import sm.jltr.graficos.MiForma;
import sm.jltr.graficos.MiLinea2D;
import sm.jltr.graficos.MiRectangulo2D;

/*
    Notas: 
        P9: Cuando se seleccione una figura del lienzo, hacer que los atributos de la ventana principal
        se activen conforme a los atributos de la figura seleccionada.
        - clase evento propia de la clase Lienzo + interface del manejador
        - biblioteca sm.xxx.eventos
 */
/**
 * Clase que representa el Lienzo del Paint
 *
 * @author torres
 */
public class Lienzo extends javax.swing.JPanel {

    // lista de las formas dibujadas
    private List<MiForma> listaFiguras;

    // estructura de la forma actual
    private MiForma estructura;

    // Forma actual que se esta dibujando
    private Forma forma;

    private Atributos atributosActual;
    private BufferedImage imagen;
    private boolean mover;

    // lista elementos LienzoListener
    ArrayList<LienzoListener> lienzoEventListeners = new ArrayList();

    /**
     * Constructor para inicializar lienzo
     */
    public Lienzo() {
        initComponents();

        atributosActual = new Atributos();
        this.setBackground(Color.white);
        this.listaFiguras = new ArrayList();
        this.forma = Forma.LINEA;
        this.mover = false;
        this.estructura = new MiLinea2D(new Point2D.Double(0, 0), new Point2D.Double(0, 0), atributosActual);

        // tamaño de la ventana interna por defecto
        this.imagen = new BufferedImage(300, 300, BufferedImage.TYPE_4BYTE_ABGR);
    }

    /**
     * Método de dibujo que sobrescribe el método paint de JPanel y dibuja las
     * formas. NO MODIFICAR ESTA FUNCION
     *
     * @param g El contexto gráfico para dibujar
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        if (imagen != null) {
            g2d.drawImage(imagen, 0, 0, this);
        }

        // setclip 
        Rectangle clip = new Rectangle(0, 0, imagen.getWidth(), imagen.getHeight());
        g2d.setClip(clip);

        for (MiForma s : listaFiguras) {
            if (s != null) {
                s.paint(g2d);
            }
        }

    }

    /**
     * Método para determinar la figura seleccionada en función de una posicion
     *
     * @param p La posicion como putno.
     * @return La figura seleccionada si existe, null de lo contrario.
     */
    private MiForma figuraSeleccionada(Point2D p) {
        for (MiForma s : listaFiguras) {
            if (s != null && s.contains(p)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Obtiene una imagen pintada que contiene la imagen actual más las figuras
     * dibujadas.
     *
     * @return Una imagen BufferedImage que contiene la imagen actual más las
     * figuras dibujadas.
     */
    public BufferedImage getPaintedImage() {
        // Código para crear una nueva imagen que
        // contenga la imagen actual más las figuras

        //BufferedImage imgout = new BufferedImage(imagen.getWidth(),imagen.getHeight(),imagen.getType());
        BufferedImage imgout = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
        Graphics2D g2dImagen = imgout.createGraphics();

        if (imagen != null) {
            g2dImagen.drawImage(imagen, 0, 0, this);
        }

        for (MiForma s : listaFiguras) {
            s.paint(g2dImagen);
        }

        return imgout;

    }

    /**
     * Devuelve una nueva imagen que contiene la imagen actual con las figuras
     * seleccionadas superpuestas. Si una figura está seleccionada, se dibuja
     * sobre la imagen y luego se elimina de la lista de figuras.
     *
     * @return Una nueva imagen que contiene la imagen actual con las figuras
     * seleccionadas superpuestas.
     */
    public BufferedImage getPaintedImageSeleccionada() {

        BufferedImage imgout = new BufferedImage(imagen.getWidth(), imagen.getHeight(), imagen.getType());
        Graphics2D g2dImagen = imgout.createGraphics();

        if (imagen != null) {
            g2dImagen.drawImage(imagen, 0, 0, this);
        }

        // ver el iterador
        // cambiarlo para que solo sea el actual
        List<MiForma> shapesToRemove = new ArrayList<>();
        for (MiForma s : listaFiguras) {
            if (s.getAtributos().isSeleccionado()) {
                s.getAtributos().setSeleccionado(false); // Unselect the current shape
                s.paint(g2dImagen);
                shapesToRemove.add(s); // Mark shape for removal
            }
        }

        listaFiguras.removeAll(shapesToRemove);

        // estructura = figura actual
        // List<MiForma> listaFiguras
        /*
        if (estructura.getAtributos().isSeleccionado()){
            estructura.getAtributos().setSeleccionado(false);
            estructura.paint(g2dImagen);
            this.listaFiguras.remove(estructura);
        }
         */
        return imgout;
    }

    /**
     * Obtiene el tipo de forma actual.
     *
     * @return El tipo de forma actual.
     */
    public Forma getForma() {
        return forma;
    }

    /**
     * Establece el tipo de forma actual.
     *
     * @param forma El tipo de forma a establecer.
     */
    public void setForma(Forma forma) {
        this.forma = forma;
    }

    /**
     * Obtiene la estructura/forma actual.
     *
     * @return La estructura/forma actual.
     */
    public MiForma getEstructura() {
        return estructura;
    }

    /**
     * Establece la estructura/forma actual.
     *
     * @param estructura La estructura/forma a establecer
     */
    public void setEstructura(MiForma estructura) {
        this.estructura = estructura;
    }

    /**
     * Obtener los atributos que actualmente estan seleccionados para el lienzo
     *
     * @return Los atributos actuales
     */
    public Atributos getAtributosActual() {
        return atributosActual;
    }

    /**
     * Estableces cambios en los atributos actuales
     *
     * @param atributosActual Los nuevos atributos a establecer
     */
    public void setAtributosActual(Atributos atributosActual) {
        this.atributosActual = atributosActual;
    }

    /**
     * Devuelve la imagen
     *
     * @return imagen a devolver
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * Establece una imagen BufferedImage
     *
     * @param imagen La imagen a establecer
     */
    public void setImagen(BufferedImage imagen) {
        //this.imagen = imagen;
        this.imagen = cloneBufferedImage(imagen);
        if (imagen != null) {
            setPreferredSize(new Dimension(imagen.getWidth(), imagen.getHeight()));
        }
    }

    private BufferedImage cloneBufferedImage(BufferedImage source) {
        if (source == null) {
            return null;
        }

        int width = source.getWidth();
        int height = source.getHeight();
        int type = source.getType();
        if (type == BufferedImage.TYPE_CUSTOM) {
            // Si es una imagen personalizada, obtenemos su tipo
            type = BufferedImage.TYPE_INT_ARGB;
        }

        BufferedImage clonedImage = new BufferedImage(width, height, type);
        Graphics2D g = clonedImage.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();

        return clonedImage;
    }

    /**
     * Obtiene la lista de formas asociadas al lienzo.
     *
     * @return La lista de formas asociadas al lienzo.
     */
    public List<MiForma> getListaFiguras() {
        return listaFiguras;
    }

    /**
     * Establece la lista de formas asociadas al lienzo.
     *
     * @param listaFiguras La lista de formas a establecer.
     */
    public void setListaFiguras(List<MiForma> listaFiguras) {
        this.listaFiguras = listaFiguras;
    }

    /**
     * Devuelve un valor booleano que indica si el lienzo está en modo de mover
     * formas.
     *
     * @return true si el lienzo está en modo de mover formas, false en caso
     * contrario.
     */
    public boolean isMover() {
        return mover;
    }

    /**
     * Establece el modo de mover formas del lienzo.
     *
     * @param mover true para activar el modo de mover formas, false para
     * desactivarlo.
     */
    public void setMover(boolean mover) {
        this.mover = mover;
    }

    /**
     * Añadir manejador a la lista de eventos
     *
     * @param listener evento a añadir
     */
    public void addLienzoListener(LienzoListener listener) {
        if (listener != null) {
            this.lienzoEventListeners.add(listener);
        }
    }

    /**
     * Notificar de que se a añadido un shape
     *
     * @param evt lienzoEvent
     */
    private void notifyShapeAddedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeAdded(evt);
            }
        }
    }

    /**
     * Notificar shape ha sido seleccionado
     *
     * @param evt lienzoEvent
     */
    private void notifyShapeSelectedEvent(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.shapeSelected(evt);
            }
        }
    }
    
    private void notifyShapePosition(LienzoEvent evt) {
        if (!lienzoEventListeners.isEmpty()) {
            for (LienzoListener listener : lienzoEventListeners) {
                listener.posicion(evt);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Cuando se presiona el raton, se selecciona la figura para mover o se
     * inicializa la estructura, luego se añade la estructura al array
     *
     * @param evt El evento del ratón.
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed

        if (this.isMover()) {
            estructura = figuraSeleccionada(evt.getPoint());

        } else {
            this.atributosActual.setPuntoInicial(evt.getPoint());

            switch (forma) {
                case LINEA -> {
                    estructura = new MiLinea2D(evt.getPoint(), evt.getPoint(), atributosActual);

                }
                case RECTANGULO -> {
                    estructura = new MiRectangulo2D(evt.getPoint(), evt.getPoint(), atributosActual);
                }
                case ELIPSE -> {
                    estructura = new MiElipse2D(evt.getPoint(), evt.getPoint(), atributosActual);
                }
                case FANTASMA -> {
                    estructura = new MiFantasma2D(evt.getPoint(), evt.getPoint(), atributosActual);
                    this.repaint();
                }
            }
            listaFiguras.add(estructura);
           
            notifyShapeAddedEvent(new LienzoEvent(this, estructura, this.getX(), this.getY()));
            // notifyShapeAddedEvent(new LienzoEvent(this,estructura.getForma());
        }


    }//GEN-LAST:event_formMousePressed

    /**
     * En movimiento hace un setlocation para establecer la nueva posicion, si
     * no establece donde termina la figura
     *
     * @param evt El evento del ratón.
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged

        if (this.isMover()) {
            if (estructura != null && estructura instanceof MiLinea2D) {
                ((MiLinea2D) estructura).setLocation(evt.getPoint());
            } else if (estructura != null && estructura instanceof MiRectangulo2D) {
                ((MiRectangulo2D) estructura).setLocation(evt.getPoint());
            } else if (estructura != null && estructura instanceof MiElipse2D) {
                ((MiElipse2D) estructura).setLocation(evt.getPoint());
            } else if (estructura != null && estructura instanceof MiFantasma2D) {
                ((MiFantasma2D) estructura).setLocation(evt.getPoint());
            }
        } else {
            if (estructura != null) {
                estructura.getAtributos().setSeleccionado(false);
            }
            switch (estructura) {
                case MiLinea2D linea ->
                    linea.setLine(this.atributosActual.getPuntoInicial(), evt.getPoint());
                case MiRectangulo2D rectangulo ->
                    rectangulo.setFrameFromDiagonal(this.atributosActual.getPuntoInicial().x, this.atributosActual.getPuntoInicial().y, evt.getPoint().x, evt.getPoint().y);
                case MiElipse2D elipse ->
                    elipse.setFrameFromDiagonal(this.atributosActual.getPuntoInicial().x, this.atributosActual.getPuntoInicial().y, evt.getPoint().x, evt.getPoint().y);
                default -> {
                }
            }

        }

        this.repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Maneja el evento de clic del ratón en el lienzo. Selecionar y
     * deseleccionar formas
     *
     * @param evt El evento de clic del ratón.
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

        estructura = figuraSeleccionada(evt.getPoint());
        System.out.println("Figura click " + estructura);

        if (estructura != null) {
            if (this.isMover()) {
                boolean SeleccionadoActual = estructura.getAtributos().isSeleccionado();
                estructura.getAtributos().setSeleccionado(!SeleccionadoActual);

                // notifico del evento
                if (estructura.getAtributos().isSeleccionado()) {
                    notifyShapeSelectedEvent(new LienzoEvent(this, estructura, this.getX(), this.getY()));
                }

                System.out.println("Figura mover: " + estructura.getAtributos().isSeleccionado() + estructura);
                this.repaint();

                for (MiForma s : this.getListaFiguras()) {
                    if (s != null && s.getAtributos().isSeleccionado()) {
                        // actualizar las variables 
                        //s.setAtributos(atributosActual);
                        this.repaint();
                    }
                }
            } else {
                for (MiForma s : this.getListaFiguras()) {
                    System.out.println("Hola");
                    s.getAtributos().setSeleccionado(false);
                }
                this.repaint();
            }

        }

    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
        System.out.println("Hola " + evt.getX() + " " + evt.getY());
        notifyShapePosition(new LienzoEvent(this, estructura, evt.getX(), evt.getY()));
        
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
