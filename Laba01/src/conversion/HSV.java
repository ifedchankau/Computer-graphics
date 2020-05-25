package conversion;

import exception.OutOfBoundsException;

/**
 * Ilya Fedchenkov, group 13
 * Variant 4
 */

public class HSV {
	private double[] hsv = new double[3];

	public static final double[] lowerBounds = { 0.0, 0.0, 0.0 };
	public static final double[] upperBounds = { 360.0, 100.0, 100.0 };

	public HSV(double x, double y, double z) {
            hsv[0] = x;
            hsv[1] = y;
            hsv[2] = z;
	}
        
        public double[] getHsv() {
            return hsv;
        }

	public double getH() {
            return hsv[0];
	}

	public double getS() {
            return hsv[1];
	}

	public double getV() {
            return hsv[2];
	}

	public void trim() throws OutOfBoundsException {
            Boolean trimmed = false;

            for (int i = 0; i < 3; ++i) {
                    if (hsv[i] < lowerBounds[i]) {
                            trimmed = true;
                            hsv[i] = lowerBounds[i];
                    } else if (hsv[i] > upperBounds[i]) {
                            trimmed = true;
                            hsv[i] = upperBounds[i];
                    }
            }

            if (trimmed) {
                    throw new OutOfBoundsException(toString());
            }
	}

	public String toString() {
            return String.format("(%.0f, %.0f, %.0f)", hsv[0], hsv[1], hsv[2]);
	}
}
