/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;
import java.awt.Color;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;


/**
 * Clase que representa una linea que extiende de MiForma
 * @author torres
 */
public class MiLinea2D extends MiForma{

   
     /**
     * Constructor para crear una línea a partir de dos puntos.
     * @param p1 Un extremo de la línea.
     * @param p2 Otro extremo de la línea.
     * @param atributosActual Los atributos de estilo a aplicar a la línea.
     */
    public MiLinea2D(Point2D p1, Point2D p2, Atributos atributosActual){
        forma = new Line2D.Double(p1, p2);
        //this.setAtributos(atributosActual); // asi no puedo
       
        this.getAtributos().setSeleccionado(atributosActual.isSeleccionado());
        this.getAtributos().setColor(atributosActual.getColor());
        this.getAtributos().setAlisado(atributosActual.isAlisado());
        this.getAtributos().setStroke(atributosActual.getStroke());
        this.getAtributos().setTransparencia(atributosActual.isTransparencia());
        this.getAtributos().setRelleno(atributosActual.getRelleno());
        
        
    }
    
    /**
     * Establece los puntos que definen los extremos de la línea.
     * @param p1 Un extremo de la línea.
     * @param p2 Otro extremo de la línea.
     */
    public void setPoints(Point2D p1, Point2D p2) {
        ((Line2D) forma).setLine(p2, p1);
    }
    
    /**
     * Determina si un punto está cerca de la línea con un umbral
     * @param p El punto que se está evaluando.
     * @return true si el punto está cerca de la línea, false en caso contrario.
     */
    public boolean isNear(Point2D p){
        return ((Line2D) forma).ptLineDist(p) <= 3.0;
    }
    
    @Override
    public boolean contains(Point2D p) {
        return isNear(p);
    }
    
    @Override
    public void setLocation(Point2D pos){
        Point2D pIni = ((Line2D) forma).getP1();
        Point2D pFin = ((Line2D) forma).getP2();

        //System.out.println("hola " + pIni + " " + pFin);

        double deltaX = pos.getX() - pIni.getX();
        double deltaY = pos.getY() - pIni.getY();

        pIni.setLocation(pos.getX(), pos.getY());
        pFin.setLocation(pFin.getX() + deltaX, pFin.getY() + deltaY);

        ((Line2D) forma).setLine(pIni, pFin);
    }
    
    /**
     * Establece los puntos que definen los extremos de la línea.
     * @param p1 Un extremo de la línea.
     * @param p2 Otro extremo de la línea.
     */
    public void setLine(Point2D p1, Point2D p2) {
        ((Line2D.Double) forma).setLine(p1, p2);
    }

    @Override
    public Point2D getLocation() {
        return ((Line2D) forma).getP1(); // devuelvo el primer punto
    }

    @Override
    public int getWidth() {
        // Obtener los puntos inicial y final de la línea
        Point2D p1 = ((Line2D) forma).getP1();
        Point2D p2 = ((Line2D) forma).getP2();
        // Calcular la diferencia en las coordenadas X
        double diffX = p2.getX() - p1.getX();
        // Devolver el valor absoluto de la diferencia como el ancho
        return (int) Math.abs(diffX);
    }

    @Override
    public int getHeight() {
        // Obtener los puntos inicial y final de la línea
        Point2D p1 = ((Line2D) forma).getP1();
        Point2D p2 = ((Line2D) forma).getP2();
        // Calcular la diferencia en las coordenadas Y
        double diffY = p2.getY() - p1.getY();
        // Devolver el valor absoluto de la diferencia como la altura
        return (int) Math.abs(diffY);
    }

}
