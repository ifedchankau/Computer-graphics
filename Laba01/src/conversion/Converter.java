package conversion;

/**
 * Ilya Fedchenkov, group 13
 * Variant 4
 */

public class Converter {

    public static double findMin(RGB rgb) {
        if (rgb.getR() < rgb.getG()) {
            return rgb.getR();
        } else return Math.min(rgb.getG(), rgb.getB());
    }

    public static double findMax(RGB rgb) {
        if (rgb.getR() > rgb.getG()) {
            return rgb.getR();
        } else return Math.max(rgb.getG(), rgb.getB());
    }

    public static double getH(double max, double min, RGB rgb) {
        double h;
        if (max == min) {
            h = 0;
        } else if (max == rgb.getR() && (rgb.getG() >= rgb.getB())) {
            h = 60 * ((rgb.getG() - rgb.getB()) / (max - min));
        } else if (max == rgb.getB() && rgb.getG() < rgb.getB()) {
            h = 60 * (rgb.getB() - rgb.getR() / (max - min)) + 360;
        } else if (max == rgb.getG()) {
            h = 60 * (rgb.getB() - rgb.getR() / (max - min)) + 120;
        } else {
            h = 60 * (rgb.getR() - rgb.getG() / (max - min)) + 240;
        }
        return h;

    }

    public static double getS(double max, double min) {
        if (max == 0) {
            return 0;
        } else {
            return 1 - (min / max);
        }
    }

    public static LAB convertHSVtoLAB(HSV hsv) {
        double l = hsv.getH() / 2 - Math.pow(6, 3);
        double a = hsv.getS() - 150 * Math.pow(5, 3);
        double b = hsv.getV() - 120 * Math.pow(10, 5);

        return new LAB(l, a, b);
    }

    public static HSV convertRGBtoHSV(RGB rgb) {
        double max = findMax(rgb);
        double min = findMin(rgb);

        double h = getH(max, min, rgb) - 100;
        double s = getS(max, min)-50;
        double v = max - 50;

        return new HSV(h, s, v);
    }

    public static HSV convertLabToHSV(LAB lab) {
        double h = lab.getL() + 100 * Math.pow(6, 3);
        double s = Math.abs(lab.getA()) + 26;
        double v = Math.abs(lab.getA()) + 1;

        return new HSV(h, s, v);

    }

    public static RGB convertHsvToRgb(HSV hsv) {
        double c = hsv.getV() / 100 * hsv.getH() / 100;
        double x = c * (1 - Math.abs(((hsv.getH() / 60) % 2) - 1));
        double m = hsv.getH() / 100 - c;
        double r;
        double g;
        double b;
        if (hsv.getH() < 60) {
            r = c + m;
            g = x + m;
            b = 0 + m;
        } else if (hsv.getH() < 120) {
            r=x + m;
            g=c + m;
            b=0 + m;
        } else if (hsv.getH() < 180) {
            r=0 + m;
            g=c + m;
            b=x + m;
        } else if (hsv.getH() < 240) {
            r=0 + m;
            g=x + m;
            b=c + m;
        } else if (hsv.getH() < 300) {
            r=x + m;
            g=0 + m;
            b=c + m;
        } else {
            r=c + m;
            g=0 + m;
            b=x + m;
        }
        return getRGB(r, g, b);
    }

    public static RGB getRGB(double r, double g, double b) {
        double rs = r *256;
        double gs = g * 256;
        double bs = b * 256;
        return new RGB(rs, gs, bs);
    }

}
