/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sm.jltr.eventos;

import java.util.EventListener;
import sm.jltr.iu.Lienzo;

/**
 * Manejadores de evento de la clase Lienzo, hereda de EventListener
 * @author torres
 */
public interface LienzoListener extends EventListener{
    
    /**
     * Se invoca cuando se agrega una forma al lienzo.
     *
     * @param evt El evento que describe la adición de la forma.
     */
    public void shapeAdded(LienzoEvent evt);
    
    /**
     * Se invoca cuando se selecciona una forma en el lienzo.
     *
     * @param evt El evento que describe la selección de la forma.
     */
    public void shapeSelected(LienzoEvent evt);
    
    /**
     * Se invoca cuando cambia la posición de una forma en el lienzo.
     *
     * @param evt El evento que describe el cambio de posición de la forma.
     */
    public void posicion(LienzoEvent evt);
    
    
    /**
     * Se invoca cuando cambia el color de una forma en el lienzo.
     *
     * @param evt El evento que describe el cambio de color de la forma.
     */
    public void color(LienzoEvent evt);
}
