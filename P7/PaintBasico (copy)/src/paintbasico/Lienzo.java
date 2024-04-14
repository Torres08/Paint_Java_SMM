package paintbasico;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.List;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
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

class Fantasma extends Area{
    
    // dibujar fantasma constructor
    public Fantasma(int x, int y){
        super(new Ellipse2D.Double(x, y, 100, 100));
        
        int baseX = x;
        int baseY = y;

        int anchoCabeza = 100;
        int altoCabeza=  100;
        int posicionCabeza = 50;
        int altoCuerpo=100;
        int anchoCuerpo = 100;
        
        //Ellipse2D cabeza = new Ellipse2D.Double(baseX, baseY, anchoCabeza, altoCabeza);
        Rectangle2D cuerpo = new Rectangle2D.Double(baseX, baseY + posicionCabeza, anchoCuerpo, altoCuerpo);  
        
        
        // posicion + tamaño de los ojos
        int ojoOffsetX = 25;
        int distanciaOjoDerecha = 40;
        int ojoOffsetY = 35;
        int anchoOjo = 10;
        int altoOjo = 10;
        
        Ellipse2D ojoI = new Ellipse2D.Double(baseX + ojoOffsetX, baseY + ojoOffsetY, anchoOjo, altoOjo);
        Ellipse2D ojoD = new Ellipse2D.Double(baseX + ojoOffsetX + distanciaOjoDerecha, baseY + ojoOffsetY, anchoOjo, altoOjo); // 40 como el espacio adicional entre los ojos

        
        // Coordenadas + tamaño para la boca
        int bocaOffsetX = 25;
        int anchoBoca = anchoCabeza / 2;
        int altoBoca = altoCabeza / 3;
        int inicioArcoBoca = 180;
        int extensiónArcoBoca = 180;
        
        Arc2D boca = new Arc2D.Double(baseX + bocaOffsetX, baseY + posicionCabeza, anchoBoca, altoBoca, inicioArcoBoca, extensiónArcoBoca, Arc2D.OPEN);
        
        
        // parte baja del fantasma, son 4 triangulos
        int baseTrianguloX = baseX;
        int alturaTriangulo = 25 + 10; 
        int incrementoX = 25; // Incremento en X para cada triángulo
        int variable1 = 50;
        
        int variable2 = 100;
        
        Polygon triangulo1 = new Polygon(new int[]{baseTrianguloX, baseTrianguloX + anchoCuerpo - incrementoX - variable1, baseTrianguloX + anchoCuerpo - incrementoX - variable1}, new int[]{baseY+variable2+variable1, baseY+variable2+variable1, baseY + altoCabeza + alturaTriangulo}, 3);
        Polygon triangulo2 = new Polygon(new int[]{baseTrianguloX + incrementoX, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX}, new int[]{baseY+variable2+variable1, baseY+variable2+variable1, baseY + altoCabeza + alturaTriangulo}, 3);
        Polygon triangulo3 = new Polygon(new int[]{baseTrianguloX + incrementoX*2, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX*2, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX*2}, new int[]{baseY+variable2+variable1, baseY+variable2+variable1, baseY + altoCabeza + alturaTriangulo}, 3);
        Polygon triangulo4 = new Polygon(new int[]{baseTrianguloX + incrementoX*3, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX*3, baseTrianguloX + anchoCuerpo - incrementoX - variable1 + incrementoX*3}, new int[]{baseY+variable2+variable1, baseY+variable2+variable1, baseY + altoCabeza + alturaTriangulo}, 3);

        
        
        //Area fantasma = new Area(cabeza);
        this.add(new Area(cuerpo));
        this.subtract(new Area(ojoI));
        this.subtract(new Area(ojoD));
        this.subtract(new Area(boca));
        this.subtract(new Area(triangulo1));
        this.subtract(new Area(triangulo2));
        this.subtract(new Area(triangulo3));
        this.subtract(new Area(triangulo4));
        
        
    }
    
    public AffineTransform transform = new AffineTransform();
    
    public void setLocation(Point2D pos){
        transform = AffineTransform.getTranslateInstance(pos.getX() - this.getBounds2D().getX(), pos.getY() - this.getBounds2D().getY());
        this.transform(transform); 
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
    // variables lo suyo es tener un constructor a la hora d eusar todas estas inicializaciones
     private Shape forma = new MiLinea.Float(0,0,0,0); // por defecto es una linea en el 0, 70 70 200
    //List<Shape> vShape = new ArrayList<>();
     private final List<Shape> vShape = new ArrayList();
     private Forma TipoForma = Forma.LINEA; // para estudiar los distintos casos
     private Color color = Color.BLACK; // por defecto el negro
     private Boolean relleno = false;
     private Point puntoInicial = null;
     private boolean mover = false;
     
     // variables para grosor, transparencia y alisado
     private Stroke stroke = new BasicStroke(1.0f);
     private final RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
     private final Composite comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
     
    private boolean alisado;
    private boolean transparencia;

    
    // punto final e inicial
    // override al paint
    @Override
    public void paint(Graphics g){
        super.paint(g);        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        
        if(alisado){
            g2d.setRenderingHints(this.antialiasing);
        }
        
        if (transparencia) {
            g2d.setComposite(comp);
        }

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

    // para lo nuevo de la P6
    public Stroke getStroke() {
        return stroke;
    }

    public Float getStrokeWidth() {
        return ((BasicStroke) stroke).getLineWidth();
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public boolean isAlisado() {
        return alisado;
    }

    public void setAlisado(boolean alisado) {
        this.alisado = alisado;
    }

    public boolean isTransparencia() {
        return transparencia;
    }

    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
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
            case FANTASMA -> {
                // forma sea dibujar el fantasma
                //forma = this.crearFantasma(evt.getPoint().x, evt.getPoint().y);
                forma  = new Fantasma(evt.getPoint().x, evt.getPoint().y);
                // solamente un repaint aqui porque fantasma no se hace un dragg
                System.out.println("hola soy un " + forma);
                this.repaint();
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
            } else if (forma!=null && forma instanceof Area){
                System.out.println("MOviendo al fantasma aaa");
                ((Fantasma)forma).setLocation(evt.getPoint());
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
