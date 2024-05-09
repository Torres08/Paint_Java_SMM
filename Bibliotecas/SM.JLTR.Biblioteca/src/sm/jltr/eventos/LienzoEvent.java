/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.eventos;

import java.awt.Shape;
import java.util.EventObject;
import sm.jltr.graficos.MiForma;



/**
 * Representa un evento lanzado por un objeto Lienzo, hereda de EventObject
 * Representa un evento que llevara asociada como informacion una figura
 * @author torres
 */
public class LienzoEvent extends EventObject{
    
    //private Shape forma;
    private MiForma forma;
    
    public LienzoEvent(Object source,MiForma forma) {
        super(source);
        this.forma = forma;
    }

    public MiForma getForma() {
        return forma;
    }
    
    
}
