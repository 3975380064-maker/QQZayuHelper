package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

/* loaded from: classes.dex */
public class ActionBarContainer extends FrameLayout {
    private View mActionBarView;
    Drawable mBackground;
    private View mContextView;
    private int mHeight;
    boolean mIsSplit;
    boolean mIsStacked;
    private boolean mIsTransitioning;
    Drawable mSplitBackground;
    Drawable mStackedBackground;
    private View mTabContainer;

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ViewCompat.setBackground(this, new ActionBarBackgroundDrawable(this));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
        this.mBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_background);
        this.mStackedBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundStacked);
        this.mHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_height, -1);
        boolean z = true;
        if (getId() == R.id.split_action_bar) {
            this.mIsSplit = true;
            this.mSplitBackground = typedArrayObtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundSplit);
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!this.mIsSplit ? this.mBackground != null || this.mStackedBackground != null : this.mSplitBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(R.id.action_bar);
        this.mContextView = findViewById(R.id.action_context_bar);
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        this.mBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            View view = this.mActionBarView;
            if (view != null) {
                this.mBackground.setBounds(view.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
            }
        }
        boolean z = true;
        if (!this.mIsSplit ? this.mBackground != null || this.mStackedBackground != null : this.mSplitBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
        Api21Impl.invalidateOutline(this);
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.mStackedBackground;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.mStackedBackground);
        }
        this.mStackedBackground = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mIsStacked && (drawable2 = this.mStackedBackground) != null) {
                drawable2.setBounds(this.mTabContainer.getLeft(), this.mTabContainer.getTop(), this.mTabContainer.getRight(), this.mTabContainer.getBottom());
            }
        }
        boolean z = true;
        if (!this.mIsSplit ? this.mBackground != null || this.mStackedBackground != null : this.mSplitBackground != null) {
            z = false;
        }
        setWillNotDraw(z);
        invalidate();
        Api21Impl.invalidateOutline(this);
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.mSplitBackground);
        }
        this.mSplitBackground = drawable;
        boolean z = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.mIsSplit && (drawable2 = this.mSplitBackground) != null) {
                drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!this.mIsSplit ? !(this.mBackground != null || this.mStackedBackground != null) : this.mSplitBackground == null) {
            z = true;
        }
        setWillNotDraw(z);
        invalidate();
        Api21Impl.invalidateOutline(this);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null) {
            drawable2.setVisible(z, false);
        }
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null) {
            drawable3.setVisible(z, false);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        if (drawable == this.mBackground && !this.mIsSplit) {
            return true;
        }
        if (drawable == this.mStackedBackground && this.mIsStacked) {
            return true;
        }
        return (drawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mBackground;
        if (drawable != null && drawable.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null && drawable2.isStateful()) {
            this.mStackedBackground.setState(getDrawableState());
        }
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 == null || !drawable3.isStateful()) {
            return;
        }
        this.mSplitBackground.setState(getDrawableState());
    }

    @Override // android.view.ViewGroup, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mStackedBackground;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        Drawable drawable3 = this.mSplitBackground;
        if (drawable3 != null) {
            drawable3.jumpToCurrentState();
        }
    }

    public void setTransitioning(boolean z) {
        this.mIsTransitioning = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        View view = this.mTabContainer;
        if (view != null) {
            removeView(view);
        }
        this.mTabContainer = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 0) {
            return super.startActionModeForChild(view, callback, i);
        }
        return null;
    }

    private boolean isCollapsed(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    private int getMeasuredHeightWithMargins(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int measuredHeightWithMargins;
        int i3;
        if (this.mActionBarView == null && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE && (i3 = this.mHeight) >= 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(i3, View.MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.mActionBarView == null) {
            return;
        }
        int mode = View.MeasureSpec.getMode(i2);
        View view = this.mTabContainer;
        if (view == null || view.getVisibility() == 8 || mode == 1073741824) {
            return;
        }
        if (!isCollapsed(this.mActionBarView)) {
            measuredHeightWithMargins = getMeasuredHeightWithMargins(this.mActionBarView);
        } else {
            measuredHeightWithMargins = !isCollapsed(this.mContextView) ? getMeasuredHeightWithMargins(this.mContextView) : 0;
        }
        setMeasuredDimension(getMeasuredWidth(), Math.min(measuredHeightWithMargins + getMeasuredHeightWithMargins(this.mTabContainer), mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i2) : Integer.MAX_VALUE));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004b A[PHI: r1
      0x004b: PHI (r1v8 boolean) = (r1v1 boolean), (r1v1 boolean), (r1v0 boolean) binds: [B:31:0x00a8, B:33:0x00ac, B:15:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayout(boolean r7, int r8, int r9, int r10, int r11) {
        /*
            r6 = this;
            super.onLayout(r7, r8, r9, r10, r11)
            r7 = r6
            android.view.View r9 = r7.mTabContainer
            r11 = 8
            r0 = 1
            r1 = 0
            if (r9 == 0) goto L14
            int r2 = r9.getVisibility()
            if (r2 == r11) goto L14
            r2 = r0
            goto L15
        L14:
            r2 = r1
        L15:
            if (r9 == 0) goto L36
            int r3 = r9.getVisibility()
            if (r3 == r11) goto L36
            int r11 = r6.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r3 = r9.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r3 = (android.widget.FrameLayout.LayoutParams) r3
            int r4 = r9.getMeasuredHeight()
            int r4 = r11 - r4
            int r5 = r3.bottomMargin
            int r4 = r4 - r5
            int r3 = r3.bottomMargin
            int r11 = r11 - r3
            r9.layout(r8, r4, r10, r11)
        L36:
            boolean r8 = r7.mIsSplit
            if (r8 == 0) goto L4e
            android.graphics.drawable.Drawable r8 = r7.mSplitBackground
            if (r8 == 0) goto L4b
            int r9 = r6.getMeasuredWidth()
            int r10 = r6.getMeasuredHeight()
            r8.setBounds(r1, r1, r9, r10)
            goto Lc1
        L4b:
            r0 = r1
            goto Lc1
        L4e:
            android.graphics.drawable.Drawable r8 = r7.mBackground
            if (r8 == 0) goto La6
            android.view.View r8 = r7.mActionBarView
            int r8 = r8.getVisibility()
            if (r8 != 0) goto L78
            android.graphics.drawable.Drawable r8 = r7.mBackground
            android.view.View r10 = r7.mActionBarView
            int r10 = r10.getLeft()
            android.view.View r11 = r7.mActionBarView
            int r11 = r11.getTop()
            android.view.View r1 = r7.mActionBarView
            int r1 = r1.getRight()
            android.view.View r3 = r7.mActionBarView
            int r3 = r3.getBottom()
            r8.setBounds(r10, r11, r1, r3)
            goto La5
        L78:
            android.view.View r8 = r7.mContextView
            if (r8 == 0) goto La0
            int r8 = r8.getVisibility()
            if (r8 != 0) goto La0
            android.graphics.drawable.Drawable r8 = r7.mBackground
            android.view.View r10 = r7.mContextView
            int r10 = r10.getLeft()
            android.view.View r11 = r7.mContextView
            int r11 = r11.getTop()
            android.view.View r1 = r7.mContextView
            int r1 = r1.getRight()
            android.view.View r3 = r7.mContextView
            int r3 = r3.getBottom()
            r8.setBounds(r10, r11, r1, r3)
            goto La5
        La0:
            android.graphics.drawable.Drawable r8 = r7.mBackground
            r8.setBounds(r1, r1, r1, r1)
        La5:
            r1 = r0
        La6:
            r7.mIsStacked = r2
            if (r2 == 0) goto L4b
            android.graphics.drawable.Drawable r8 = r7.mStackedBackground
            if (r8 == 0) goto L4b
            int r10 = r9.getLeft()
            int r11 = r9.getTop()
            int r1 = r9.getRight()
            int r9 = r9.getBottom()
            r8.setBounds(r10, r11, r1, r9)
        Lc1:
            if (r0 == 0) goto Lc6
            r6.invalidate()
        Lc6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionBarContainer.onLayout(boolean, int, int, int, int):void");
    }

    private static class Api21Impl {
        private Api21Impl() {
        }

        public static void invalidateOutline(ActionBarContainer actionBarContainer) {
            actionBarContainer.invalidateOutline();
        }
    }
}
