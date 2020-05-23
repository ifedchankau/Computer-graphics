package model;

import view.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Ilya Fedcnhenkov, 13 group
 * Variant 4
 */

public class HistogramAlignment{

    public static BufferedImage align(BufferedImage original) {

        BufferedImage histogramEQ = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        ArrayList<int[]> listLUT = histogramAlignmentLUT(original);

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                int red = listLUT.get(0)[new Color(original.getRGB(i, j)).getRed()];
                int green = listLUT.get(1)[new Color(original.getRGB(i, j)).getGreen()];
                int blue = listLUT.get(2)[new Color(original.getRGB(i, j)).getBlue()];
                int color = Image.colorToRGB(alpha, red, green, blue);
                histogramEQ.setRGB(i, j, color);
            }
        }
        return histogramEQ;
    }

    private static ArrayList<int[]> histogramAlignmentLUT(BufferedImage input) {
        ArrayList<int[]> imageHist = Image.imageHistogram(input);
        ArrayList<int[]> imageLUT = new ArrayList();

        int[] rhistogram = new int[256];
        int[] ghistogram = new int[256];
        int[] bhistogram = new int[256];

        for (int i = 0; i < rhistogram.length; i++)
            rhistogram[i] = 0;
        for (int i = 0; i < ghistogram.length; i++)
            ghistogram[i] = 0;
        for (int i = 0; i < bhistogram.length; i++)
            bhistogram[i] = 0;

        long sumr = 0;
        long sumg = 0;
        long sumb = 0;

        float scale_factor = (float) (255.0 / (input.getWidth() * input.getHeight()));

        for (int i = 0; i < rhistogram.length; i++) {
            sumr += imageHist.get(0)[i];
            int valr = (int) (sumr * scale_factor);
            if (valr > 255) {
                rhistogram[i] = 255;
            } else rhistogram[i] = valr;

            sumg += imageHist.get(1)[i];
            int valg = (int) (sumg * scale_factor);
            if (valg > 255) {
                ghistogram[i] = 255;
            } else ghistogram[i] = valg;

            sumb += imageHist.get(2)[i];
            int valb = (int) (sumb * scale_factor);
            if (valb > 255) {
                bhistogram[i] = 255;
            } else bhistogram[i] = valb;
        }

        imageLUT.add(rhistogram);
        imageLUT.add(ghistogram);
        imageLUT.add(bhistogram);

        return imageLUT;
    }
}
