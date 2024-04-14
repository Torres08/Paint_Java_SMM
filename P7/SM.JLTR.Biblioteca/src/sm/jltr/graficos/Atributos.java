/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * Encapsula las propiedades de MiForma
 * @author torres
 */
public class Atributos {
    
    // Atributos
    private Color color; 
    private Boolean relleno;
    private Stroke stroke ;
    private final RenderingHints antialiasing ;
    private final Composite comp;
     
    private boolean alisado;
    private boolean transparencia;
    
    private boolean mover;
    private Point puntoInicial;
    
    AlphaComposite comp_sin_tansparencia = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
    RenderingHints antialiasing_sin_alisado = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_OFF);
    
    /**
     * Constructor por defecto
     */
    public Atributos() {
        color = Color.BLACK;
        relleno = false;
        stroke = new BasicStroke(1.0f);
        antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        alisado = false;
        transparencia = false;
        
        puntoInicial = null;
        mover = false;
    }
    
    
    /**
     * Aplica los atributos color, stroke, antialiasing y transparencia a MiForma
     * @param g2d Contexto grafico donde aplicamos los atributos
     */
    public void Actualizar(Graphics2D g2d){
        
        g2d.setPaint(color);
        g2d.setStroke(stroke);
        
        if(isAlisado()){
            g2d.setRenderingHints(this.antialiasing);
        }else{
           g2d.setRenderingHints(antialiasing_sin_alisado);
        }
        
        if (isTransparencia()){
            g2d.setComposite(comp);
        } else {
            g2d.setComposite(comp_sin_tansparencia);
        }
            
    }

    /**
     * Obtiene el color actual 
     * @return color actual
     */
    public Color getColor() {
        return color;
    }

    /**
     * Establece el color.
     * @param color Nuevo color a establecer.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Obtiene el estado de relleno.
     * @return true si el relleno está activado, false en caso contrario.
     */
    public Boolean getRelleno() {
        return relleno;
    }

    /**
     * Establece el estado de relleno.
     * @param relleno Estado de relleno a establecer.
     */
    public void setRelleno(Boolean relleno) {
        this.relleno = relleno;
    }

    /**
     * Obtiene el trazo actual.
     * @return El trazo actual.
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * Establece el trazo.
     * @param stroke Nuevo trazo a establecer.
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

     /**
     * Verifica si el alisado está activado.
     * @return true si el alisado está activado, false en caso contrario.
     */
    public boolean isAlisado() {
        return alisado;
    }

    /**
     * Establece el estado de alisado.
     * @param alisado Estado de alisado a establecer.
     */
    public void setAlisado(boolean alisado) {
        this.alisado = alisado;
    }

    /**
     * Verifica si la transparencia está activada.
     * @return true si la transparencia está activada, false en caso contrario.
     */
    public boolean isTransparencia() {
        return transparencia;
    }

    /**
     * Establece el estado de transparencia.
     * @param transparencia Estado de transparencia a establecer.
     */
    public void setTransparencia(boolean transparencia) {
        this.transparencia = transparencia;
    }

    
     /**
     * Ve si la forma se puede mover.
     * @return true si la forma se puede mover, false en caso contrario.
     */
    public boolean isMover() {
        return mover;
    }

    /**
     * Establece si se mueve la figura.
     * @param mover El estado de movimiento a establecer.
     */
    public void setMover(boolean mover) {
        this.mover = mover;
    }

    /**
     * Obtiene el punto inicial para mover la forma.
     * @return El punto inicial.
     */
    public Point getPuntoInicial() {
        return puntoInicial;
    }

    
    /**
    * Establece el punto inicial para mover la forma.
    * @param puntoInicial El punto inicial a establecer.
    */
    public void setPuntoInicial(Point puntoInicial) {
        this.puntoInicial = puntoInicial;
    }
    
    
    
    

}
