package com.google.android.material.color.utilities;

/* loaded from: classes.dex */
public final class Cam16 {
    private final double astar;
    private final double bstar;
    private final double chroma;
    private final double hue;
    private final double j;
    private final double jstar;
    private final double m;
    private final double q;
    private final double s;
    private final double[] tempArray = {0.0d, 0.0d, 0.0d};
    static final double[][] XYZ_TO_CAM16RGB = {new double[]{0.401288d, 0.650173d, -0.051461d}, new double[]{-0.250268d, 1.204414d, 0.045854d}, new double[]{-0.002079d, 0.048952d, 0.953127d}};
    static final double[][] CAM16RGB_TO_XYZ = {new double[]{1.8620678d, -1.0112547d, 0.14918678d}, new double[]{0.38752654d, 0.62144744d, -0.00897398d}, new double[]{-0.0158415d, -0.03412294d, 1.0499644d}};

    double distance(Cam16 cam16) {
        double jstar = getJstar() - cam16.getJstar();
        double astar = getAstar() - cam16.getAstar();
        double bstar = getBstar() - cam16.getBstar();
        return Math.pow(Math.sqrt((jstar * jstar) + (astar * astar) + (bstar * bstar)), 0.63d) * 1.41d;
    }

    public double getHue() {
        return this.hue;
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getJ() {
        return this.j;
    }

    public double getQ() {
        return this.q;
    }

    public double getM() {
        return this.m;
    }

    public double getS() {
        return this.s;
    }

    public double getJstar() {
        return this.jstar;
    }

    public double getAstar() {
        return this.astar;
    }

    public double getBstar() {
        return this.bstar;
    }

    private Cam16(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.hue = d;
        this.chroma = d2;
        this.j = d3;
        this.q = d4;
        this.m = d5;
        this.s = d6;
        this.jstar = d7;
        this.astar = d8;
        this.bstar = d9;
    }

    public static Cam16 fromInt(int i) {
        return fromIntInViewingConditions(i, ViewingConditions.DEFAULT);
    }

    static Cam16 fromIntInViewingConditions(int i, ViewingConditions viewingConditions) {
        double dLinearized = ColorUtils.linearized((16711680 & i) >> 16);
        double dLinearized2 = ColorUtils.linearized((65280 & i) >> 8);
        double dLinearized3 = ColorUtils.linearized(i & 255);
        return fromXyzInViewingConditions((0.41233895d * dLinearized) + (0.35762064d * dLinearized2) + (0.18051042d * dLinearized3), (0.2126d * dLinearized) + (0.7152d * dLinearized2) + (0.0722d * dLinearized3), (dLinearized * 0.01932141d) + (dLinearized2 * 0.11916382d) + (dLinearized3 * 0.95034478d), viewingConditions);
    }

    static Cam16 fromXyzInViewingConditions(double d, double d2, double d3, ViewingConditions viewingConditions) {
        double[][] dArr = XYZ_TO_CAM16RGB;
        double[] dArr2 = dArr[0];
        double d4 = (dArr2[0] * d) + (dArr2[1] * d2) + (dArr2[2] * d3);
        double[] dArr3 = dArr[1];
        double d5 = (dArr3[0] * d) + (dArr3[1] * d2) + (dArr3[2] * d3);
        double[] dArr4 = dArr[2];
        double d6 = (dArr4[0] * d) + (dArr4[1] * d2) + (dArr4[2] * d3);
        double d7 = viewingConditions.getRgbD()[0] * d4;
        double d8 = viewingConditions.getRgbD()[1] * d5;
        double d9 = viewingConditions.getRgbD()[2] * d6;
        double dPow = Math.pow((viewingConditions.getFl() * Math.abs(d7)) / 100.0d, 0.42d);
        double dPow2 = Math.pow((viewingConditions.getFl() * Math.abs(d8)) / 100.0d, 0.42d);
        double dPow3 = Math.pow((viewingConditions.getFl() * Math.abs(d9)) / 100.0d, 0.42d);
        double dSignum = ((Math.signum(d7) * 400.0d) * dPow) / (dPow + 27.13d);
        double dSignum2 = ((Math.signum(d8) * 400.0d) * dPow2) / (dPow2 + 27.13d);
        double dSignum3 = ((Math.signum(d9) * 400.0d) * dPow3) / (dPow3 + 27.13d);
        double d10 = (((dSignum * 11.0d) + ((-12.0d) * dSignum2)) + dSignum3) / 11.0d;
        double d11 = ((dSignum + dSignum2) - (dSignum3 * 2.0d)) / 9.0d;
        double d12 = dSignum2 * 20.0d;
        double d13 = (((dSignum * 20.0d) + d12) + (21.0d * dSignum3)) / 20.0d;
        double d14 = (((dSignum * 40.0d) + d12) + dSignum3) / 20.0d;
        double degrees = Math.toDegrees(Math.atan2(d11, d10));
        if (degrees < 0.0d) {
            degrees += 360.0d;
        } else if (degrees >= 360.0d) {
            degrees -= 360.0d;
        }
        double d15 = degrees;
        double radians = Math.toRadians(d15);
        double dPow4 = Math.pow((d14 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ()) * 100.0d;
        double d16 = dPow4 / 100.0d;
        double flRoot = viewingConditions.getFlRoot() * (4.0d / viewingConditions.getC()) * Math.sqrt(d16) * (viewingConditions.getAw() + 4.0d);
        double dPow5 = Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d) * Math.pow(((((((Math.cos(Math.toRadians(d15 < 20.14d ? d15 + 360.0d : d15) + 2.0d) + 3.8d) * 0.25d) * 3846.153846153846d) * viewingConditions.getNc()) * viewingConditions.getNcb()) * Math.hypot(d10, d11)) / (d13 + 0.305d), 0.9d);
        double dSqrt = dPow5 * Math.sqrt(d16);
        double flRoot2 = dSqrt * viewingConditions.getFlRoot();
        double dSqrt2 = Math.sqrt((dPow5 * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0d)) * 50.0d;
        double d17 = (1.7000000000000002d * dPow4) / ((0.007d * dPow4) + 1.0d);
        double dLog1p = Math.log1p(0.0228d * flRoot2) * 43.859649122807014d;
        return new Cam16(d15, dSqrt, dPow4, flRoot, flRoot2, dSqrt2, d17, dLog1p * Math.cos(radians), dLog1p * Math.sin(radians));
    }

    static Cam16 fromJch(double d, double d2, double d3) {
        return fromJchInViewingConditions(d, d2, d3, ViewingConditions.DEFAULT);
    }

    private static Cam16 fromJchInViewingConditions(double d, double d2, double d3, ViewingConditions viewingConditions) {
        double d4 = d / 100.0d;
        double c = (4.0d / viewingConditions.getC()) * Math.sqrt(d4) * (viewingConditions.getAw() + 4.0d) * viewingConditions.getFlRoot();
        double flRoot = d2 * viewingConditions.getFlRoot();
        double dSqrt = Math.sqrt(((d2 / Math.sqrt(d4)) * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0d)) * 50.0d;
        double radians = Math.toRadians(d3);
        double d5 = (1.7000000000000002d * d) / ((0.007d * d) + 1.0d);
        double dLog1p = Math.log1p(0.0228d * flRoot) * 43.859649122807014d;
        return new Cam16(d3, d2, d, c, flRoot, dSqrt, d5, dLog1p * Math.cos(radians), dLog1p * Math.sin(radians));
    }

    public static Cam16 fromUcs(double d, double d2, double d3) {
        return fromUcsInViewingConditions(d, d2, d3, ViewingConditions.DEFAULT);
    }

    public static Cam16 fromUcsInViewingConditions(double d, double d2, double d3, ViewingConditions viewingConditions) {
        double dExpm1 = (Math.expm1(Math.hypot(d2, d3) * 0.0228d) / 0.0228d) / viewingConditions.getFlRoot();
        double dAtan2 = Math.atan2(d3, d2) * 57.29577951308232d;
        if (dAtan2 < 0.0d) {
            dAtan2 += 360.0d;
        }
        return fromJchInViewingConditions(d / (1.0d - ((d - 100.0d) * 0.007d)), dExpm1, dAtan2, viewingConditions);
    }

    public int toInt() {
        return viewed(ViewingConditions.DEFAULT);
    }

    int viewed(ViewingConditions viewingConditions) {
        double[] dArrXyzInViewingConditions = xyzInViewingConditions(viewingConditions, this.tempArray);
        return ColorUtils.argbFromXyz(dArrXyzInViewingConditions[0], dArrXyzInViewingConditions[1], dArrXyzInViewingConditions[2]);
    }

    double[] xyzInViewingConditions(ViewingConditions viewingConditions, double[] dArr) {
        double dPow = Math.pow(((getChroma() == 0.0d || getJ() == 0.0d) ? 0.0d : getChroma() / Math.sqrt(getJ() / 100.0d)) / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        double radians = Math.toRadians(getHue());
        double dCos = (Math.cos(2.0d + radians) + 3.8d) * 0.25d;
        double aw = viewingConditions.getAw() * Math.pow(getJ() / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ());
        double nc = dCos * 3846.153846153846d * viewingConditions.getNc() * viewingConditions.getNcb();
        double nbb = aw / viewingConditions.getNbb();
        double dSin = Math.sin(radians);
        double dCos2 = Math.cos(radians);
        double d = (((0.305d + nbb) * 23.0d) * dPow) / (((nc * 23.0d) + ((11.0d * dPow) * dCos2)) + ((dPow * 108.0d) * dSin));
        double d2 = dCos2 * d;
        double d3 = d * dSin;
        double d4 = nbb * 460.0d;
        double d5 = (((451.0d * d2) + d4) + (288.0d * d3)) / 1403.0d;
        double d6 = ((d4 - (891.0d * d2)) - (261.0d * d3)) / 1403.0d;
        double d7 = ((d4 - (d2 * 220.0d)) - (d3 * 6300.0d)) / 1403.0d;
        double dSignum = Math.signum(d5) * (100.0d / viewingConditions.getFl()) * Math.pow(Math.max(0.0d, (Math.abs(d5) * 27.13d) / (400.0d - Math.abs(d5))), 2.380952380952381d);
        double dSignum2 = Math.signum(d6) * (100.0d / viewingConditions.getFl()) * Math.pow(Math.max(0.0d, (Math.abs(d6) * 27.13d) / (400.0d - Math.abs(d6))), 2.380952380952381d);
        double dSignum3 = Math.signum(d7) * (100.0d / viewingConditions.getFl()) * Math.pow(Math.max(0.0d, (Math.abs(d7) * 27.13d) / (400.0d - Math.abs(d7))), 2.380952380952381d);
        double d8 = dSignum / viewingConditions.getRgbD()[0];
        double d9 = dSignum2 / viewingConditions.getRgbD()[1];
        double d10 = dSignum3 / viewingConditions.getRgbD()[2];
        double[][] dArr2 = CAM16RGB_TO_XYZ;
        double[] dArr3 = dArr2[0];
        double d11 = (dArr3[0] * d8) + (dArr3[1] * d9) + (dArr3[2] * d10);
        double[] dArr4 = dArr2[1];
        double d12 = (dArr4[0] * d8) + (dArr4[1] * d9) + (dArr4[2] * d10);
        double[] dArr5 = dArr2[2];
        double d13 = (d8 * dArr5[0]) + (d9 * dArr5[1]) + (d10 * dArr5[2]);
        if (dArr != null) {
            dArr[0] = d11;
            dArr[1] = d12;
            dArr[2] = d13;
            return dArr;
        }
        return new double[]{d11, d12, d13};
    }
}
