package kotlin.collections;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UArraySorting.kt */
@Metadata(d1 = {"\u00000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0010\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0006\u0010\u0007\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\t\u0010\n\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\f\u0010\r\u001a'\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u000f\u0010\u0010\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0013\u0010\u0014\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0015\u0010\u0016\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0017\u0010\u0018\u001a'\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001H\u0003¢\u0006\u0004\b\u0019\u0010\u001a\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001e\u0010\u0014\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b\u001f\u0010\u0016\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b \u0010\u0018\u001a'\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0002\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u0001H\u0001¢\u0006\u0004\b!\u0010\u001a¨\u0006\""}, d2 = {"partition", "", "array", "Lkotlin/UByteArray;", "left", "right", "partition-4UcCI2c", "([BII)I", "Lkotlin/UIntArray;", "partition-oBK06Vg", "([III)I", "Lkotlin/ULongArray;", "partition--nroSd4", "([JII)I", "Lkotlin/UShortArray;", "partition-Aa5vz7o", "([SII)I", "quickSort", "", "quickSort-4UcCI2c", "([BII)V", "quickSort-oBK06Vg", "([III)V", "quickSort--nroSd4", "([JII)V", "quickSort-Aa5vz7o", "([SII)V", "sortArray", "fromIndex", "toIndex", "sortArray-4UcCI2c", "sortArray-oBK06Vg", "sortArray--nroSd4", "sortArray-Aa5vz7o", "kotlin-stdlib"}, k = 2, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class UArraySortingKt {
    /* renamed from: partition-4UcCI2c, reason: not valid java name */
    private static final int m591partition4UcCI2c(byte[] bArr, int i, int i2) {
        int i3;
        byte bM211getw2LRezQ = UByteArray.m211getw2LRezQ(bArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM211getw2LRezQ = UByteArray.m211getw2LRezQ(bArr, i) & UByte.MAX_VALUE;
                i3 = bM211getw2LRezQ & UByte.MAX_VALUE;
                if (Intrinsics.compare(iM211getw2LRezQ, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UByteArray.m211getw2LRezQ(bArr, i2) & UByte.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                byte bM211getw2LRezQ2 = UByteArray.m211getw2LRezQ(bArr, i);
                UByteArray.m216setVurrAj0(bArr, i, UByteArray.m211getw2LRezQ(bArr, i2));
                UByteArray.m216setVurrAj0(bArr, i2, bM211getw2LRezQ2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-4UcCI2c, reason: not valid java name */
    private static final void m595quickSort4UcCI2c(byte[] bArr, int i, int i2) {
        int iM591partition4UcCI2c = m591partition4UcCI2c(bArr, i, i2);
        int i3 = iM591partition4UcCI2c - 1;
        if (i < i3) {
            m595quickSort4UcCI2c(bArr, i, i3);
        }
        if (iM591partition4UcCI2c < i2) {
            m595quickSort4UcCI2c(bArr, iM591partition4UcCI2c, i2);
        }
    }

    /* renamed from: partition-Aa5vz7o, reason: not valid java name */
    private static final int m592partitionAa5vz7o(short[] sArr, int i, int i2) {
        int i3;
        short sM474getMh2AYeg = UShortArray.m474getMh2AYeg(sArr, (i + i2) / 2);
        while (i <= i2) {
            while (true) {
                int iM474getMh2AYeg = UShortArray.m474getMh2AYeg(sArr, i) & UShort.MAX_VALUE;
                i3 = sM474getMh2AYeg & UShort.MAX_VALUE;
                if (Intrinsics.compare(iM474getMh2AYeg, i3) >= 0) {
                    break;
                }
                i++;
            }
            while (Intrinsics.compare(UShortArray.m474getMh2AYeg(sArr, i2) & UShort.MAX_VALUE, i3) > 0) {
                i2--;
            }
            if (i <= i2) {
                short sM474getMh2AYeg2 = UShortArray.m474getMh2AYeg(sArr, i);
                UShortArray.m479set01HTLdE(sArr, i, UShortArray.m474getMh2AYeg(sArr, i2));
                UShortArray.m479set01HTLdE(sArr, i2, sM474getMh2AYeg2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-Aa5vz7o, reason: not valid java name */
    private static final void m596quickSortAa5vz7o(short[] sArr, int i, int i2) {
        int iM592partitionAa5vz7o = m592partitionAa5vz7o(sArr, i, i2);
        int i3 = iM592partitionAa5vz7o - 1;
        if (i < i3) {
            m596quickSortAa5vz7o(sArr, i, i3);
        }
        if (iM592partitionAa5vz7o < i2) {
            m596quickSortAa5vz7o(sArr, iM592partitionAa5vz7o, i2);
        }
    }

    /* renamed from: partition-oBK06Vg, reason: not valid java name */
    private static final int m593partitionoBK06Vg(int[] iArr, int i, int i2) {
        int iM290getpVg5ArA = UIntArray.m290getpVg5ArA(iArr, (i + i2) / 2);
        while (i <= i2) {
            while (Integer.compare(UIntArray.m290getpVg5ArA(iArr, i) ^ Integer.MIN_VALUE, iM290getpVg5ArA ^ Integer.MIN_VALUE) < 0) {
                i++;
            }
            while (Integer.compare(UIntArray.m290getpVg5ArA(iArr, i2) ^ Integer.MIN_VALUE, iM290getpVg5ArA ^ Integer.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                int iM290getpVg5ArA2 = UIntArray.m290getpVg5ArA(iArr, i);
                UIntArray.m295setVXSXFK8(iArr, i, UIntArray.m290getpVg5ArA(iArr, i2));
                UIntArray.m295setVXSXFK8(iArr, i2, iM290getpVg5ArA2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort-oBK06Vg, reason: not valid java name */
    private static final void m597quickSortoBK06Vg(int[] iArr, int i, int i2) {
        int iM593partitionoBK06Vg = m593partitionoBK06Vg(iArr, i, i2);
        int i3 = iM593partitionoBK06Vg - 1;
        if (i < i3) {
            m597quickSortoBK06Vg(iArr, i, i3);
        }
        if (iM593partitionoBK06Vg < i2) {
            m597quickSortoBK06Vg(iArr, iM593partitionoBK06Vg, i2);
        }
    }

    /* renamed from: partition--nroSd4, reason: not valid java name */
    private static final int m590partitionnroSd4(long[] jArr, int i, int i2) {
        long jM369getsVKNKU = ULongArray.m369getsVKNKU(jArr, (i + i2) / 2);
        while (i <= i2) {
            while (Long.compare(ULongArray.m369getsVKNKU(jArr, i) ^ Long.MIN_VALUE, jM369getsVKNKU ^ Long.MIN_VALUE) < 0) {
                i++;
            }
            while (Long.compare(ULongArray.m369getsVKNKU(jArr, i2) ^ Long.MIN_VALUE, jM369getsVKNKU ^ Long.MIN_VALUE) > 0) {
                i2--;
            }
            if (i <= i2) {
                long jM369getsVKNKU2 = ULongArray.m369getsVKNKU(jArr, i);
                ULongArray.m374setk8EXiF4(jArr, i, ULongArray.m369getsVKNKU(jArr, i2));
                ULongArray.m374setk8EXiF4(jArr, i2, jM369getsVKNKU2);
                i++;
                i2--;
            }
        }
        return i;
    }

    /* renamed from: quickSort--nroSd4, reason: not valid java name */
    private static final void m594quickSortnroSd4(long[] jArr, int i, int i2) {
        int iM590partitionnroSd4 = m590partitionnroSd4(jArr, i, i2);
        int i3 = iM590partitionnroSd4 - 1;
        if (i < i3) {
            m594quickSortnroSd4(jArr, i, i3);
        }
        if (iM590partitionnroSd4 < i2) {
            m594quickSortnroSd4(jArr, iM590partitionnroSd4, i2);
        }
    }

    /* renamed from: sortArray-4UcCI2c, reason: not valid java name */
    public static final void m599sortArray4UcCI2c(byte[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m595quickSort4UcCI2c(array, i, i2 - 1);
    }

    /* renamed from: sortArray-Aa5vz7o, reason: not valid java name */
    public static final void m600sortArrayAa5vz7o(short[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m596quickSortAa5vz7o(array, i, i2 - 1);
    }

    /* renamed from: sortArray-oBK06Vg, reason: not valid java name */
    public static final void m601sortArrayoBK06Vg(int[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m597quickSortoBK06Vg(array, i, i2 - 1);
    }

    /* renamed from: sortArray--nroSd4, reason: not valid java name */
    public static final void m598sortArraynroSd4(long[] array, int i, int i2) {
        Intrinsics.checkNotNullParameter(array, "array");
        m594quickSortnroSd4(array, i, i2 - 1);
    }
}
