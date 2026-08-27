package com.google.android.material.color.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/* loaded from: classes.dex */
public final class TemperatureCache {
    private final Hct input;
    private Hct precomputedComplement;
    private List<Hct> precomputedHctsByHue;
    private List<Hct> precomputedHctsByTemp;
    private Map<Hct, Double> precomputedTempsByHct;

    private static boolean isBetween(double d, double d2, double d3) {
        return d2 < d3 ? d2 <= d && d <= d3 : d2 <= d || d <= d3;
    }

    private TemperatureCache() {
        throw new UnsupportedOperationException();
    }

    public TemperatureCache(Hct hct) {
        this.input = hct;
    }

    public Hct getComplement() {
        double d;
        Hct hct = this.precomputedComplement;
        if (hct != null) {
            return hct;
        }
        double hue = getColdest().getHue();
        double dDoubleValue = getTempsByHct().get(getColdest()).doubleValue();
        double hue2 = getWarmest().getHue();
        double dDoubleValue2 = getTempsByHct().get(getWarmest()).doubleValue() - dDoubleValue;
        boolean zIsBetween = isBetween(this.input.getHue(), hue, hue2);
        double d2 = zIsBetween ? hue2 : hue;
        double d3 = zIsBetween ? hue : hue2;
        Hct hct2 = getHctsByHue().get((int) Math.round(this.input.getHue()));
        double d4 = 1.0d;
        double relativeTemperature = 1.0d - getRelativeTemperature(this.input);
        double d5 = 1000.0d;
        double d6 = 0.0d;
        while (d6 <= 360.0d) {
            double dSanitizeDegreesDouble = MathUtils.sanitizeDegreesDouble((d4 * d6) + d2);
            if (isBetween(dSanitizeDegreesDouble, d2, d3)) {
                d = d4;
                Hct hct3 = getHctsByHue().get((int) Math.round(dSanitizeDegreesDouble));
                double dAbs = Math.abs(relativeTemperature - ((getTempsByHct().get(hct3).doubleValue() - dDoubleValue) / dDoubleValue2));
                if (dAbs < d5) {
                    hct2 = hct3;
                    d5 = dAbs;
                }
            } else {
                d = d4;
            }
            d6 += d;
            d4 = d;
        }
        this.precomputedComplement = hct2;
        return hct2;
    }

    public List<Hct> getAnalogousColors() {
        return getAnalogousColors(5, 12);
    }

    public List<Hct> getAnalogousColors(int i, int i2) {
        int iRound = (int) Math.round(this.input.getHue());
        Hct hct = getHctsByHue().get(iRound);
        double relativeTemperature = getRelativeTemperature(hct);
        ArrayList arrayList = new ArrayList();
        arrayList.add(hct);
        double dAbs = 0.0d;
        double dAbs2 = 0.0d;
        int i3 = 0;
        while (i3 < 360) {
            double relativeTemperature2 = getRelativeTemperature(getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i3)));
            dAbs2 += Math.abs(relativeTemperature2 - relativeTemperature);
            i3++;
            relativeTemperature = relativeTemperature2;
        }
        double d = dAbs2 / i2;
        double relativeTemperature3 = getRelativeTemperature(hct);
        int i4 = 1;
        while (true) {
            if (arrayList.size() >= i2) {
                break;
            }
            Hct hct2 = getHctsByHue().get(MathUtils.sanitizeDegreesInt(iRound + i4));
            double relativeTemperature4 = getRelativeTemperature(hct2);
            dAbs += Math.abs(relativeTemperature4 - relativeTemperature3);
            boolean z = dAbs >= ((double) arrayList.size()) * d;
            int i5 = 1;
            while (z && arrayList.size() < i2) {
                arrayList.add(hct2);
                int i6 = i4;
                z = dAbs >= ((double) (arrayList.size() + i5)) * d;
                i5++;
                i4 = i6;
            }
            i4++;
            if (i4 > 360) {
                while (arrayList.size() < i2) {
                    arrayList.add(hct2);
                }
            } else {
                relativeTemperature3 = relativeTemperature4;
            }
        }
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.input);
        int iFloor = (int) Math.floor((i - 1.0d) / 2.0d);
        for (int i7 = 1; i7 < iFloor + 1; i7++) {
            int size = 0 - i7;
            while (size < 0) {
                size += arrayList.size();
            }
            if (size >= arrayList.size()) {
                size %= arrayList.size();
            }
            arrayList2.add(0, (Hct) arrayList.get(size));
        }
        int i8 = i - iFloor;
        for (int i9 = 1; i9 < i8; i9++) {
            int size2 = i9;
            while (size2 < 0) {
                size2 += arrayList.size();
            }
            if (size2 >= arrayList.size()) {
                size2 %= arrayList.size();
            }
            arrayList2.add((Hct) arrayList.get(size2));
        }
        return arrayList2;
    }

    public double getRelativeTemperature(Hct hct) {
        double dDoubleValue = getTempsByHct().get(getWarmest()).doubleValue() - getTempsByHct().get(getColdest()).doubleValue();
        double dDoubleValue2 = getTempsByHct().get(hct).doubleValue() - getTempsByHct().get(getColdest()).doubleValue();
        if (dDoubleValue == 0.0d) {
            return 0.5d;
        }
        return dDoubleValue2 / dDoubleValue;
    }

    public static double rawTemperature(Hct hct) {
        double[] dArrLabFromArgb = ColorUtils.labFromArgb(hct.toInt());
        return ((Math.pow(Math.hypot(dArrLabFromArgb[1], dArrLabFromArgb[2]), 1.07d) * 0.02d) * Math.cos(Math.toRadians(MathUtils.sanitizeDegreesDouble(MathUtils.sanitizeDegreesDouble(Math.toDegrees(Math.atan2(dArrLabFromArgb[2], dArrLabFromArgb[1]))) - 50.0d)))) - 0.5d;
    }

    private Hct getColdest() {
        return getHctsByTemp().get(0);
    }

    private List<Hct> getHctsByHue() {
        List<Hct> list = this.precomputedHctsByHue;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (double d = 0.0d; d <= 360.0d; d += 1.0d) {
            arrayList.add(Hct.from(d, this.input.getChroma(), this.input.getTone()));
        }
        List<Hct> listUnmodifiableList = Collections.unmodifiableList(arrayList);
        this.precomputedHctsByHue = listUnmodifiableList;
        return listUnmodifiableList;
    }

    private List<Hct> getHctsByTemp() {
        List<Hct> list = this.precomputedHctsByTemp;
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        Collections.sort(arrayList, Comparator.comparing(new Function() { // from class: com.google.android.material.color.utilities.TemperatureCache$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m87lambda$getHctsByTemp$0$comgoogleandroidmaterialcolorutilitiesTemperatureCache((Hct) obj);
            }
        }, new Comparator() { // from class: com.google.android.material.color.utilities.TemperatureCache$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ((Double) obj).compareTo((Double) obj2);
            }
        }));
        this.precomputedHctsByTemp = arrayList;
        return arrayList;
    }

    /* renamed from: lambda$getHctsByTemp$0$com-google-android-material-color-utilities-TemperatureCache, reason: not valid java name */
    /* synthetic */ Double m87lambda$getHctsByTemp$0$comgoogleandroidmaterialcolorutilitiesTemperatureCache(Hct hct) {
        return getTempsByHct().get(hct);
    }

    private Map<Hct, Double> getTempsByHct() {
        Map<Hct, Double> map = this.precomputedTempsByHct;
        if (map != null) {
            return map;
        }
        ArrayList<Hct> arrayList = new ArrayList(getHctsByHue());
        arrayList.add(this.input);
        HashMap map2 = new HashMap();
        for (Hct hct : arrayList) {
            map2.put(hct, Double.valueOf(rawTemperature(hct)));
        }
        this.precomputedTempsByHct = map2;
        return map2;
    }

    private Hct getWarmest() {
        return getHctsByTemp().get(getHctsByTemp().size() - 1);
    }
}
