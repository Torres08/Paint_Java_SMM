/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.eventos;

import java.awt.Color;
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
    int x,y;
    
    public LienzoEvent(Object source,MiForma forma, int x, int y) {
        super(source);
        this.forma = forma;
        this.x = x;
        this.y = y;
    }

    public MiForma getForma() {
        return forma;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    
}
