/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Define estructura basica para las formas de mi paint. Maneja los atributos comunes de dibujo 
 * @author torres
 */
public abstract class MiForma{
    
    /**
     * Atributos de dibujo y estilo para MiForma
     */
    private Atributos atributos = new Atributos();
    
    /**
     * La forma geométrica específica de esta instancia, definida por la subclase.
     */
    Shape forma;
    
    /**
    * La imagen asociada a la forma.
    */
    private BufferedImage imagen;

    /**
     * Aplica los atributos y luego dibuja  y rellena si es el caso
     * @param g2d contexto grafico para dibujar
     */
    public void paint(Graphics2D g2d){
         

        // si esta en mover dibujar un circulo en su esquina
        if(atributos.isSeleccionado()){
            Point2D punto = this.getLocation();
            
            Atributos atributosElipseEdicion = new Atributos();
            atributosElipseEdicion.setColor(Color.red);
            float[] dashPattern = {10, 5}; 
            BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f);
            atributosElipseEdicion.setStroke(stroke);

            Ellipse2D elipseEdicion = new Ellipse2D.Double(punto.getX(), punto.getY(), 10, 10);             
            g2d.setColor(atributosElipseEdicion.getColor());
            g2d.setStroke(atributosElipseEdicion.getStroke());
            g2d.draw((Shape)elipseEdicion);
        } 
        
        atributos.Actualizar(g2d); 

        if (atributos.getRelleno()){
            g2d.fill(forma);
        }
        
        g2d.draw(forma); 
        
    }
    
    
    /**
     * Obtiene los atributos de estilo de MiForma
     * @return los atributos de MiForma
     */
    public Atributos getAtributos() {
        return atributos;
    }

    /**
     * Establece los atributos de estilo y dibujo para MiForma
     * @param atributos Los nuevos atributos de estilo 
     */
    public void setAtributos(Atributos atributos) {
        //this.atributos = atributos;
        // no quiero pasar el atributo mover el resto si 
        this.getAtributos().setColor(atributos.getColor());
        this.getAtributos().setRelleno(atributos.getRelleno());
        this.getAtributos().setAlisado(atributos.isAlisado());
        this.getAtributos().setStroke(atributos.getStroke());
        this.getAtributos().setTransparencia(atributos.isTransparencia());
    }
          
    
    /**
     * Obtiene la ubicación de la forma.
     * @return devuelve un Point2D de la ubicacion de la forma
     */
    public abstract Point2D getLocation();
    
    /**
     * Establece una nueva ubicación para la forma
     * @param pos La nueva posición
     */
    public abstract void setLocation(Point2D pos);
    
    /**
     * Determina si un punto está contenido dentro de la forma
     * @param p El punto a verificar
     * @return true si el punto esta contenido en la forma, false en caso contrario
     */
    public abstract boolean contains(Point2D p);
    
    
    
    /**
     * Obtiene el ancho de la forma.
     * @return el ancho de la forma
     */
    public abstract int getWidth();
    
    /**
     * Obtiene el alto de la forma.
     * @return el alto de la forma
     */
    public abstract int getHeight();
    
    /**
     * Establece la imagen asociada a la forma.
     * @param imagen la imagen a establecer
     */
    public void setImage(BufferedImage imagen) {
        this.imagen = imagen;
    }
    
    /**
     * Obtiene la imagen asociada a la forma.
     * @return la imagen asociada a la forma
     */
    public BufferedImage getImage() {
        return imagen;
    }
    
    
    /**
     * Devuelve la forma actual almacenada en este objeto.
     * @return La forma actual almacenada en este objeto.
     */
    public Shape getForma() {
        return forma;
    }

    /**
    * Establece la forma para este objeto.
    * @param forma La forma que se asignará a este objeto.
    */
    public void setForma(Shape forma) {
        this.forma = forma;
    }
    
        
}
