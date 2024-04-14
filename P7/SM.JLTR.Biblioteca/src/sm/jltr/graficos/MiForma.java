/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;

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
     * Aplica los atributos y luego dibuja  y rellena si es el caso
     * @param g2d contexto grafico para dibujar
     */
    public void paint(Graphics2D g2d){
         
        //g2d.draw(forma); // pongo primero draw por que asi aplico bien el color y grosor

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
        this.atributos = atributos;
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
    
        
}
