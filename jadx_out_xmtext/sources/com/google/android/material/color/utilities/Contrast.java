package com.google.android.material.color.utilities;

/* loaded from: classes.dex */
public final class Contrast {
    private static final double CONTRAST_RATIO_EPSILON = 0.04d;
    private static final double LUMINANCE_GAMUT_MAP_TOLERANCE = 0.4d;
    public static final double RATIO_30 = 3.0d;
    public static final double RATIO_45 = 4.5d;
    public static final double RATIO_70 = 7.0d;
    public static final double RATIO_MAX = 21.0d;
    public static final double RATIO_MIN = 1.0d;

    private Contrast() {
    }

    public static double ratioOfYs(double d, double d2) {
        double dMax = Math.max(d, d2);
        if (dMax != d2) {
            d = d2;
        }
        return (dMax + 5.0d) / (d + 5.0d);
    }

    public static double ratioOfTones(double d, double d2) {
        return ratioOfYs(ColorUtils.yFromLstar(d), ColorUtils.yFromLstar(d2));
    }

    public static double lighter(double d, double d2) {
        if (d >= 0.0d && d <= 100.0d) {
            double dYFromLstar = ColorUtils.yFromLstar(d);
            double d3 = ((dYFromLstar + 5.0d) * d2) - 5.0d;
            if (d3 >= 0.0d && d3 <= 100.0d) {
                double dRatioOfYs = ratioOfYs(d3, dYFromLstar);
                double dAbs = Math.abs(dRatioOfYs - d2);
                if (dRatioOfYs < d2 && dAbs > CONTRAST_RATIO_EPSILON) {
                    return -1.0d;
                }
                double dLstarFromY = ColorUtils.lstarFromY(d3) + LUMINANCE_GAMUT_MAP_TOLERANCE;
                if (dLstarFromY >= 0.0d && dLstarFromY <= 100.0d) {
                    return dLstarFromY;
                }
            }
        }
        return -1.0d;
    }

    public static double lighterUnsafe(double d, double d2) {
        double dLighter = lighter(d, d2);
        if (dLighter < 0.0d) {
            return 100.0d;
        }
        return dLighter;
    }

    public static double darker(double d, double d2) {
        if (d >= 0.0d && d <= 100.0d) {
            double dYFromLstar = ColorUtils.yFromLstar(d);
            double d3 = ((dYFromLstar + 5.0d) / d2) - 5.0d;
            if (d3 >= 0.0d && d3 <= 100.0d) {
                double dRatioOfYs = ratioOfYs(dYFromLstar, d3);
                double dAbs = Math.abs(dRatioOfYs - d2);
                if (dRatioOfYs < d2 && dAbs > CONTRAST_RATIO_EPSILON) {
                    return -1.0d;
                }
                double dLstarFromY = ColorUtils.lstarFromY(d3) - LUMINANCE_GAMUT_MAP_TOLERANCE;
                if (dLstarFromY >= 0.0d && dLstarFromY <= 100.0d) {
                    return dLstarFromY;
                }
            }
        }
        return -1.0d;
    }

    public static double darkerUnsafe(double d, double d2) {
        return Math.max(0.0d, darker(d, d2));
    }
}
