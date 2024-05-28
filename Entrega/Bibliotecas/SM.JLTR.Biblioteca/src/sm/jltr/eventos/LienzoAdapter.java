/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.eventos;


/*
    Manejador de eventos 2 formas
    1. implemntar el interface LienzoListener
    2. heredando de Lienzo Adapter y sobrecagrando el metodo que nos interese 

    implementar RGB y posicion del raton
*/

/**
 * Adapter asociado a LienzoListener
 * @author torres
 */
public class LienzoAdapter implements LienzoListener{

    @Override
    public void shapeSelected(LienzoEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void shapeAdded(LienzoEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void posicion(LienzoEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void color(LienzoEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
