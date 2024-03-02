/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aplicacionaula;

/**
 *
 * @author torres
 */
public class Delegado extends Alumno {
    
    // 1. probar el modificador static
    private static int contadorDelegados = 0;
    
    public Delegado(String nom) {
        super(nom);
        contadorDelegados++;
    }
    
    public static int getContadorDelegados(){
        return contadorDelegados;
    }
    
}
