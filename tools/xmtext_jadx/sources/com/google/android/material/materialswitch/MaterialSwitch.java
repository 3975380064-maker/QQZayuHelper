package com.google.android.material.materialswitch;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import com.google.android.material.R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

/* loaded from: classes.dex */
public class MaterialSwitch extends SwitchCompat {
    private static final int DEF_STYLE_RES = R.style.Widget_Material3_CompoundButton_MaterialSwitch;
    private static final int[] STATE_SET_WITH_ICON = {R.attr.state_with_icon};
    private int[] currentStateChecked;
    private int[] currentStateUnchecked;
    private Drawable thumbDrawable;
    private Drawable thumbIconDrawable;
    private int thumbIconSize;
    private ColorStateList thumbIconTintList;
    private PorterDuff.Mode thumbIconTintMode;
    private ColorStateList thumbTintList;
    private Drawable trackDecorationDrawable;
    private ColorStateList trackDecorationTintList;
    private PorterDuff.Mode trackDecorationTintMode;
    private Drawable trackDrawable;
    private ColorStateList trackTintList;

    public MaterialSwitch(Context context) {
        this(context, null);
    }

    public MaterialSwitch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialSwitchStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public MaterialSwitch(Context context, AttributeSet attributeSet, int i) {
        int i2 = DEF_STYLE_RES;
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        this.thumbIconSize = -1;
        Context context2 = getContext();
        this.thumbDrawable = super.getThumbDrawable();
        this.thumbTintList = super.getThumbTintList();
        super.setThumbTintList(null);
        this.trackDrawable = super.getTrackDrawable();
        this.trackTintList = super.getTrackTintList();
        super.setTrackTintList(null);
        TintTypedArray tintTypedArrayObtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R.styleable.MaterialSwitch, i, i2, new int[0]);
        this.thumbIconDrawable = tintTypedArrayObtainTintedStyledAttributes.getDrawable(R.styleable.MaterialSwitch_thumbIcon);
        this.thumbIconSize = tintTypedArrayObtainTintedStyledAttributes.getDimensionPixelSize(R.styleable.MaterialSwitch_thumbIconSize, -1);
        this.thumbIconTintList = tintTypedArrayObtainTintedStyledAttributes.getColorStateList(R.styleable.MaterialSwitch_thumbIconTint);
        this.thumbIconTintMode = ViewUtils.parseTintMode(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.MaterialSwitch_thumbIconTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.trackDecorationDrawable = tintTypedArrayObtainTintedStyledAttributes.getDrawable(R.styleable.MaterialSwitch_trackDecoration);
        this.trackDecorationTintList = tintTypedArrayObtainTintedStyledAttributes.getColorStateList(R.styleable.MaterialSwitch_trackDecorationTint);
        this.trackDecorationTintMode = ViewUtils.parseTintMode(tintTypedArrayObtainTintedStyledAttributes.getInt(R.styleable.MaterialSwitch_trackDecorationTintMode, -1), PorterDuff.Mode.SRC_IN);
        tintTypedArrayObtainTintedStyledAttributes.recycle();
        setEnforceSwitchWidth(false);
        refreshThumbDrawable();
        refreshTrackDrawable();
    }

    @Override // android.view.View
    public void invalidate() {
        updateDrawableTints();
        super.invalidate();
    }

    @Override // androidx.appcompat.widget.SwitchCompat, android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.thumbIconDrawable != null) {
            mergeDrawableStates(iArrOnCreateDrawableState, STATE_SET_WITH_ICON);
        }
        this.currentStateUnchecked = DrawableUtils.getUncheckedState(iArrOnCreateDrawableState);
        this.currentStateChecked = DrawableUtils.getCheckedState(iArrOnCreateDrawableState);
        return iArrOnCreateDrawableState;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setThumbDrawable(Drawable drawable) {
        this.thumbDrawable = drawable;
        refreshThumbDrawable();
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public Drawable getThumbDrawable() {
        return this.thumbDrawable;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setThumbTintList(ColorStateList colorStateList) {
        this.thumbTintList = colorStateList;
        refreshThumbDrawable();
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public ColorStateList getThumbTintList() {
        return this.thumbTintList;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setThumbTintMode(PorterDuff.Mode mode) {
        super.setThumbTintMode(mode);
        refreshThumbDrawable();
    }

    public void setThumbIconResource(int i) {
        setThumbIconDrawable(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setThumbIconDrawable(Drawable drawable) {
        this.thumbIconDrawable = drawable;
        refreshThumbDrawable();
    }

    public Drawable getThumbIconDrawable() {
        return this.thumbIconDrawable;
    }

    public void setThumbIconSize(int i) {
        if (this.thumbIconSize != i) {
            this.thumbIconSize = i;
            refreshThumbDrawable();
        }
    }

    public int getThumbIconSize() {
        return this.thumbIconSize;
    }

    public void setThumbIconTintList(ColorStateList colorStateList) {
        this.thumbIconTintList = colorStateList;
        refreshThumbDrawable();
    }

    public ColorStateList getThumbIconTintList() {
        return this.thumbIconTintList;
    }

    public void setThumbIconTintMode(PorterDuff.Mode mode) {
        this.thumbIconTintMode = mode;
        refreshThumbDrawable();
    }

    public PorterDuff.Mode getThumbIconTintMode() {
        return this.thumbIconTintMode;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setTrackDrawable(Drawable drawable) {
        this.trackDrawable = drawable;
        refreshTrackDrawable();
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public Drawable getTrackDrawable() {
        return this.trackDrawable;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setTrackTintList(ColorStateList colorStateList) {
        this.trackTintList = colorStateList;
        refreshTrackDrawable();
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public ColorStateList getTrackTintList() {
        return this.trackTintList;
    }

    @Override // androidx.appcompat.widget.SwitchCompat
    public void setTrackTintMode(PorterDuff.Mode mode) {
        super.setTrackTintMode(mode);
        refreshTrackDrawable();
    }

    public void setTrackDecorationResource(int i) {
        setTrackDecorationDrawable(AppCompatResources.getDrawable(getContext(), i));
    }

    public void setTrackDecorationDrawable(Drawable drawable) {
        this.trackDecorationDrawable = drawable;
        refreshTrackDrawable();
    }

    public Drawable getTrackDecorationDrawable() {
        return this.trackDecorationDrawable;
    }

    public void setTrackDecorationTintList(ColorStateList colorStateList) {
        this.trackDecorationTintList = colorStateList;
        refreshTrackDrawable();
    }

    public ColorStateList getTrackDecorationTintList() {
        return this.trackDecorationTintList;
    }

    public void setTrackDecorationTintMode(PorterDuff.Mode mode) {
        this.trackDecorationTintMode = mode;
        refreshTrackDrawable();
    }

    public PorterDuff.Mode getTrackDecorationTintMode() {
        return this.trackDecorationTintMode;
    }

    private void refreshThumbDrawable() {
        this.thumbDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.thumbDrawable, this.thumbTintList, getThumbTintMode());
        this.thumbIconDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.thumbIconDrawable, this.thumbIconTintList, this.thumbIconTintMode);
        updateDrawableTints();
        Drawable drawable = this.thumbDrawable;
        Drawable drawable2 = this.thumbIconDrawable;
        int i = this.thumbIconSize;
        super.setThumbDrawable(DrawableUtils.compositeTwoLayeredDrawable(drawable, drawable2, i, i));
        refreshDrawableState();
    }

    private void refreshTrackDrawable() {
        this.trackDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.trackDrawable, this.trackTintList, getTrackTintMode());
        this.trackDecorationDrawable = DrawableUtils.createTintableDrawableIfNeeded(this.trackDecorationDrawable, this.trackDecorationTintList, this.trackDecorationTintMode);
        updateDrawableTints();
        Drawable layerDrawable = this.trackDrawable;
        if (layerDrawable != null && this.trackDecorationDrawable != null) {
            layerDrawable = new LayerDrawable(new Drawable[]{this.trackDrawable, this.trackDecorationDrawable});
        } else if (layerDrawable == null) {
            layerDrawable = this.trackDecorationDrawable;
        }
        if (layerDrawable != null) {
            setSwitchMinWidth(layerDrawable.getIntrinsicWidth());
        }
        super.setTrackDrawable(layerDrawable);
    }

    private void updateDrawableTints() {
        if (this.thumbTintList == null && this.thumbIconTintList == null && this.trackTintList == null && this.trackDecorationTintList == null) {
            return;
        }
        float thumbPosition = getThumbPosition();
        ColorStateList colorStateList = this.thumbTintList;
        if (colorStateList != null) {
            setInterpolatedDrawableTintIfPossible(this.thumbDrawable, colorStateList, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
        }
        ColorStateList colorStateList2 = this.thumbIconTintList;
        if (colorStateList2 != null) {
            setInterpolatedDrawableTintIfPossible(this.thumbIconDrawable, colorStateList2, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
        }
        ColorStateList colorStateList3 = this.trackTintList;
        if (colorStateList3 != null) {
            setInterpolatedDrawableTintIfPossible(this.trackDrawable, colorStateList3, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
        }
        ColorStateList colorStateList4 = this.trackDecorationTintList;
        if (colorStateList4 != null) {
            setInterpolatedDrawableTintIfPossible(this.trackDecorationDrawable, colorStateList4, this.currentStateUnchecked, this.currentStateChecked, thumbPosition);
        }
    }

    private static void setInterpolatedDrawableTintIfPossible(Drawable drawable, ColorStateList colorStateList, int[] iArr, int[] iArr2, float f) {
        if (drawable == null || colorStateList == null) {
            return;
        }
        DrawableCompat.setTint(drawable, ColorUtils.blendARGB(colorStateList.getColorForState(iArr, 0), colorStateList.getColorForState(iArr2, 0), f));
    }
}
