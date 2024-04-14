/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase que representa un rectangulo que extiende de MiForma
 * @author torres
 */
public class MiRectangulo2D extends MiForma{

    
    /**
     * Constructor para crear un rectángulo a partir de dos puntos 
     * @param p1 Una esquina del rectángulo.
     * @param p2 La esquina opuesta del rectángulo.
     * @param atributosActual Los atributos de estilo a aplicar a la línea.
     */
    public MiRectangulo2D(Point2D p1,Point2D p2,Atributos atributosActual) {
         forma  = new Rectangle2D.Double();
        ((Rectangle2D) forma).setFrameFromDiagonal(p1, p2);
        
        //this.setAtributos(atributosActual); // asi no puedo
        
        this.getAtributos().setMover(atributosActual.isMover());
        this.getAtributos().setColor(atributosActual.getColor());
        this.getAtributos().setAlisado(atributosActual.isAlisado());
        this.getAtributos().setStroke(atributosActual.getStroke());
        this.getAtributos().setTransparencia(atributosActual.isTransparencia());
        this.getAtributos().setRelleno(atributosActual.getRelleno());
    }
    

    /**
     * Situa el rectángulo 
     * @param rec El objeto Rectangle2D que define las dimensiones y la posición del rectángulo.
     */
    public void setRectangulo(Rectangle2D rec) {
        Point2D p1 = new Point2D.Double(rec.getMaxX(), rec.getMaxY());
        Point2D p2 = new Point2D.Double(rec.getMinX(), rec.getMinY());

        ((Rectangle2D) forma).setFrameFromDiagonal(p1, p2);
    }
    
   /**
     * Establece los puntos que definen las esquinas del rectángulo.
     * @param p1 El primer punto que define una esquina del rectángulo.
     * @param p2 El segundo punto que define la esquina opuesta del rectángulo.
     */
    public void setPoints(Point2D p1, Point2D p2) {
        ((Rectangle2D) forma).setFrameFromDiagonal(p1, p2);
    }
    
    
    @Override
    public void setLocation(Point2D pos) {
        ((Rectangle2D) forma).setRect(pos.getX(), pos.getY(), ((Rectangle2D) forma).getWidth(), ((Rectangle2D) forma).getHeight());    
    }
    
    @Override
    public boolean contains(Point2D p) {
        return  forma.contains(p);
    }
    
    /**
     * Establece el tamaño y la posición del rectángulo a partir de dos puntos.
     * @param x1 x primer punto.
     * @param y1 y primer punto.
     * @param x2 x segundo punto.
     * @param y2 y segundo punto.
     */
    public void setFrameFromDiagonal(int x1, int y1, int x2, int y2) {
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);

        double width = maxX - minX;
        double height = maxY - minY;

        ((Rectangle2D) forma).setFrame(minX, minY, width, height);
    }

    @Override
    public Point2D getLocation() {
        Rectangle2D bounds = ((Rectangle2D) forma).getBounds2D(); // obtener los limites 
        double x = bounds.getX() + bounds.getWidth() / 2.0;
        double y = bounds.getY() + bounds.getHeight() / 2.0;
        return new Point2D.Double(x, y);
    }

}
