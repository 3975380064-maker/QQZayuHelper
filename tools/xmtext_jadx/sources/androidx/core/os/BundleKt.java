package androidx.core.os;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.io.Serializable;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

/* compiled from: Bundle.kt */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u0006\u0010\u0000\u001a\u00020\u0001\u001a;\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\u0010\u0007¨\u0006\b"}, d2 = {"bundleOf", "Landroid/os/Bundle;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/os/Bundle;", "core-ktx_release"}, k = 2, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class BundleKt {
    public static final Bundle bundleOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        Bundle bundle = new Bundle(pairs.length);
        for (Pair<String, ? extends Object> pair : pairs) {
            String strComponent1 = pair.component1();
            Object objComponent2 = pair.component2();
            if (objComponent2 == null) {
                bundle.putString(strComponent1, null);
            } else if (objComponent2 instanceof Boolean) {
                bundle.putBoolean(strComponent1, ((Boolean) objComponent2).booleanValue());
            } else if (objComponent2 instanceof Byte) {
                bundle.putByte(strComponent1, ((Number) objComponent2).byteValue());
            } else if (objComponent2 instanceof Character) {
                bundle.putChar(strComponent1, ((Character) objComponent2).charValue());
            } else if (objComponent2 instanceof Double) {
                bundle.putDouble(strComponent1, ((Number) objComponent2).doubleValue());
            } else if (objComponent2 instanceof Float) {
                bundle.putFloat(strComponent1, ((Number) objComponent2).floatValue());
            } else if (objComponent2 instanceof Integer) {
                bundle.putInt(strComponent1, ((Number) objComponent2).intValue());
            } else if (objComponent2 instanceof Long) {
                bundle.putLong(strComponent1, ((Number) objComponent2).longValue());
            } else if (objComponent2 instanceof Short) {
                bundle.putShort(strComponent1, ((Number) objComponent2).shortValue());
            } else if (objComponent2 instanceof Bundle) {
                bundle.putBundle(strComponent1, (Bundle) objComponent2);
            } else if (objComponent2 instanceof CharSequence) {
                bundle.putCharSequence(strComponent1, (CharSequence) objComponent2);
            } else if (objComponent2 instanceof Parcelable) {
                bundle.putParcelable(strComponent1, (Parcelable) objComponent2);
            } else if (objComponent2 instanceof boolean[]) {
                bundle.putBooleanArray(strComponent1, (boolean[]) objComponent2);
            } else if (objComponent2 instanceof byte[]) {
                bundle.putByteArray(strComponent1, (byte[]) objComponent2);
            } else if (objComponent2 instanceof char[]) {
                bundle.putCharArray(strComponent1, (char[]) objComponent2);
            } else if (objComponent2 instanceof double[]) {
                bundle.putDoubleArray(strComponent1, (double[]) objComponent2);
            } else if (objComponent2 instanceof float[]) {
                bundle.putFloatArray(strComponent1, (float[]) objComponent2);
            } else if (objComponent2 instanceof int[]) {
                bundle.putIntArray(strComponent1, (int[]) objComponent2);
            } else if (objComponent2 instanceof long[]) {
                bundle.putLongArray(strComponent1, (long[]) objComponent2);
            } else if (objComponent2 instanceof short[]) {
                bundle.putShortArray(strComponent1, (short[]) objComponent2);
            } else if (objComponent2 instanceof Object[]) {
                Class<?> componentType = objComponent2.getClass().getComponentType();
                Intrinsics.checkNotNull(componentType);
                if (Parcelable.class.isAssignableFrom(componentType)) {
                    Intrinsics.checkNotNull(objComponent2, "null cannot be cast to non-null type kotlin.Array<android.os.Parcelable>");
                    bundle.putParcelableArray(strComponent1, (Parcelable[]) objComponent2);
                } else if (String.class.isAssignableFrom(componentType)) {
                    Intrinsics.checkNotNull(objComponent2, "null cannot be cast to non-null type kotlin.Array<kotlin.String>");
                    bundle.putStringArray(strComponent1, (String[]) objComponent2);
                } else if (CharSequence.class.isAssignableFrom(componentType)) {
                    Intrinsics.checkNotNull(objComponent2, "null cannot be cast to non-null type kotlin.Array<kotlin.CharSequence>");
                    bundle.putCharSequenceArray(strComponent1, (CharSequence[]) objComponent2);
                } else if (Serializable.class.isAssignableFrom(componentType)) {
                    bundle.putSerializable(strComponent1, (Serializable) objComponent2);
                } else {
                    throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + strComponent1 + Typography.quote);
                }
            } else if (objComponent2 instanceof Serializable) {
                bundle.putSerializable(strComponent1, (Serializable) objComponent2);
            } else if (objComponent2 instanceof IBinder) {
                BundleApi18ImplKt.putBinder(bundle, strComponent1, (IBinder) objComponent2);
            } else if (objComponent2 instanceof Size) {
                BundleApi21ImplKt.putSize(bundle, strComponent1, (Size) objComponent2);
            } else if (objComponent2 instanceof SizeF) {
                BundleApi21ImplKt.putSizeF(bundle, strComponent1, (SizeF) objComponent2);
            } else {
                throw new IllegalArgumentException("Illegal value type " + objComponent2.getClass().getCanonicalName() + " for key \"" + strComponent1 + Typography.quote);
            }
        }
        return bundle;
    }

    public static final Bundle bundleOf() {
        return new Bundle(0);
    }
}
