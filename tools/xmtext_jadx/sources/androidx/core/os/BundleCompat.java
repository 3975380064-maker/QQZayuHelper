package androidx.core.os;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class BundleCompat {
    private BundleCompat() {
    }

    public static <T> T getParcelable(Bundle bundle, String str, Class<T> cls) {
        if (BuildCompat.isAtLeastU()) {
            return (T) Api33Impl.getParcelable(bundle, str, cls);
        }
        T t = (T) bundle.getParcelable(str);
        if (cls.isInstance(t)) {
            return t;
        }
        return null;
    }

    public static Parcelable[] getParcelableArray(Bundle bundle, String str, Class<? extends Parcelable> cls) {
        if (BuildCompat.isAtLeastU()) {
            return (Parcelable[]) Api33Impl.getParcelableArray(bundle, str, cls);
        }
        return bundle.getParcelableArray(str);
    }

    public static <T> ArrayList<T> getParcelableArrayList(Bundle bundle, String str, Class<? extends T> cls) {
        if (BuildCompat.isAtLeastU()) {
            return Api33Impl.getParcelableArrayList(bundle, str, cls);
        }
        return bundle.getParcelableArrayList(str);
    }

    public static <T> SparseArray<T> getSparseParcelableArray(Bundle bundle, String str, Class<? extends T> cls) {
        if (BuildCompat.isAtLeastU()) {
            return Api33Impl.getSparseParcelableArray(bundle, str, cls);
        }
        return bundle.getSparseParcelableArray(str);
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static <T> T getParcelable(Bundle bundle, String str, Class<T> cls) {
            return (T) bundle.getParcelable(str, cls);
        }

        static <T> T[] getParcelableArray(Bundle bundle, String str, Class<T> cls) {
            return (T[]) bundle.getParcelableArray(str, cls);
        }

        static <T> ArrayList<T> getParcelableArrayList(Bundle bundle, String str, Class<? extends T> cls) {
            return bundle.getParcelableArrayList(str, cls);
        }

        static <T> SparseArray<T> getSparseParcelableArray(Bundle bundle, String str, Class<? extends T> cls) {
            return bundle.getSparseParcelableArray(str, cls);
        }
    }
}
