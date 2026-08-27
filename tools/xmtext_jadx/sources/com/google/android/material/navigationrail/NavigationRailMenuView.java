package com.google.android.material.navigationrail;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.google.android.material.navigation.NavigationBarItemView;
import com.google.android.material.navigation.NavigationBarMenuView;

/* loaded from: classes.dex */
public class NavigationRailMenuView extends NavigationBarMenuView {
    private int itemMinimumHeight;
    private final FrameLayout.LayoutParams layoutParams;

    public NavigationRailMenuView(Context context) {
        super(context);
        this.itemMinimumHeight = -1;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        this.layoutParams = layoutParams;
        layoutParams.gravity = 49;
        setLayoutParams(layoutParams);
        setItemActiveIndicatorResizeable(true);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int iMeasureSharedChildHeights;
        int size = View.MeasureSpec.getSize(i2);
        int size2 = getMenu().getVisibleItems().size();
        if (size2 > 1 && isShifting(getLabelVisibilityMode(), size2)) {
            iMeasureSharedChildHeights = measureShiftingChildHeights(i, size, size2);
        } else {
            iMeasureSharedChildHeights = measureSharedChildHeights(i, size, size2, null);
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.resolveSizeAndState(iMeasureSharedChildHeights, i2, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int i5 = i3 - i;
        int i6 = 0;
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt = getChildAt(i7);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight() + i6;
                childAt.layout(0, i6, i5, measuredHeight);
                i6 = measuredHeight;
            }
        }
    }

    @Override // com.google.android.material.navigation.NavigationBarMenuView
    protected NavigationBarItemView createNavigationBarItemView(Context context) {
        return new NavigationRailItemView(context);
    }

    private int makeSharedHeightSpec(int i, int i2, int i3) {
        int iMax = i2 / Math.max(1, i3);
        int size = this.itemMinimumHeight;
        if (size == -1) {
            size = View.MeasureSpec.getSize(i);
        }
        return View.MeasureSpec.makeMeasureSpec(Math.min(size, iMax), 0);
    }

    private int measureShiftingChildHeights(int i, int i2, int i3) {
        int iMeasureChildHeight;
        View childAt = getChildAt(getSelectedItemPosition());
        if (childAt != null) {
            iMeasureChildHeight = measureChildHeight(childAt, i, makeSharedHeightSpec(i, i2, i3));
            i2 -= iMeasureChildHeight;
            i3--;
        } else {
            iMeasureChildHeight = 0;
        }
        return iMeasureChildHeight + measureSharedChildHeights(i, i2, i3, childAt);
    }

    private int measureSharedChildHeights(int i, int i2, int i3, View view) {
        int iMakeMeasureSpec;
        if (view == null) {
            iMakeMeasureSpec = makeSharedHeightSpec(i, i2, i3);
        } else {
            iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getMeasuredHeight(), 0);
        }
        int childCount = getChildCount();
        int iMeasureChildHeight = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt != view) {
                iMeasureChildHeight += measureChildHeight(childAt, i, iMakeMeasureSpec);
            }
        }
        return iMeasureChildHeight;
    }

    private int measureChildHeight(View view, int i, int i2) {
        if (view.getVisibility() == 8) {
            return 0;
        }
        view.measure(i, i2);
        return view.getMeasuredHeight();
    }

    void setMenuGravity(int i) {
        if (this.layoutParams.gravity != i) {
            this.layoutParams.gravity = i;
            setLayoutParams(this.layoutParams);
        }
    }

    int getMenuGravity() {
        return this.layoutParams.gravity;
    }

    public void setItemMinimumHeight(int i) {
        if (this.itemMinimumHeight != i) {
            this.itemMinimumHeight = i;
            requestLayout();
        }
    }

    public int getItemMinimumHeight() {
        return this.itemMinimumHeight;
    }

    boolean isTopGravity() {
        return (this.layoutParams.gravity & 112) == 48;
    }
}
