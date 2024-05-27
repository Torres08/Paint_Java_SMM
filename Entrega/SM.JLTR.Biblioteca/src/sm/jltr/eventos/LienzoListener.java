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
    public void shapeAdded(LienzoEvent evt);
    public void shapeSelected(LienzoEvent evt);
    public void posicion(LienzoEvent evt);
}
