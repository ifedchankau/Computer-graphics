package view;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Ilya Fedcnhenkov, 13 group
 * Variant 4
 */

public class Image {

    public static ArrayList<int[]> imageHistogram(BufferedImage input) {

        int[] redHistogram = new int[256];
        int[] greenHistogram = new int[256];
        int[] blueHistogram = new int[256];

        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                int red = new java.awt.Color(input.getRGB(i, j)).getRed();
                int green = new java.awt.Color(input.getRGB(i, j)).getGreen();
                int blue = new java.awt.Color(input.getRGB(i, j)).getBlue();
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
    public static int colorToRGB(int alpha, int red, int green, int blue) {
        int color = 0;
        color += alpha;
        color = color << 8;
        color += red;
        color = color << 8;
        color += green;
        color = color << 8;
        color += blue;
        return color;
    }
}
