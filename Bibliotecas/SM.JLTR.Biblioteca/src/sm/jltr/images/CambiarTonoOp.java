/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sm.jltr.images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 *
 * @author torres
 */

/*
    Cambiar c1 (el que quiero cambiar) por c2(por el que va a ser cambiado)
    umbral T [0,360], margen aceptacion, como RojoOp
    en cada pixel hacemos
        - convertimos el color del pixel de RGB a HSB (RGBtoHSB
        
 */
public class CambiarTonoOp extends BufferedImageOpAdapter {

    private int umbral;
    private float hueC1; //hue = tono
    private float hueC2;

    public CambiarTonoOp(Color c1, Color c2, int umbral) {
        float[] hsbC1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
        float[] hsbC2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);
        this.hueC1 = hsbC1[0] * 360;
        this.hueC2 = hsbC2[0] * 360;
        this.umbral = umbral;
    }

    private float distancia(float h1, float h2) {
        float diff = Math.abs(h1 - h2);
        return diff <= 180 ? diff : 360 - diff;
    }

    private float HResultado(float huePx) {
        return distancia(hueC1, huePx) <= umbral ? hueC2 / 360 : huePx / 360;
    }
    
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
        
        int[] pixelComp = new int[srcRaster.getNumBands()];
        int[] pixelCompDest = new int[srcRaster.getNumBands()];
        
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                srcRaster.getPixel(x, y, pixelComp);
                
                // 1. Convert RGB to HSB
                float[] hsb = Color.RGBtoHSB(pixelComp[0], pixelComp[1], pixelComp[2], null);
                float hue = hsb[0] * 360;
                
                // 2. Determine the new hue based on the threshold
                hsb[0] = HResultado(hue);
                
                // 3. Convert HSB back to RGB
                // comentar esto, funcionar funciona
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                pixelComp[0] = (rgb >> 16) & 0xFF;
                pixelComp[1] = (rgb >> 8) & 0xFF;
                pixelComp[2] = rgb & 0xFF;

                destRaster.setPixel(x, y, pixelComp);
            }
        }
        return dest;
    }
}
