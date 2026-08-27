package kotlin.internal;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.UByte$$ExternalSyntheticBackport0;
import kotlin.UInt;
import kotlin.ULong;

/* compiled from: UProgressionUtil.kt */
@Metadata(d1 = {"\u0000 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0001H\u0002¢\u0006\u0004\b\u0005\u0010\u0006\u001a'\u0010\u0000\u001a\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00072\u0006\u0010\u0004\u001a\u00020\u0007H\u0002¢\u0006\u0004\b\b\u0010\t\u001a'\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0001¢\u0006\u0004\b\u000f\u0010\u0006\u001a'\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u00072\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0010H\u0001¢\u0006\u0004\b\u0011\u0010\t¨\u0006\u0012"}, d2 = {"differenceModulo", "Lkotlin/UInt;", "a", "b", "c", "differenceModulo-WZ9TVnA", "(III)I", "Lkotlin/ULong;", "differenceModulo-sambcqE", "(JJJ)J", "getProgressionLastElement", "start", "end", "step", "", "getProgressionLastElement-Nkh28Cs", "", "getProgressionLastElement-7ftBX0g", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class UProgressionUtilKt {
    /* renamed from: differenceModulo-WZ9TVnA, reason: not valid java name */
    private static final int m1341differenceModuloWZ9TVnA(int i, int i2, int i3) {
        int iM$1 = UByte$$ExternalSyntheticBackport0.m$1(i, i3);
        int iM$12 = UByte$$ExternalSyntheticBackport0.m$1(i2, i3);
        int iCompare = Integer.compare(iM$1 ^ Integer.MIN_VALUE, iM$12 ^ Integer.MIN_VALUE);
        int iM230constructorimpl = UInt.m230constructorimpl(iM$1 - iM$12);
        return iCompare >= 0 ? iM230constructorimpl : UInt.m230constructorimpl(iM230constructorimpl + i3);
    }

    /* renamed from: differenceModulo-sambcqE, reason: not valid java name */
    private static final long m1342differenceModulosambcqE(long j, long j2, long j3) {
        long jM203m = UByte$$ExternalSyntheticBackport0.m203m(j, j3);
        long jM203m2 = UByte$$ExternalSyntheticBackport0.m203m(j2, j3);
        int iCompare = Long.compare(jM203m ^ Long.MIN_VALUE, jM203m2 ^ Long.MIN_VALUE);
        long jM309constructorimpl = ULong.m309constructorimpl(jM203m - jM203m2);
        return iCompare >= 0 ? jM309constructorimpl : ULong.m309constructorimpl(jM309constructorimpl + j3);
    }

    /* renamed from: getProgressionLastElement-Nkh28Cs, reason: not valid java name */
    public static final int m1344getProgressionLastElementNkh28Cs(int i, int i2, int i3) {
        if (i3 > 0) {
            if (Integer.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) < 0) {
                return UInt.m230constructorimpl(i2 - m1341differenceModuloWZ9TVnA(i2, i, UInt.m230constructorimpl(i3)));
            }
        } else if (i3 < 0) {
            if (Integer.compare(i ^ Integer.MIN_VALUE, i2 ^ Integer.MIN_VALUE) > 0) {
                return UInt.m230constructorimpl(i2 + m1341differenceModuloWZ9TVnA(i, i2, UInt.m230constructorimpl(-i3)));
            }
        } else {
            throw new IllegalArgumentException("Step is zero.");
        }
        return i2;
    }

    /* renamed from: getProgressionLastElement-7ftBX0g, reason: not valid java name */
    public static final long m1343getProgressionLastElement7ftBX0g(long j, long j2, long j3) {
        if (j3 > 0) {
            return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) >= 0 ? j2 : ULong.m309constructorimpl(j2 - m1342differenceModulosambcqE(j2, j, ULong.m309constructorimpl(j3)));
        }
        if (j3 < 0) {
            return Long.compare(j ^ Long.MIN_VALUE, j2 ^ Long.MIN_VALUE) <= 0 ? j2 : ULong.m309constructorimpl(j2 + m1342differenceModulosambcqE(j, j2, ULong.m309constructorimpl(-j3)));
        }
        throw new IllegalArgumentException("Step is zero.");
    }
}
