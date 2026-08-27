package androidx.core.graphics;

import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
final class WeightTypefaceApi21 {
    private static final String NATIVE_CREATE_FROM_TYPEFACE_METHOD = "nativeCreateFromTypeface";
    private static final String NATIVE_CREATE_WEIGHT_ALIAS_METHOD = "nativeCreateWeightAlias";
    private static final String NATIVE_INSTANCE_FIELD = "native_instance";
    private static final String TAG = "WeightTypeface";
    private static final Constructor<Typeface> sConstructor;
    private static final Method sNativeCreateFromTypeface;
    private static final Method sNativeCreateWeightAlias;
    private static final Field sNativeInstance;
    private static final Object sWeightCacheLock;
    private static final LongSparseArray<SparseArray<Typeface>> sWeightTypefaceCache;

    static {
        Field declaredField;
        Method declaredMethod;
        Method declaredMethod2;
        Constructor<Typeface> declaredConstructor;
        try {
            declaredField = Typeface.class.getDeclaredField(NATIVE_INSTANCE_FIELD);
            declaredMethod = Typeface.class.getDeclaredMethod(NATIVE_CREATE_FROM_TYPEFACE_METHOD, Long.TYPE, Integer.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod2 = Typeface.class.getDeclaredMethod(NATIVE_CREATE_WEIGHT_ALIAS_METHOD, Long.TYPE, Integer.TYPE);
            declaredMethod2.setAccessible(true);
            declaredConstructor = Typeface.class.getDeclaredConstructor(Long.TYPE);
            declaredConstructor.setAccessible(true);
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            Log.e(TAG, e.getClass().getName(), e);
            declaredField = null;
            declaredMethod = null;
            declaredMethod2 = null;
            declaredConstructor = null;
        }
        sNativeInstance = declaredField;
        sNativeCreateFromTypeface = declaredMethod;
        sNativeCreateWeightAlias = declaredMethod2;
        sConstructor = declaredConstructor;
        sWeightTypefaceCache = new LongSparseArray<>(3);
        sWeightCacheLock = new Object();
    }

    private static boolean isPrivateApiAvailable() {
        return sNativeInstance != null;
    }

    static Typeface createWeightStyle(Typeface typeface, int i, boolean z) {
        Typeface typefaceCreate;
        if (!isPrivateApiAvailable()) {
            return null;
        }
        int i2 = (i << 1) | (z ? 1 : 0);
        synchronized (sWeightCacheLock) {
            long nativeInstance = getNativeInstance(typeface);
            LongSparseArray<SparseArray<Typeface>> longSparseArray = sWeightTypefaceCache;
            SparseArray<Typeface> sparseArray = longSparseArray.get(nativeInstance);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>(4);
                longSparseArray.put(nativeInstance, sparseArray);
            } else {
                Typeface typeface2 = sparseArray.get(i2);
                if (typeface2 != null) {
                    return typeface2;
                }
            }
            if (z == typeface.isItalic()) {
                typefaceCreate = create(nativeCreateWeightAlias(nativeInstance, i));
            } else {
                typefaceCreate = create(nativeCreateFromTypefaceWithExactStyle(nativeInstance, i, z));
            }
            sparseArray.put(i2, typefaceCreate);
            return typefaceCreate;
        }
    }

    private static long getNativeInstance(Typeface typeface) {
        try {
            return sNativeInstance.getLong(typeface);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static long nativeCreateFromTypefaceWithExactStyle(long j, int i, boolean z) {
        try {
            return ((Long) sNativeCreateWeightAlias.invoke(null, Long.valueOf(((Long) sNativeCreateFromTypeface.invoke(null, Long.valueOf(j), Integer.valueOf(z ? 2 : 0))).longValue()), Integer.valueOf(i))).longValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static long nativeCreateWeightAlias(long j, int i) {
        try {
            return ((Long) sNativeCreateWeightAlias.invoke(null, Long.valueOf(j), Integer.valueOf(i))).longValue();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static Typeface create(long j) {
        try {
            return sConstructor.newInstance(Long.valueOf(j));
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException unused) {
            return null;
        }
    }

    private WeightTypefaceApi21() {
    }
}
