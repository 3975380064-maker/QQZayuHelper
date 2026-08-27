package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public final class ResourcesCompat {
    public static final int ID_NULL = 0;
    private static final String TAG = "ResourcesCompat";
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();
    private static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);
    private static final Object sColorStateCacheLock = new Object();

    public static void clearCachesForTheme(Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            Iterator<ColorStateListCacheKey> it = sColorStateCaches.keySet().iterator();
            while (it.hasNext()) {
                ColorStateListCacheKey next = it.next();
                if (next != null && theme.equals(next.mTheme)) {
                    it.remove();
                }
            }
        }
    }

    public static Drawable getDrawable(Resources resources, int i, Resources.Theme theme) throws Resources.NotFoundException {
        return Api21Impl.getDrawable(resources, i, theme);
    }

    public static Drawable getDrawableForDensity(Resources resources, int i, int i2, Resources.Theme theme) throws Resources.NotFoundException {
        return Api21Impl.getDrawableForDensity(resources, i, i2, theme);
    }

    public static int getColor(Resources resources, int i, Resources.Theme theme) throws Resources.NotFoundException {
        return Api23Impl.getColor(resources, i, theme);
    }

    public static ColorStateList getColorStateList(Resources resources, int i, Resources.Theme theme) throws Resources.NotFoundException {
        ColorStateListCacheKey colorStateListCacheKey = new ColorStateListCacheKey(resources, theme);
        ColorStateList cachedColorStateList = getCachedColorStateList(colorStateListCacheKey, i);
        if (cachedColorStateList != null) {
            return cachedColorStateList;
        }
        ColorStateList colorStateListInflateColorStateList = inflateColorStateList(resources, i, theme);
        if (colorStateListInflateColorStateList != null) {
            addColorStateListToCache(colorStateListCacheKey, i, colorStateListInflateColorStateList, theme);
            return colorStateListInflateColorStateList;
        }
        return Api23Impl.getColorStateList(resources, i, theme);
    }

    private static ColorStateList inflateColorStateList(Resources resources, int i, Resources.Theme theme) {
        if (isColorInt(resources, i)) {
            return null;
        }
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(i), theme);
        } catch (Exception e) {
            Log.w(TAG, "Failed to inflate ColorStateList, leaving it to the framework", e);
            return null;
        }
    }

    private static ColorStateList getCachedColorStateList(ColorStateListCacheKey colorStateListCacheKey, int i) {
        ColorStateListCacheEntry colorStateListCacheEntry;
        synchronized (sColorStateCacheLock) {
            SparseArray<ColorStateListCacheEntry> sparseArray = sColorStateCaches.get(colorStateListCacheKey);
            if (sparseArray != null && sparseArray.size() > 0 && (colorStateListCacheEntry = sparseArray.get(i)) != null) {
                if (colorStateListCacheEntry.mConfiguration.equals(colorStateListCacheKey.mResources.getConfiguration()) && ((colorStateListCacheKey.mTheme == null && colorStateListCacheEntry.mThemeHash == 0) || (colorStateListCacheKey.mTheme != null && colorStateListCacheEntry.mThemeHash == colorStateListCacheKey.mTheme.hashCode()))) {
                    return colorStateListCacheEntry.mValue;
                }
                sparseArray.remove(i);
            }
            return null;
        }
    }

    private static void addColorStateListToCache(ColorStateListCacheKey colorStateListCacheKey, int i, ColorStateList colorStateList, Resources.Theme theme) {
        synchronized (sColorStateCacheLock) {
            WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> weakHashMap = sColorStateCaches;
            SparseArray<ColorStateListCacheEntry> sparseArray = weakHashMap.get(colorStateListCacheKey);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                weakHashMap.put(colorStateListCacheKey, sparseArray);
            }
            sparseArray.append(i, new ColorStateListCacheEntry(colorStateList, colorStateListCacheKey.mResources.getConfiguration(), theme));
        }
    }

    private static boolean isColorInt(Resources resources, int i) throws Resources.NotFoundException {
        TypedValue typedValue = getTypedValue();
        resources.getValue(i, typedValue, true);
        return typedValue.type >= 28 && typedValue.type <= 31;
    }

    private static TypedValue getTypedValue() {
        ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
        TypedValue typedValue = threadLocal.get();
        if (typedValue != null) {
            return typedValue;
        }
        TypedValue typedValue2 = new TypedValue();
        threadLocal.set(typedValue2);
        return typedValue2;
    }

    private static final class ColorStateListCacheKey {
        final Resources mResources;
        final Resources.Theme mTheme;

        ColorStateListCacheKey(Resources resources, Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
                if (this.mResources.equals(colorStateListCacheKey.mResources) && ObjectsCompat.equals(this.mTheme, colorStateListCacheKey.mTheme)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return ObjectsCompat.hash(this.mResources, this.mTheme);
        }
    }

    private static class ColorStateListCacheEntry {
        final Configuration mConfiguration;
        final int mThemeHash;
        final ColorStateList mValue;

        ColorStateListCacheEntry(ColorStateList colorStateList, Configuration configuration, Resources.Theme theme) {
            this.mValue = colorStateList;
            this.mConfiguration = configuration;
            this.mThemeHash = theme == null ? 0 : theme.hashCode();
        }
    }

    public static float getFloat(Resources resources, int i) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getFloat(resources, i);
        }
        TypedValue typedValue = getTypedValue();
        resources.getValue(i, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        throw new Resources.NotFoundException("Resource ID #0x" + Integer.toHexString(i) + " type #0x" + Integer.toHexString(typedValue.type) + " is not valid");
    }

    public static Typeface getFont(Context context, int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, new TypedValue(), 0, null, null, false, false);
    }

    public static Typeface getCachedFont(Context context, int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, new TypedValue(), 0, null, null, false, true);
    }

    public static abstract class FontCallback {
        /* renamed from: onFontRetrievalFailed, reason: merged with bridge method [inline-methods] */
        public abstract void m12lambda$callbackFailAsync$1$androidxcorecontentresResourcesCompat$FontCallback(int i);

        /* renamed from: onFontRetrieved, reason: merged with bridge method [inline-methods] */
        public abstract void m13lambda$callbackSuccessAsync$0$androidxcorecontentresResourcesCompat$FontCallback(Typeface typeface);

        public final void callbackSuccessAsync(final Typeface typeface, Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m13lambda$callbackSuccessAsync$0$androidxcorecontentresResourcesCompat$FontCallback(typeface);
                }
            });
        }

        public final void callbackFailAsync(final int i, Handler handler) {
            getHandler(handler).post(new Runnable() { // from class: androidx.core.content.res.ResourcesCompat$FontCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.m12lambda$callbackFailAsync$1$androidxcorecontentresResourcesCompat$FontCallback(i);
                }
            });
        }

        public static Handler getHandler(Handler handler) {
            return handler == null ? new Handler(Looper.getMainLooper()) : handler;
        }
    }

    public static void getFont(Context context, int i, FontCallback fontCallback, Handler handler) throws Resources.NotFoundException {
        Preconditions.checkNotNull(fontCallback);
        if (context.isRestricted()) {
            fontCallback.callbackFailAsync(-4, handler);
        } else {
            loadFont(context, i, new TypedValue(), 0, fontCallback, handler, false, false);
        }
    }

    public static Typeface getFont(Context context, int i, TypedValue typedValue, int i2, FontCallback fontCallback) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, typedValue, i2, fontCallback, null, true, false);
    }

    private static Typeface loadFont(Context context, int i, TypedValue typedValue, int i2, FontCallback fontCallback, Handler handler, boolean z, boolean z2) throws Resources.NotFoundException {
        Resources resources = context.getResources();
        resources.getValue(i, typedValue, true);
        Typeface typefaceLoadFont = loadFont(context, resources, typedValue, i, i2, fontCallback, handler, z, z2);
        if (typefaceLoadFont == null && fontCallback == null && !z2) {
            throw new Resources.NotFoundException("Font resource ID #0x" + Integer.toHexString(i) + " could not be retrieved.");
        }
        return typefaceLoadFont;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ae  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Typeface loadFont(android.content.Context r13, android.content.res.Resources r14, android.util.TypedValue r15, int r16, int r17, androidx.core.content.res.ResourcesCompat.FontCallback r18, android.os.Handler r19, boolean r20, boolean r21) {
        /*
            r2 = r16
            r7 = r18
            r8 = r19
            java.lang.String r10 = "ResourcesCompat"
            java.lang.CharSequence r0 = r15.string
            if (r0 == 0) goto Lb2
            java.lang.CharSequence r0 = r15.string
            java.lang.String r3 = r0.toString()
            java.lang.String r0 = "res/"
            boolean r0 = r3.startsWith(r0)
            r11 = -3
            r12 = 0
            if (r0 != 0) goto L22
            if (r7 == 0) goto L21
            r7.callbackFailAsync(r11, r8)
        L21:
            return r12
        L22:
            int r0 = r15.assetCookie
            r5 = r17
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.findFromCache(r14, r2, r3, r0, r5)
            if (r0 == 0) goto L32
            if (r7 == 0) goto L31
            r7.callbackSuccessAsync(r0, r8)
        L31:
            return r0
        L32:
            if (r21 == 0) goto L35
            return r12
        L35:
            java.lang.String r0 = r3.toLowerCase()     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            java.lang.String r1 = ".xml"
            boolean r0 = r0.endsWith(r1)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            if (r0 == 0) goto L6b
            android.content.res.XmlResourceParser r0 = r14.getXml(r2)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r1 = androidx.core.content.res.FontResourcesParserCompat.parse(r0, r14)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            if (r1 != 0) goto L56
            java.lang.String r13 = "Failed to find font-family tag"
            android.util.Log.e(r10, r13)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            if (r7 == 0) goto L55
            r7.callbackFailAsync(r11, r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
        L55:
            return r12
        L56:
            int r5 = r15.assetCookie     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            r0 = r13
            r6 = r17
            r9 = r20
            r4 = r3
            r3 = r2
            r2 = r14
            android.graphics.Typeface r13 = androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9)     // Catch: java.io.IOException -> L65 org.xmlpull.v1.XmlPullParserException -> L68
            return r13
        L65:
            r0 = move-exception
            r3 = r4
            goto L84
        L68:
            r0 = move-exception
            r3 = r4
            goto L99
        L6b:
            int r4 = r15.assetCookie     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            r0 = r13
            r1 = r14
            r2 = r16
            r5 = r17
            android.graphics.Typeface r13 = androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile(r0, r1, r2, r3, r4, r5)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            if (r7 == 0) goto L82
            if (r13 == 0) goto L7f
            r7.callbackSuccessAsync(r13, r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
            return r13
        L7f:
            r7.callbackFailAsync(r11, r8)     // Catch: java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L98
        L82:
            return r13
        L83:
            r0 = move-exception
        L84:
            r13 = r0
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "Failed to read xml resource "
            r14.<init>(r15)
            java.lang.StringBuilder r14 = r14.append(r3)
            java.lang.String r14 = r14.toString()
            android.util.Log.e(r10, r14, r13)
            goto Lac
        L98:
            r0 = move-exception
        L99:
            r13 = r0
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            java.lang.String r15 = "Failed to parse xml resource "
            r14.<init>(r15)
            java.lang.StringBuilder r14 = r14.append(r3)
            java.lang.String r14 = r14.toString()
            android.util.Log.e(r10, r14, r13)
        Lac:
            if (r7 == 0) goto Lb1
            r7.callbackFailAsync(r11, r8)
        Lb1:
            return r12
        Lb2:
            android.content.res.Resources$NotFoundException r13 = new android.content.res.Resources$NotFoundException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "Resource \""
            r0.<init>(r3)
            java.lang.String r14 = r14.getResourceName(r2)
            java.lang.StringBuilder r14 = r0.append(r14)
            java.lang.String r0 = "\" ("
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r0 = java.lang.Integer.toHexString(r2)
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.String r0 = ") is not a Font: "
            java.lang.StringBuilder r14 = r14.append(r0)
            java.lang.StringBuilder r14 = r14.append(r15)
            java.lang.String r14 = r14.toString()
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.loadFont(android.content.Context, android.content.res.Resources, android.util.TypedValue, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, android.os.Handler, boolean, boolean):android.graphics.Typeface");
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static float getFloat(Resources resources, int i) {
            return resources.getFloat(i);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static ColorStateList getColorStateList(Resources resources, int i, Resources.Theme theme) {
            return resources.getColorStateList(i, theme);
        }

        static int getColor(Resources resources, int i, Resources.Theme theme) {
            return resources.getColor(i, theme);
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static Drawable getDrawable(Resources resources, int i, Resources.Theme theme) {
            return resources.getDrawable(i, theme);
        }

        static Drawable getDrawableForDensity(Resources resources, int i, int i2, Resources.Theme theme) {
            return resources.getDrawableForDensity(i, i2, theme);
        }
    }

    static class Api15Impl {
        private Api15Impl() {
        }

        static Drawable getDrawableForDensity(Resources resources, int i, int i2) {
            return resources.getDrawableForDensity(i, i2);
        }
    }

    private ResourcesCompat() {
    }

    public static final class ThemeCompat {
        private ThemeCompat() {
        }

        public static void rebase(Resources.Theme theme) {
            if (Build.VERSION.SDK_INT >= 29) {
                Api29Impl.rebase(theme);
            } else {
                Api23Impl.rebase(theme);
            }
        }

        static class Api29Impl {
            private Api29Impl() {
            }

            static void rebase(Resources.Theme theme) {
                theme.rebase();
            }
        }

        static class Api23Impl {
            private static Method sRebaseMethod;
            private static boolean sRebaseMethodFetched;
            private static final Object sRebaseMethodLock = new Object();

            private Api23Impl() {
            }

            /* JADX WARN: Removed duplicated region for block: B:30:0x0027 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            static void rebase(android.content.res.Resources.Theme r6) {
                /*
                    java.lang.Object r0 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodLock
                    monitor-enter(r0)
                    boolean r1 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodFetched     // Catch: java.lang.Throwable -> L3c
                    r2 = 0
                    if (r1 != 0) goto L23
                    r1 = 1
                    java.lang.Class<android.content.res.Resources$Theme> r3 = android.content.res.Resources.Theme.class
                    java.lang.String r4 = "rebase"
                    java.lang.Class[] r5 = new java.lang.Class[r2]     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    java.lang.reflect.Method r3 = r3.getDeclaredMethod(r4, r5)     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod = r3     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    r3.setAccessible(r1)     // Catch: java.lang.NoSuchMethodException -> L19 java.lang.Throwable -> L3c
                    goto L21
                L19:
                    r3 = move-exception
                    java.lang.String r4 = "ResourcesCompat"
                    java.lang.String r5 = "Failed to retrieve rebase() method"
                    android.util.Log.i(r4, r5, r3)     // Catch: java.lang.Throwable -> L3c
                L21:
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethodFetched = r1     // Catch: java.lang.Throwable -> L3c
                L23:
                    java.lang.reflect.Method r1 = androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod     // Catch: java.lang.Throwable -> L3c
                    if (r1 == 0) goto L3a
                    java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.reflect.InvocationTargetException -> L2d java.lang.IllegalAccessException -> L2f java.lang.Throwable -> L3c
                    r1.invoke(r6, r2)     // Catch: java.lang.reflect.InvocationTargetException -> L2d java.lang.IllegalAccessException -> L2f java.lang.Throwable -> L3c
                    goto L3a
                L2d:
                    r6 = move-exception
                    goto L30
                L2f:
                    r6 = move-exception
                L30:
                    java.lang.String r1 = "ResourcesCompat"
                    java.lang.String r2 = "Failed to invoke rebase() method via reflection"
                    android.util.Log.i(r1, r2, r6)     // Catch: java.lang.Throwable -> L3c
                    r6 = 0
                    androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.sRebaseMethod = r6     // Catch: java.lang.Throwable -> L3c
                L3a:
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L3c
                    return
                L3c:
                    r6 = move-exception
                    monitor-exit(r0)     // Catch: java.lang.Throwable -> L3c
                    throw r6
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.ThemeCompat.Api23Impl.rebase(android.content.res.Resources$Theme):void");
            }
        }
    }
}
