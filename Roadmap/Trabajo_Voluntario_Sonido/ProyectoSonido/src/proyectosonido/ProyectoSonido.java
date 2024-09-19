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
 * Porgrama para ver tano ela frecuencia de muestreo, resolucion, tamaño y nombre de las pistas usadas para el apartado 1 del proyecto;
 * @author torres
 */
public class ProyectoSonido {

    
    
    public static void analizarArchivoDeSonido(String rutaArchivo) {
        try {
            File file = new File(rutaArchivo);
            String nombreArchivo = file.getName();
            long tamañoArchivo = file.length(); // Obtener el tamaño del archivo en bytes
            double tamañoArchivoKB = (double) tamañoArchivo / 1024; // Convertir a kilobytes

            FileInputStream fis = new FileInputStream(rutaArchivo);
            BufferedInputStream bis = new BufferedInputStream(fis);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);

            AudioFormat format = audioInputStream.getFormat();

            float frecuenciaMuestreo = format.getSampleRate();
            int resolucion = format.getSampleSizeInBits();
    
            System.out.println("\nArchivo: " + nombreArchivo);
            System.out.println("Frecuencia de muestreo: " + frecuenciaMuestreo + " Hz");
            System.out.println("Resolución: " + resolucion + " bits");
            System.out.println("Tamaño: " + tamañoArchivoKB + "Kb" + "\n");   

            bis.close();
            fis.close();
            audioInputStream.close();

        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        String directorioProyecto = System.getProperty("user.dir");
        directorioProyecto = directorioProyecto.substring(0, directorioProyecto.lastIndexOf("/ProyectoSonido"));
        //System.out.println(directorioProyecto);
        
        String MiVoz = directorioProyecto + "/Audio/MiVoz/MiVoz-original.wav";
        //String MiVoz = "/home/torres/Documents/Github/Practicas_SMM/Trabajo_Voluntario_Sonido/Audio/MiVoz/MiVoz-original.wav";
        String MiVoz4832 = directorioProyecto + "/Audio/MiVoz/MiVoz-48Hz-32b.wav";
        String MiVoz4432 = directorioProyecto +"/Audio/MiVoz/MiVoz-44Hz-32b.wav";
        String MiVoz4416 = directorioProyecto +"/Audio/MiVoz/MiVoz-44Hz-16b.wav";
        String MiVoz2432 = directorioProyecto +"/Audio/MiVoz/MiVoz-24Hz-32b.wav";
        String MiVoz2416 = directorioProyecto +"/Audio/MiVoz/MiVoz-24Hz-16b.wav";
        String MiVoz1132 = directorioProyecto +"/Audio/MiVoz/MiVoz-11Hz-32b.wav";
        String MiVoz1116 = directorioProyecto + "/Audio/MiVoz/MiVoz-11Hz-16b.wav";

        
        String sample = directorioProyecto + "/Audio/Sample/sample-original.wav";
        String sample4832 = directorioProyecto + "/Audio/Sample/sample-48Hz-32b.wav";
        String sample4816 = directorioProyecto + "/Audio/Sample/sample-48Hz-16b.wav";
        String sample4432 = directorioProyecto + "/Audio/Sample/sample-44Hz-32b.wav";
        String sample2432 = directorioProyecto + "/Audio/Sample/sample-24Hz-32b.wav";
        String sample2416 = directorioProyecto + "/Audio/Sample/sample-24Hz-16b.wav";
        String sample1132 = directorioProyecto + "/Audio/Sample/sample-11Hz-32b.wav";
        String sample1116 = directorioProyecto + "/Audio/Sample/sample-11Hz-16b.wav";
        
        
        analizarArchivoDeSonido(MiVoz);

        analizarArchivoDeSonido(MiVoz4832);
        analizarArchivoDeSonido(MiVoz4432);
        analizarArchivoDeSonido(MiVoz4416);
        analizarArchivoDeSonido(MiVoz2432);
        analizarArchivoDeSonido(MiVoz2416);
        analizarArchivoDeSonido(MiVoz1132);
        analizarArchivoDeSonido(MiVoz1116);
        
        
        analizarArchivoDeSonido(sample);
        analizarArchivoDeSonido(sample4832);
        analizarArchivoDeSonido(sample4816);
        analizarArchivoDeSonido(sample4432);
        analizarArchivoDeSonido(sample2432);
        analizarArchivoDeSonido(sample2416);
        analizarArchivoDeSonido(sample1132);
        analizarArchivoDeSonido(sample1116);
        
        
    }
    
}
