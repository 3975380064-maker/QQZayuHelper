package kotlinx.coroutines;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.ranges.RangesKt;
import kotlin.time.Duration;

/* compiled from: Delay.kt */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0011\u0010\u0005\u001a\u00020\u0006H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u0019\u0010\u0000\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a!\u0010\u0000\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0086@ø\u0001\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u000e\u0010\u000b\u001a\u0019\u0010\u000f\u001a\u00020\n*\u00020\rH\u0000ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0010\u0010\u0011\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u0012"}, d2 = {"delay", "Lkotlinx/coroutines/Delay;", "Lkotlin/coroutines/CoroutineContext;", "getDelay", "(Lkotlin/coroutines/CoroutineContext;)Lkotlinx/coroutines/Delay;", "awaitCancellation", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "timeMillis", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "duration", "Lkotlin/time/Duration;", "delay-VtjQ1oo", "toDelayMillis", "toDelayMillis-LRDsOJo", "(J)J", "kotlinx-coroutines-core"}, k = 2, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes.dex */
public final class DelayKt {

    /* compiled from: Delay.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.DelayKt", f = "Delay.kt", i = {}, l = {148}, m = "awaitCancellation", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.DelayKt$awaitCancellation$1, reason: invalid class name */
    static final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return DelayKt.awaitCancellation(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object awaitCancellation(kotlin.coroutines.Continuation<?> r4) throws java.lang.Throwable {
        /*
            boolean r0 = r4 instanceof kotlinx.coroutines.DelayKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r4
            kotlinx.coroutines.DelayKt$awaitCancellation$1 r0 = (kotlinx.coroutines.DelayKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r4 = r0.label
            int r4 = r4 - r2
            r0.label = r4
            goto L19
        L14:
            kotlinx.coroutines.DelayKt$awaitCancellation$1 r0 = new kotlinx.coroutines.DelayKt$awaitCancellation$1
            r0.<init>(r4)
        L19:
            java.lang.Object r4 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L32
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r0)
            throw r4
        L2e:
            kotlin.ResultKt.throwOnFailure(r4)
            goto L58
        L32:
            kotlin.ResultKt.throwOnFailure(r4)
            r0.label = r3
            kotlin.coroutines.Continuation r0 = (kotlin.coroutines.Continuation) r0
            kotlinx.coroutines.CancellableContinuationImpl r4 = new kotlinx.coroutines.CancellableContinuationImpl
            kotlin.coroutines.Continuation r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.intercepted(r0)
            r4.<init>(r2, r3)
            r4.initCancellability()
            r2 = r4
            kotlinx.coroutines.CancellableContinuation r2 = (kotlinx.coroutines.CancellableContinuation) r2
            java.lang.Object r4 = r4.getResult()
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            if (r4 != r2) goto L55
            kotlin.coroutines.jvm.internal.DebugProbesKt.probeCoroutineSuspended(r0)
        L55:
            if (r4 != r1) goto L58
            return r1
        L58:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DelayKt.awaitCancellation(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final Object delay(long j, Continuation<? super Unit> continuation) {
        if (j <= 0) {
            return Unit.INSTANCE;
        }
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        if (j < Long.MAX_VALUE) {
            getDelay(cancellableContinuationImpl2.getContext()).mo1689scheduleResumeAfterDelay(j, cancellableContinuationImpl2);
        }
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    /* renamed from: delay-VtjQ1oo, reason: not valid java name */
    public static final Object m1626delayVtjQ1oo(long j, Continuation<? super Unit> continuation) {
        Object objDelay = delay(m1627toDelayMillisLRDsOJo(j), continuation);
        return objDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDelay : Unit.INSTANCE;
    }

    public static final Delay getDelay(CoroutineContext coroutineContext) {
        CoroutineContext.Element element = coroutineContext.get(ContinuationInterceptor.Key);
        Delay delay = element instanceof Delay ? (Delay) element : null;
        return delay == null ? DefaultExecutorKt.getDefaultDelay() : delay;
    }

    /* renamed from: toDelayMillis-LRDsOJo, reason: not valid java name */
    public static final long m1627toDelayMillisLRDsOJo(long j) {
        if (Duration.m1460compareToLRDsOJo(j, Duration.Companion.m1564getZEROUwyO8pc()) > 0) {
            return RangesKt.coerceAtLeast(Duration.m1479getInWholeMillisecondsimpl(j), 1L);
        }
        return 0L;
    }
}
