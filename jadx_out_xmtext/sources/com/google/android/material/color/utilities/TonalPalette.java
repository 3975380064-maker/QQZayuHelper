package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class TonalPalette {
    Map<Integer, Integer> cache = new HashMap();
    double chroma;
    double hue;
    Hct keyColor;

    public static TonalPalette fromInt(int i) {
        return fromHct(Hct.fromInt(i));
    }

    public static TonalPalette fromHct(Hct hct) {
        return new TonalPalette(hct.getHue(), hct.getChroma(), hct);
    }

    public static TonalPalette fromHueAndChroma(double d, double d2) {
        return new TonalPalette(d, d2, createKeyColor(d, d2));
    }

    private TonalPalette(double d, double d2, Hct hct) {
        this.hue = d;
        this.chroma = d2;
        this.keyColor = hct;
    }

    private static Hct createKeyColor(double d, double d2) {
        Hct hctFrom = Hct.from(d, d2, 50.0d);
        double dAbs = Math.abs(hctFrom.getChroma() - d2);
        for (double d3 = 1.0d; d3 < 50.0d && Math.round(d2) != Math.round(hctFrom.getChroma()); d3 += 1.0d) {
            Hct hctFrom2 = Hct.from(d, d2, 50.0d + d3);
            double dAbs2 = Math.abs(hctFrom2.getChroma() - d2);
            if (dAbs2 < dAbs) {
                dAbs = dAbs2;
                hctFrom = hctFrom2;
            }
            Hct hctFrom3 = Hct.from(d, d2, 50.0d - d3);
            double dAbs3 = Math.abs(hctFrom3.getChroma() - d2);
            if (dAbs3 < dAbs) {
                dAbs = dAbs3;
                hctFrom = hctFrom3;
            }
        }
        return hctFrom;
    }

    public int tone(int i) {
        Integer numValueOf = this.cache.get(Integer.valueOf(i));
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(Hct.from(this.hue, this.chroma, i).toInt());
            this.cache.put(Integer.valueOf(i), numValueOf);
        }
        return numValueOf.intValue();
    }

    public Hct getHct(double d) {
        return Hct.from(this.hue, this.chroma, d);
    }

    public double getChroma() {
        return this.chroma;
    }

    public double getHue() {
        return this.hue;
    }

    public Hct getKeyColor() {
        return this.keyColor;
    }
}
