/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.images;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Una clase para aplicar el efecto de posterización a una imagen.
 * La posterización reduce el número de colores utilizados en una imagen
 * @author torres
 */

public class PosterizarOp extends BufferedImageOpAdapter {

    private int niveles;

    public PosterizarOp(int niveles) {
        this.niveles = niveles;
    }

    
    /*
        C𝑜𝑚𝑝_𝑝𝑜𝑡𝑒𝑟𝑖𝑧𝑎𝑑𝑜 = 𝐾 ∗ ⌊𝑐𝑜𝑚𝑝_𝑜𝑟𝑖𝑔𝑖𝑛𝑎𝑙 / 𝐾⌋
        𝐾 = 256 / 𝑁⁄
        𝑁 ∈ [1,256]
    */
    
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if (src == null) {
            throw new NullPointerException("src image is null");
        }
        if (dest == null) {
            dest = createCompatibleDestImage(src, null);
        }
        WritableRaster srcRaster = src.getRaster();
        WritableRaster destRaster = dest.getRaster();
        int sample;
        
        // definir K
        float K = 256.f/niveles;
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                for (int band = 0; band < srcRaster.getNumBands(); band++) {
                    sample = srcRaster.getSample(x, y, band);
                    
                    sample = (int) (K * (int) (sample/K));
                    
                    
                    destRaster.setSample(x, y, band, sample);
                }
            }
        }
        return dest;
    }
}
