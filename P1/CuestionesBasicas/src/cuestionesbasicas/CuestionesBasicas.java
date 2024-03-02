/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cuestionesbasicas;



/**
 *
 * @author torres
 */
public class CuestionesBasicas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // 1. Valor Absoluto de 3.7
        double valorAbsoluto = Math.abs(3.7);
        System.out.println("Valor absoluto de 3.7:" + valorAbsoluto);
        
        // 2. raiz cuadrada de 37
        double raizCuadrada= Math.sqrt(37);
        System.out.println("Raiz cuadrada de 37: " + raizCuadrada);
    
        // 3. minucula 
        char a = 'a'; // "a" no vale eso para String
        
        if(Character.isLowerCase(a)){
            System.out.println("Es minuscula");
        } else {
            System.out.println("Es mayuscula");
        }
        
        // 4. Convertir 5 a String
        String valor = String.valueOf(5);
        if (valor instanceof String){
            System.out.println("Es String " + valor);
        } else {
            System.out.println("No es String " + valor);
        }
        
        // 5. Convertir 5 a int, compruebo con el try and catch si el paso se ha hecho correctamente
        int valor2;
        try {
            valor2 = Integer.parseInt(valor);

            // Comprobar si es int
            System.out.println("Es Int: " + valor2);

            // Puedes realizar acciones adicionales si es de tipo int
        } catch (NumberFormatException e) {
            // Si no se puede convertir a int
            System.out.println("No es Int: " + valor);
        }      
        
        // 6. Convertir 5.5 a String
        String valor3 = String.valueOf(5.5);
        if (valor3 instanceof String){
            System.out.println("Es String " + valor3);
        } else {
            System.out.println("No es String " + valor3);
        }
        
        // 7. Cual es el maximo flotante
        float maxFloat = Float.MAX_VALUE;
        System.out.println("Máximo flotante: " + maxFloat);
        
        // 8. IMprime el segundo caracter de "Hola"
        String saludo = "Hola";
        char segundoCaracter = saludo.charAt(1);
        System.out.println("Segundo carácter de \"Hola\": " + segundoCaracter);
        
        // 9. Compara "hola" y "Hola"
        String str1 = "hola";
        String str2 = "Hola";
        boolean sonIguales = str1.equals(str2);
        System.out.println("Comparar \"hola\" con \"Hola\": " + sonIguales);
   
        // 10. Crea "Hola" y añadele adios sin crear un nuevo objeto
        // las cadenas string son inmutables, para no crear un nuevo objeto usamos StringBuilder
        // String mutable = stringBuilder
        StringBuilder mensaje = new StringBuilder("Hola");
        mensaje.append(" y adiós");
        System.out.println("Mensaje modificado: " + mensaje.toString());
    }
    
}
