package com.google.android.material.carousel;

import androidx.core.math.MathUtils;

/* loaded from: classes.dex */
final class Arrangement {
    private static final float MEDIUM_ITEM_FLEX_PERCENTAGE = 0.1f;
    final float cost;
    final int largeCount;
    float largeSize;
    final int mediumCount;
    float mediumSize;
    final int priority;
    final int smallCount;
    float smallSize;

    private float calculateLargeSize(float f, int i, float f2, int i2, int i3) {
        if (i <= 0) {
            f2 = 0.0f;
        }
        float f3 = i2 / 2.0f;
        return (f - ((i + f3) * f2)) / (i3 + f3);
    }

    Arrangement(int i, float f, float f2, float f3, int i2, float f4, int i3, float f5, int i4, float f6) {
        this.priority = i;
        this.smallSize = MathUtils.clamp(f, f2, f3);
        this.smallCount = i2;
        this.mediumSize = f4;
        this.mediumCount = i3;
        this.largeSize = f5;
        this.largeCount = i4;
        fit(f6, f2, f3, f5);
        this.cost = cost(f5);
    }

    public String toString() {
        return "Arrangement [priority=" + this.priority + ", smallCount=" + this.smallCount + ", smallSize=" + this.smallSize + ", mediumCount=" + this.mediumCount + ", mediumSize=" + this.mediumSize + ", largeCount=" + this.largeCount + ", largeSize=" + this.largeSize + ", cost=" + this.cost + "]";
    }

    private float getSpace() {
        return (this.largeSize * this.largeCount) + (this.mediumSize * this.mediumCount) + (this.smallSize * this.smallCount);
    }

    private void fit(float f, float f2, float f3, float f4) {
        float space = f - getSpace();
        int i = this.smallCount;
        if (i > 0 && space > 0.0f) {
            float f5 = this.smallSize;
            this.smallSize = f5 + Math.min(space / i, f3 - f5);
        } else if (i > 0 && space < 0.0f) {
            float f6 = this.smallSize;
            this.smallSize = f6 + Math.max(space / i, f2 - f6);
        }
        float fCalculateLargeSize = calculateLargeSize(f, this.smallCount, this.smallSize, this.mediumCount, this.largeCount);
        this.largeSize = fCalculateLargeSize;
        float f7 = (this.smallSize + fCalculateLargeSize) / 2.0f;
        this.mediumSize = f7;
        int i2 = this.mediumCount;
        if (i2 <= 0 || fCalculateLargeSize == f4) {
            return;
        }
        float f8 = (f4 - fCalculateLargeSize) * this.largeCount;
        float fMin = Math.min(Math.abs(f8), f7 * 0.1f * i2);
        if (f8 > 0.0f) {
            this.mediumSize -= fMin / this.mediumCount;
            this.largeSize += fMin / this.largeCount;
        } else {
            this.mediumSize += fMin / this.mediumCount;
            this.largeSize -= fMin / this.largeCount;
        }
    }

    private boolean isValid() {
        int i = this.largeCount;
        if (i <= 0 || this.smallCount <= 0 || this.mediumCount <= 0) {
            return i <= 0 || this.smallCount <= 0 || this.largeSize > this.smallSize;
        }
        float f = this.largeSize;
        float f2 = this.mediumSize;
        return f > f2 && f2 > this.smallSize;
    }

    private float cost(float f) {
        if (isValid()) {
            return Math.abs(f - this.largeSize) * this.priority;
        }
        return Float.MAX_VALUE;
    }

    static Arrangement findLowestCostArrangement(float f, float f2, float f3, float f4, int[] iArr, float f5, int[] iArr2, float f6, int[] iArr3) {
        Arrangement arrangement = null;
        int i = 1;
        for (int i2 : iArr3) {
            int length = iArr2.length;
            int i3 = 0;
            while (i3 < length) {
                int i4 = iArr2[i3];
                int length2 = iArr.length;
                int i5 = 0;
                while (i5 < length2) {
                    int i6 = length;
                    int i7 = i3;
                    int i8 = i;
                    int i9 = length2;
                    int i10 = i5;
                    Arrangement arrangement2 = new Arrangement(i8, f2, f3, f4, iArr[i5], f5, i4, f6, i2, f);
                    if (arrangement == null || arrangement2.cost < arrangement.cost) {
                        if (arrangement2.cost == 0.0f) {
                            return arrangement2;
                        }
                        arrangement = arrangement2;
                    }
                    int i11 = i8 + 1;
                    i5 = i10 + 1;
                    i3 = i7;
                    i = i11;
                    length = i6;
                    length2 = i9;
                }
                i3++;
                i = i;
                length = length;
            }
        }
        return arrangement;
    }
}
