package kotlinx.coroutines.flow;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import kotlinx.coroutines.flow.internal.FlowExceptions_commonKt;

/* compiled from: Limit.kt */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aV\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u000323\b\u0004\u0010\u0004\u001a-\b\u0001\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0005H\u0080Hø\u0001\u0000¢\u0006\u0002\u0010\f\u001a$\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f\u001aH\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0005ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a+\u0010\u0012\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00132\u0006\u0010\b\u001a\u0002H\u0002H\u0082@ø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015\u001a$\u0010\u0016\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u000f\u001aH\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\"\u0010\u0004\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0005ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001ap\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u00190\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0019*\b\u0012\u0004\u0012\u0002H\u00020\u00032D\b\u0001\u0010\u001a\u001a>\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00190\u0013\u0012\u0013\u0012\u0011H\u0002¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u001b¢\u0006\u0002\b\u001cø\u0001\u0000¢\u0006\u0002\u0010\u001d\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, d2 = {"collectWhile", "", "T", "Lkotlinx/coroutines/flow/Flow;", "predicate", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "value", "Lkotlin/coroutines/Continuation;", "", "", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "drop", "count", "", "dropWhile", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/flow/Flow;", "emitAbort", "Lkotlinx/coroutines/flow/FlowCollector;", "emitAbort$FlowKt__LimitKt", "(Lkotlinx/coroutines/flow/FlowCollector;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "take", "takeWhile", "transformWhile", "R", "transform", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "(Lkotlinx/coroutines/flow/Flow;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/flow/Flow;", "kotlinx-coroutines-core"}, k = 5, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE, xs = "kotlinx/coroutines/flow/FlowKt")
/* loaded from: classes.dex */
final /* synthetic */ class FlowKt__LimitKt {

    /* compiled from: Limit.kt */
    @Metadata(k = 3, mv = {1, 6, 0}, xi = 176)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__LimitKt", f = "Limit.kt", i = {0}, l = {136}, m = "collectWhile", n = {"collector"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1, reason: invalid class name */
    static final class AnonymousClass1<T> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__LimitKt.collectWhile(null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object emitAbort$FlowKt__LimitKt(kotlinx.coroutines.flow.FlowCollector<? super T> r4, T r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) throws java.lang.Throwable {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1
            if (r0 == 0) goto L14
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$emitAbort$1
            r0.<init>(r6)
        L19:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 == r3) goto L2e
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2e:
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L44
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r4.emit(r5, r0)
            if (r5 != r1) goto L44
            return r1
        L44:
            kotlinx.coroutines.flow.internal.AbortFlowException r5 = new kotlinx.coroutines.flow.internal.AbortFlowException
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt.emitAbort$FlowKt__LimitKt(kotlinx.coroutines.flow.FlowCollector, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Add missing generic type declarations: [R] */
    /* compiled from: Limit.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;"}, k = 3, mv = {1, 6, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    @DebugMetadata(c = "kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1", f = "Limit.kt", i = {0}, l = {152}, m = "invokeSuspend", n = {"collector$iv"}, s = {"L$0"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$transformWhile$1, reason: invalid class name and case insensitive filesystem */
    static final class C01141<R> extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Flow<T> $this_transformWhile;
        final /* synthetic */ Function3<FlowCollector<? super R>, T, Continuation<? super Boolean>, Object> $transform;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        C01141(Flow<? extends T> flow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Boolean>, ? extends Object> function3, Continuation<? super C01141> continuation) {
            super(2, continuation);
            this.$this_transformWhile = flow;
            this.$transform = function3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            C01141 c01141 = new C01141(this.$this_transformWhile, this.$transform, continuation);
            c01141.L$0 = obj;
            return c01141;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(FlowCollector<? super R> flowCollector, Continuation<? super Unit> continuation) {
            return ((C01141) create(flowCollector, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                Flow<T> flow = this.$this_transformWhile;
                FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$12 = new FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1(this.$transform, flowCollector);
                try {
                    this.L$0 = flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$12;
                    this.label = 1;
                    if (flow.collect(flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$12, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } catch (AbortFlowException e) {
                    e = e;
                    flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 = flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$12;
                    FlowExceptions_commonKt.checkOwnership(e, flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1);
                    return Unit.INSTANCE;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1 = (FlowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1) this.L$0;
                try {
                    ResultKt.throwOnFailure(obj);
                } catch (AbortFlowException e2) {
                    e = e2;
                    FlowExceptions_commonKt.checkOwnership(e, flowKt__LimitKt$transformWhile$1$invokeSuspend$$inlined$collectWhile$1);
                    return Unit.INSTANCE;
                }
            }
            return Unit.INSTANCE;
        }
    }

    public static final <T, R> Flow<R> transformWhile(Flow<? extends T> flow, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Boolean>, ? extends Object> function3) {
        return FlowKt.flow(new C01141(flow, function3, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <T> java.lang.Object collectWhile(kotlinx.coroutines.flow.Flow<? extends T> r4, kotlin.jvm.functions.Function2<? super T, ? super kotlin.coroutines.Continuation<? super java.lang.Boolean>, ? extends java.lang.Object> r5, kotlin.coroutines.Continuation<? super kotlin.Unit> r6) throws java.lang.Throwable {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.flow.FlowKt__LimitKt.AnonymousClass1
            if (r0 == 0) goto L14
            r0 = r6
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1 r0 = (kotlinx.coroutines.flow.FlowKt__LimitKt.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L14
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L19
        L14:
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1 r0 = new kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$1
            r0.<init>(r6)
        L19:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L30
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1 r4 = (kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1) r4
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L2e
            goto L55
        L2e:
            r5 = move-exception
            goto L50
        L30:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L38:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1 r6 = new kotlinx.coroutines.flow.FlowKt__LimitKt$collectWhile$collector$1
            r6.<init>(r5)
            r5 = r6
            kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L4e
            r0.L$0 = r6     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L4e
            r0.label = r3     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L4e
            java.lang.Object r4 = r4.collect(r5, r0)     // Catch: kotlinx.coroutines.flow.internal.AbortFlowException -> L4e
            if (r4 != r1) goto L55
            return r1
        L4e:
            r5 = move-exception
            r4 = r6
        L50:
            kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
            kotlinx.coroutines.flow.internal.FlowExceptions_commonKt.checkOwnership(r5, r4)
        L55:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__LimitKt.collectWhile(kotlinx.coroutines.flow.Flow, kotlin.jvm.functions.Function2, kotlin.coroutines.Continuation):java.lang.Object");
    }

    private static final <T> Object collectWhile$$forInline(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super Unit> continuation) {
        FlowKt__LimitKt$collectWhile$collector$1 flowKt__LimitKt$collectWhile$collector$1 = new FlowKt__LimitKt$collectWhile$collector$1(function2);
        try {
            flow.collect(flowKt__LimitKt$collectWhile$collector$1, continuation);
        } catch (AbortFlowException e) {
            FlowExceptions_commonKt.checkOwnership(e, flowKt__LimitKt$collectWhile$collector$1);
        }
        return Unit.INSTANCE;
    }

    public static final <T> Flow<T> drop(final Flow<? extends T> flow, final int i) {
        if (i < 0) {
            throw new IllegalArgumentException(("Drop count should be non-negative, but had " + i).toString());
        }
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
                Object objCollect = flow.collect(new FlowKt__LimitKt$drop$2$1(new Ref.IntRef(), i, flowCollector), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public static final <T> Flow<T> dropWhile(final Flow<? extends T> flow, final Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        return new Flow<T>() { // from class: kotlinx.coroutines.flow.FlowKt__LimitKt$dropWhile$$inlined$unsafeFlow$1
            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
                Object objCollect = flow.collect(new FlowKt__LimitKt$dropWhile$1$1(new Ref.BooleanRef(), flowCollector, function2), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public static final <T> Flow<T> take(Flow<? extends T> flow, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(("Requested element count " + i + " should be positive").toString());
        }
        return new FlowKt__LimitKt$take$$inlined$unsafeFlow$1(flow, i);
    }

    public static final <T> Flow<T> takeWhile(Flow<? extends T> flow, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        return new FlowKt__LimitKt$takeWhile$$inlined$unsafeFlow$1(flow, function2);
    }
}
