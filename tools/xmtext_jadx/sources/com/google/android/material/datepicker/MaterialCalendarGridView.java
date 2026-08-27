package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;

/* loaded from: classes.dex */
final class MaterialCalendarGridView extends GridView {
    private final Calendar dayCompute;
    private final boolean nestedScrollable;

    public MaterialCalendarGridView(Context context) {
        this(context, null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dayCompute = UtcDates.getUtcCalendar();
        if (MaterialDatePicker.isFullscreen(getContext())) {
            setNextFocusLeftId(R.id.cancel_button);
            setNextFocusRightId(R.id.confirm_button);
        }
        this.nestedScrollable = MaterialDatePicker.isNestedScrollable(getContext());
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() { // from class: com.google.android.material.datepicker.MaterialCalendarGridView.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo(null);
            }
        });
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAdapter().notifyDataSetChanged();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public void setSelection(int i) {
        if (i < getAdapter().firstPositionInMonth()) {
            super.setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.setSelection(i);
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!super.onKeyDown(i, keyEvent)) {
            return false;
        }
        if (getSelectedItemPosition() == -1 || getSelectedItemPosition() >= getAdapter().firstPositionInMonth()) {
            return true;
        }
        if (19 != i) {
            return false;
        }
        setSelection(getAdapter().firstPositionInMonth());
        return true;
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public MonthAdapter getAdapter() {
        return (MonthAdapter) super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(ListAdapter listAdapter) {
        if (!(listAdapter instanceof MonthAdapter)) {
            throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()));
        }
        super.setAdapter(listAdapter);
    }

    @Override // android.view.View
    protected final void onDraw(Canvas canvas) {
        int iDayToPosition;
        int iHorizontalMidPoint;
        int iDayToPosition2;
        int iHorizontalMidPoint2;
        int width;
        int i;
        int left;
        int left2;
        MaterialCalendarGridView materialCalendarGridView = this;
        super.onDraw(canvas);
        MonthAdapter adapter = materialCalendarGridView.getAdapter();
        DateSelector<?> dateSelector = adapter.dateSelector;
        CalendarStyle calendarStyle = adapter.calendarStyle;
        int iMax = Math.max(adapter.firstPositionInMonth(), materialCalendarGridView.getFirstVisiblePosition());
        int iMin = Math.min(adapter.lastPositionInMonth(), materialCalendarGridView.getLastVisiblePosition());
        Long item = adapter.getItem(iMax);
        Long item2 = adapter.getItem(iMin);
        for (Pair<Long, Long> pair : dateSelector.getSelectedRanges()) {
            if (pair.first == null) {
                materialCalendarGridView = this;
            } else if (pair.second != null) {
                long jLongValue = pair.first.longValue();
                long jLongValue2 = pair.second.longValue();
                if (!skipMonth(item, item2, Long.valueOf(jLongValue), Long.valueOf(jLongValue2))) {
                    boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(materialCalendarGridView);
                    if (jLongValue < item.longValue()) {
                        if (adapter.isFirstInRow(iMax)) {
                            left2 = 0;
                        } else if (!zIsLayoutRtl) {
                            left2 = materialCalendarGridView.getChildAtPosition(iMax - 1).getRight();
                        } else {
                            left2 = materialCalendarGridView.getChildAtPosition(iMax - 1).getLeft();
                        }
                        iHorizontalMidPoint = left2;
                        iDayToPosition = iMax;
                    } else {
                        materialCalendarGridView.dayCompute.setTimeInMillis(jLongValue);
                        iDayToPosition = adapter.dayToPosition(materialCalendarGridView.dayCompute.get(5));
                        iHorizontalMidPoint = horizontalMidPoint(materialCalendarGridView.getChildAtPosition(iDayToPosition));
                    }
                    if (jLongValue2 > item2.longValue()) {
                        if (adapter.isLastInRow(iMin)) {
                            left = materialCalendarGridView.getWidth();
                        } else if (!zIsLayoutRtl) {
                            left = materialCalendarGridView.getChildAtPosition(iMin).getRight();
                        } else {
                            left = materialCalendarGridView.getChildAtPosition(iMin).getLeft();
                        }
                        iHorizontalMidPoint2 = left;
                        iDayToPosition2 = iMin;
                    } else {
                        materialCalendarGridView.dayCompute.setTimeInMillis(jLongValue2);
                        iDayToPosition2 = adapter.dayToPosition(materialCalendarGridView.dayCompute.get(5));
                        iHorizontalMidPoint2 = horizontalMidPoint(materialCalendarGridView.getChildAtPosition(iDayToPosition2));
                    }
                    int itemId = (int) adapter.getItemId(iDayToPosition);
                    int i2 = iMax;
                    int i3 = iMin;
                    int itemId2 = (int) adapter.getItemId(iDayToPosition2);
                    while (itemId <= itemId2) {
                        int numColumns = materialCalendarGridView.getNumColumns() * itemId;
                        int numColumns2 = (numColumns + materialCalendarGridView.getNumColumns()) - 1;
                        View childAtPosition = materialCalendarGridView.getChildAtPosition(numColumns);
                        int top = childAtPosition.getTop() + calendarStyle.day.getTopInset();
                        MonthAdapter monthAdapter = adapter;
                        int bottom = childAtPosition.getBottom() - calendarStyle.day.getBottomInset();
                        if (!zIsLayoutRtl) {
                            i = numColumns > iDayToPosition ? 0 : iHorizontalMidPoint;
                            width = iDayToPosition2 > numColumns2 ? getWidth() : iHorizontalMidPoint2;
                        } else {
                            int i4 = iDayToPosition2 > numColumns2 ? 0 : iHorizontalMidPoint2;
                            width = numColumns > iDayToPosition ? getWidth() : iHorizontalMidPoint;
                            i = i4;
                        }
                        canvas.drawRect(i, top, width, bottom, calendarStyle.rangeFill);
                        itemId++;
                        materialCalendarGridView = this;
                        adapter = monthAdapter;
                    }
                    materialCalendarGridView = this;
                    iMax = i2;
                    iMin = i3;
                }
            }
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.nestedScrollable) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(ViewCompat.MEASURED_SIZE_MASK, Integer.MIN_VALUE));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(i, i2);
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    protected void onFocusChanged(boolean z, int i, Rect rect) {
        if (z) {
            gainFocus(i, rect);
        } else {
            super.onFocusChanged(false, i, rect);
        }
    }

    private void gainFocus(int i, Rect rect) {
        if (i == 33) {
            setSelection(getAdapter().lastPositionInMonth());
        } else if (i == 130) {
            setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.onFocusChanged(true, i, rect);
        }
    }

    private View getChildAtPosition(int i) {
        return getChildAt(i - getFirstVisiblePosition());
    }

    private static boolean skipMonth(Long l, Long l2, Long l3, Long l4) {
        return l == null || l2 == null || l3 == null || l4 == null || l3.longValue() > l2.longValue() || l4.longValue() < l.longValue();
    }

    private static int horizontalMidPoint(View view) {
        return view.getLeft() + (view.getWidth() / 2);
    }
}
