package kotlin.ranges;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: ULongRange.kt */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001c2\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u00022\b\u0012\u0004\u0012\u00020\u00030\u0004:\u0001\u001cB\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0096\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u0013\u0010\u0014\u001a\u00020\u00102\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0010H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016R\u001d\u0010\b\u001a\u00020\u00038VX\u0097\u0004ø\u0001\u0000¢\u0006\f\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0006\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\r\u0010\fR\u0017\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u000e\u0010\f\u0082\u0002\u0004\n\u0002\b!¨\u0006\u001d"}, d2 = {"Lkotlin/ranges/ULongRange;", "Lkotlin/ranges/ULongProgression;", "Lkotlin/ranges/ClosedRange;", "Lkotlin/ULong;", "Lkotlin/ranges/OpenEndRange;", "start", "endInclusive", "(JJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "endExclusive", "getEndExclusive-s-VKNKU$annotations", "()V", "getEndExclusive-s-VKNKU", "()J", "getEndInclusive-s-VKNKU", "getStart-s-VKNKU", "contains", "", "value", "contains-VKZWuLQ", "(J)Z", "equals", "other", "", "hashCode", "", "isEmpty", "toString", "", "Companion", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class ULongRange extends ULongProgression implements ClosedRange<ULong>, OpenEndRange<ULong> {
    public static final Companion Companion = new Companion(null);
    private static final ULongRange EMPTY = new ULongRange(-1, 0, null);

    public /* synthetic */ ULongRange(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    @Deprecated(message = "Can throw an exception when it's impossible to represent the value with ULong type, for example, when the range includes MAX_VALUE. It's recommended to use 'endInclusive' property that doesn't throw.")
    /* renamed from: getEndExclusive-s-VKNKU$annotations, reason: not valid java name */
    public static /* synthetic */ void m1371getEndExclusivesVKNKU$annotations() {
    }

    @Override // kotlin.ranges.ClosedRange
    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return m1372containsVKZWuLQ(((ULong) comparable).m361unboximpl());
    }

    @Override // kotlin.ranges.OpenEndRange
    public /* bridge */ /* synthetic */ Comparable getEndExclusive() {
        return ULong.m303boximpl(m1373getEndExclusivesVKNKU());
    }

    @Override // kotlin.ranges.ClosedRange
    public /* bridge */ /* synthetic */ Comparable getEndInclusive() {
        return ULong.m303boximpl(m1374getEndInclusivesVKNKU());
    }

    @Override // kotlin.ranges.ClosedRange
    public /* bridge */ /* synthetic */ Comparable getStart() {
        return ULong.m303boximpl(m1375getStartsVKNKU());
    }

    private ULongRange(long j, long j2) {
        super(j, j2, 1L, null);
    }

    /* renamed from: getStart-s-VKNKU, reason: not valid java name */
    public long m1375getStartsVKNKU() {
        return m1367getFirstsVKNKU();
    }

    /* renamed from: getEndInclusive-s-VKNKU, reason: not valid java name */
    public long m1374getEndInclusivesVKNKU() {
        return m1368getLastsVKNKU();
    }

    /* renamed from: getEndExclusive-s-VKNKU, reason: not valid java name */
    public long m1373getEndExclusivesVKNKU() {
        if (m1368getLastsVKNKU() == -1) {
            throw new IllegalStateException("Cannot return the exclusive upper bound of a range that includes MAX_VALUE.".toString());
        }
        return ULong.m309constructorimpl(m1368getLastsVKNKU() + ULong.m309constructorimpl(1 & 4294967295L));
    }

    /* renamed from: contains-VKZWuLQ, reason: not valid java name */
    public boolean m1372containsVKZWuLQ(long j) {
        return Long.compare(m1367getFirstsVKNKU() ^ Long.MIN_VALUE, j ^ Long.MIN_VALUE) <= 0 && Long.compare(j ^ Long.MIN_VALUE, m1368getLastsVKNKU() ^ Long.MIN_VALUE) <= 0;
    }

    @Override // kotlin.ranges.ULongProgression, kotlin.ranges.ClosedRange
    public boolean isEmpty() {
        return Long.compare(m1367getFirstsVKNKU() ^ Long.MIN_VALUE, m1368getLastsVKNKU() ^ Long.MIN_VALUE) > 0;
    }

    @Override // kotlin.ranges.ULongProgression
    public boolean equals(Object obj) {
        if (!(obj instanceof ULongRange)) {
            return false;
        }
        if (isEmpty() && ((ULongRange) obj).isEmpty()) {
            return true;
        }
        ULongRange uLongRange = (ULongRange) obj;
        return m1367getFirstsVKNKU() == uLongRange.m1367getFirstsVKNKU() && m1368getLastsVKNKU() == uLongRange.m1368getLastsVKNKU();
    }

    @Override // kotlin.ranges.ULongProgression
    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((int) ULong.m309constructorimpl(m1367getFirstsVKNKU() ^ ULong.m309constructorimpl(m1367getFirstsVKNKU() >>> 32))) * 31) + ((int) ULong.m309constructorimpl(m1368getLastsVKNKU() ^ ULong.m309constructorimpl(m1368getLastsVKNKU() >>> 32)));
    }

    @Override // kotlin.ranges.ULongProgression
    public String toString() {
        return ((Object) ULong.m355toStringimpl(m1367getFirstsVKNKU())) + ".." + ((Object) ULong.m355toStringimpl(m1368getLastsVKNKU()));
    }

    /* compiled from: ULongRange.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lkotlin/ranges/ULongRange$Companion;", "", "()V", "EMPTY", "Lkotlin/ranges/ULongRange;", "getEMPTY", "()Lkotlin/ranges/ULongRange;", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ULongRange getEMPTY() {
            return ULongRange.EMPTY;
        }
    }
}
