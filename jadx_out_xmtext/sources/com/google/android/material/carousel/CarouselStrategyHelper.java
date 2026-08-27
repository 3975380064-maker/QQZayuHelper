package com.google.android.material.carousel;

import android.content.Context;
import com.google.android.material.R;
import com.google.android.material.carousel.KeylineState;

/* loaded from: classes.dex */
final class CarouselStrategyHelper {
    private CarouselStrategyHelper() {
    }

    static float getExtraSmallSize(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_gone_size);
    }

    static float getSmallSizeMin(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_min);
    }

    static float getSmallSizeMax(Context context) {
        return context.getResources().getDimension(R.dimen.m3_carousel_small_item_size_max);
    }

    static KeylineState createLeftAlignedKeylineState(Context context, float f, float f2, Arrangement arrangement) {
        float extraSmallSize = getExtraSmallSize(context) + f;
        float f3 = extraSmallSize / 2.0f;
        float f4 = 0.0f - f3;
        float f5 = (arrangement.largeSize / 2.0f) + 0.0f;
        float fMax = (Math.max(0, arrangement.largeCount - 1) * arrangement.largeSize) + f5;
        float f6 = (arrangement.largeSize / 2.0f) + fMax;
        if (arrangement.mediumCount > 0) {
            fMax = (arrangement.mediumSize / 2.0f) + f6;
        }
        if (arrangement.mediumCount > 0) {
            f6 = (arrangement.mediumSize / 2.0f) + fMax;
        }
        float f7 = arrangement.smallCount > 0 ? f6 + (arrangement.smallSize / 2.0f) : fMax;
        float f8 = f2 + f3;
        float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(extraSmallSize, arrangement.largeSize, f);
        float childMaskPercentage2 = CarouselStrategy.getChildMaskPercentage(arrangement.smallSize, arrangement.largeSize, f);
        float childMaskPercentage3 = CarouselStrategy.getChildMaskPercentage(arrangement.mediumSize, arrangement.largeSize, f);
        KeylineState.Builder builderAddKeylineRange = new KeylineState.Builder(arrangement.largeSize).addKeyline(f4, childMaskPercentage, extraSmallSize).addKeylineRange(f5, 0.0f, arrangement.largeSize, arrangement.largeCount, true);
        if (arrangement.mediumCount > 0) {
            builderAddKeylineRange.addKeyline(fMax, childMaskPercentage3, arrangement.mediumSize);
        }
        if (arrangement.smallCount > 0) {
            builderAddKeylineRange.addKeylineRange(f7, childMaskPercentage2, arrangement.smallSize, arrangement.smallCount);
        }
        builderAddKeylineRange.addKeyline(f8, childMaskPercentage, extraSmallSize);
        return builderAddKeylineRange.build();
    }

    static int maxValue(int[] iArr) {
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }
}
