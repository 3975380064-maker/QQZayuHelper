package com.google.android.material.carousel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.KeylineState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class CarouselLayoutManager extends RecyclerView.LayoutManager implements Carousel, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int HORIZONTAL = 0;
    private static final String TAG = "CarouselLayoutManager";
    public static final int VERTICAL = 1;
    private CarouselStrategy carouselStrategy;
    private int currentFillStartPosition;
    private KeylineState currentKeylineState;
    private final DebugItemDecoration debugItemDecoration;
    private boolean isDebuggingEnabled;
    private KeylineStateList keylineStateList;
    private Map<Integer, KeylineState> keylineStatePositionMap;
    int maxScroll;
    int minScroll;
    private CarouselOrientationHelper orientationHelper;
    int scrollOffset;

    private static int calculateShouldScrollBy(int i, int i2, int i3, int i4) {
        int i5 = i2 + i;
        return i5 < i3 ? i3 - i2 : i5 > i4 ? i4 - i2 : i;
    }

    private static final class ChildCalculations {
        final float center;
        final View child;
        final float offsetCenter;
        final KeylineRange range;

        ChildCalculations(View view, float f, float f2, KeylineRange keylineRange) {
            this.child = view;
            this.center = f;
            this.offsetCenter = f2;
            this.range = keylineRange;
        }
    }

    public CarouselLayoutManager() {
        this(new MultiBrowseCarouselStrategy());
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy) {
        this(carouselStrategy, 0);
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy, int i) {
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        setCarouselStrategy(carouselStrategy);
        setOrientation(i);
    }

    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        setOrientation(getProperties(context, attributeSet, i, i2).orientation);
        setCarouselStrategy(new MultiBrowseCarouselStrategy());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public void setCarouselStrategy(CarouselStrategy carouselStrategy) {
        this.carouselStrategy = carouselStrategy;
        refreshKeylineState();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() <= 0) {
            removeAndRecycleAllViews(recycler);
            this.currentFillStartPosition = 0;
            return;
        }
        boolean zIsLayoutRtl = isLayoutRtl();
        boolean z = this.keylineStateList == null;
        if (z) {
            View viewForPosition = recycler.getViewForPosition(0);
            measureChildWithMargins(viewForPosition, 0, 0);
            KeylineState keylineStateOnFirstChildMeasuredWithMargins = this.carouselStrategy.onFirstChildMeasuredWithMargins(this, viewForPosition);
            if (zIsLayoutRtl) {
                keylineStateOnFirstChildMeasuredWithMargins = KeylineState.reverse(keylineStateOnFirstChildMeasuredWithMargins);
            }
            this.keylineStateList = KeylineStateList.from(this, keylineStateOnFirstChildMeasuredWithMargins);
        }
        int iCalculateStartScroll = calculateStartScroll(this.keylineStateList);
        int iCalculateEndScroll = calculateEndScroll(state, this.keylineStateList);
        int i = zIsLayoutRtl ? iCalculateEndScroll : iCalculateStartScroll;
        this.minScroll = i;
        if (zIsLayoutRtl) {
            iCalculateEndScroll = iCalculateStartScroll;
        }
        this.maxScroll = iCalculateEndScroll;
        if (z) {
            this.scrollOffset = iCalculateStartScroll;
            this.keylineStatePositionMap = this.keylineStateList.getKeylineStateForPositionMap(getItemCount(), this.minScroll, this.maxScroll, isLayoutRtl());
        } else {
            int i2 = this.scrollOffset;
            this.scrollOffset = i2 + calculateShouldScrollBy(0, i2, i, iCalculateEndScroll);
        }
        this.currentFillStartPosition = MathUtils.clamp(this.currentFillStartPosition, 0, state.getItemCount());
        updateCurrentKeylineStateForScrollOffset();
        detachAndScrapAttachedViews(recycler);
        fill(recycler, state);
    }

    private void refreshKeylineState() {
        this.keylineStateList = null;
        requestLayout();
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        removeAndRecycleOutOfBoundsViews(recycler);
        if (getChildCount() == 0) {
            addViewsStart(recycler, this.currentFillStartPosition - 1);
            addViewsEnd(recycler, state, this.currentFillStartPosition);
        } else {
            int position = getPosition(getChildAt(0));
            int position2 = getPosition(getChildAt(getChildCount() - 1));
            addViewsStart(recycler, position - 1);
            addViewsEnd(recycler, state, position2 + 1);
        }
        validateChildOrderIfDebugging();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (getChildCount() == 0) {
            this.currentFillStartPosition = 0;
        } else {
            this.currentFillStartPosition = getPosition(getChildAt(0));
        }
        validateChildOrderIfDebugging();
    }

    private void addViewsStart(RecyclerView.Recycler recycler, int i) {
        int iCalculateChildStartForFill = calculateChildStartForFill(i);
        while (i >= 0) {
            ChildCalculations childCalculationsMakeChildCalculations = makeChildCalculations(recycler, iCalculateChildStartForFill, i);
            if (isLocOffsetOutOfFillBoundsStart(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                return;
            }
            iCalculateChildStartForFill = addStart(iCalculateChildStartForFill, (int) this.currentKeylineState.getItemSize());
            if (!isLocOffsetOutOfFillBoundsEnd(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                addAndLayoutView(childCalculationsMakeChildCalculations.child, 0, childCalculationsMakeChildCalculations);
            }
            i--;
        }
    }

    private void addViewsEnd(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        int iCalculateChildStartForFill = calculateChildStartForFill(i);
        while (i < state.getItemCount()) {
            ChildCalculations childCalculationsMakeChildCalculations = makeChildCalculations(recycler, iCalculateChildStartForFill, i);
            if (isLocOffsetOutOfFillBoundsEnd(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                return;
            }
            iCalculateChildStartForFill = addEnd(iCalculateChildStartForFill, (int) this.currentKeylineState.getItemSize());
            if (!isLocOffsetOutOfFillBoundsStart(childCalculationsMakeChildCalculations.offsetCenter, childCalculationsMakeChildCalculations.range)) {
                addAndLayoutView(childCalculationsMakeChildCalculations.child, -1, childCalculationsMakeChildCalculations);
            }
            i++;
        }
    }

    private void logChildrenIfDebugging() {
        if (this.isDebuggingEnabled && Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "internal representation of views on the screen");
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                Log.d(TAG, "item position " + getPosition(childAt) + ", center:" + getDecoratedCenterXWithMargins(childAt) + ", child index:" + i);
            }
            Log.d(TAG, "==============");
        }
    }

    private void validateChildOrderIfDebugging() {
        if (!this.isDebuggingEnabled || getChildCount() < 1) {
            return;
        }
        int i = 0;
        while (i < getChildCount() - 1) {
            int position = getPosition(getChildAt(i));
            int i2 = i + 1;
            int position2 = getPosition(getChildAt(i2));
            if (position > position2) {
                logChildrenIfDebugging();
                throw new IllegalStateException("Detected invalid child order. Child at index [" + i + "] had adapter position [" + position + "] and child at index [" + i2 + "] had adapter position [" + position2 + "].");
            }
            i = i2;
        }
    }

    private ChildCalculations makeChildCalculations(RecyclerView.Recycler recycler, float f, int i) {
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        View viewForPosition = recycler.getViewForPosition(i);
        measureChildWithMargins(viewForPosition, 0, 0);
        float fAddEnd = addEnd((int) f, (int) itemSize);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fAddEnd, false);
        return new ChildCalculations(viewForPosition, fAddEnd, calculateChildOffsetCenterForLocation(viewForPosition, fAddEnd, surroundingKeylineRange), surroundingKeylineRange);
    }

    private void addAndLayoutView(View view, int i, ChildCalculations childCalculations) {
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        addView(view, i);
        this.orientationHelper.layoutDecoratedWithMargins(view, (int) (childCalculations.offsetCenter - itemSize), (int) (childCalculations.offsetCenter + itemSize));
        updateChildMaskForLocation(view, childCalculations.center, childCalculations.range);
    }

    private boolean isLocOffsetOutOfFillBoundsStart(float f, KeylineRange keylineRange) {
        int iAddEnd = addEnd((int) f, (int) (getMaskedItemSizeForLocOffset(f, keylineRange) / 2.0f));
        return isLayoutRtl() ? iAddEnd > getContainerSize() : iAddEnd < 0;
    }

    @Override // com.google.android.material.carousel.Carousel
    public boolean isHorizontal() {
        return this.orientationHelper.orientation == 0;
    }

    private boolean isLocOffsetOutOfFillBoundsEnd(float f, KeylineRange keylineRange) {
        int iAddStart = addStart((int) f, (int) (getMaskedItemSizeForLocOffset(f, keylineRange) / 2.0f));
        return isLayoutRtl() ? iAddStart < 0 : iAddStart > getContainerSize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        float fCenterX = rect.centerX();
        float fWidth = (rect.width() - getMaskedItemSizeForLocOffset(fCenterX, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fCenterX, true))) / 2.0f;
        rect.set((int) (rect.left + fWidth), rect.top, (int) (rect.right - fWidth), rect.bottom);
    }

    private float getDecoratedCenterXWithMargins(View view) {
        super.getDecoratedBoundsWithMargins(view, new Rect());
        return r0.centerX();
    }

    private void removeAndRecycleOutOfBoundsViews(RecyclerView.Recycler recycler) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            float decoratedCenterXWithMargins = getDecoratedCenterXWithMargins(childAt);
            if (!isLocOffsetOutOfFillBoundsStart(decoratedCenterXWithMargins, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterXWithMargins, true))) {
                break;
            } else {
                removeAndRecycleView(childAt, recycler);
            }
        }
        while (getChildCount() - 1 >= 0) {
            View childAt2 = getChildAt(getChildCount() - 1);
            float decoratedCenterXWithMargins2 = getDecoratedCenterXWithMargins(childAt2);
            if (!isLocOffsetOutOfFillBoundsEnd(decoratedCenterXWithMargins2, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterXWithMargins2, true))) {
                return;
            } else {
                removeAndRecycleView(childAt2, recycler);
            }
        }
    }

    private static KeylineRange getSurroundingKeylineRange(List<KeylineState.Keyline> list, float f, boolean z) {
        float f2 = Float.MAX_VALUE;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        float f3 = -3.4028235E38f;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        for (int i5 = 0; i5 < list.size(); i5++) {
            KeylineState.Keyline keyline = list.get(i5);
            float f6 = z ? keyline.locOffset : keyline.loc;
            float fAbs = Math.abs(f6 - f);
            if (f6 <= f && fAbs <= f2) {
                i = i5;
                f2 = fAbs;
            }
            if (f6 > f && fAbs <= f4) {
                i3 = i5;
                f4 = fAbs;
            }
            if (f6 <= f5) {
                i2 = i5;
                f5 = f6;
            }
            if (f6 > f3) {
                i4 = i5;
                f3 = f6;
            }
        }
        if (i == -1) {
            i = i2;
        }
        if (i3 == -1) {
            i3 = i4;
        }
        return new KeylineRange(list.get(i), list.get(i3));
    }

    private void updateCurrentKeylineStateForScrollOffset() {
        int i = this.maxScroll;
        int i2 = this.minScroll;
        if (i <= i2) {
            this.currentKeylineState = isLayoutRtl() ? this.keylineStateList.getEndState() : this.keylineStateList.getStartState();
        } else {
            this.currentKeylineState = this.keylineStateList.getShiftedState(this.scrollOffset, i2, i);
        }
        this.debugItemDecoration.setKeylines(this.currentKeylineState.getKeylines());
    }

    private int calculateStartScroll(KeylineStateList keylineStateList) {
        boolean zIsLayoutRtl = isLayoutRtl();
        KeylineState endState = zIsLayoutRtl ? keylineStateList.getEndState() : keylineStateList.getStartState();
        return (int) (((getPaddingStart() * (zIsLayoutRtl ? 1 : -1)) + getParentStart()) - addStart((int) (zIsLayoutRtl ? endState.getLastFocalKeyline() : endState.getFirstFocalKeyline()).loc, (int) (endState.getItemSize() / 2.0f)));
    }

    private int calculateEndScroll(RecyclerView.State state, KeylineStateList keylineStateList) {
        boolean zIsLayoutRtl = isLayoutRtl();
        KeylineState startState = zIsLayoutRtl ? keylineStateList.getStartState() : keylineStateList.getEndState();
        KeylineState.Keyline firstFocalKeyline = zIsLayoutRtl ? startState.getFirstFocalKeyline() : startState.getLastFocalKeyline();
        float itemCount = (((state.getItemCount() - 1) * startState.getItemSize()) + getPaddingEnd()) * (zIsLayoutRtl ? -1.0f : 1.0f);
        float parentStart = firstFocalKeyline.loc - getParentStart();
        float parentEnd = getParentEnd() - firstFocalKeyline.loc;
        if (Math.abs(parentStart) > Math.abs(itemCount)) {
            return 0;
        }
        return (int) ((itemCount - parentStart) + parentEnd);
    }

    private int calculateChildStartForFill(int i) {
        return addEnd(getParentStart() - this.scrollOffset, (int) (this.currentKeylineState.getItemSize() * i));
    }

    private float calculateChildOffsetCenterForLocation(View view, float f, KeylineRange keylineRange) {
        float fLerp = AnimationUtils.lerp(keylineRange.leftOrTop.locOffset, keylineRange.rightOrBottom.locOffset, keylineRange.leftOrTop.loc, keylineRange.rightOrBottom.loc, f);
        if (keylineRange.rightOrBottom != this.currentKeylineState.getFirstKeyline() && keylineRange.leftOrTop != this.currentKeylineState.getLastKeyline()) {
            return fLerp;
        }
        return fLerp + ((f - keylineRange.rightOrBottom.loc) * ((1.0f - keylineRange.rightOrBottom.mask) + (this.orientationHelper.getMaskMargins((RecyclerView.LayoutParams) view.getLayoutParams()) / this.currentKeylineState.getItemSize())));
    }

    private float getMaskedItemSizeForLocOffset(float f, KeylineRange keylineRange) {
        return AnimationUtils.lerp(keylineRange.leftOrTop.maskedItemSize, keylineRange.rightOrBottom.maskedItemSize, keylineRange.leftOrTop.locOffset, keylineRange.rightOrBottom.locOffset, f);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void updateChildMaskForLocation(View view, float f, KeylineRange keylineRange) {
        if (view instanceof Maskable) {
            float fLerp = AnimationUtils.lerp(keylineRange.leftOrTop.mask, keylineRange.rightOrBottom.mask, keylineRange.leftOrTop.loc, keylineRange.rightOrBottom.loc, f);
            float height = view.getHeight();
            float width = view.getWidth();
            RectF maskRect = this.orientationHelper.getMaskRect(height, width, AnimationUtils.lerp(0.0f, height / 2.0f, 0.0f, 1.0f, fLerp), AnimationUtils.lerp(0.0f, width / 2.0f, 0.0f, 1.0f, fLerp));
            float fCalculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(view, f, keylineRange);
            RectF rectF = new RectF(fCalculateChildOffsetCenterForLocation - (maskRect.width() / 2.0f), fCalculateChildOffsetCenterForLocation - (maskRect.height() / 2.0f), fCalculateChildOffsetCenterForLocation + (maskRect.width() / 2.0f), (maskRect.height() / 2.0f) + fCalculateChildOffsetCenterForLocation);
            RectF rectF2 = new RectF(getParentLeft(), getParentTop(), getParentRight(), getParentBottom());
            if (this.carouselStrategy.isContained()) {
                this.orientationHelper.containMaskWithinBounds(maskRect, rectF, rectF2);
            }
            this.orientationHelper.moveMaskOnEdgeOutsideBounds(maskRect, rectF, rectF2);
            ((Maskable) view).setMaskRectF(maskRect);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void measureChildWithMargins(View view, int i, int i2) {
        float itemSize;
        float itemSize2;
        if (!(view instanceof Maskable)) {
            throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        Rect rect = new Rect();
        calculateItemDecorationsForChild(view, rect);
        int i3 = i + rect.left + rect.right;
        int i4 = i2 + rect.top + rect.bottom;
        if (this.keylineStateList != null && this.orientationHelper.orientation == 0) {
            itemSize = this.keylineStateList.getDefaultState().getItemSize();
        } else {
            itemSize = layoutParams.width;
        }
        if (this.keylineStateList != null && this.orientationHelper.orientation == 1) {
            itemSize2 = this.keylineStateList.getDefaultState().getItemSize();
        } else {
            itemSize2 = layoutParams.height;
        }
        view.measure(getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin + i3, (int) itemSize, canScrollHorizontally()), getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin + i4, (int) itemSize2, canScrollVertically()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentLeft() {
        return this.orientationHelper.getParentLeft();
    }

    private int getParentStart() {
        return this.orientationHelper.getParentStart();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentRight() {
        return this.orientationHelper.getParentRight();
    }

    private int getParentEnd() {
        return this.orientationHelper.getParentEnd();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentTop() {
        return this.orientationHelper.getParentTop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getParentBottom() {
        return this.orientationHelper.getParentBottom();
    }

    @Override // com.google.android.material.carousel.Carousel
    public int getContainerWidth() {
        return getWidth();
    }

    @Override // com.google.android.material.carousel.Carousel
    public int getContainerHeight() {
        return getHeight();
    }

    private int getContainerSize() {
        if (isHorizontal()) {
            return getContainerWidth();
        }
        return getContainerHeight();
    }

    boolean isLayoutRtl() {
        return isHorizontal() && getLayoutDirection() == 1;
    }

    private int addStart(int i, int i2) {
        return isLayoutRtl() ? i + i2 : i - i2;
    }

    private int addEnd(int i, int i2) {
        return isLayoutRtl() ? i - i2 : i + i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(getPosition(getChildAt(0)));
            accessibilityEvent.setToIndex(getPosition(getChildAt(getChildCount() - 1)));
        }
    }

    private int getScrollOffsetForPosition(int i, KeylineState keylineState) {
        if (isLayoutRtl()) {
            return (int) (((getContainerSize() - keylineState.getLastFocalKeyline().loc) - (i * keylineState.getItemSize())) - (keylineState.getItemSize() / 2.0f));
        }
        return (int) (((i * keylineState.getItemSize()) - keylineState.getFirstFocalKeyline().loc) + (keylineState.getItemSize() / 2.0f));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int i) {
        if (this.keylineStateList == null) {
            return null;
        }
        int offsetToScrollToPosition = getOffsetToScrollToPosition(i, getKeylineStateForPosition(i));
        if (isHorizontal()) {
            return new PointF(offsetToScrollToPosition, 0.0f);
        }
        return new PointF(0.0f, offsetToScrollToPosition);
    }

    int getOffsetToScrollToPosition(int i, KeylineState keylineState) {
        return getScrollOffsetForPosition(i, keylineState) - this.scrollOffset;
    }

    int getOffsetToScrollToPositionForSnap(int i, boolean z) {
        int offsetToScrollToPosition = getOffsetToScrollToPosition(i, this.keylineStateList.getShiftedState(this.scrollOffset, this.minScroll, this.maxScroll, true));
        int offsetToScrollToPosition2 = this.keylineStatePositionMap != null ? getOffsetToScrollToPosition(i, getKeylineStateForPosition(i)) : offsetToScrollToPosition;
        return (!z || Math.abs(offsetToScrollToPosition2) >= Math.abs(offsetToScrollToPosition)) ? offsetToScrollToPosition : offsetToScrollToPosition2;
    }

    private KeylineState getKeylineStateForPosition(int i) {
        KeylineState keylineState;
        Map<Integer, KeylineState> map = this.keylineStatePositionMap;
        return (map == null || (keylineState = map.get(Integer.valueOf(MathUtils.clamp(i, 0, Math.max(0, getItemCount() + (-1)))))) == null) ? this.keylineStateList.getDefaultState() : keylineState;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        if (this.keylineStateList == null) {
            return;
        }
        this.scrollOffset = getScrollOffsetForPosition(i, getKeylineStateForPosition(i));
        this.currentFillStartPosition = MathUtils.clamp(i, 0, Math.max(0, getItemCount() - 1));
        updateCurrentKeylineStateForScrollOffset();
        requestLayout();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: com.google.android.material.carousel.CarouselLayoutManager.1
            @Override // androidx.recyclerview.widget.RecyclerView.SmoothScroller
            public PointF computeScrollVectorForPosition(int i2) {
                return CarouselLayoutManager.this.computeScrollVectorForPosition(i2);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDxToMakeVisible(View view, int i2) {
                if (CarouselLayoutManager.this.keylineStateList == null || !CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDyToMakeVisible(View view, int i2) {
                if (CarouselLayoutManager.this.keylineStateList == null || CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }
        };
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }

    int calculateScrollDeltaToMakePositionVisible(int i) {
        return (int) (this.scrollOffset - getScrollOffsetForPosition(i, getKeylineStateForPosition(i)));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollHorizontally()) {
            return scrollBy(i, recycler, state);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return !isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollVertically()) {
            return scrollBy(i, recycler, state);
        }
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        if (this.keylineStateList == null) {
            return false;
        }
        int offsetToScrollToPosition = getOffsetToScrollToPosition(getPosition(view), getKeylineStateForPosition(getPosition(view)));
        if (z2 || offsetToScrollToPosition == 0) {
            return false;
        }
        recyclerView.scrollBy(offsetToScrollToPosition, 0);
        return true;
    }

    private int scrollBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        int iCalculateShouldScrollBy = calculateShouldScrollBy(i, this.scrollOffset, this.minScroll, this.maxScroll);
        this.scrollOffset += iCalculateShouldScrollBy;
        updateCurrentKeylineStateForScrollOffset();
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        int iCalculateChildStartForFill = calculateChildStartForFill(getPosition(getChildAt(0)));
        Rect rect = new Rect();
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            offsetChild(getChildAt(i2), iCalculateChildStartForFill, itemSize, rect);
            iCalculateChildStartForFill = addEnd(iCalculateChildStartForFill, (int) this.currentKeylineState.getItemSize());
        }
        fill(recycler, state);
        return iCalculateShouldScrollBy;
    }

    private void offsetChild(View view, float f, float f2, Rect rect) {
        float fAddEnd = addEnd((int) f, (int) f2);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), fAddEnd, false);
        float fCalculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(view, fAddEnd, surroundingKeylineRange);
        super.getDecoratedBoundsWithMargins(view, rect);
        updateChildMaskForLocation(view, fAddEnd, surroundingKeylineRange);
        this.orientationHelper.offsetChild(view, rect, f2, fCalculateChildOffsetCenterForLocation);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return (int) this.keylineStateList.getDefaultState().getItemSize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return (int) this.keylineStateList.getDefaultState().getItemSize();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    public int getOrientation() {
        return this.orientationHelper.orientation;
    }

    public void setOrientation(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("invalid orientation:" + i);
        }
        assertNotInLayoutOrScroll(null);
        CarouselOrientationHelper carouselOrientationHelper = this.orientationHelper;
        if (carouselOrientationHelper == null || i != carouselOrientationHelper.orientation) {
            this.orientationHelper = CarouselOrientationHelper.createOrientationHelper(this, i);
            refreshKeylineState();
        }
    }

    public void setDebuggingEnabled(RecyclerView recyclerView, boolean z) {
        this.isDebuggingEnabled = z;
        recyclerView.removeItemDecoration(this.debugItemDecoration);
        if (z) {
            recyclerView.addItemDecoration(this.debugItemDecoration);
        }
        recyclerView.invalidateItemDecorations();
    }

    private static class KeylineRange {
        final KeylineState.Keyline leftOrTop;
        final KeylineState.Keyline rightOrBottom;

        KeylineRange(KeylineState.Keyline keyline, KeylineState.Keyline keyline2) {
            Preconditions.checkArgument(keyline.loc <= keyline2.loc);
            this.leftOrTop = keyline;
            this.rightOrBottom = keyline2;
        }
    }

    private static class DebugItemDecoration extends RecyclerView.ItemDecoration {
        private List<KeylineState.Keyline> keylines;
        private final Paint linePaint;

        DebugItemDecoration() {
            Paint paint = new Paint();
            this.linePaint = paint;
            this.keylines = Collections.unmodifiableList(new ArrayList());
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        void setKeylines(List<KeylineState.Keyline> list) {
            this.keylines = Collections.unmodifiableList(list);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            super.onDrawOver(canvas, recyclerView, state);
            this.linePaint.setStrokeWidth(recyclerView.getResources().getDimension(R.dimen.m3_carousel_debug_keyline_width));
            for (KeylineState.Keyline keyline : this.keylines) {
                this.linePaint.setColor(ColorUtils.blendARGB(-65281, -16776961, keyline.mask));
                if (((CarouselLayoutManager) recyclerView.getLayoutManager()).isHorizontal()) {
                    canvas.drawLine(keyline.locOffset, ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentTop(), keyline.locOffset, ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentBottom(), this.linePaint);
                } else {
                    canvas.drawLine(((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentLeft(), keyline.locOffset, ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentRight(), keyline.locOffset, this.linePaint);
                }
            }
        }
    }
}
