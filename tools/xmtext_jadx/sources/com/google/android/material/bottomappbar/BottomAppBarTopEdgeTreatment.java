package com.google.android.material.bottomappbar;

import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* loaded from: classes.dex */
public class BottomAppBarTopEdgeTreatment extends EdgeTreatment implements Cloneable {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private static final float ROUNDED_CORNER_FAB_OFFSET = 1.75f;
    private float cradleVerticalOffset;
    private float fabCornerSize = -1.0f;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f, float f2, float f3) {
        this.fabMargin = f;
        this.roundedCornerRadius = f2;
        setCradleVerticalOffset(f3);
        this.horizontalOffset = 0.0f;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        float f4;
        float f5;
        float f6 = this.fabDiameter;
        if (f6 == 0.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        float f7 = ((this.fabMargin * 2.0f) + f6) / 2.0f;
        float f8 = f3 * this.roundedCornerRadius;
        float f9 = f2 + this.horizontalOffset;
        float f10 = (this.cradleVerticalOffset * f3) + ((1.0f - f3) * f7);
        if (f10 / f7 >= 1.0f) {
            shapePath.lineTo(f, 0.0f);
            return;
        }
        float f11 = this.fabCornerSize;
        float f12 = f11 * f3;
        boolean z = f11 == -1.0f || Math.abs((f11 * 2.0f) - f6) < 0.1f;
        if (z) {
            f4 = f10;
            f5 = 0.0f;
        } else {
            f5 = ROUNDED_CORNER_FAB_OFFSET;
            f4 = 0.0f;
        }
        float f13 = f7 + f8;
        float f14 = f4 + f8;
        float fSqrt = (float) Math.sqrt((f13 * f13) - (f14 * f14));
        float f15 = f9 - fSqrt;
        float f16 = f9 + fSqrt;
        float degrees = (float) Math.toDegrees(Math.atan(fSqrt / f14));
        float f17 = (90.0f - degrees) + f5;
        shapePath.lineTo(f15, 0.0f);
        float f18 = f15 - f8;
        float f19 = f15 + f8;
        float f20 = f8 * 2.0f;
        shapePath.addArc(f18, 0.0f, f19, f20, 270.0f, degrees);
        if (z) {
            shapePath.addArc(f9 - f7, (-f7) - f4, f9 + f7, f7 - f4, 180.0f - f17, (f17 * 2.0f) - 180.0f);
        } else {
            float f21 = this.fabMargin;
            float f22 = f12 * 2.0f;
            float f23 = f21 + f22;
            float f24 = f9 - f7;
            shapePath.addArc(f24, -(f12 + f21), f23 + f24, f21 + f12, 180.0f - f17, ((f17 * 2.0f) - 180.0f) / 2.0f);
            float f25 = f9 + f7;
            float f26 = this.fabMargin;
            shapePath.lineTo(f25 - ((f26 / 2.0f) + f12), f26 + f12);
            float f27 = this.fabMargin;
            shapePath.addArc(f25 - (f22 + f27), -(f12 + f27), f25, f27 + f12, 90.0f, f17 - 90.0f);
        }
        shapePath.addArc(f16 - f8, 0.0f, f16 + f8, f20, 270.0f - degrees, degrees);
        shapePath.lineTo(f, 0.0f);
    }

    public float getFabDiameter() {
        return this.fabDiameter;
    }

    public void setFabDiameter(float f) {
        this.fabDiameter = f;
    }

    void setHorizontalOffset(float f) {
        this.horizontalOffset = f;
    }

    public float getHorizontalOffset() {
        return this.horizontalOffset;
    }

    float getCradleVerticalOffset() {
        return this.cradleVerticalOffset;
    }

    void setCradleVerticalOffset(float f) {
        if (f < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.cradleVerticalOffset = f;
    }

    float getFabCradleMargin() {
        return this.fabMargin;
    }

    void setFabCradleMargin(float f) {
        this.fabMargin = f;
    }

    float getFabCradleRoundedCornerRadius() {
        return this.roundedCornerRadius;
    }

    void setFabCradleRoundedCornerRadius(float f) {
        this.roundedCornerRadius = f;
    }

    public float getFabCornerRadius() {
        return this.fabCornerSize;
    }

    public void setFabCornerSize(float f) {
        this.fabCornerSize = f;
    }
}
