/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;

import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase que extiende MiForma que define mediante un area la forma de un Fantasma
 * @author torres
 */
public class MiFantasma2D extends MiForma {

    /**
     * transformacion para luego aplicarla para mover
     */
    public AffineTransform transform = new AffineTransform();

   
    /**
     * Constructor de la forma fantasma basada en dos puntos dados.
     * @param p1 Primer punto que determina una esquina de la forma.
     * @param p2 Segundo punto que determina la esquina opuesta de la forma.
    * @param atributosActual Los atributos de estilo a aplicar a la línea.
    */
    public MiFantasma2D(Point2D p1, Point2D p2, Atributos atributosActual ){
        this.forma=crearFantasma(p1,p2);
        
        this.getAtributos().setSeleccionado(atributosActual.isSeleccionado());
        this.getAtributos().setColor(atributosActual.getColor());
        this.getAtributos().setAlisado(atributosActual.isAlisado());
        this.getAtributos().setStroke(atributosActual.getStroke());
        this.getAtributos().setTransparencia(atributosActual.isTransparencia());
        this.getAtributos().setRelleno(atributosActual.getRelleno());
    }

    
    
    /**
     * Método privado para crear la forma compuesta del fantasma a partir de dos puntos.
     *
     * @param p1 Primer punto 
     * @param p2 Segundo punto 
     * @return Area que representa la forma compuesta del fantasma.
     */
    private Area crearFantasma(Point2D p1, Point2D p2){
        int baseX = (int)Math.min(p1.getX(), p2.getX());
        int baseY = (int)Math.min(p1.getY(), p2.getY());
        
        int anchoCabeza = 100;
        int altoCabeza=  100;
        int posicionCabeza = 50;
        int altoCuerpo=100;
        int anchoCuerpo = 100;
        
        Ellipse2D cabeza = new Ellipse2D.Double(baseX, baseY, anchoCabeza, altoCabeza);
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
    
        Area fantasma = new Area(cabeza);
        fantasma.add(new Area(cuerpo));
        fantasma.subtract(new Area(ojoI));
        fantasma.subtract(new Area(ojoD));
        fantasma.subtract(new Area(boca));
        fantasma.subtract(new Area(triangulo1));
        fantasma.subtract(new Area(triangulo2));
        fantasma.subtract(new Area(triangulo3));
        fantasma.subtract(new Area(triangulo4));
        
        return fantasma;
    }
    
    
    /**
     * Obtiene la ubicacion central del fantasma
     * @return un Point2D que representa el centro del fantasma
     */
    @Override
    public Point2D getLocation() {
        Rectangle2D bounds = forma.getBounds2D();
        double x = bounds.getX(); 
        double y = bounds.getY(); 
        return new Point2D.Double(x, y); 
    }

    /**
     * Establece una nueva ubicación para el fantasma, usando affinetransform para actualizar su ubicacion
     * @param pos La nueva posicion del fantasma
     */
    @Override
    public void setLocation(Point2D pos) {
       transform = AffineTransform.getTranslateInstance(pos.getX() - forma.getBounds2D().getX(), pos.getY() - forma.getBounds2D().getY()); 
       transform(transform);
    }
    
    /**
     * Verifica si un punto específico está dentro de la forma del fantasma.
     * @param p El punto a verificar 
     * @return true si el punto está dentro de la forma, false en caso contrario.
     */
    @Override
    public boolean contains(Point2D p) {
       return forma.contains(p);
    }

     /**
     * Aplica la transformación afín especificada al fantasma para luego actualizar su ubicacion 
     * @param transform La transformación 
     */
    private void transform(AffineTransform transform) {
       forma = transform.createTransformedShape(forma);
    }

    @Override
    public int getWidth() {
        // Obtener el rectángulo que contiene el fantasma
        Rectangle2D bounds = forma.getBounds2D();
        // Devolver el ancho del rectángulo
        return (int) bounds.getWidth();
    }

    @Override
    public int getHeight() {
        // Obtener el rectángulo que contiene el fantasma
        Rectangle2D bounds = forma.getBounds2D();
        // Devolver la altura del rectángulo
        return (int) bounds.getHeight();
    }
    
}
