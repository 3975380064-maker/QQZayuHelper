package com.google.android.material.transition.platform;

import android.graphics.RectF;

/* loaded from: classes.dex */
class FitModeEvaluators {
    private static final FitModeEvaluator WIDTH = new FitModeEvaluator() { // from class: com.google.android.material.transition.platform.FitModeEvaluators.1
        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float fLerp = TransitionUtils.lerp(f4, f6, f2, f3, f, true);
            float f8 = fLerp / f4;
            float f9 = fLerp / f6;
            return new FitModeResult(f8, f9, fLerp, f5 * f8, fLerp, f7 * f9);
        }

        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartHeight > fitModeResult.currentEndHeight;
        }

        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
            rectF.bottom -= Math.abs(fitModeResult.currentEndHeight - fitModeResult.currentStartHeight) * f;
        }
    };
    private static final FitModeEvaluator HEIGHT = new FitModeEvaluator() { // from class: com.google.android.material.transition.platform.FitModeEvaluators.2
        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public FitModeResult evaluate(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
            float fLerp = TransitionUtils.lerp(f5, f7, f2, f3, f, true);
            float f8 = fLerp / f5;
            float f9 = fLerp / f7;
            return new FitModeResult(f8, f9, f4 * f8, fLerp, f6 * f9, fLerp);
        }

        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public boolean shouldMaskStartBounds(FitModeResult fitModeResult) {
            return fitModeResult.currentStartWidth > fitModeResult.currentEndWidth;
        }

        @Override // com.google.android.material.transition.platform.FitModeEvaluator
        public void applyMask(RectF rectF, float f, FitModeResult fitModeResult) {
            float fAbs = (Math.abs(fitModeResult.currentEndWidth - fitModeResult.currentStartWidth) / 2.0f) * f;
            rectF.left += fAbs;
            rectF.right -= fAbs;
        }
    };

    static FitModeEvaluator get(int i, boolean z, RectF rectF, RectF rectF2) {
        if (i == 0) {
            return shouldAutoFitToWidth(z, rectF, rectF2) ? WIDTH : HEIGHT;
        }
        if (i == 1) {
            return WIDTH;
        }
        if (i == 2) {
            return HEIGHT;
        }
        throw new IllegalArgumentException("Invalid fit mode: " + i);
    }

    private static boolean shouldAutoFitToWidth(boolean z, RectF rectF, RectF rectF2) {
        float fWidth = rectF.width();
        float fHeight = rectF.height();
        float fWidth2 = rectF2.width();
        float fHeight2 = rectF2.height();
        return z ? (fHeight2 * fWidth) / fWidth2 >= fHeight : (fWidth2 * fHeight) / fWidth >= fHeight2;
    }

    private FitModeEvaluators() {
    }
}
