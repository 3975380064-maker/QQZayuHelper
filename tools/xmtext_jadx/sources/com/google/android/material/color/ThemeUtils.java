package com.google.android.material.color;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;

/* loaded from: classes.dex */
public final class ThemeUtils {
    private ThemeUtils() {
    }

    public static void applyThemeOverlay(Context context, int i) {
        Resources.Theme windowDecorViewTheme;
        context.getTheme().applyStyle(i, true);
        if (!(context instanceof Activity) || (windowDecorViewTheme = getWindowDecorViewTheme((Activity) context)) == null) {
            return;
        }
        windowDecorViewTheme.applyStyle(i, true);
    }

    private static Resources.Theme getWindowDecorViewTheme(Activity activity) {
        View viewPeekDecorView;
        Context context;
        Window window = activity.getWindow();
        if (window == null || (viewPeekDecorView = window.peekDecorView()) == null || (context = viewPeekDecorView.getContext()) == null) {
            return null;
        }
        return context.getTheme();
    }
}
