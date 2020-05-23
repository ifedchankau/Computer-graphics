package model;

import view.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Ilya Fedcnhenkov, 13 group
 * Variant 4
 */

public class LinearContrast {

    public static BufferedImage whiteBalance(BufferedImage image) {

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        ArrayList<int[]> imageHist = imageHistogram(image);
        double emissionFactor = 0.01;
        int[][] hists = new int[3][256];

        int[] redHistogram = imageHist.get(0);
        int[] greenHistogram = imageHist.get(1);
        int[] blueHistogram = imageHist.get(2);


        hists[0] = redHistogram;
        hists[1] = greenHistogram;
        hists[2] = blueHistogram;

        int totalImage = image.getWidth() * image.getHeight();

        int[] min = new int[3];
        int[] max = new int[3];

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 255; ++j)
                hists[i][j + 1] += hists[i][j];
            min[i] = 0;
            max[i] = 255;
            while (hists[i][min[i]] <emissionFactor * totalImage)
                min[i] += 1;
            while (hists[i][max[i]] > (1 - emissionFactor) * totalImage)
                max[i] -= 1;
            if (max[i] < 255 - 1)
                max[i] += 1;
        }

        for (int y = 0; y < image.getWidth(); ++y) {
            for (int x = 0; x < image.getHeight(); ++x) {

                int[] rgbValues = new int[3];

                for (int j = 0; j < 3; ++j) {
                    int val = 0;
                    int red = new Color(image.getRGB(y, x)).getRed();
                    int green = new Color(image.getRGB(y, x)).getGreen();
                    int blue = new Color(image.getRGB(y, x)).getBlue();

                    if (j == 0) val = red;
                    if (j == 1) val = green;
                    if (j == 2) val = blue;

                    if (val < min[j])
                        val = min[j];
                    if (val > max[j])
                        val = max[j];

                    rgbValues[j] = (int) ((val - min[j]) * 255.0 / (max[j] - min[j]));
                }
                int alpha = new Color(image.getRGB(y, x)).getAlpha();
                int newPixel = Image.colorToRGB(alpha, rgbValues[0], rgbValues[1], rgbValues[2]);
                newImage.setRGB(y, x, newPixel);
            }
        }

        return newImage;
    }

    private static ArrayList<int[]> imageHistogram(BufferedImage input) {
        int[] redHistogram = new int[256];
        int[] greenHistogram = new int[256];
        int[] blueHistogram = new int[256];

        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {

                int red = new Color(input.getRGB(i, j)).getRed();
                int green = new Color(input.getRGB(i, j)).getGreen();
                int blue = new Color(input.getRGB(i, j)).getBlue();

                redHistogram[red]++;
                greenHistogram[green]++;
                blueHistogram[blue]++;
            }
        }

        ArrayList<int[]> hist = new ArrayList<>();
        hist.add(redHistogram);
        hist.add(greenHistogram);
        hist.add(blueHistogram);

        return hist;
    }
}
