package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes.dex */
public class Flow extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mDisplayedWidgets;
    private int mHorizontalStyle = -1;
    private int mVerticalStyle = -1;
    private int mFirstHorizontalStyle = -1;
    private int mFirstVerticalStyle = -1;
    private int mLastHorizontalStyle = -1;
    private int mLastVerticalStyle = -1;
    private float mHorizontalBias = 0.5f;
    private float mVerticalBias = 0.5f;
    private float mFirstHorizontalBias = 0.5f;
    private float mFirstVerticalBias = 0.5f;
    private float mLastHorizontalBias = 0.5f;
    private float mLastVerticalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mVerticalGap = 0;
    private int mHorizontalAlign = 2;
    private int mVerticalAlign = 2;
    private int mWrapMode = 0;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private ArrayList<WidgetsList> mChainList = new ArrayList<>();
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private int[] mAlignedDimensions = null;
    private int mDisplayedWidgetsCount = 0;

    @Override // androidx.constraintlayout.solver.widgets.HelperWidget, androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> map) {
        super.copy(constraintWidget, map);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }

    public void setOrientation(int i) {
        this.mOrientation = i;
    }

    public void setFirstHorizontalStyle(int i) {
        this.mFirstHorizontalStyle = i;
    }

    public void setFirstVerticalStyle(int i) {
        this.mFirstVerticalStyle = i;
    }

    public void setLastHorizontalStyle(int i) {
        this.mLastHorizontalStyle = i;
    }

    public void setLastVerticalStyle(int i) {
        this.mLastVerticalStyle = i;
    }

    public void setHorizontalStyle(int i) {
        this.mHorizontalStyle = i;
    }

    public void setVerticalStyle(int i) {
        this.mVerticalStyle = i;
    }

    public void setHorizontalBias(float f) {
        this.mHorizontalBias = f;
    }

    public void setVerticalBias(float f) {
        this.mVerticalBias = f;
    }

    public void setFirstHorizontalBias(float f) {
        this.mFirstHorizontalBias = f;
    }

    public void setFirstVerticalBias(float f) {
        this.mFirstVerticalBias = f;
    }

    public void setLastHorizontalBias(float f) {
        this.mLastHorizontalBias = f;
    }

    public void setLastVerticalBias(float f) {
        this.mLastVerticalBias = f;
    }

    public void setHorizontalAlign(int i) {
        this.mHorizontalAlign = i;
    }

    public void setVerticalAlign(int i) {
        this.mVerticalAlign = i;
    }

    public void setWrapMode(int i) {
        this.mWrapMode = i;
    }

    public void setHorizontalGap(int i) {
        this.mHorizontalGap = i;
    }

    public void setVerticalGap(int i) {
        this.mVerticalGap = i;
    }

    public void setMaxElementsWrap(int i) {
        this.mMaxElementsWrap = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget constraintWidget2;
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            constraintWidget2 = constraintWidget;
        } else {
            if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentWidth * i);
                if (i2 != constraintWidget.getWidth()) {
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i2, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                }
                return i2;
            }
            constraintWidget2 = constraintWidget;
            if (constraintWidget2.mMatchConstraintDefaultWidth == 1) {
                return constraintWidget2.getWidth();
            }
            if (constraintWidget2.mMatchConstraintDefaultWidth == 3) {
                return (int) ((constraintWidget2.getHeight() * constraintWidget2.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget2.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i) {
        ConstraintWidget constraintWidget2;
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            constraintWidget2 = constraintWidget;
        } else {
            if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
                return 0;
            }
            if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
                int i2 = (int) (constraintWidget.mMatchConstraintPercentHeight * i);
                if (i2 != constraintWidget.getHeight()) {
                    measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i2);
                }
                return i2;
            }
            constraintWidget2 = constraintWidget;
            if (constraintWidget2.mMatchConstraintDefaultHeight == 1) {
                return constraintWidget2.getHeight();
            }
            if (constraintWidget2.mMatchConstraintDefaultHeight == 3) {
                return (int) ((constraintWidget2.getWidth() * constraintWidget2.mDimensionRatio) + 0.5f);
            }
        }
        return constraintWidget2.getHeight();
    }

    @Override // androidx.constraintlayout.solver.widgets.VirtualLayout
    public void measure(int i, int i2, int i3, int i4) {
        ConstraintWidget[] constraintWidgetArr;
        if (this.mWidgetsCount > 0 && !measureChildren()) {
            setMeasure(0, 0);
            needsCallbackFromSolver(false);
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int[] iArr = new int[2];
        int i5 = (i2 - paddingLeft) - paddingRight;
        int i6 = this.mOrientation;
        if (i6 == 1) {
            i5 = (i4 - paddingTop) - paddingBottom;
        }
        int i7 = i5;
        if (i6 == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] constraintWidgetArr2 = this.mWidgets;
        int i8 = 0;
        for (int i9 = 0; i9 < this.mWidgetsCount; i9++) {
            if (this.mWidgets[i9].getVisibility() == 8) {
                i8++;
            }
        }
        int i10 = this.mWidgetsCount;
        if (i8 > 0) {
            ConstraintWidget[] constraintWidgetArr3 = new ConstraintWidget[this.mWidgetsCount - i8];
            i10 = 0;
            int i11 = 0;
            while (i11 < this.mWidgetsCount) {
                ConstraintWidget constraintWidget = this.mWidgets[i11];
                ConstraintWidget[] constraintWidgetArr4 = constraintWidgetArr3;
                if (constraintWidget.getVisibility() != 8) {
                    constraintWidgetArr4[i10] = constraintWidget;
                    i10++;
                }
                i11++;
                constraintWidgetArr3 = constraintWidgetArr4;
            }
            constraintWidgetArr = constraintWidgetArr3;
        } else {
            constraintWidgetArr = constraintWidgetArr2;
        }
        int i12 = i10;
        this.mDisplayedWidgets = constraintWidgetArr;
        this.mDisplayedWidgetsCount = i12;
        int i13 = this.mWrapMode;
        if (i13 == 0) {
            measureNoWrap(constraintWidgetArr, i12, this.mOrientation, i7, iArr);
        } else if (i13 == 1) {
            measureChainWrap(constraintWidgetArr, i12, this.mOrientation, i7, iArr);
        } else if (i13 == 2) {
            measureAligned(constraintWidgetArr, i12, this.mOrientation, i7, iArr);
        }
        int iMin = iArr[0] + paddingLeft + paddingRight;
        int iMin2 = iArr[1] + paddingTop + paddingBottom;
        if (i == 1073741824) {
            iMin = i2;
        } else if (i == Integer.MIN_VALUE) {
            iMin = Math.min(iMin, i2);
        } else if (i != 0) {
            iMin = 0;
        }
        if (i3 == 1073741824) {
            iMin2 = i4;
        } else if (i3 == Integer.MIN_VALUE) {
            iMin2 = Math.min(iMin2, i4);
        } else if (i3 != 0) {
            iMin2 = 0;
        }
        setMeasure(iMin, iMin2);
        setWidth(iMin);
        setHeight(iMin2);
        needsCallbackFromSolver(this.mWidgetsCount > 0);
    }

    private class WidgetsList {
        private ConstraintAnchor mBottom;
        private ConstraintAnchor mLeft;
        private int mMax;
        private int mOrientation;
        private int mPaddingBottom;
        private int mPaddingLeft;
        private int mPaddingRight;
        private int mPaddingTop;
        private ConstraintAnchor mRight;
        private ConstraintAnchor mTop;
        private ConstraintWidget biggest = null;
        int biggestDimension = 0;
        private int mWidth = 0;
        private int mHeight = 0;
        private int mStartIndex = 0;
        private int mCount = 0;
        private int mNbMatchConstraintsWidgets = 0;

        public WidgetsList(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
            this.mPaddingLeft = 0;
            this.mPaddingTop = 0;
            this.mPaddingRight = 0;
            this.mPaddingBottom = 0;
            this.mMax = 0;
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = i2;
        }

        public void setup(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2, int i3, int i4, int i5, int i6) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i2;
            this.mPaddingTop = i3;
            this.mPaddingRight = i4;
            this.mPaddingBottom = i5;
            this.mMax = i6;
        }

        public void clear() {
            this.biggestDimension = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public void setStartIndex(int i) {
            this.mStartIndex = i;
        }

        public int getWidth() {
            if (this.mOrientation == 0) {
                return this.mWidth - Flow.this.mHorizontalGap;
            }
            return this.mWidth;
        }

        public int getHeight() {
            if (this.mOrientation == 1) {
                return this.mHeight - Flow.this.mVerticalGap;
            }
            return this.mHeight;
        }

        public void add(ConstraintWidget constraintWidget) {
            if (this.mOrientation == 0) {
                int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetWidth = 0;
                }
                this.mWidth += widgetWidth + (constraintWidget.getVisibility() != 8 ? Flow.this.mHorizontalGap : 0);
                int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.biggestDimension < widgetHeight) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                int widgetWidth2 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    this.mNbMatchConstraintsWidgets++;
                    widgetHeight2 = 0;
                }
                this.mHeight += widgetHeight2 + (constraintWidget.getVisibility() != 8 ? Flow.this.mVerticalGap : 0);
                if (this.biggest == null || this.biggestDimension < widgetWidth2) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        public void createConstraints(boolean z, int i, boolean z2) {
            ConstraintWidget constraintWidget;
            int i2;
            char c;
            int i3 = this.mCount;
            for (int i4 = 0; i4 < i3 && this.mStartIndex + i4 < Flow.this.mDisplayedWidgetsCount; i4++) {
                ConstraintWidget constraintWidget2 = Flow.this.mDisplayedWidgets[this.mStartIndex + i4];
                if (constraintWidget2 != null) {
                    constraintWidget2.resetAnchors();
                }
            }
            if (i3 == 0 || this.biggest == null) {
                return;
            }
            boolean z3 = z2 && i == 0;
            int i5 = -1;
            int i6 = -1;
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = z ? (i3 - 1) - i7 : i7;
                if (this.mStartIndex + i8 >= Flow.this.mDisplayedWidgetsCount) {
                    break;
                }
                if (Flow.this.mDisplayedWidgets[this.mStartIndex + i8].getVisibility() == 0) {
                    if (i5 == -1) {
                        i5 = i7;
                    }
                    i6 = i7;
                }
            }
            ConstraintWidget constraintWidget3 = null;
            if (this.mOrientation == 0) {
                ConstraintWidget constraintWidget4 = this.biggest;
                constraintWidget4.setVerticalChainStyle(Flow.this.mVerticalStyle);
                int i9 = this.mPaddingTop;
                if (i > 0) {
                    i9 += Flow.this.mVerticalGap;
                }
                constraintWidget4.mTop.connect(this.mTop, i9);
                if (z2) {
                    constraintWidget4.mBottom.connect(this.mBottom, this.mPaddingBottom);
                }
                if (i > 0) {
                    this.mTop.mOwner.mBottom.connect(constraintWidget4.mTop, 0);
                }
                if (Flow.this.mVerticalAlign != 3 || constraintWidget4.hasBaseline()) {
                    constraintWidget = constraintWidget4;
                } else {
                    for (int i10 = 0; i10 < i3; i10++) {
                        int i11 = z ? (i3 - 1) - i10 : i10;
                        if (this.mStartIndex + i11 >= Flow.this.mDisplayedWidgetsCount) {
                            break;
                        }
                        constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i11];
                        if (constraintWidget.hasBaseline()) {
                            break;
                        }
                    }
                    constraintWidget = constraintWidget4;
                }
                int i12 = 0;
                while (i12 < i3) {
                    int i13 = z ? (i3 - 1) - i12 : i12;
                    if (this.mStartIndex + i13 >= Flow.this.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget5 = Flow.this.mDisplayedWidgets[this.mStartIndex + i13];
                    if (i12 == 0) {
                        i2 = 1;
                        constraintWidget5.connect(constraintWidget5.mLeft, this.mLeft, this.mPaddingLeft);
                    } else {
                        i2 = 1;
                    }
                    if (i13 == 0) {
                        int i14 = Flow.this.mHorizontalStyle;
                        float f = Flow.this.mHorizontalBias;
                        if (this.mStartIndex != 0 || Flow.this.mFirstHorizontalStyle == -1) {
                            if (z2 && Flow.this.mLastHorizontalStyle != -1) {
                                i14 = Flow.this.mLastHorizontalStyle;
                                f = Flow.this.mLastHorizontalBias;
                            }
                        } else {
                            i14 = Flow.this.mFirstHorizontalStyle;
                            f = Flow.this.mFirstHorizontalBias;
                        }
                        constraintWidget5.setHorizontalChainStyle(i14);
                        constraintWidget5.setHorizontalBiasPercent(f);
                    }
                    if (i12 == i3 - 1) {
                        constraintWidget5.connect(constraintWidget5.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget3 != null) {
                        constraintWidget5.mLeft.connect(constraintWidget3.mRight, Flow.this.mHorizontalGap);
                        if (i12 == i5) {
                            constraintWidget5.mLeft.setGoneMargin(this.mPaddingLeft);
                        }
                        constraintWidget3.mRight.connect(constraintWidget5.mLeft, 0);
                        if (i12 == i6 + 1) {
                            constraintWidget3.mRight.setGoneMargin(this.mPaddingRight);
                        }
                    }
                    if (constraintWidget5 != constraintWidget4) {
                        c = 3;
                        if (Flow.this.mVerticalAlign != 3 || !constraintWidget.hasBaseline() || constraintWidget5 == constraintWidget || !constraintWidget5.hasBaseline()) {
                            int i15 = Flow.this.mVerticalAlign;
                            if (i15 == 0) {
                                constraintWidget5.mTop.connect(constraintWidget4.mTop, 0);
                            } else if (i15 == i2) {
                                constraintWidget5.mBottom.connect(constraintWidget4.mBottom, 0);
                            } else if (z3) {
                                constraintWidget5.mTop.connect(this.mTop, this.mPaddingTop);
                                constraintWidget5.mBottom.connect(this.mBottom, this.mPaddingBottom);
                            } else {
                                constraintWidget5.mTop.connect(constraintWidget4.mTop, 0);
                                constraintWidget5.mBottom.connect(constraintWidget4.mBottom, 0);
                            }
                        } else {
                            constraintWidget5.mBaseline.connect(constraintWidget.mBaseline, 0);
                        }
                    } else {
                        c = 3;
                    }
                    i12++;
                    constraintWidget3 = constraintWidget5;
                }
                return;
            }
            ConstraintWidget constraintWidget6 = this.biggest;
            constraintWidget6.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
            int i16 = this.mPaddingLeft;
            if (i > 0) {
                i16 += Flow.this.mHorizontalGap;
            }
            if (z) {
                constraintWidget6.mRight.connect(this.mRight, i16);
                if (z2) {
                    constraintWidget6.mLeft.connect(this.mLeft, this.mPaddingRight);
                }
                if (i > 0) {
                    this.mRight.mOwner.mLeft.connect(constraintWidget6.mRight, 0);
                }
            } else {
                constraintWidget6.mLeft.connect(this.mLeft, i16);
                if (z2) {
                    constraintWidget6.mRight.connect(this.mRight, this.mPaddingRight);
                }
                if (i > 0) {
                    this.mLeft.mOwner.mRight.connect(constraintWidget6.mLeft, 0);
                }
            }
            int i17 = 0;
            while (i17 < i3 && this.mStartIndex + i17 < Flow.this.mDisplayedWidgetsCount) {
                ConstraintWidget constraintWidget7 = Flow.this.mDisplayedWidgets[this.mStartIndex + i17];
                if (i17 == 0) {
                    constraintWidget7.connect(constraintWidget7.mTop, this.mTop, this.mPaddingTop);
                    int i18 = Flow.this.mVerticalStyle;
                    float f2 = Flow.this.mVerticalBias;
                    if (this.mStartIndex != 0 || Flow.this.mFirstVerticalStyle == -1) {
                        if (z2 && Flow.this.mLastVerticalStyle != -1) {
                            i18 = Flow.this.mLastVerticalStyle;
                            f2 = Flow.this.mLastVerticalBias;
                        }
                    } else {
                        i18 = Flow.this.mFirstVerticalStyle;
                        f2 = Flow.this.mFirstVerticalBias;
                    }
                    constraintWidget7.setVerticalChainStyle(i18);
                    constraintWidget7.setVerticalBiasPercent(f2);
                }
                if (i17 == i3 - 1) {
                    constraintWidget7.connect(constraintWidget7.mBottom, this.mBottom, this.mPaddingBottom);
                }
                if (constraintWidget3 != null) {
                    constraintWidget7.mTop.connect(constraintWidget3.mBottom, Flow.this.mVerticalGap);
                    if (i17 == i5) {
                        constraintWidget7.mTop.setGoneMargin(this.mPaddingTop);
                    }
                    constraintWidget3.mBottom.connect(constraintWidget7.mTop, 0);
                    if (i17 == i6 + 1) {
                        constraintWidget3.mBottom.setGoneMargin(this.mPaddingBottom);
                    }
                }
                if (constraintWidget7 != constraintWidget6) {
                    if (z) {
                        int i19 = Flow.this.mHorizontalAlign;
                        if (i19 == 0) {
                            constraintWidget7.mRight.connect(constraintWidget6.mRight, 0);
                        } else if (i19 == 1) {
                            constraintWidget7.mLeft.connect(constraintWidget6.mLeft, 0);
                        } else if (i19 == 2) {
                            constraintWidget7.mLeft.connect(constraintWidget6.mLeft, 0);
                            constraintWidget7.mRight.connect(constraintWidget6.mRight, 0);
                        }
                    } else {
                        int i20 = Flow.this.mHorizontalAlign;
                        if (i20 == 0) {
                            constraintWidget7.mLeft.connect(constraintWidget6.mLeft, 0);
                        } else if (i20 == 1) {
                            constraintWidget7.mRight.connect(constraintWidget6.mRight, 0);
                        } else if (i20 == 2) {
                            if (z3) {
                                constraintWidget7.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                constraintWidget7.mRight.connect(this.mRight, this.mPaddingRight);
                            } else {
                                constraintWidget7.mLeft.connect(constraintWidget6.mLeft, 0);
                                constraintWidget7.mRight.connect(constraintWidget6.mRight, 0);
                            }
                        }
                    }
                }
                i17++;
                constraintWidget3 = constraintWidget7;
            }
        }

        public void measureMatchConstraints(int i) {
            int i2 = this.mNbMatchConstraintsWidgets;
            if (i2 == 0) {
                return;
            }
            int i3 = this.mCount;
            int i4 = i / i2;
            for (int i5 = 0; i5 < i3 && this.mStartIndex + i5 < Flow.this.mDisplayedWidgetsCount; i5++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i5];
                if (this.mOrientation == 0) {
                    if (constraintWidget != null && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultWidth == 0) {
                        Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i4, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                    }
                } else if (constraintWidget != null && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT && constraintWidget.mMatchConstraintDefaultHeight == 0) {
                    int i6 = i4;
                    Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i6);
                    i4 = i6;
                }
            }
            recomputeDimensions();
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int i = this.mCount;
            for (int i2 = 0; i2 < i && this.mStartIndex + i2 < Flow.this.mDisplayedWidgetsCount; i2++) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + i2];
                if (this.mOrientation != 0) {
                    int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    int widgetHeight = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    int i3 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i3 = 0;
                    }
                    this.mHeight += widgetHeight + i3;
                    if (this.biggest == null || this.biggestDimension < widgetWidth) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetWidth;
                        this.mWidth = widgetWidth;
                    }
                } else {
                    int width = constraintWidget.getWidth();
                    int i4 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        i4 = 0;
                    }
                    this.mWidth += width + i4;
                    int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.biggestDimension < widgetHeight2) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = widgetHeight2;
                        this.mHeight = widgetHeight2;
                    }
                }
            }
        }
    }

    private void measureChainWrap(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        int i4;
        Flow flow;
        int i5;
        ConstraintAnchor constraintAnchor;
        int i6;
        Flow flow2 = this;
        if (i == 0) {
            return;
        }
        flow2.mChainList.clear();
        int i7 = i3;
        WidgetsList widgetsList = flow2.new WidgetsList(i2, flow2.mLeft, flow2.mTop, flow2.mRight, flow2.mBottom, i7);
        flow2.mChainList.add(widgetsList);
        if (i2 == 0) {
            i4 = 0;
            int i8 = 0;
            int i9 = 0;
            while (i9 < i) {
                ConstraintWidget constraintWidget = constraintWidgetArr[i9];
                int widgetWidth = flow2.getWidgetWidth(constraintWidget, i7);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i4++;
                }
                int i10 = i4;
                boolean z = (i8 == i7 || (flow2.mHorizontalGap + i8) + widgetWidth > i7) && widgetsList.biggest != null;
                if (!z && i9 > 0 && (i6 = flow2.mMaxElementsWrap) > 0 && i9 % i6 == 0) {
                    z = true;
                }
                if (z) {
                    widgetsList = flow2.new WidgetsList(i2, flow2.mLeft, flow2.mTop, flow2.mRight, flow2.mBottom, i7);
                    widgetsList.setStartIndex(i9);
                    flow2.mChainList.add(widgetsList);
                } else {
                    if (i9 > 0) {
                        i8 += flow2.mHorizontalGap + widgetWidth;
                    }
                    widgetsList.add(constraintWidget);
                    i9++;
                    i4 = i10;
                }
                i8 = widgetWidth;
                widgetsList.add(constraintWidget);
                i9++;
                i4 = i10;
            }
        } else {
            i4 = 0;
            int i11 = 0;
            int i12 = 0;
            while (i12 < i) {
                ConstraintWidget constraintWidget2 = constraintWidgetArr[i12];
                int widgetHeight = flow2.getWidgetHeight(constraintWidget2, i7);
                if (constraintWidget2.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    i4++;
                }
                int i13 = i4;
                boolean z2 = (i11 == i7 || (flow2.mVerticalGap + i11) + widgetHeight > i7) && widgetsList.biggest != null;
                if (!z2 && i12 > 0 && (i5 = flow2.mMaxElementsWrap) > 0 && i12 % i5 == 0) {
                    z2 = true;
                }
                if (z2) {
                    widgetsList = flow2.new WidgetsList(i2, flow2.mLeft, flow2.mTop, flow2.mRight, flow2.mBottom, i7);
                    flow = flow2;
                    widgetsList.setStartIndex(i12);
                    flow.mChainList.add(widgetsList);
                } else {
                    flow = flow2;
                    if (i12 > 0) {
                        i11 += flow.mVerticalGap + widgetHeight;
                    }
                    widgetsList.add(constraintWidget2);
                    i12++;
                    i7 = i3;
                    i4 = i13;
                    flow2 = flow;
                }
                i11 = widgetHeight;
                widgetsList.add(constraintWidget2);
                i12++;
                i7 = i3;
                i4 = i13;
                flow2 = flow;
            }
        }
        Flow flow3 = flow2;
        int size = flow3.mChainList.size();
        ConstraintAnchor constraintAnchor2 = flow3.mLeft;
        ConstraintAnchor constraintAnchor3 = flow3.mTop;
        ConstraintAnchor constraintAnchor4 = flow3.mRight;
        ConstraintAnchor constraintAnchor5 = flow3.mBottom;
        int paddingLeft = flow3.getPaddingLeft();
        int paddingTop = flow3.getPaddingTop();
        int paddingRight = flow3.getPaddingRight();
        int paddingBottom = flow3.getPaddingBottom();
        boolean z3 = flow3.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || flow3.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        if (i4 > 0 && z3) {
            for (int i14 = 0; i14 < size; i14++) {
                WidgetsList widgetsList2 = flow3.mChainList.get(i14);
                if (i2 == 0) {
                    widgetsList2.measureMatchConstraints(i3 - widgetsList2.getWidth());
                } else {
                    widgetsList2.measureMatchConstraints(i3 - widgetsList2.getHeight());
                }
            }
        }
        ConstraintAnchor constraintAnchor6 = constraintAnchor2;
        int paddingBottom2 = paddingBottom;
        int i15 = 0;
        int paddingRight2 = paddingRight;
        int i16 = paddingTop;
        int i17 = paddingLeft;
        ConstraintAnchor constraintAnchor7 = constraintAnchor5;
        ConstraintAnchor constraintAnchor8 = constraintAnchor4;
        ConstraintAnchor constraintAnchor9 = constraintAnchor3;
        int i18 = 0;
        for (int i19 = 0; i19 < size; i19++) {
            WidgetsList widgetsList3 = flow3.mChainList.get(i19);
            if (i2 == 0) {
                if (i19 < size - 1) {
                    constraintAnchor7 = flow3.mChainList.get(i19 + 1).biggest.mTop;
                    paddingBottom2 = 0;
                } else {
                    constraintAnchor7 = flow3.mBottom;
                    paddingBottom2 = flow3.getPaddingBottom();
                }
                ConstraintAnchor constraintAnchor10 = widgetsList3.biggest.mBottom;
                int i20 = i18;
                widgetsList3.setup(i2, constraintAnchor6, constraintAnchor9, constraintAnchor8, constraintAnchor7, i17, i16, paddingRight2, paddingBottom2, i3);
                int iMax = Math.max(i15, widgetsList3.getWidth());
                int height = widgetsList3.getHeight() + i20;
                if (i19 > 0) {
                    height += flow3.mVerticalGap;
                }
                i18 = height;
                i15 = iMax;
                constraintAnchor9 = constraintAnchor10;
                i16 = 0;
            } else {
                int i21 = i15;
                int i22 = i18;
                if (i19 < size - 1) {
                    constraintAnchor = flow3.mChainList.get(i19 + 1).biggest.mLeft;
                    paddingRight2 = 0;
                } else {
                    constraintAnchor = flow3.mRight;
                    paddingRight2 = flow3.getPaddingRight();
                }
                constraintAnchor8 = constraintAnchor;
                ConstraintAnchor constraintAnchor11 = widgetsList3.biggest.mRight;
                widgetsList3.setup(i2, constraintAnchor6, constraintAnchor9, constraintAnchor8, constraintAnchor7, i17, i16, paddingRight2, paddingBottom2, i3);
                int width = widgetsList3.getWidth() + i21;
                int iMax2 = Math.max(i22, widgetsList3.getHeight());
                if (i19 > 0) {
                    width += flow3.mHorizontalGap;
                }
                int i23 = width;
                i18 = iMax2;
                i15 = i23;
                i17 = 0;
                constraintAnchor6 = constraintAnchor11;
            }
        }
        iArr[0] = i15;
        iArr[1] = i18;
    }

    private void measureNoWrap(ConstraintWidget[] constraintWidgetArr, int i, int i2, int i3, int[] iArr) {
        WidgetsList widgetsList;
        if (i == 0) {
            return;
        }
        if (this.mChainList.size() == 0) {
            widgetsList = new WidgetsList(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, i3);
            this.mChainList.add(widgetsList);
        } else {
            WidgetsList widgetsList2 = this.mChainList.get(0);
            widgetsList2.clear();
            widgetsList2.setup(i2, this.mLeft, this.mTop, this.mRight, this.mBottom, getPaddingLeft(), getPaddingTop(), getPaddingRight(), getPaddingBottom(), i3);
            widgetsList = widgetsList2;
        }
        for (int i4 = 0; i4 < i; i4++) {
            widgetsList.add(constraintWidgetArr[i4]);
        }
        iArr[0] = widgetsList.getWidth();
        iArr[1] = widgetsList.getHeight();
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x005e  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x010d -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x010f -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:108:0x0115 -> B:42:0x0059). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x0117 -> B:42:0x0059). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[] r11, int r12, int r13, int r14, int[] r15) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.measureAligned(androidx.constraintlayout.solver.widgets.ConstraintWidget[], int, int, int, int[]):void");
    }

    private void createAlignedConstraints(boolean z) {
        ConstraintWidget constraintWidget;
        if (this.mAlignedDimensions == null || this.mAlignedBiggestElementsInCols == null || this.mAlignedBiggestElementsInRows == null) {
            return;
        }
        for (int i = 0; i < this.mDisplayedWidgetsCount; i++) {
            this.mDisplayedWidgets[i].resetAnchors();
        }
        int[] iArr = this.mAlignedDimensions;
        int i2 = iArr[0];
        int i3 = iArr[1];
        ConstraintWidget constraintWidget2 = null;
        for (int i4 = 0; i4 < i2; i4++) {
            ConstraintWidget constraintWidget3 = this.mAlignedBiggestElementsInCols[z ? (i2 - i4) - 1 : i4];
            if (constraintWidget3 != null && constraintWidget3.getVisibility() != 8) {
                if (i4 == 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, this.mLeft, getPaddingLeft());
                    constraintWidget3.setHorizontalChainStyle(this.mHorizontalStyle);
                    constraintWidget3.setHorizontalBiasPercent(this.mHorizontalBias);
                }
                if (i4 == i2 - 1) {
                    constraintWidget3.connect(constraintWidget3.mRight, this.mRight, getPaddingRight());
                }
                if (i4 > 0) {
                    constraintWidget3.connect(constraintWidget3.mLeft, constraintWidget2.mRight, this.mHorizontalGap);
                    constraintWidget2.connect(constraintWidget2.mRight, constraintWidget3.mLeft, 0);
                }
                constraintWidget2 = constraintWidget3;
            }
        }
        for (int i5 = 0; i5 < i3; i5++) {
            ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInRows[i5];
            if (constraintWidget4 != null && constraintWidget4.getVisibility() != 8) {
                if (i5 == 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, this.mTop, getPaddingTop());
                    constraintWidget4.setVerticalChainStyle(this.mVerticalStyle);
                    constraintWidget4.setVerticalBiasPercent(this.mVerticalBias);
                }
                if (i5 == i3 - 1) {
                    constraintWidget4.connect(constraintWidget4.mBottom, this.mBottom, getPaddingBottom());
                }
                if (i5 > 0) {
                    constraintWidget4.connect(constraintWidget4.mTop, constraintWidget2.mBottom, this.mVerticalGap);
                    constraintWidget2.connect(constraintWidget2.mBottom, constraintWidget4.mTop, 0);
                }
                constraintWidget2 = constraintWidget4;
            }
        }
        for (int i6 = 0; i6 < i2; i6++) {
            for (int i7 = 0; i7 < i3; i7++) {
                int i8 = (i7 * i2) + i6;
                if (this.mOrientation == 1) {
                    i8 = (i6 * i3) + i7;
                }
                ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                if (i8 < constraintWidgetArr.length && (constraintWidget = constraintWidgetArr[i8]) != null && constraintWidget.getVisibility() != 8) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInCols[i6];
                    ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInRows[i7];
                    if (constraintWidget != constraintWidget5) {
                        constraintWidget.connect(constraintWidget.mLeft, constraintWidget5.mLeft, 0);
                        constraintWidget.connect(constraintWidget.mRight, constraintWidget5.mRight, 0);
                    }
                    if (constraintWidget != constraintWidget6) {
                        constraintWidget.connect(constraintWidget.mTop, constraintWidget6.mTop, 0);
                        constraintWidget.connect(constraintWidget.mBottom, constraintWidget6.mBottom, 0);
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        super.addToSolver(linearSystem);
        boolean zIsRtl = getParent() != null ? ((ConstraintWidgetContainer) getParent()).isRtl() : false;
        int i = this.mWrapMode;
        if (i != 0) {
            if (i == 1) {
                int size = this.mChainList.size();
                int i2 = 0;
                while (i2 < size) {
                    this.mChainList.get(i2).createConstraints(zIsRtl, i2, i2 == size + (-1));
                    i2++;
                }
            } else if (i == 2) {
                createAlignedConstraints(zIsRtl);
            }
        } else if (this.mChainList.size() > 0) {
            this.mChainList.get(0).createConstraints(zIsRtl, 0, true);
        }
        needsCallbackFromSolver(false);
    }
}
