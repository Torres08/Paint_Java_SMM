package paintbasico;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static paintbasico.Forma.ELIPSE;
import static paintbasico.Forma.LINEA;
import static paintbasico.Forma.RECTANGULO;

// mi linea 

class MiLinea extends Line2D.Float {
    public MiLinea(Point2D p1, Point2D p2) {
        super(p1,p2);
    }
    
    public boolean isNear(Point2D p){
        if(this.getP1().equals(this.getP2())) return this.getP1().distance(p)<=2.0; //p1=p2
        return this.ptLineDist(p)<=2.0; // p1!=p2
    }
    
    
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    
    
    public void setLocation(Point2D pos){
        double dx=pos.getX()-this.getX1();
        double dy=pos.getY()-this.getY1();
        Point2D newp2 = new Point2D.Double(this.getX2()+dx,this.getY2()+dy);
        this.setLine(pos,newp2);
    }
}

/**
 *
 * @author torres
 */
public class Lienzo extends javax.swing.JPanel {

    // seleccionar la primera figura situada bajo del punto p, usa el contains 
    private Shape figuraSeleccionada(Point2D p){
        for(Shape s:vShape)
            if(s.contains(p)) return s;
            
        
        return null;
    }

    /**
     * Creates new form Lienzo
     */
    public Lienzo() {
        initComponents();
    }
    // variables
     private Shape forma = new MiLinea.Float(0,0,0,0); // por defecto es una linea en el 0, 70 70 200
    //List<Shape> vShape = new ArrayList<>();
     private final List<Shape> vShape = new ArrayList();
     private Forma TipoForma = Forma.LINEA; // para estudiar los distintos casos
     private Color color = Color.BLACK; // por defecto el negro
     private Boolean relleno = false;
     private Point puntoInicial = null;
     private boolean mover = false;
     
    // punto final e inicial?
    
    // override al paint
    @Override
    public void paint(Graphics g){
        super.paint(g);        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(color);
        
        //g2d.draw(forma);
        for(Shape s:vShape){
            if (relleno)
                g2d.fill(s); 
            
             g2d.draw(s);
        }
        
        
    }
    
    // gets & setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        System.out.println("Cambio color a " + color);
        this.color = color;
    } 
    
    public Shape getForma() {
        return forma;
    }
    
    public void setForma(Forma forma) {
        System.out.println("Cambio forma a " + forma);
        this.TipoForma = forma;

    }
    
    public Boolean getRelleno() {
        return relleno;
    }

    public void setRelleno(Boolean relleno) {
        this.relleno = relleno;
    }
    
    public boolean getMover() {
        return mover;
    }

    public void setMover(boolean mover) {
        this.mover = mover;
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
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
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

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        
        // System.out.println("Pressed");
        // estudiar varios casos segun Forma enum 
        // Shape forma
        if (mover){
            forma = figuraSeleccionada(evt.getPoint());
            System.out.println("Figura Seleccionada " + forma);

        } else{
            puntoInicial = evt.getPoint();
      
        switch(TipoForma){  
            case LINEA ->  {
                forma = new MiLinea(evt.getPoint(), evt.getPoint());
            }
            case RECTANGULO ->  {
                forma = new Rectangle2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
            }
            case ELIPSE -> {
                forma = new Ellipse2D.Float(evt.getPoint().x, evt.getPoint().y, 0, 0);
            }
        }
        
        vShape.add(forma);
        }
        
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // TODO add your handling code here:
        //linea.setLine(linea.getP1(), evt.getPoint());
        // System.out.println("Dragged");
        // if del getclass forma.instanceof 
        // if (forma instanceof Line2D
        
        // almacenar punto mejor
        
        if (mover){
            //Código para el caso del rectángulo
            //System.out.println("Hola " + forma);
            if (forma!=null && forma instanceof Rectangle2D){
                // System.out.println("Estoy moviendo");
                //((Rectangle2D)forma).setRect(evt.getPoint().getX(), evt.getPoint().getY(), ((Rectangle2D)forma).getWidth(), ((Rectangle2D)forma).getHeight());
                ((Rectangle2D)forma).setFrame(evt.getPoint().getX(), evt.getPoint().getY(), ((Rectangle2D)forma).getWidth(), ((Rectangle2D)forma).getHeight());
            } else if (forma!=null && forma instanceof Ellipse2D){
                ((Ellipse2D)forma).setFrame(evt.getPoint().getX(), evt.getPoint().getY(), ((Ellipse2D)forma).getWidth(), ((Ellipse2D)forma).getHeight());
                
            } else if (forma!=null && forma instanceof Line2D){
                ((MiLinea)forma).setLocation(evt.getPoint());
            }
            
            
        }else{
            System.out.println("Mi forma es " + forma);
            if (forma instanceof Line2D linea) {
             linea.setLine(puntoInicial, evt.getPoint());
            } else if (forma instanceof Rectangle2D rectangulo) {
                rectangulo.setFrameFromDiagonal(puntoInicial.x, puntoInicial.y, evt.getPoint().x, evt.getPoint().y);
            } else if (forma instanceof Ellipse2D elipse) {
                elipse.setFrameFromDiagonal(puntoInicial.x, puntoInicial.y, evt.getPoint().x, evt.getPoint().y);
            }
            
        }
        
        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
