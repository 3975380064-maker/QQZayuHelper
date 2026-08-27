package com.google.android.material.color.utilities;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/* loaded from: classes.dex */
public final class QuantizerWsmeans {
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_MOVEMENT_DISTANCE = 3.0d;

    private QuantizerWsmeans() {
    }

    private static final class Distance implements Comparable<Distance> {
        int index = -1;
        double distance = -1.0d;

        Distance() {
        }

        @Override // java.lang.Comparable
        public int compareTo(Distance distance) {
            return Double.valueOf(this.distance).compareTo(Double.valueOf(distance.distance));
        }
    }

    public static Map<Integer, Integer> quantize(int[] iArr, int[] iArr2, int i) {
        boolean z;
        double[] dArr;
        double[] dArr2;
        Random random = new Random(272008L);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        double[][] dArr3 = new double[iArr.length][];
        int[] iArr3 = new int[iArr.length];
        PointProviderLab pointProviderLab = new PointProviderLab();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            z = true;
            if (i2 >= iArr.length) {
                break;
            }
            int i4 = iArr[i2];
            Integer num = (Integer) linkedHashMap.get(Integer.valueOf(i4));
            if (num == null) {
                dArr3[i3] = pointProviderLab.fromInt(i4);
                iArr3[i3] = i4;
                i3++;
                linkedHashMap.put(Integer.valueOf(i4), 1);
            } else {
                linkedHashMap.put(Integer.valueOf(i4), Integer.valueOf(num.intValue() + 1));
            }
            i2++;
        }
        int[] iArr4 = new int[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            iArr4[i5] = ((Integer) linkedHashMap.get(Integer.valueOf(iArr3[i5]))).intValue();
        }
        int iMin = Math.min(i, i3);
        if (iArr2.length != 0) {
            iMin = Math.min(iMin, iArr2.length);
        }
        double[][] dArr4 = new double[iMin][];
        int i6 = 0;
        for (int i7 = 0; i7 < iArr2.length; i7++) {
            dArr4[i7] = pointProviderLab.fromInt(iArr2[i7]);
            i6++;
        }
        int i8 = iMin - i6;
        if (i8 > 0) {
            for (int i9 = 0; i9 < i8; i9++) {
            }
        }
        int[] iArr5 = new int[i3];
        for (int i10 = 0; i10 < i3; i10++) {
            iArr5[i10] = random.nextInt(iMin);
        }
        int[][] iArr6 = new int[iMin][];
        for (int i11 = 0; i11 < iMin; i11++) {
            iArr6[i11] = new int[iMin];
        }
        Distance[][] distanceArr = new Distance[iMin][];
        for (int i12 = 0; i12 < iMin; i12++) {
            distanceArr[i12] = new Distance[iMin];
            for (int i13 = 0; i13 < iMin; i13++) {
                distanceArr[i12][i13] = new Distance();
            }
        }
        int[] iArr7 = new int[iMin];
        int i14 = 0;
        while (i14 < 10) {
            int i15 = 0;
            while (i15 < iMin) {
                int i16 = i15 + 1;
                int i17 = i16;
                while (i17 < iMin) {
                    int[] iArr8 = iArr4;
                    double dDistance = pointProviderLab.distance(dArr4[i15], dArr4[i17]);
                    distanceArr[i17][i15].distance = dDistance;
                    distanceArr[i17][i15].index = i15;
                    distanceArr[i15][i17].distance = dDistance;
                    distanceArr[i15][i17].index = i17;
                    i17++;
                    iArr4 = iArr8;
                    iArr5 = iArr5;
                    z = z;
                }
                int[] iArr9 = iArr4;
                int[] iArr10 = iArr5;
                boolean z2 = z;
                Arrays.sort(distanceArr[i15]);
                for (int i18 = 0; i18 < iMin; i18++) {
                    iArr6[i15][i18] = distanceArr[i15][i18].index;
                }
                iArr4 = iArr9;
                iArr5 = iArr10;
                i15 = i16;
                z = z2;
            }
            int[] iArr11 = iArr4;
            int[] iArr12 = iArr5;
            boolean z3 = z;
            int i19 = 0;
            int i20 = 0;
            while (i19 < i3) {
                double[] dArr5 = dArr3[i19];
                int i21 = iArr12[i19];
                double dDistance2 = pointProviderLab.distance(dArr5, dArr4[i21]);
                int i22 = i19;
                double d = dDistance2;
                int i23 = -1;
                int i24 = 0;
                while (i24 < iMin) {
                    int i25 = i20;
                    int[][] iArr13 = iArr6;
                    if (distanceArr[i21][i24].distance < 4.0d * dDistance2) {
                        double dDistance3 = pointProviderLab.distance(dArr5, dArr4[i24]);
                        if (dDistance3 < d) {
                            d = dDistance3;
                            i23 = i24;
                        }
                    }
                    i24++;
                    iArr6 = iArr13;
                    i20 = i25;
                }
                int i26 = i20;
                int[][] iArr14 = iArr6;
                if (i23 == -1 || Math.abs(Math.sqrt(d) - Math.sqrt(dDistance2)) <= 3.0d) {
                    i20 = i26;
                } else {
                    i20 = i26 + 1;
                    iArr12[i22] = i23;
                }
                i19 = i22 + 1;
                iArr6 = iArr14;
            }
            int[][] iArr15 = iArr6;
            if (i20 == 0 && i14 != 0) {
                break;
            }
            double[] dArr6 = new double[iMin];
            double[] dArr7 = new double[iMin];
            double[] dArr8 = new double[iMin];
            boolean z4 = false;
            Arrays.fill(iArr7, 0);
            int i27 = 0;
            while (i27 < i3) {
                int i28 = iArr12[i27];
                double[] dArr9 = dArr3[i27];
                boolean z5 = z4;
                int i29 = iArr11[i27];
                iArr7[i28] = iArr7[i28] + i29;
                double d2 = i29;
                dArr6[i28] = dArr6[i28] + (dArr9[z5 ? 1 : 0] * d2);
                dArr7[i28] = dArr7[i28] + (dArr9[z3 ? 1 : 0] * d2);
                dArr8[i28] = dArr8[i28] + (dArr9[2] * d2);
                i27++;
                z4 = false;
            }
            int i30 = 0;
            while (i30 < iMin) {
                int i31 = iArr7[i30];
                if (i31 == 0) {
                    dArr4[i30] = new double[]{0.0d, 0.0d, 0.0d};
                    dArr = dArr6;
                    dArr2 = dArr7;
                } else {
                    double d3 = dArr6[i30];
                    dArr = dArr6;
                    dArr2 = dArr7;
                    double d4 = i31;
                    double d5 = d3 / d4;
                    double d6 = dArr2[i30] / d4;
                    double d7 = dArr8[i30] / d4;
                    double[] dArr10 = dArr4[i30];
                    dArr10[0] = d5;
                    dArr10[z3 ? 1 : 0] = d6;
                    dArr10[2] = d7;
                }
                i30++;
                dArr6 = dArr;
                dArr7 = dArr2;
            }
            i14++;
            iArr4 = iArr11;
            iArr5 = iArr12;
            z = z3 ? 1 : 0;
            iArr6 = iArr15;
        }
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (int i32 = 0; i32 < iMin; i32++) {
            int i33 = iArr7[i32];
            if (i33 != 0) {
                int i34 = pointProviderLab.toInt(dArr4[i32]);
                if (!linkedHashMap2.containsKey(Integer.valueOf(i34))) {
                    linkedHashMap2.put(Integer.valueOf(i34), Integer.valueOf(i33));
                }
            }
        }
        return linkedHashMap2;
    }
}
