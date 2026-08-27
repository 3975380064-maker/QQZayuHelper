package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public final class MultiBrowseCarouselStrategy extends CarouselStrategy {
    private final boolean forceCompactArrangement;
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {1, 0};
    private static final int[] MEDIUM_COUNTS_COMPACT = {0};

    public MultiBrowseCarouselStrategy() {
        this(false);
    }

    public MultiBrowseCarouselStrategy(boolean z) {
        this.forceCompactArrangement = z;
    }

    @Override // com.google.android.material.carousel.CarouselStrategy
    KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view) {
        float containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        float f = containerHeight;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f2 = layoutParams.topMargin + layoutParams.bottomMargin;
        float measuredHeight = view.getMeasuredHeight();
        if (carousel.isHorizontal()) {
            f2 = layoutParams.leftMargin + layoutParams.rightMargin;
            measuredHeight = view.getMeasuredWidth();
        }
        float f3 = f2;
        float smallSizeMin = CarouselStrategyHelper.getSmallSizeMin(view.getContext()) + f3;
        float smallSizeMax = CarouselStrategyHelper.getSmallSizeMax(view.getContext()) + f3;
        float fMin = Math.min(measuredHeight + f3, f);
        float fClamp = MathUtils.clamp((measuredHeight / 3.0f) + f3, CarouselStrategyHelper.getSmallSizeMin(view.getContext()) + f3, CarouselStrategyHelper.getSmallSizeMax(view.getContext()) + f3);
        float f4 = (fMin + fClamp) / 2.0f;
        int[] iArr = SMALL_COUNTS;
        int[] iArr2 = this.forceCompactArrangement ? MEDIUM_COUNTS_COMPACT : MEDIUM_COUNTS;
        int iMax = (int) Math.max(1.0d, Math.floor(((f - (CarouselStrategyHelper.maxValue(iArr2) * f4)) - (CarouselStrategyHelper.maxValue(iArr) * smallSizeMax)) / fMin));
        int iCeil = (int) Math.ceil(f / fMin);
        int i = (iCeil - iMax) + 1;
        int[] iArr3 = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr3[i2] = iCeil - i2;
        }
        return CarouselStrategyHelper.createLeftAlignedKeylineState(view.getContext(), f3, f, Arrangement.findLowestCostArrangement(f, fClamp, smallSizeMin, smallSizeMax, iArr, f4, iArr2, fMin, iArr3));
    }
}
