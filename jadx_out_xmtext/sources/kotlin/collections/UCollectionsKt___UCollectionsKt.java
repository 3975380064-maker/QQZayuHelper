package kotlin.collections;

import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: _UCollections.kt */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u0019\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007¢\u0006\u0004\b\b\u0010\t\u001a\u0019\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u0017\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007¢\u0006\u0002\u0010\u000f\u001a\u0017\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007¢\u0006\u0002\u0010\u0012\u001a\u0017\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007¢\u0006\u0002\u0010\u0015\u001a\u0017\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007¢\u0006\u0002\u0010\u0018¨\u0006\u0019"}, d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/collections/UCollectionsKt")
/* loaded from: classes.dex */
class UCollectionsKt___UCollectionsKt {
    public static final byte[] toUByteArray(Collection<UByte> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        byte[] bArrM205constructorimpl = UByteArray.m205constructorimpl(collection.size());
        Iterator<UByte> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            UByteArray.m216setVurrAj0(bArrM205constructorimpl, i, it.next().m202unboximpl());
            i++;
        }
        return bArrM205constructorimpl;
    }

    public static final int[] toUIntArray(Collection<UInt> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        int[] iArrM284constructorimpl = UIntArray.m284constructorimpl(collection.size());
        Iterator<UInt> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            UIntArray.m295setVXSXFK8(iArrM284constructorimpl, i, it.next().m282unboximpl());
            i++;
        }
        return iArrM284constructorimpl;
    }

    public static final long[] toULongArray(Collection<ULong> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        long[] jArrM363constructorimpl = ULongArray.m363constructorimpl(collection.size());
        Iterator<ULong> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            ULongArray.m374setk8EXiF4(jArrM363constructorimpl, i, it.next().m361unboximpl());
            i++;
        }
        return jArrM363constructorimpl;
    }

    public static final short[] toUShortArray(Collection<UShort> collection) {
        Intrinsics.checkNotNullParameter(collection, "<this>");
        short[] sArrM468constructorimpl = UShortArray.m468constructorimpl(collection.size());
        Iterator<UShort> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            UShortArray.m479set01HTLdE(sArrM468constructorimpl, i, it.next().m466unboximpl());
            i++;
        }
        return sArrM468constructorimpl;
    }

    public static final int sumOfUInt(Iterable<UInt> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<UInt> it = iterable.iterator();
        int iM230constructorimpl = 0;
        while (it.hasNext()) {
            iM230constructorimpl = UInt.m230constructorimpl(iM230constructorimpl + it.next().m282unboximpl());
        }
        return iM230constructorimpl;
    }

    public static final long sumOfULong(Iterable<ULong> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<ULong> it = iterable.iterator();
        long jM309constructorimpl = 0;
        while (it.hasNext()) {
            jM309constructorimpl = ULong.m309constructorimpl(jM309constructorimpl + it.next().m361unboximpl());
        }
        return jM309constructorimpl;
    }

    public static final int sumOfUByte(Iterable<UByte> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<UByte> it = iterable.iterator();
        int iM230constructorimpl = 0;
        while (it.hasNext()) {
            iM230constructorimpl = UInt.m230constructorimpl(iM230constructorimpl + UInt.m230constructorimpl(it.next().m202unboximpl() & UByte.MAX_VALUE));
        }
        return iM230constructorimpl;
    }

    public static final int sumOfUShort(Iterable<UShort> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Iterator<UShort> it = iterable.iterator();
        int iM230constructorimpl = 0;
        while (it.hasNext()) {
            iM230constructorimpl = UInt.m230constructorimpl(iM230constructorimpl + UInt.m230constructorimpl(it.next().m466unboximpl() & UShort.MAX_VALUE));
        }
        return iM230constructorimpl;
    }
}
