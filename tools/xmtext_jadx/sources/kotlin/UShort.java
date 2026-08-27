package kotlin;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;
import kotlin.ranges.URangesKt;

/* compiled from: UShort.kt */
@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\n\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b!\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u000e\b\u0087@\u0018\u0000 v2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001vB\u0011\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b\n\u0010\u000bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u000f\u0010\u0010J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u0012\u0010\u0013J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\u0097\n¢\u0006\u0004\b\u0017\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b\u001a\u0010\u0005J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b\u001c\u0010\u0010J\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b\u001d\u0010\u0013J\u0018\u0010\u001b\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b\u001e\u0010\u001fJ\u0018\u0010\u001b\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b'\u0010\u0010J\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b(\u0010\u0013J\u0018\u0010&\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b)\u0010\u001fJ\u0018\u0010&\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b*\u0010\u0018J\u0010\u0010+\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b,\u0010-J\u0013\u0010.\u001a\u00020\u0000H\u0087\nø\u0001\u0000¢\u0006\u0004\b/\u0010\u0005J\u0013\u00100\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\b1\u0010\u0005J\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b3\u0010\u0010J\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\b4\u0010\u0013J\u0018\u00102\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\b5\u0010\u001fJ\u0018\u00102\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\b6\u0010\u0018J\u0018\u00107\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\u000eH\u0087\b¢\u0006\u0004\b8\u00109J\u0018\u00107\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\b¢\u0006\u0004\b:\u0010\u0013J\u0018\u00107\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\b¢\u0006\u0004\b;\u0010\u001fJ\u0018\u00107\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\b¢\u0006\u0004\b<\u0010\u000bJ\u0018\u0010=\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\b>\u0010\u000bJ\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\b@\u0010\u0010J\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bA\u0010\u0013J\u0018\u0010?\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bB\u0010\u001fJ\u0018\u0010?\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bC\u0010\u0018J\u0018\u0010D\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bF\u0010GJ\u0018\u0010H\u001a\u00020E2\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bI\u0010GJ\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\bK\u0010\u0010J\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bL\u0010\u0013J\u0018\u0010J\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bM\u0010\u001fJ\u0018\u0010J\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bN\u0010\u0018J\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u000eH\u0087\n¢\u0006\u0004\bP\u0010\u0010J\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0011H\u0087\n¢\u0006\u0004\bQ\u0010\u0013J\u0018\u0010O\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u0014H\u0087\n¢\u0006\u0004\bR\u0010\u001fJ\u0018\u0010O\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0000H\u0087\n¢\u0006\u0004\bS\u0010\u0018J\u0010\u0010T\u001a\u00020UH\u0087\b¢\u0006\u0004\bV\u0010WJ\u0010\u0010X\u001a\u00020YH\u0087\b¢\u0006\u0004\bZ\u0010[J\u0010\u0010\\\u001a\u00020]H\u0087\b¢\u0006\u0004\b^\u0010_J\u0010\u0010`\u001a\u00020\rH\u0087\b¢\u0006\u0004\ba\u0010-J\u0010\u0010b\u001a\u00020cH\u0087\b¢\u0006\u0004\bd\u0010eJ\u0010\u0010f\u001a\u00020\u0003H\u0087\b¢\u0006\u0004\bg\u0010\u0005J\u000f\u0010h\u001a\u00020iH\u0016¢\u0006\u0004\bj\u0010kJ\u0013\u0010l\u001a\u00020\u000eH\u0087\bø\u0001\u0000¢\u0006\u0004\bm\u0010WJ\u0013\u0010n\u001a\u00020\u0011H\u0087\bø\u0001\u0000¢\u0006\u0004\bo\u0010-J\u0013\u0010p\u001a\u00020\u0014H\u0087\bø\u0001\u0000¢\u0006\u0004\bq\u0010eJ\u0013\u0010r\u001a\u00020\u0000H\u0087\bø\u0001\u0000¢\u0006\u0004\bs\u0010\u0005J\u0018\u0010t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\u0087\f¢\u0006\u0004\bu\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0081\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007\u0088\u0001\u0002\u0092\u0001\u00020\u0003\u0082\u0002\u0004\n\u0002\b!¨\u0006w"}, d2 = {"Lkotlin/UShort;", "", "data", "", "constructor-impl", "(S)S", "getData$annotations", "()V", "and", "other", "and-xj2QHRw", "(SS)S", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(SB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(SI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(SJ)I", "compareTo-xj2QHRw", "(SS)I", "dec", "dec-Mh2AYeg", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(SJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(SLjava/lang/Object;)Z", "floorDiv", "floorDiv-7apg3OU", "floorDiv-WZ4Q5Ns", "floorDiv-VKZWuLQ", "floorDiv-xj2QHRw", "hashCode", "hashCode-impl", "(S)I", "inc", "inc-Mh2AYeg", "inv", "inv-Mh2AYeg", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "mod", "mod-7apg3OU", "(SB)B", "mod-WZ4Q5Ns", "mod-VKZWuLQ", "mod-xj2QHRw", "or", "or-xj2QHRw", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-xj2QHRw", "(SS)Lkotlin/ranges/UIntRange;", "rangeUntil", "rangeUntil-xj2QHRw", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(S)B", "toDouble", "", "toDouble-impl", "(S)D", "toFloat", "", "toFloat-impl", "(S)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(S)J", "toShort", "toShort-impl", "toString", "", "toString-impl", "(S)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-xj2QHRw", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
@JvmInline
/* loaded from: classes.dex */
public final class UShort implements Comparable<UShort> {
    public static final Companion Companion = new Companion(null);
    public static final short MAX_VALUE = -1;
    public static final short MIN_VALUE = 0;
    public static final int SIZE_BITS = 16;
    public static final int SIZE_BYTES = 2;
    private final short data;

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ UShort m410boximpl(short s) {
        return new UShort(s);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static short m416constructorimpl(short s) {
        return s;
    }

    /* renamed from: equals-impl, reason: not valid java name */
    public static boolean m422equalsimpl(short s, Object obj) {
        return (obj instanceof UShort) && s == ((UShort) obj).m466unboximpl();
    }

    /* renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m423equalsimpl0(short s, short s2) {
        return s == s2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl, reason: not valid java name */
    public static int m428hashCodeimpl(short s) {
        return Short.hashCode(s);
    }

    /* renamed from: toByte-impl, reason: not valid java name */
    private static final byte m454toByteimpl(short s) {
        return (byte) s;
    }

    /* renamed from: toInt-impl, reason: not valid java name */
    private static final int m457toIntimpl(short s) {
        return s & MAX_VALUE;
    }

    /* renamed from: toLong-impl, reason: not valid java name */
    private static final long m458toLongimpl(short s) {
        return s & 65535;
    }

    /* renamed from: toShort-impl, reason: not valid java name */
    private static final short m459toShortimpl(short s) {
        return s;
    }

    /* renamed from: toUShort-Mh2AYeg, reason: not valid java name */
    private static final short m464toUShortMh2AYeg(short s) {
        return s;
    }

    public boolean equals(Object obj) {
        return m422equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m428hashCodeimpl(this.data);
    }

    /* renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ short m466unboximpl() {
        return this.data;
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(UShort uShort) {
        return Intrinsics.compare(m466unboximpl() & MAX_VALUE, uShort.m466unboximpl() & MAX_VALUE);
    }

    private /* synthetic */ UShort(short s) {
        this.data = s;
    }

    /* compiled from: UShort.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0013\u0010\u0003\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u0013\u0010\u0006\u001a\u00020\u0004X\u0086Tø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b!¨\u0006\n"}, d2 = {"Lkotlin/UShort$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UShort;", "S", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: compareTo-7apg3OU, reason: not valid java name */
    private static final int m411compareTo7apg3OU(short s, byte b) {
        return Intrinsics.compare(s & MAX_VALUE, b & UByte.MAX_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private int m414compareToxj2QHRw(short s) {
        return Intrinsics.compare(m466unboximpl() & MAX_VALUE, s & MAX_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw, reason: not valid java name */
    private static int m415compareToxj2QHRw(short s, short s2) {
        return Intrinsics.compare(s & MAX_VALUE, s2 & MAX_VALUE);
    }

    /* renamed from: compareTo-WZ4Q5Ns, reason: not valid java name */
    private static final int m413compareToWZ4Q5Ns(short s, int i) {
        return Integer.compare(UInt.m230constructorimpl(s & MAX_VALUE) ^ Integer.MIN_VALUE, i ^ Integer.MIN_VALUE);
    }

    /* renamed from: compareTo-VKZWuLQ, reason: not valid java name */
    private static final int m412compareToVKZWuLQ(short s, long j) {
        return Long.compare(ULong.m309constructorimpl(s & 65535) ^ Long.MIN_VALUE, j ^ Long.MIN_VALUE);
    }

    /* renamed from: plus-7apg3OU, reason: not valid java name */
    private static final int m440plus7apg3OU(short s, byte b) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) + UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: plus-xj2QHRw, reason: not valid java name */
    private static final int m443plusxj2QHRw(short s, short s2) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) + UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: plus-WZ4Q5Ns, reason: not valid java name */
    private static final int m442plusWZ4Q5Ns(short s, int i) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) + i);
    }

    /* renamed from: plus-VKZWuLQ, reason: not valid java name */
    private static final long m441plusVKZWuLQ(short s, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(s & 65535) + j);
    }

    /* renamed from: minus-7apg3OU, reason: not valid java name */
    private static final int m431minus7apg3OU(short s, byte b) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) - UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: minus-xj2QHRw, reason: not valid java name */
    private static final int m434minusxj2QHRw(short s, short s2) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) - UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: minus-WZ4Q5Ns, reason: not valid java name */
    private static final int m433minusWZ4Q5Ns(short s, int i) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) - i);
    }

    /* renamed from: minus-VKZWuLQ, reason: not valid java name */
    private static final long m432minusVKZWuLQ(short s, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(s & 65535) - j);
    }

    /* renamed from: times-7apg3OU, reason: not valid java name */
    private static final int m450times7apg3OU(short s, byte b) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) * UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: times-xj2QHRw, reason: not valid java name */
    private static final int m453timesxj2QHRw(short s, short s2) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) * UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: times-WZ4Q5Ns, reason: not valid java name */
    private static final int m452timesWZ4Q5Ns(short s, int i) {
        return UInt.m230constructorimpl(UInt.m230constructorimpl(s & MAX_VALUE) * i);
    }

    /* renamed from: times-VKZWuLQ, reason: not valid java name */
    private static final long m451timesVKZWuLQ(short s, long j) {
        return ULong.m309constructorimpl(ULong.m309constructorimpl(s & 65535) * j);
    }

    /* renamed from: div-7apg3OU, reason: not valid java name */
    private static final int m418div7apg3OU(short s, byte b) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: div-xj2QHRw, reason: not valid java name */
    private static final int m421divxj2QHRw(short s, short s2) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: div-WZ4Q5Ns, reason: not valid java name */
    private static final int m420divWZ4Q5Ns(short s, int i) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), i);
    }

    /* renamed from: div-VKZWuLQ, reason: not valid java name */
    private static final long m419divVKZWuLQ(short s, long j) {
        return UByte$$ExternalSyntheticBackport0.m$1(ULong.m309constructorimpl(s & 65535), j);
    }

    /* renamed from: rem-7apg3OU, reason: not valid java name */
    private static final int m446rem7apg3OU(short s, byte b) {
        return UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: rem-xj2QHRw, reason: not valid java name */
    private static final int m449remxj2QHRw(short s, short s2) {
        return UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: rem-WZ4Q5Ns, reason: not valid java name */
    private static final int m448remWZ4Q5Ns(short s, int i) {
        return UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), i);
    }

    /* renamed from: rem-VKZWuLQ, reason: not valid java name */
    private static final long m447remVKZWuLQ(short s, long j) {
        return UByte$$ExternalSyntheticBackport0.m203m(ULong.m309constructorimpl(s & 65535), j);
    }

    /* renamed from: floorDiv-7apg3OU, reason: not valid java name */
    private static final int m424floorDiv7apg3OU(short s, byte b) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(b & UByte.MAX_VALUE));
    }

    /* renamed from: floorDiv-xj2QHRw, reason: not valid java name */
    private static final int m427floorDivxj2QHRw(short s, short s2) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: floorDiv-WZ4Q5Ns, reason: not valid java name */
    private static final int m426floorDivWZ4Q5Ns(short s, int i) {
        return UByte$$ExternalSyntheticBackport0.m(UInt.m230constructorimpl(s & MAX_VALUE), i);
    }

    /* renamed from: floorDiv-VKZWuLQ, reason: not valid java name */
    private static final long m425floorDivVKZWuLQ(short s, long j) {
        return UByte$$ExternalSyntheticBackport0.m$1(ULong.m309constructorimpl(s & 65535), j);
    }

    /* renamed from: mod-7apg3OU, reason: not valid java name */
    private static final byte m435mod7apg3OU(short s, byte b) {
        return UByte.m152constructorimpl((byte) UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(b & UByte.MAX_VALUE)));
    }

    /* renamed from: mod-xj2QHRw, reason: not valid java name */
    private static final short m438modxj2QHRw(short s, short s2) {
        return m416constructorimpl((short) UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE)));
    }

    /* renamed from: mod-WZ4Q5Ns, reason: not valid java name */
    private static final int m437modWZ4Q5Ns(short s, int i) {
        return UByte$$ExternalSyntheticBackport0.m$1(UInt.m230constructorimpl(s & MAX_VALUE), i);
    }

    /* renamed from: mod-VKZWuLQ, reason: not valid java name */
    private static final long m436modVKZWuLQ(short s, long j) {
        return UByte$$ExternalSyntheticBackport0.m203m(ULong.m309constructorimpl(s & 65535), j);
    }

    /* renamed from: inc-Mh2AYeg, reason: not valid java name */
    private static final short m429incMh2AYeg(short s) {
        return m416constructorimpl((short) (s + 1));
    }

    /* renamed from: dec-Mh2AYeg, reason: not valid java name */
    private static final short m417decMh2AYeg(short s) {
        return m416constructorimpl((short) (s - 1));
    }

    /* renamed from: rangeTo-xj2QHRw, reason: not valid java name */
    private static final UIntRange m444rangeToxj2QHRw(short s, short s2) {
        return new UIntRange(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE), null);
    }

    /* renamed from: rangeUntil-xj2QHRw, reason: not valid java name */
    private static final UIntRange m445rangeUntilxj2QHRw(short s, short s2) {
        return URangesKt.m1403untilJ1ME1BU(UInt.m230constructorimpl(s & MAX_VALUE), UInt.m230constructorimpl(s2 & MAX_VALUE));
    }

    /* renamed from: and-xj2QHRw, reason: not valid java name */
    private static final short m409andxj2QHRw(short s, short s2) {
        return m416constructorimpl((short) (s & s2));
    }

    /* renamed from: or-xj2QHRw, reason: not valid java name */
    private static final short m439orxj2QHRw(short s, short s2) {
        return m416constructorimpl((short) (s | s2));
    }

    /* renamed from: xor-xj2QHRw, reason: not valid java name */
    private static final short m465xorxj2QHRw(short s, short s2) {
        return m416constructorimpl((short) (s ^ s2));
    }

    /* renamed from: inv-Mh2AYeg, reason: not valid java name */
    private static final short m430invMh2AYeg(short s) {
        return m416constructorimpl((short) (~s));
    }

    /* renamed from: toUByte-w2LRezQ, reason: not valid java name */
    private static final byte m461toUBytew2LRezQ(short s) {
        return UByte.m152constructorimpl((byte) s);
    }

    /* renamed from: toUInt-pVg5ArA, reason: not valid java name */
    private static final int m462toUIntpVg5ArA(short s) {
        return UInt.m230constructorimpl(s & MAX_VALUE);
    }

    /* renamed from: toULong-s-VKNKU, reason: not valid java name */
    private static final long m463toULongsVKNKU(short s) {
        return ULong.m309constructorimpl(s & 65535);
    }

    /* renamed from: toFloat-impl, reason: not valid java name */
    private static final float m456toFloatimpl(short s) {
        return (float) UnsignedKt.uintToDouble(s & MAX_VALUE);
    }

    /* renamed from: toDouble-impl, reason: not valid java name */
    private static final double m455toDoubleimpl(short s) {
        return UnsignedKt.uintToDouble(s & MAX_VALUE);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m460toStringimpl(short s) {
        return String.valueOf(s & MAX_VALUE);
    }

    public String toString() {
        return m460toStringimpl(this.data);
    }
}
