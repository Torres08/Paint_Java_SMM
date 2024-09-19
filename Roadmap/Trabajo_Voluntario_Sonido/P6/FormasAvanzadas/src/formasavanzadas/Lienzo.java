/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package formasavanzadas;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Map;

/**
 *
 * @author torres
 */
public class Lienzo extends javax.swing.JPanel {

    /**
     * Creates new form Lienzo
     */
    public Lienzo() {
        initComponents();
    }

    // variables 
    private String TipoFuente = "Arial";
    private Boolean ventanaClipActiva = false;
    public Ellipse2D clipArea = new Ellipse2D.Float(100,100,500,500);
    
    public String getTipoFuente() {
        return TipoFuente;
    }

    public void setTipoFuente(String TipoFuente) {
        this.TipoFuente = TipoFuente;
    }

    public Boolean getVentanaClipActiva() {
        return ventanaClipActiva;
    }

    public void setVentanaClipActiva(Boolean ventanaClipActiva) {
        this.ventanaClipActiva = ventanaClipActiva;
    }
    
    
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        
        if (ventanaClipActiva) {
            g2d.setClip(clipArea);
        }
        
        this.pruebaAtributos(g2d);

    }
    
    private void pruebaAtributos(Graphics2D g2d){
        
        // Trazo
        // trazo discontinuo de grosor 10
        Stroke trazo;
        float patronDiscontinuidad[]={15.0f, 15.0f};
        trazo = new BasicStroke(10.0f,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER, 1.0f,
                patronDiscontinuidad, 0.0f);
        g2d.setStroke(trazo);
        
        g2d.draw(new Line2D.Float(40,40,160,160));
  
        // Relleno
        // relleno liso
        Paint relleno;
        relleno = new Color (255,100,0);
        g2d.setPaint(relleno);
        
        g2d.draw(new Rectangle (170,40,120,120));
        g2d.fill(new Rectangle (300,40,120,120));
        
        // relleno con degradado (diagonal de rojo a azul, desde la esquina superior izquierda a la inferior derecha
        
        Point pc1 = new Point (430,40), pc2 = new Point(550,160);
        relleno = new GradientPaint(pc1,Color.RED, pc2,Color.BLUE);
        g2d.setPaint(relleno);
        
        g2d.fill (new Rectangle(430,40,120,120));
        
        
        //Composición Transparencia
        Composite comp;
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f);
        g2d.setComposite(comp);
        
        g2d.fill(new Rectangle (190,100,200,120));
        
        
        
        //Transformación rotacion 45 grados
        Rectangle r = new Rectangle(430,190,120,120);
        g2d.draw(r);
        
        AffineTransform at;
        at = AffineTransform.getRotateInstance(Math.toRadians(45.0),r.getCenterX(),r.getCenterY());
        
        g2d.setTransform(at);
        g2d.fill(r);
        
        
        // prueba de escalado, tener cuidado si mencionar el centroide o no
        at = AffineTransform.getScaleInstance(0.5,0.5);
        g2d.setTransform(at);
        g2d.draw(r);
        
        // trasladar la forma, colocalndo su centro en el origen de coordenadas, la escala
        // y despues la trasnlada de nuevo a su posicion original
        at.setToIdentity();
        at.translate(r.getCenterX(),r.getCenterY());
        at.scale(0.5,0.5);
        at.translate(-r.getCenterX(), -r.getCenterY());
        g2d.setTransform(at);
        g2d.draw(r);
 
        // escala a 0.25 y rotar 45 grados el rectangulo (rectangulo pequeño rotado 
        at.setToIdentity();
        at.translate(r.getCenterX(), r.getCenterY());
        at.rotate(Math.toRadians(45)); // Rotar 45 grados (en radianes)
        at.scale(0.25, 0.25);
        at.translate(-r.getCenterX(), -r.getCenterY());
        g2d.setTransform(at);
        g2d.draw(r);

        // Tras las transformaciones anteriores, dejar la identidad como transformación activa
        at.setToIdentity(); // Restablecer a la identidad
        g2d.setTransform(at); // Aplicar la transformación
        
        //Fuente
        // establecemos una fuente Arial tam 45 y estilo negrita italica
        Font fuente;
        fuente = new Font("Arial",Font.BOLD | Font.ITALIC,45 );
        g2d.setFont(fuente);
        g2d.drawString("Hola", 30, 220);
        
        Map atributosTexto= fuente.getAttributes();
        atributosTexto.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        g2d.setFont( new Font(atributosTexto));
        g2d.drawString("mundo", 30,260);
        
        //Renderización
        // activar mejora en el alisado de formas y texto (activa antialiasing para hola)
        RenderingHints render;
        g2d.draw(new Ellipse2D.Float(40,350,510,50)); // elipse sin antialiasing
        
        render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // mejorar el renderizado de color
        render.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        
        g2d.setRenderingHints(render);
        
        g2d.draw(new Ellipse2D.Float(40,450,510,50));
        g2d.drawString("Hola", 30,170);
        
        //Recorte
        //Shape clipArea;
        //clipArea = new Ellipse2D.Float(100,100,500,500);
        //g2d.setClip(clipArea);
        
        // para marcar los limites del clip area
        //g2d.draw(clipArea);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
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

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        // TODO add your handling code here:
        if(ventanaClipActiva){
            Point corner = evt.getPoint();
            corner.translate((int)clipArea.getWidth()/2,
            (int)clipArea.getHeight()/2);
            clipArea.setFrameFromCenter(evt.getPoint(), corner);
            this.repaint();
        }
    }//GEN-LAST:event_formMouseMoved


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
