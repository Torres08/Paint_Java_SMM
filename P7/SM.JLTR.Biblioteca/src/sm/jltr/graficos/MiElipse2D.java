/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;


import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase que representa una elipse que extiende de MiForma
 * @author torres
 */
public class MiElipse2D extends MiForma {

    /**
     * Constructor de la Elipse a partir de 2 puntos  
     * @param p1 El primer punto que define la diagonal del rectángulo contenedor.
     * @param p2 El segundo punto que define la diagonal opuesta del rectángulo contenedor.
     * @param atributosActual Los atributos de estilo a aplicar a la línea.
     */
       
    public MiElipse2D(Point2D p1, Point2D p2,Atributos atributosActual){
        this.forma = new Ellipse2D.Double();
        ((Ellipse2D)forma).setFrameFromDiagonal(p1, p2);
        
        this.getAtributos().setMover(atributosActual.isMover());
        this.getAtributos().setColor(atributosActual.getColor());
        this.getAtributos().setAlisado(atributosActual.isAlisado());
        this.getAtributos().setStroke(atributosActual.getStroke());
        this.getAtributos().setTransparencia(atributosActual.isTransparencia());
        this.getAtributos().setRelleno(atributosActual.getRelleno());
    }
    
    /**
     * Establece los puntos que definen la diagonal del rectángulo contenedor de la elipse.
     * @param p1 El primer punto que define la diagonal del rectángulo contenedor.
     * @param p2 El segundo punto que define la diagonal opuesta 
     */
    public void setPoints(Point2D p1, Point2D p2) {
        ((Ellipse2D)forma).setFrameFromDiagonal(p1, p2);
    }
    
    
  
    @Override
    public Point2D getLocation() {
        Rectangle2D frame = ((Ellipse2D)forma).getFrame(); 
        double x = frame.getX() + frame.getWidth() / 2.0; 
        double y = frame.getY() + frame.getHeight() / 2.0; 
        return new Point2D.Double(x, y); 
    }


    @Override
    public void setLocation(Point2D pos) {
       ((Ellipse2D) forma).setFrame(pos.getX(), pos.getY(), ((Ellipse2D) forma).getWidth(), ((Ellipse2D) forma).getHeight());
    }

    @Override
    public boolean contains(Point2D p) {
        return ((Ellipse2D)forma).contains(p);
    }
    
    
    /**
     * Establece el tamaño y la posición de la elipse a partir de dos puntos. 
     * @param x1 La coordenada x del primer punto.
     * @param y1 La coordenada y del primer punto.
     * @param x2 La coordenada x del segundo punto.
     * @param y2 La coordenada y del segundo punto.
     */
    public void setFrameFromDiagonal(int x1, int y1, int x2, int y2) {
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);

        double width = maxX - minX;
        double height = maxY - minY;

        ((Ellipse2D) forma).setFrame(minX, minY, width, height);
    }
    
}
