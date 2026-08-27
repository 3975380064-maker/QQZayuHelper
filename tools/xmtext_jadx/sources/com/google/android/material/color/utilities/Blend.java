package com.google.android.material.color.utilities;

/* loaded from: classes.dex */
public class Blend {
    private Blend() {
    }

    public static int harmonize(int i, int i2) {
        Hct hctFromInt = Hct.fromInt(i);
        Hct hctFromInt2 = Hct.fromInt(i2);
        return Hct.from(MathUtils.sanitizeDegreesDouble(hctFromInt.getHue() + (Math.min(MathUtils.differenceDegrees(hctFromInt.getHue(), hctFromInt2.getHue()) * 0.5d, 15.0d) * MathUtils.rotationDirection(hctFromInt.getHue(), hctFromInt2.getHue()))), hctFromInt.getChroma(), hctFromInt.getTone()).toInt();
    }

    public static int hctHue(int i, int i2, double d) {
        return Hct.from(Cam16.fromInt(cam16Ucs(i, i2, d)).getHue(), Cam16.fromInt(i).getChroma(), ColorUtils.lstarFromArgb(i)).toInt();
    }

    public static int cam16Ucs(int i, int i2, double d) {
        Cam16 cam16FromInt = Cam16.fromInt(i);
        Cam16 cam16FromInt2 = Cam16.fromInt(i2);
        double jstar = cam16FromInt.getJstar();
        double astar = cam16FromInt.getAstar();
        double bstar = cam16FromInt.getBstar();
        return Cam16.fromUcs(jstar + ((cam16FromInt2.getJstar() - jstar) * d), astar + ((cam16FromInt2.getAstar() - astar) * d), bstar + ((cam16FromInt2.getBstar() - bstar) * d)).toInt();
    }
}
