package com.google.android.material.motion;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.TypedValue;
import android.view.animation.AnimationUtils;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.google.android.material.resources.MaterialAttributes;

/* loaded from: classes.dex */
public class MotionUtils {
    private static final String EASING_TYPE_CUBIC_BEZIER = "cubic-bezier";
    private static final String EASING_TYPE_FORMAT_END = ")";
    private static final String EASING_TYPE_FORMAT_START = "(";
    private static final String EASING_TYPE_PATH = "path";

    private MotionUtils() {
    }

    public static int resolveThemeDuration(Context context, int i, int i2) {
        return MaterialAttributes.resolveInteger(context, i, i2);
    }

    public static TimeInterpolator resolveThemeInterpolator(Context context, int i, TimeInterpolator timeInterpolator) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            return timeInterpolator;
        }
        if (typedValue.type != 3) {
            throw new IllegalArgumentException("Motion easing theme attribute must be an @interpolator resource for ?attr/motionEasing*Interpolator attributes or a string for ?attr/motionEasing* attributes.");
        }
        String strValueOf = String.valueOf(typedValue.string);
        if (isLegacyEasingAttribute(strValueOf)) {
            return getLegacyThemeInterpolator(strValueOf);
        }
        return AnimationUtils.loadInterpolator(context, typedValue.resourceId);
    }

    private static TimeInterpolator getLegacyThemeInterpolator(String str) {
        if (isLegacyEasingType(str, EASING_TYPE_CUBIC_BEZIER)) {
            String[] strArrSplit = getLegacyEasingContent(str, EASING_TYPE_CUBIC_BEZIER).split(",");
            if (strArrSplit.length != 4) {
                throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + strArrSplit.length);
            }
            return PathInterpolatorCompat.create(getLegacyControlPoint(strArrSplit, 0), getLegacyControlPoint(strArrSplit, 1), getLegacyControlPoint(strArrSplit, 2), getLegacyControlPoint(strArrSplit, 3));
        }
        if (isLegacyEasingType(str, EASING_TYPE_PATH)) {
            return PathInterpolatorCompat.create(PathParser.createPathFromPathData(getLegacyEasingContent(str, EASING_TYPE_PATH)));
        }
        throw new IllegalArgumentException("Invalid motion easing type: " + str);
    }

    private static boolean isLegacyEasingAttribute(String str) {
        return isLegacyEasingType(str, EASING_TYPE_CUBIC_BEZIER) || isLegacyEasingType(str, EASING_TYPE_PATH);
    }

    private static boolean isLegacyEasingType(String str, String str2) {
        return str.startsWith(new StringBuilder().append(str2).append(EASING_TYPE_FORMAT_START).toString()) && str.endsWith(EASING_TYPE_FORMAT_END);
    }

    private static String getLegacyEasingContent(String str, String str2) {
        return str.substring(str2.length() + EASING_TYPE_FORMAT_START.length(), str.length() - EASING_TYPE_FORMAT_END.length());
    }

    private static float getLegacyControlPoint(String[] strArr, int i) throws NumberFormatException {
        float f = Float.parseFloat(strArr[i]);
        if (f < 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + f);
        }
        return f;
    }
}
