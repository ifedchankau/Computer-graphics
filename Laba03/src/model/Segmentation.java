package model;

import view.Image;

import java.awt.image.BufferedImage;

/**
 * Ilya Fedcnhenkov, 13 group
 * Variant 4
 */

public class Segmentation {

    public static BufferedImage Segmented(BufferedImage image) {
        BufferedImage processImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        double value = 0;
        int pixelAmount = image.getHeight() * image.getWidth();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int pixel = image.getRGB(i, j);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                int a = (pixel >> 24) & 0xff;

                int avg = (r + g + b) / 3;

                pixel = (a << 24) | (avg << 16) | (avg << 8) | avg;

                value += (double) pixel / pixelAmount;

                processImage.setRGB(i, j, pixel);
            }
        }

        for (int i = 0; i < processImage.getWidth(); i++) {
            for (int j = 0; j < processImage.getHeight(); j++) {
                int p = processImage.getRGB(i, j);

                if (p > value) {
                    processImage.setRGB(i, j, Image.colorToRGB(255, 255, 255, 255));
                } else {
                    processImage.setRGB(i, j, Image.colorToRGB(255, 0, 0, 0));
                }
            }
        }

        return processImage;
    }
}
