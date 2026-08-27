package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* compiled from: UnsignedJVM.kt */
@Metadata(d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001¢\u0006\u0002\u0010\u0004\u001a\u0015\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0001¢\u0006\u0002\u0010\u0007\u001a\u0016\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\tH\u0081\b¢\u0006\u0002\u0010\n\u001a\u0016\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\tH\u0081\b¢\u0006\u0002\u0010\f\u001a\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u000eH\u0001\u001a\u001f\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u0012\u0010\u0013\u001a\u001f\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u0015\u0010\u0013\u001a\u0010\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u000eH\u0001\u001a\u0011\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u000eH\u0081\b\u001a\u0011\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u000eH\u0081\b\u001a\u0011\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u000eH\u0081\b\u001a\u0019\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u000eH\u0081\b\u001a\u0016\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u000eH\u0081\b¢\u0006\u0002\u0010\u001e\u001a\u0018\u0010\u001f\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u0019H\u0001\u001a\u001f\u0010 \u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0001¢\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0001¢\u0006\u0004\b$\u0010\"\u001a\u0010\u0010%\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0019H\u0001\u001a\u0011\u0010&\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0019H\u0081\b\u001a\u0011\u0010'\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u0019H\u0081\b\u001a\u0018\u0010'\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u000eH\u0000¨\u0006("}, d2 = {"doubleToUInt", "Lkotlin/UInt;", "value", "", "(D)I", "doubleToULong", "Lkotlin/ULong;", "(D)J", "floatToUInt", "", "(F)I", "floatToULong", "(F)J", "uintCompare", "", "v1", "v2", "uintDivide", "uintDivide-J1ME1BU", "(II)I", "uintRemainder", "uintRemainder-J1ME1BU", "uintToDouble", "uintToFloat", "uintToLong", "", "uintToString", "", "base", "uintToULong", "(I)J", "ulongCompare", "ulongDivide", "ulongDivide-eb3DHEI", "(JJ)J", "ulongRemainder", "ulongRemainder-eb3DHEI", "ulongToDouble", "ulongToFloat", "ulongToString", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class UnsignedKt {
    public static final double uintToDouble(int i) {
        return (Integer.MAX_VALUE & i) + (((i >>> 31) << 30) * 2);
    }

    private static final long uintToLong(int i) {
        return i & 4294967295L;
    }

    public static final double ulongToDouble(long j) {
        return ((j >>> 11) * 2048) + (j & 2047);
    }

    /* renamed from: uintRemainder-J1ME1BU, reason: not valid java name */
    public static final int m487uintRemainderJ1ME1BU(int i, int i2) {
        return UInt.m230constructorimpl((int) ((i & 4294967295L) % (i2 & 4294967295L)));
    }

    /* renamed from: uintDivide-J1ME1BU, reason: not valid java name */
    public static final int m486uintDivideJ1ME1BU(int i, int i2) {
        return UInt.m230constructorimpl((int) ((i & 4294967295L) / (i2 & 4294967295L)));
    }

    /* renamed from: ulongDivide-eb3DHEI, reason: not valid java name */
    public static final long m488ulongDivideeb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) < 0 ? ULong.m309constructorimpl(0L) : ULong.m309constructorimpl(1L);
        }
        if (j >= 0) {
            return ULong.m309constructorimpl(j / j2);
        }
        long j3 = ((j >>> 1) / j2) << 1;
        return ULong.m309constructorimpl(j3 + (Long.compare(ULong.m309constructorimpl(j - (j3 * j2)) ^ Long.MIN_VALUE, ULong.m309constructorimpl(j2) ^ Long.MIN_VALUE) < 0 ? 0 : 1));
    }

    /* renamed from: ulongRemainder-eb3DHEI, reason: not valid java name */
    public static final long m489ulongRemaindereb3DHEI(long j, long j2) {
        if (j2 < 0) {
            return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) < 0 ? j : ULong.m309constructorimpl(j - j2);
        }
        if (j >= 0) {
            return ULong.m309constructorimpl(j % j2);
        }
        long j3 = j - ((((j >>> 1) / j2) << 1) * j2);
        if (Long.compare(ULong.m309constructorimpl(j3) ^ Long.MIN_VALUE, ULong.m309constructorimpl(j2) ^ Long.MIN_VALUE) < 0) {
            j2 = 0;
        }
        return ULong.m309constructorimpl(j3 - j2);
    }

    public static final int uintCompare(int i, int i2) {
        return Intrinsics.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE);
    }

    public static final int ulongCompare(long j, long j2) {
        return Intrinsics.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE);
    }

    private static final long uintToULong(int i) {
        return ULong.m309constructorimpl(i & 4294967295L);
    }

    private static final float uintToFloat(int i) {
        return (float) uintToDouble(i);
    }

    private static final int floatToUInt(float f) {
        return doubleToUInt(f);
    }

    public static final int doubleToUInt(double d) {
        if (Double.isNaN(d) || d <= uintToDouble(0)) {
            return 0;
        }
        if (d >= uintToDouble(-1)) {
            return -1;
        }
        if (d <= 2.147483647E9d) {
            return UInt.m230constructorimpl((int) d);
        }
        return UInt.m230constructorimpl(UInt.m230constructorimpl((int) (d - Integer.MAX_VALUE)) + UInt.m230constructorimpl(Integer.MAX_VALUE));
    }

    private static final float ulongToFloat(long j) {
        return (float) ulongToDouble(j);
    }

    private static final long floatToULong(float f) {
        return doubleToULong(f);
    }

    public static final long doubleToULong(double d) {
        if (Double.isNaN(d) || d <= ulongToDouble(0L)) {
            return 0L;
        }
        if (d >= ulongToDouble(-1L)) {
            return -1L;
        }
        if (d < 9.223372036854776E18d) {
            return ULong.m309constructorimpl((long) d);
        }
        return ULong.m309constructorimpl(ULong.m309constructorimpl((long) (d - 9.223372036854776E18d)) - Long.MIN_VALUE);
    }

    private static final String uintToString(int i) {
        return String.valueOf(i & 4294967295L);
    }

    private static final String uintToString(int i, int i2) {
        return ulongToString(i & 4294967295L, i2);
    }

    private static final String ulongToString(long j) {
        return ulongToString(j, 10);
    }

    public static final String ulongToString(long j, int i) {
        if (j >= 0) {
            String string = Long.toString(j, CharsKt.checkRadix(i));
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        long j2 = i;
        long j3 = ((j >>> 1) / j2) << 1;
        long j4 = j - (j3 * j2);
        if (j4 >= j2) {
            j4 -= j2;
            j3++;
        }
        StringBuilder sb = new StringBuilder();
        String string2 = Long.toString(j3, CharsKt.checkRadix(i));
        Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
        StringBuilder sbAppend = sb.append(string2);
        String string3 = Long.toString(j4, CharsKt.checkRadix(i));
        Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
        return sbAppend.append(string3).toString();
    }
}
