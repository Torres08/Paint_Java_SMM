package aplicacionaula;

public class AplicacionAula {

    public static void main(String[] args) {
        Profesor jesus,joaquin;
        Alumno juan,beatriz;
        Persona per;

        jesus   = new Profesor("Jesus");
        joaquin = new Profesor("Joaquin","B7");
        juan  = new Alumno("Juan");
        beatriz = new Alumno("Beatriz",4,5);
        per = new Persona();

        per.altura=3.3F;
        System.out.println("\n"+jesus.toString());   // Mostramos a los profesores
        System.out.println(joaquin.toString());
        jesus.setDespacho("C7");                     // Cambiamos el despacho de Jesus
        System.out.println(jesus.toString()+"\n");   // Comprobamos la nueva informacion

        jesus.darClase();                               // Jesus comineza la clase
        jesus.habla();
        juan.pregunta("¿Que es un objeto?");             // El alumno juan pregunta
        jesus.responderPregunta("¿Que es un objeto?");   // Jesus contesta
        beatriz.pregunta("¿Es lo mismo clase y objeto?");
        jesus.responderPregunta("¿Es lo mismo clase y objeto?");
        jesus.pregunta("¿Os habeis enterado?");
        
        
        // P1
        
        // 1. Crea un vector de 10 delegados 
        Delegado[] delegado = new Delegado[10];
        for (int i = 0; i < 10; ++i){
            delegado[i] = new Delegado("Delegado" + (i+1) );
        }
        
        // 2. probar el modificador static 
        System.out.println("Numero total de delegados: " + Delegado.getContadorDelegados());
    
        // 3. protecciones
        // No se puede acceder directamente a nombre y edad desde fuera de la clase Alumno
        Delegado delegado2 = new Delegado("Juan");
        // System.out.println(delegado.nombre); // recuerda que esta protegido
        System.out.println(delegado2.getNombre());
    
        // lo mismo llamar a delegado2 que al mismo Delegado para static
        System.out.println("Numero total de delegados: " + delegado2.getContadorDelegados());

    }

}
