package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;

/* compiled from: UInt.kt */
@Metadata(d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 {2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001{B\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u000e\u0010\u000fJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u0010\u0010\u000bJ\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\f\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u0015\u0010\u0016J\u0013\u0010\u0017\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u0018\u0010\u0005J\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b\u001a\u0010\u000fJ\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b\u001b\u0010\u000bJ\u0018\u0010\u0019\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u001c\u0010\u001dJ\u0018\u0010\u0019\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u001e\u0010\u0016J\u001a\u0010\u001f\u001a\u00020 2\b\u0010\t\u001a\u0004\u0018\u00010!HÖ\u0003¢\u0006\u0004\b\"\u0010#J\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\b¢\u0006\u0004\b%\u0010\u000fJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b&\u0010\u000bJ\u0018\u0010$\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b'\u0010\u001dJ\u0018\u0010$\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b(\u0010\u0016J\u0010\u0010)\u001a\u00020\u0003HÖ\u0001¢\u0006\u0004\b*\u0010\u0005J\u0013\u0010+\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b,\u0010\u0005J\u0013\u0010-\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b.\u0010\u0005J\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b0\u0010\u000fJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b1\u0010\u000bJ\u0018\u0010/\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b2\u0010\u001dJ\u0018\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b3\u0010\u0016J\u0018\u00104\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\rH\u0087\b¢\u0006\u0004\b5\u00106J\u0018\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b7\u0010\u000bJ\u0018\u00104\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b8\u0010\u001dJ\u0018\u00104\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b9\u0010:J\u0018\u0010;\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b<\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\b>\u0010\u000fJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b?\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b@\u0010\u001dJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bA\u0010\u0016J\u0018\u0010B\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bD\u0010EJ\u0018\u0010F\u001a\u00020C2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bG\u0010EJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\bI\u0010\u000fJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bJ\u0010\u000bJ\u0018\u0010H\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bK\u0010\u001dJ\u0018\u0010H\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bL\u0010\u0016J\u001b\u0010M\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\u0087\fø\u0001\u0000¢\u0006\u0004\bO\u0010\u000bJ\u001b\u0010P\u001a\u00020\u00002\u0006\u0010N\u001a\u00020\u0003H\u0087\fø\u0001\u0000¢\u0006\u0004\bQ\u0010\u000bJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\rH\u0087\n¢\u0006\u0004\bS\u0010\u000fJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bT\u0010\u000bJ\u0018\u0010R\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bU\u0010\u001dJ\u0018\u0010R\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bV\u0010\u0016J\u0010\u0010W\u001a\u00020XH\u0087\b¢\u0006\u0004\bY\u0010ZJ\u0010\u0010[\u001a\u00020\\H\u0087\b¢\u0006\u0004\b]\u0010^J\u0010\u0010_\u001a\u00020`H\u0087\b¢\u0006\u0004\ba\u0010bJ\u0010\u0010c\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bd\u0010\u0005J\u0010\u0010e\u001a\u00020fH\u0087\b¢\u0006\u0004\bg\u0010hJ\u0010\u0010i\u001a\u00020jH\u0087\b¢\u0006\u0004\bk\u0010lJ\u000f\u0010m\u001a\u00020nH\u0016¢\u0006\u0004\bo\u0010pJ\u0013\u0010q\u001a\u00020\rH\u0087\bø\u0001\u0000¢\u0006\u0004\br\u0010ZJ\u0013\u0010s\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bt\u0010\u0005J\u0013\u0010u\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bv\u0010hJ\u0013\u0010w\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\bx\u0010lJ\u0018\u0010y\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bz\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u0004\n\u0002\b!¨\u0006|"}, d2 = {"Lkotlin/UInt;", "", "data", "", "constructor-impl", "(I)I", "getData$annotations", "()V", "and", "other", "and-WZ4Q5Ns", "(II)I", "compareTo", "Lkotlin/UByte;", "compareTo-7apg3OU", "(IB)I", "compareTo-WZ4Q5Ns", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(IJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(IS)I", "dec", "dec-pVg5ArA", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(IJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(ILjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "inc", "inc-pVg5ArA", "inv", "inv-pVg5ArA", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(IB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "(IS)S", "or", "or-WZ4Q5Ns", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-WZ4Q5Ns", "(II)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-WZ4Q5Ns", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-pVg5ArA", "shr", "shr-pVg5ArA", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(I)B", "toDouble", "", "toDouble-impl", "(I)D", "toFloat", "", "toFloat-impl", "(I)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(I)J", "toShort", "", "toShort-impl", "(I)S", "toString", "", "toString-impl", "(I)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-WZ4Q5Ns", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@JvmInline
/* loaded from: classes.dex */
public final class UInt implements Comparable<UInt> {
    public static final Companion Companion = new Companion(null);
    public static final int MAX_VALUE = -1;
    public static final int MIN_VALUE = 0;
    public static final int SIZE_BITS = 32;
    public static final int SIZE_BYTES = 4;
    private final int data;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UInt m224boximpl(int i) {
        return new UInt(i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static int m230constructorimpl(int i) {
        return i;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m236equalsimpl(int i, Object obj) {
        return (obj instanceof UInt) && i == ((UInt) obj).m282unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m237equalsimpl0(int i, int i2) {
        return i == i2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m242hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m270toByteimpl(int i) {
        return (byte) i;
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m273toIntimpl(int i) {
        return i;
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m274toLongimpl(int i) {
        return i & 4294967295L;
    }

    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m275toShortimpl(int i) {
        return (short) i;
    }

    /* renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m278toUIntpVg5ArA(int i) {
        return i;
    }

    public boolean equals(Object obj) {
        return m236equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m242hashCodeimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m282unboximpl() {
        return this.data;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UInt uInt) {
        return UnsignedKt.uintCompare(m282unboximpl(), uInt.m282unboximpl());
    }

    private /* synthetic */ UInt(int i) {
        this.data = i;
    }

    /* compiled from: UInt.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UInt$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UInt;", "I", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m225compareTo7apg3OU(int i, byte b) {
        return Integer.compare(i ^ Integer.MIN_VALUE, m230constructorimpl(b & UByte.MAX_VALUE) ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static final int m229compareToxj2QHRw(int i, short s) {
        return Integer.compare(i ^ Integer.MIN_VALUE, m230constructorimpl(s & UShort.MAX_VALUE) ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private int m227compareToWZ4Q5Ns(int i) {
        return UnsignedKt.uintCompare(m282unboximpl(), i);
    }

    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static int m228compareToWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2);
    }

    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m226compareToVKZWuLQ(int i, long j) {
        return Long.compare(ULong.m309constructorimpl(i & 4294967295L) ^ Long.MIN_VALUE, j ^ Long.MIN_VALUE);
    }

    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m254plus7apg3OU(int i, byte b) {
        return m230constructorimpl(i + m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m257plusxj2QHRw(int i, short s) {
        return m230constructorimpl(i + m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m256plusWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i + i2);
    }

    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m255plusVKZWuLQ(int i, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(i & 4294967295L) + j);
    }

    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m245minus7apg3OU(int i, byte b) {
        return m230constructorimpl(i - m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m248minusxj2QHRw(int i, short s) {
        return m230constructorimpl(i - m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m247minusWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i - i2);
    }

    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m246minusVKZWuLQ(int i, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(i & 4294967295L) - j);
    }

    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m266times7apg3OU(int i, byte b) {
        return m230constructorimpl(i * m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m269timesxj2QHRw(int i, short s) {
        return m230constructorimpl(i * m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m268timesWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i * i2);
    }

    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m267timesVKZWuLQ(int i, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(i & 4294967295L) * j);
    }

    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m232div7apg3OU(int i, byte b) {
        return UByte$$ExternalSyntheticBackport0.m(i, m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m235divxj2QHRw(int i, short s) {
        return UByte$$ExternalSyntheticBackport0.m(i, m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m234divWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m486uintDivideJ1ME1BU(i, i2);
    }

    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m233divVKZWuLQ(int i, long j) {
        return UByte$$ExternalSyntheticBackport0.m$1(ULong.m309constructorimpl(i & 4294967295L), j);
    }

    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m260rem7apg3OU(int i, byte b) {
        return UByte$$ExternalSyntheticBackport0.m$1(i, m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m263remxj2QHRw(int i, short s) {
        return UByte$$ExternalSyntheticBackport0.m$1(i, m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m262remWZ4Q5Ns(int i, int i2) {
        return UnsignedKt.m487uintRemainderJ1ME1BU(i, i2);
    }

    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m261remVKZWuLQ(int i, long j) {
        return UByte$$ExternalSyntheticBackport0.m203m(ULong.m309constructorimpl(i & 4294967295L), j);
    }

    /* renamed from: floorDiv-7apg3OU, reason: not valid java name */
    private static final int m238floorDiv7apg3OU(int i, byte b) {
        return UByte$$ExternalSyntheticBackport0.m(i, m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: floorDiv-xj2QHRw, reason: not valid java name */
    private static final int m241floorDivxj2QHRw(int i, short s) {
        return UByte$$ExternalSyntheticBackport0.m(i, m230constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: floorDiv-WZ4Q5Ns, reason: not valid java name */
    private static final int m240floorDivWZ4Q5Ns(int i, int i2) {
        return UByte$$ExternalSyntheticBackport0.m(i, i2);
    }

    /* renamed from: floorDiv-VKZWuLQ, reason: not valid java name */
    private static final long m239floorDivVKZWuLQ(int i, long j) {
        return UByte$$ExternalSyntheticBackport0.m$1(ULong.m309constructorimpl(i & 4294967295L), j);
    }

    /* renamed from: mod-7apg3OU, reason: not valid java name */
    private static final byte m249mod7apg3OU(int i, byte b) {
        return UByte.m152constructorimpl((byte) UByte$$ExternalSyntheticBackport0.m$1(i, m230constructorimpl(b & UByte.MAX_VALUE)));
    }

    /* renamed from: mod-xj2QHRw, reason: not valid java name */
    private static final short m252modxj2QHRw(int i, short s) {
        return UShort.m416constructorimpl((short) UByte$$ExternalSyntheticBackport0.m$1(i, m230constructorimpl(s & UShort.MAX_VALUE)));
    }

    /* renamed from: mod-WZ4Q5Ns, reason: not valid java name */
    private static final int m251modWZ4Q5Ns(int i, int i2) {
        return UByte$$ExternalSyntheticBackport0.m$1(i, i2);
    }

    /* renamed from: mod-VKZWuLQ, reason: not valid java name */
    private static final long m250modVKZWuLQ(int i, long j) {
        return UByte$$ExternalSyntheticBackport0.m203m(ULong.m309constructorimpl(i & 4294967295L), j);
    }

    /* renamed from: inc-pVg5ArA, reason: not valid java name */
    private static final int m243incpVg5ArA(int i) {
        return m230constructorimpl(i + 1);
    }

    /* renamed from: dec-pVg5ArA, reason: not valid java name */
    private static final int m231decpVg5ArA(int i) {
        return m230constructorimpl(i - 1);
    }

    /* renamed from: rangeTo-WZ4Q5Ns, reason: not valid java name */
    private static final UIntRange m258rangeToWZ4Q5Ns(int i, int i2) {
        return new UIntRange(i, i2, null);
    }

    /* renamed from: rangeUntil-WZ4Q5Ns, reason: not valid java name */
    private static final UIntRange m259rangeUntilWZ4Q5Ns(int i, int i2) {
        return URangesKt.m1403untilJ1ME1BU(i, i2);
    }

    /* renamed from: shl-pVg5ArA, reason: not valid java name */
    private static final int m264shlpVg5ArA(int i, int i2) {
        return m230constructorimpl(i << i2);
    }

    /* renamed from: shr-pVg5ArA, reason: not valid java name */
    private static final int m265shrpVg5ArA(int i, int i2) {
        return m230constructorimpl(i >>> i2);
    }

    /* renamed from: and-WZ4Q5Ns, reason: not valid java name */
    private static final int m223andWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i & i2);
    }

    /* renamed from: or-WZ4Q5Ns, reason: not valid java name */
    private static final int m253orWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i | i2);
    }

    /* renamed from: xor-WZ4Q5Ns, reason: not valid java name */
    private static final int m281xorWZ4Q5Ns(int i, int i2) {
        return m230constructorimpl(i ^ i2);
    }

    /* renamed from: inv-pVg5ArA, reason: not valid java name */
    private static final int m244invpVg5ArA(int i) {
        return m230constructorimpl(~i);
    }

    /* renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m277toUBytew2LRezQ(int i) {
        return UByte.m152constructorimpl((byte) i);
    }

    /* renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m280toUShortMh2AYeg(int i) {
        return UShort.m416constructorimpl((short) i);
    }

    /* renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m279toULongsVKNKU(int i) {
        return ULong.m309constructorimpl(i & 4294967295L);
    }

    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m272toFloatimpl(int i) {
        return (float) UnsignedKt.uintToDouble(i);
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m271toDoubleimpl(int i) {
        return UnsignedKt.uintToDouble(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m276toStringimpl(int i) {
        return String.valueOf(i & 4294967295L);
    }

    public String toString() {
        return m276toStringimpl(this.data);
    }
}
