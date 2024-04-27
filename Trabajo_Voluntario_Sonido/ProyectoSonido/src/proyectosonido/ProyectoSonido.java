/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectosonido;

// para cargar 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.*;

/**
 * /home/torres/Documents/Github/Practicas_SMM/Trabajo_Voluntario_Sonido/MiVoz.wav";
 * @author torres
 */
public class ProyectoSonido {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Ruta al archivo de sonido
        String archivoSonido = "/home/torres/Documents/Github/Practicas_SMM/Trabajo_Voluntario_Sonido/MiVoz.wav";

        // necesito otro archivo 
        
        // Cargar y analizar el archivo de sonido
        try {
            
            File file = new File(archivoSonido);
            String nombreArchivo = file.getName();
            
            // Crear un FileInputStream para el archivo de sonido
            FileInputStream fis = new FileInputStream(archivoSonido);
            // Envolver FileInputStream en un BufferedInputStream para permitir reset()
            BufferedInputStream bis = new BufferedInputStream(fis);
            
            // Cargar el archivo de sonido como un AudioInputStream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
            
            // Obtener la información del formato de audio
            AudioFormat format = audioInputStream.getFormat();
            
            // Obtener la frecuencia de muestreo y la resolución
            float frecuenciaMuestreo = format.getSampleRate();
            int resolucion = format.getSampleSizeInBits();

            // Mostrar información sobre el archivo de sonido
            System.out.println("Archivo: " + nombreArchivo);
            System.out.println("Frecuencia de muestreo: " + frecuenciaMuestreo + " Hz");
            System.out.println("Resolución: " + resolucion + " bits");
            
            // Cerrar el FileInputStream, BufferedInputStream y AudioInputStream
            bis.close();
            fis.close();
            audioInputStream.close();
            
            
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    
}
