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
    private float tonoC1; //hue = tono
    private float tonoC2;

    public CambiarTonoOp(Color c1, Color c2, int umbral) {
        float[] hsbC1 = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
        float[] hsbC2 = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);
        this.tonoC1 = hsbC1[0] * 360;
        this.tonoC2 = hsbC2[0] * 360;
        this.umbral = umbral;
    }

    private float distancia(float h1, float h2) {
        float diff = Math.abs(h1 - h2);

        if (diff <= 180) {
            return diff;
        } else {
            return 360 - diff;
        }

    }

    private float HResultado(float huePx) {
        // se tiene que dividir antes por 360 antes de pasarla a HSBtoRGB
        if (distancia(tonoC1, huePx) <= umbral) {
            return tonoC2 / 360;
        } else {
            return huePx / 360;
        }
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

                // 1. Convertimos RGB a HSB
                float[] hsb = Color.RGBtoHSB(pixelComp[0], pixelComp[1], pixelComp[2], null);
                float tono = hsb[0] * 360;

                // 2. Determinamos el tono segun el umbral
                hsb[0] = HResultado(tono);

                // 3. Convertimos HSB a RGB
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                // >> desplazar los bit de valor rgb a la derecha
                // 0xff enmascara bits para obtener solos los 8 bits menos significativos
                pixelComp[0] = (rgb >> 16) & 0xFF;
                pixelComp[1] = (rgb >> 8) & 0xFF;
                pixelComp[2] = rgb & 0xFF;

                destRaster.setPixel(x, y, pixelComp);
            }
        }
        return dest;
    }
}
