package com.google.android.material.shadow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.R;

@Deprecated
/* loaded from: classes.dex */
public class ShadowDrawableWrapper extends DrawableWrapperCompat {
    static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    static final float SHADOW_BOTTOM_SCALE = 1.0f;
    static final float SHADOW_HORIZ_SCALE = 0.5f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    static final float SHADOW_TOP_SCALE = 0.25f;
    private boolean addPaddingForCorners;
    final RectF contentBounds;
    float cornerRadius;
    final Paint cornerShadowPaint;
    Path cornerShadowPath;
    private boolean dirty;
    final Paint edgeShadowPaint;
    float maxShadowSize;
    private boolean printedShadowClipWarning;
    float rawMaxShadowSize;
    float rawShadowSize;
    private float rotation;
    private final int shadowEndColor;
    private final int shadowMiddleColor;
    float shadowSize;
    private final int shadowStartColor;

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public ShadowDrawableWrapper(Context context, Drawable drawable, float f, float f2, float f3) {
        super(drawable);
        this.dirty = true;
        this.addPaddingForCorners = true;
        this.printedShadowClipWarning = false;
        this.shadowStartColor = ContextCompat.getColor(context, R.color.design_fab_shadow_start_color);
        this.shadowMiddleColor = ContextCompat.getColor(context, R.color.design_fab_shadow_mid_color);
        this.shadowEndColor = ContextCompat.getColor(context, R.color.design_fab_shadow_end_color);
        Paint paint = new Paint(5);
        this.cornerShadowPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.cornerRadius = Math.round(f);
        this.contentBounds = new RectF();
        Paint paint2 = new Paint(paint);
        this.edgeShadowPaint = paint2;
        paint2.setAntiAlias(false);
        setShadowSize(f2, f3);
    }

    private static int toEven(float f) {
        int iRound = Math.round(f);
        return iRound % 2 == 1 ? iRound - 1 : iRound;
    }

    public void setAddPaddingForCorners(boolean z) {
        this.addPaddingForCorners = z;
        invalidateSelf();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        super.setAlpha(i);
        this.cornerShadowPaint.setAlpha(i);
        this.edgeShadowPaint.setAlpha(i);
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        this.dirty = true;
    }

    public void setShadowSize(float f, float f2) {
        if (f < 0.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float even = toEven(f);
        float even2 = toEven(f2);
        if (even > even2) {
            if (!this.printedShadowClipWarning) {
                this.printedShadowClipWarning = true;
            }
            even = even2;
        }
        if (this.rawShadowSize == even && this.rawMaxShadowSize == even2) {
            return;
        }
        this.rawShadowSize = even;
        this.rawMaxShadowSize = even2;
        this.shadowSize = Math.round(even * SHADOW_MULTIPLIER);
        this.maxShadowSize = even2;
        this.dirty = true;
        invalidateSelf();
    }

    public void setShadowSize(float f) {
        setShadowSize(f, this.rawMaxShadowSize);
    }

    public float getShadowSize() {
        return this.rawShadowSize;
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        int iCeil = (int) Math.ceil(calculateVerticalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        int iCeil2 = (int) Math.ceil(calculateHorizontalPadding(this.rawMaxShadowSize, this.cornerRadius, this.addPaddingForCorners));
        rect.set(iCeil2, iCeil, iCeil2, iCeil);
        return true;
    }

    public static float calculateVerticalPadding(float f, float f2, boolean z) {
        return z ? (float) ((f * SHADOW_MULTIPLIER) + ((1.0d - COS_45) * f2)) : f * SHADOW_MULTIPLIER;
    }

    public static float calculateHorizontalPadding(float f, float f2, boolean z) {
        return z ? (float) (f + ((1.0d - COS_45) * f2)) : f;
    }

    public void setCornerRadius(float f) {
        float fRound = Math.round(f);
        if (this.cornerRadius == fRound) {
            return;
        }
        this.cornerRadius = fRound;
        this.dirty = true;
        invalidateSelf();
    }

    @Override // androidx.appcompat.graphics.drawable.DrawableWrapperCompat, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.dirty) {
            buildComponents(getBounds());
            this.dirty = false;
        }
        drawShadow(canvas);
        super.draw(canvas);
    }

    public final void setRotation(float f) {
        if (this.rotation != f) {
            this.rotation = f;
            invalidateSelf();
        }
    }

    private void drawShadow(Canvas canvas) {
        float f;
        int i;
        int iSave = canvas.save();
        canvas.rotate(this.rotation, this.contentBounds.centerX(), this.contentBounds.centerY());
        float f2 = this.cornerRadius;
        float f3 = (-f2) - this.shadowSize;
        float f4 = f2 * 2.0f;
        boolean z = this.contentBounds.width() - f4 > 0.0f;
        boolean z2 = this.contentBounds.height() - f4 > 0.0f;
        float f5 = this.rawShadowSize;
        float f6 = f2 / ((f5 - (0.5f * f5)) + f2);
        float f7 = f2 / ((f5 - (SHADOW_TOP_SCALE * f5)) + f2);
        float f8 = f2 / ((f5 - (f5 * 1.0f)) + f2);
        int iSave2 = canvas.save();
        canvas.translate(this.contentBounds.left + f2, this.contentBounds.top + f2);
        canvas.scale(f6, f7);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z) {
            canvas.scale(1.0f / f6, 1.0f);
            f = 1.0f;
            i = iSave2;
            canvas.drawRect(0.0f, f3, this.contentBounds.width() - f4, -this.cornerRadius, this.edgeShadowPaint);
        } else {
            f = 1.0f;
            i = iSave2;
        }
        canvas.restoreToCount(i);
        int iSave3 = canvas.save();
        canvas.translate(this.contentBounds.right - f2, this.contentBounds.bottom - f2);
        canvas.scale(f6, f8);
        canvas.rotate(180.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z) {
            canvas.scale(f / f6, f);
            canvas.drawRect(0.0f, f3, this.contentBounds.width() - f4, this.shadowSize + (-this.cornerRadius), this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave3);
        int iSave4 = canvas.save();
        canvas.translate(this.contentBounds.left + f2, this.contentBounds.bottom - f2);
        canvas.scale(f6, f8);
        canvas.rotate(270.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z2) {
            canvas.scale(1.0f / f8, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave4);
        int iSave5 = canvas.save();
        canvas.translate(this.contentBounds.right - f2, this.contentBounds.top + f2);
        canvas.scale(f6, f7);
        canvas.rotate(90.0f);
        canvas.drawPath(this.cornerShadowPath, this.cornerShadowPaint);
        if (z2) {
            canvas.scale(1.0f / f7, 1.0f);
            canvas.drawRect(0.0f, f3, this.contentBounds.height() - f4, -this.cornerRadius, this.edgeShadowPaint);
        }
        canvas.restoreToCount(iSave5);
        canvas.restoreToCount(iSave);
    }

    private void buildShadowCorners() {
        float f = this.cornerRadius;
        RectF rectF = new RectF(-f, -f, f, f);
        RectF rectF2 = new RectF(rectF);
        float f2 = this.shadowSize;
        rectF2.inset(-f2, -f2);
        Path path = this.cornerShadowPath;
        if (path == null) {
            this.cornerShadowPath = new Path();
        } else {
            path.reset();
        }
        this.cornerShadowPath.setFillType(Path.FillType.EVEN_ODD);
        this.cornerShadowPath.moveTo(-this.cornerRadius, 0.0f);
        this.cornerShadowPath.rLineTo(-this.shadowSize, 0.0f);
        this.cornerShadowPath.arcTo(rectF2, 180.0f, 90.0f, false);
        this.cornerShadowPath.arcTo(rectF, 270.0f, -90.0f, false);
        this.cornerShadowPath.close();
        float f3 = -rectF2.top;
        if (f3 > 0.0f) {
            float f4 = this.cornerRadius / f3;
            this.cornerShadowPaint.setShader(new RadialGradient(0.0f, 0.0f, f3, new int[]{0, this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, f4, ((1.0f - f4) / 2.0f) + f4, 1.0f}, Shader.TileMode.CLAMP));
        }
        this.edgeShadowPaint.setShader(new LinearGradient(0.0f, rectF.top, 0.0f, rectF2.top, new int[]{this.shadowStartColor, this.shadowMiddleColor, this.shadowEndColor}, new float[]{0.0f, 0.5f, 1.0f}, Shader.TileMode.CLAMP));
        this.edgeShadowPaint.setAntiAlias(false);
    }

    private void buildComponents(Rect rect) {
        float f = this.rawMaxShadowSize * SHADOW_MULTIPLIER;
        this.contentBounds.set(rect.left + this.rawMaxShadowSize, rect.top + f, rect.right - this.rawMaxShadowSize, rect.bottom - f);
        getDrawable().setBounds((int) this.contentBounds.left, (int) this.contentBounds.top, (int) this.contentBounds.right, (int) this.contentBounds.bottom);
        buildShadowCorners();
    }

    public float getCornerRadius() {
        return this.cornerRadius;
    }

    public void setMaxShadowSize(float f) {
        setShadowSize(this.rawShadowSize, f);
    }

    public float getMaxShadowSize() {
        return this.rawMaxShadowSize;
    }

    public float getMinWidth() {
        float f = this.rawMaxShadowSize;
        return (Math.max(f, this.cornerRadius + (f / 2.0f)) * 2.0f) + (this.rawMaxShadowSize * 2.0f);
    }

    public float getMinHeight() {
        float f = this.rawMaxShadowSize;
        return (Math.max(f, this.cornerRadius + ((f * SHADOW_MULTIPLIER) / 2.0f)) * 2.0f) + (this.rawMaxShadowSize * SHADOW_MULTIPLIER * 2.0f);
    }
}
