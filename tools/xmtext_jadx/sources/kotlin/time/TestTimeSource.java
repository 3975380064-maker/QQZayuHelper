package kotlin.time;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;

/* compiled from: TimeSources.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002¢\u0006\u0004\b\t\u0010\nJ\u0018\u0010\u000b\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086\u0002¢\u0006\u0004\b\f\u0010\nJ\b\u0010\r\u001a\u00020\u0004H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlin/time/TestTimeSource;", "Lkotlin/time/AbstractLongTimeSource;", "()V", "reading", "", "overflow", "", "duration", "Lkotlin/time/Duration;", "overflow-LRDsOJo", "(J)V", "plusAssign", "plusAssign-LRDsOJo", "read", "kotlin-stdlib"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class TestTimeSource extends AbstractLongTimeSource {
    private long reading;

    public TestTimeSource() {
        super(DurationUnit.NANOSECONDS);
        markNow();
    }

    @Override // kotlin.time.AbstractLongTimeSource
    protected long read() {
        return this.reading;
    }

    /* renamed from: plusAssign-LRDsOJo, reason: not valid java name */
    public final void m1597plusAssignLRDsOJo(long j) {
        long jM1507toLongimpl = Duration.m1507toLongimpl(j, getUnit());
        if (((jM1507toLongimpl - 1) | 1) != Long.MAX_VALUE) {
            long j2 = this.reading;
            long j3 = j2 + jM1507toLongimpl;
            if ((jM1507toLongimpl ^ j2) >= 0 && (j2 ^ j3) < 0) {
                m1596overflowLRDsOJo(j);
            }
            this.reading = j3;
            return;
        }
        long jM1464divUwyO8pc = Duration.m1464divUwyO8pc(j, 2);
        if ((1 | (Duration.m1507toLongimpl(jM1464divUwyO8pc, getUnit()) - 1)) != Long.MAX_VALUE) {
            long j4 = this.reading;
            try {
                m1597plusAssignLRDsOJo(jM1464divUwyO8pc);
                m1597plusAssignLRDsOJo(Duration.m1496minusLRDsOJo(j, jM1464divUwyO8pc));
                return;
            } catch (IllegalStateException e) {
                this.reading = j4;
                throw e;
            }
        }
        m1596overflowLRDsOJo(j);
    }

    /* renamed from: overflow-LRDsOJo, reason: not valid java name */
    private final void m1596overflowLRDsOJo(long j) {
        throw new IllegalStateException("TestTimeSource will overflow if its reading " + this.reading + DurationUnitKt.shortName(getUnit()) + " is advanced by " + ((Object) Duration.m1510toStringimpl(j)) + '.');
    }
}
