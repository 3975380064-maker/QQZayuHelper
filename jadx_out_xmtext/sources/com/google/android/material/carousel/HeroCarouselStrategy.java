package com.google.android.material.carousel;

import android.view.View;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class HeroCarouselStrategy extends CarouselStrategy {
    private static final int[] SMALL_COUNTS = {1};
    private static final int[] MEDIUM_COUNTS = {0, 1};

    @Override // com.google.android.material.carousel.CarouselStrategy
    KeylineState onFirstChildMeasuredWithMargins(Carousel carousel, View view) {
        int containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        float f = layoutParams.topMargin + layoutParams.bottomMargin;
        float measuredWidth = view.getMeasuredWidth() * 2;
        if (carousel.isHorizontal()) {
            f = layoutParams.leftMargin + layoutParams.rightMargin;
            measuredWidth = view.getMeasuredHeight() * 2;
        }
        float smallSizeMin = CarouselStrategyHelper.getSmallSizeMin(view.getContext()) + f;
        float smallSizeMax = CarouselStrategyHelper.getSmallSizeMax(view.getContext()) + f;
        float f2 = containerHeight;
        float fMin = Math.min(measuredWidth + f, f2);
        float fClamp = MathUtils.clamp((measuredWidth / 3.0f) + f, CarouselStrategyHelper.getSmallSizeMin(view.getContext()) + f, CarouselStrategyHelper.getSmallSizeMax(view.getContext()) + f);
        float f3 = (fMin + fClamp) / 2.0f;
        int iMax = (int) Math.max(1.0d, Math.floor((f2 - (CarouselStrategyHelper.maxValue(SMALL_COUNTS) * smallSizeMax)) / fMin));
        int iCeil = (((int) Math.ceil(f2 / fMin)) - iMax) + 1;
        int[] iArr = new int[iCeil];
        for (int i = 0; i < iCeil; i++) {
            iArr[i] = iMax + i;
        }
        return CarouselStrategyHelper.createLeftAlignedKeylineState(view.getContext(), f, f2, Arrangement.findLowestCostArrangement(f2, fClamp, smallSizeMin, smallSizeMax, SMALL_COUNTS, f3, MEDIUM_COUNTS, fMin, iArr));
    }
}
